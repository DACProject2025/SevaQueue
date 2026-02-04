package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.CounterRequestDto;
import com.sevaqueue.dto.CounterResponseDto;
import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.service.CounterService;

@RestController
@RequestMapping("/api/counter")
public class CounterController {

	@Autowired
	private CounterService counterService;

	@PostMapping("/assign")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CounterResponseDto> assignCounter(
			@RequestParam Long serviceId,
			@RequestParam Long staffId,
			@RequestParam Integer counterNumber) {

		return ResponseEntity.ok(counterService.assignCounter(serviceId, staffId, counterNumber));

	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public ResponseEntity<CounterResponseDto> createCounter(
			@RequestBody CounterRequestDto dto) {

		return ResponseEntity.ok(counterService.createCounter(dto));
	}

	@GetMapping("/service/{serviceId}")
	public ResponseEntity<List<CounterResponseDto>> getCounterByService(@PathVariable Long serviceId) {
		return ResponseEntity.ok(counterService.getCountersByService(serviceId));
	}

	@PutMapping("/{counterId}/status")
	@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
	public ResponseEntity<ApiResponseDto> updateStatus(
			@PathVariable Long counterId,
			@RequestParam CounterStatus status) {

		return ResponseEntity.ok(counterService.updateCounterStatus(counterId, status));

	}
	
	@GetMapping("/staff/{staffId}")
	@PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
	public ResponseEntity<List<CounterResponseDto>> getCountersByStaff(@PathVariable Long staffId) {
		return ResponseEntity.ok(counterService.getCountersByStaff(staffId)); 
	}

}
