package com.sevaqueue.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoggerClient {

	private final RestTemplate restTemplate = new RestTemplate();
	private static final String LOGGER_URL = "http://localhost:5090/api/logs";
	
	public void log(String level, String message) {
		
		Map<String, String> body = new HashMap<>();
		body.put("serviceName", "SevaQueue");
		body.put("level", level);
		body.put("message", message);
		
		try {
			restTemplate.postForObject(LOGGER_URL, body, String.class);
		} catch(Exception e) {
			// logger failure should NOT break main app
			System.out.println("Logger service unavailable");
		}
		
	}
	
}
