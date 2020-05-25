package com.infatuation.logsplorer.controller;

import com.infatuation.logsplorer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {
	@Autowired
	LogService logService;

	@GetMapping("/logs")
	public ResponseEntity <List<String>> getLogs(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String method,
			@RequestParam(required = false) String user){
		long start = System.currentTimeMillis();
		List<String> logs = logService.searchLog(code, method, user);
		long end = System.currentTimeMillis();
		System.out.println(String.format("================ Search %d logs in %d msec", logs.size(), end - start));
		return ResponseEntity.ok(logs);
	}
}
