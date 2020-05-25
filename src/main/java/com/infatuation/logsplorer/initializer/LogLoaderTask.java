package com.infatuation.logsplorer.initializer;

import com.infatuation.logsplorer.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

/**
 * A thread operation, trigger on application start up.
 * LogLoaderTask keep pinging Loggerator URL every second until it successfully connect.
 * Then terminate connection upon completion.
 */
@Component
public class LogLoaderTask implements Runnable {

	Logger logger = LoggerFactory.getLogger(LogLoaderTask.class);

	@Autowired
	private LogService logService;

	final String LOGGERATOR_URL = "127.0.0.1";
	final int PORT = 8080;

	public void run() {

		boolean loading = true;

		while(loading){
			try {
				Thread.sleep(1000);
				// Attempt to connect to the Loggerator server
				Socket socket = new Socket(LOGGERATOR_URL, PORT);
				logger.info(String.format("Loading from - %s:%s", LOGGERATOR_URL, PORT));
				long start = System.currentTimeMillis();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// Ingest data from the server and persist onto database until we finish reading the document
				int logCount = 0;
				String line = in.readLine();
				while (line != null) {
					logCount++;
					logService.persistLine(line);
					line = in.readLine();
				}

				// Close streams upon completion
				in.close();
				socket.close();
				long end = System.currentTimeMillis();
				logger.info(String.format("================ Uploaded: %d logs into database in %d msec.", logCount, end-start));
				loading = false;
			} catch (InterruptedException e) {
				logger.error("Connection interrupted.", e);
			} catch (ConnectException e) {
				logger.info(String.format("Waiting for connection from %s:%s", LOGGERATOR_URL, PORT));
			} catch (IOException e) {
				logger.error("Unable to read from socket.", e);
				e.printStackTrace();
			}
		}
	}
}
