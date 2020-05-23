package com.infatuation.logsplorer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@RestController
public class LogController {

	@RequestMapping("/socket")
	public String initLog() throws IOException {
		String result = "Fetch Log Failed";
		String LOGGERATOR_URL = "127.0.0.1";
		int PORT = 8080;
		System.out.println("Loading contents of URL: " + LOGGERATOR_URL + PORT);

		try {
			// Connect to the server
			Socket socket = new Socket(LOGGERATOR_URL, PORT);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder builder = new StringBuilder();

			// Read data from the server until we finish reading the document
			String line = in.readLine();
			while (line != null) {
				builder.append(line);
				line = in.readLine();
			}
			result = builder.toString();

			// Close our streams
			in.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	@GetMapping
	public String getLogs(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String method,
			@RequestParam(required = false) String user){
		return "Hello";
	}
}
