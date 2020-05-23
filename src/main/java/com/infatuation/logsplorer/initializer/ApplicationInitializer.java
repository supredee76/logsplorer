package com.infatuation.logsplorer.initializer;

import com.infatuation.logsplorer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
@Order(0)
public class ApplicationInitializer
		implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private LogService logService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("=============================  initializer ========================");
		String LOGGERATOR_URL = "127.0.0.1";
		int PORT = 8080;
		System.out.println(String.format("Loading contents of URL: %s:%s",LOGGERATOR_URL, PORT));

		try {
			// Connect to the server
			Socket socket = new Socket(LOGGERATOR_URL, PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// Read data from the server until we finish reading the document
			String line = in.readLine();
			while (line != null) {
				logService.persistLine(line);
				line = in.readLine();
			}

			// Close our streams
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}