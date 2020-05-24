package com.infatuation.logsplorer.controller;

import com.infatuation.logsplorer.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogController {

	@Autowired
	LogService logService;

	@GetMapping("/logs")
	public List<String> getLogs(
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String method,
			@RequestParam(required = false) String user){
		long start = System.currentTimeMillis();
		List<String> ret = logService.searchLog(code, method, user);
		long end = System.currentTimeMillis();
		System.out.println(String.format("================ Search %d logs in %d msec", ret.size(), end - start));
//		print(ret);
		return ret;
	}

	private void print(List<String> list){

		for(String s : list){
			System.out.println(s);
		}
		System.out.println("Total: " + list.size());
	}
}
