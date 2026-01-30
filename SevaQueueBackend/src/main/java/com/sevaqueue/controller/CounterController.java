package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.CounterResponseDto;
import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.service.CounterService;

@RestController
@RequestMapping("/api/counter")
public class CounterController {

	@Autowired
	private CounterService counterService;
	
	@PostMapping
	public ResponseEntity<CounterResponseDto> assignCounter(
			@RequestParam Long serviceId,
			@RequestParam Long staffId,
			@RequestParam Integer counterNumber ) {
		
		return ResponseEntity.ok(counterService.assignCounter(serviceId, staffId, counterNumber));
		
	}
	
	@GetMapping("/service/{serviceId}")
	public ResponseEntity<List<CounterResponseDto>> getCounterByService(@PathVariable Long serviceId) {
		return ResponseEntity.ok(counterService.getCountersByService(serviceId));
	}
	
	@PutMapping("/{counterId}/status")
	public ResponseEntity<ApiResponseDto> updateStatus(
			@PathVariable Long counterId,
			@RequestParam CounterStatus status) {
		
		return ResponseEntity.ok(counterService.updateCounterStatus(counterId, status));
		
	}
	
	
}
