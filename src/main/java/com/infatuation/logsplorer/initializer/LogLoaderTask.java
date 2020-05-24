package com.infatuation.logsplorer.initializer;

import com.infatuation.logsplorer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;

@Component
public class LogLoaderTask implements Runnable {
	@Autowired
	private LogService logService;

	final String LOGGERATOR_URL = "127.0.0.1";
	final int PORT = 8080;

	public void run() {

		boolean loading = true;

		while(loading){
			try {
				Thread.sleep(1000);
				// Connect to the server
				Socket socket = new Socket(LOGGERATOR_URL, PORT);
				System.out.println(String.format("Loading from - %s:%s", LOGGERATOR_URL, PORT));
				long start = System.currentTimeMillis();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// Read data from the server until we finish reading the document
				int logCount = 0;
				String line = in.readLine();
				while (line != null) {
					logCount++;
					logService.persistLine(line);
					line = in.readLine();
				}

				// Close our streams
				in.close();
				socket.close();
				long end = System.currentTimeMillis();
				System.out.println(String.format("================ Uploaded: %d logs into database in %d msec.", logCount, end-start));
				loading = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ConnectException e) {
				System.out.println(String.format("Waiting for connection from %s:%s", LOGGERATOR_URL, PORT));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
