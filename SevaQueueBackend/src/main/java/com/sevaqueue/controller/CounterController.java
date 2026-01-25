package com.sevaqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.service.CounterService;

@RestController
@RequestMapping("/api/counter")
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@PostMapping
	public ResponseEntity<Counter> assignCounter(
			@RequestParam Long serviceId,
			@RequestParam Long staffId,
			@RequestParam Integer counterNumber ) {
		
		Counter counter = counterService.assignCounter(serviceId, staffId, counterNumber);
		return ResponseEntity.ok(counter);
		
	}
	
}
