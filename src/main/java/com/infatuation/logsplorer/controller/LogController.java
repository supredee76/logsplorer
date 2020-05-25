package com.infatuation.logsplorer.controller;

import com.infatuation.logsplorer.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {

	Logger logger = LoggerFactory.getLogger(LogController.class);

	@Autowired
	LogService logService;

	/**
	* Log searching API.
	* Accept optional query parameter.
	* @param code - Query for Status Code. eg, 200, 500
	* @param method - Query for HTTP Method. eg, Get, GET, PosT
	* @param user - Query for Auth User
	* @return - ResponsEntity with a list of logs result, in JSON format.
	*/
	@GetMapping("/logs")
	public ResponseEntity <List<String>> getLogs(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String method,
			@RequestParam(required = false) String user){
		long start = System.currentTimeMillis();
		List<String> logs = logService.searchLog(code, method, user);
		long end = System.currentTimeMillis();
		logger.info(String.format("================ Search %d logs in %d msec ", logs.size(), end - start));
		return ResponseEntity.ok(logs);
	}
}
