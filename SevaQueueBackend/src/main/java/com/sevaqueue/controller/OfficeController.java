package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.OfficeRequestDto;
import com.sevaqueue.dto.OfficeResponseDto;
import com.sevaqueue.service.OfficeService;


@RestController
@RequestMapping("/api/offices")
public class OfficeController {
  
		@Autowired
		private OfficeService officeService;
		
		
		@PostMapping
		public ResponseEntity<OfficeResponseDto> createOffice(@RequestBody OfficeRequestDto dto) {
			return ResponseEntity.ok(officeService.createOffice(dto));
		}
		
		@GetMapping
		public ResponseEntity<List<OfficeResponseDto>> getAllOffices() {
			return ResponseEntity.ok(officeService.getAllOffices());
		}
		
		@GetMapping("/{officeId}")
		public ResponseEntity<OfficeResponseDto> getOfficeById(@PathVariable Long officeId){
			return ResponseEntity.ok(officeService.getOfficeById(officeId));
		}
		
		@GetMapping("/{officeId}/counter-count")
		public ResponseEntity<Long> getCounterCount(@PathVariable Long officeId){
			return ResponseEntity.ok(officeService.getCounterCountByoffice(officeId));
		}
		
		@PutMapping("/{officeId}/deactivate")
		public ResponseEntity<ApiResponseDto> deactivateOffice(@PathVariable Long officeId) {
			
			return ResponseEntity.ok(officeService.deactivateOffice(officeId));
					
		}
	
}
