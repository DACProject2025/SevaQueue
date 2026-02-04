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
import com.sevaqueue.dto.ServiceRequestDto;
import com.sevaqueue.dto.ServiceResponseDto;
import com.sevaqueue.service.OfficeServiceService;

@RestController
@RequestMapping("/api/services")
public class OfficeServiceController {
	
	@Autowired
	private OfficeServiceService serviceService;
	
	@GetMapping("/office/{officeId}")
	public ResponseEntity<List<ServiceResponseDto>> getServicesByOffice(@PathVariable Long officeId) {
		return ResponseEntity.ok(serviceService.getServiceByOffice(officeId));
	}

	@GetMapping("/office/{officeId}/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ServiceResponseDto>> getAllServicesByOffice(@PathVariable Long officeId) {
		return ResponseEntity.ok(serviceService.getAllServicesByOffice(officeId));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ServiceResponseDto> createService(
			@RequestParam Long officeId,
			@RequestBody ServiceRequestDto service ) {
		
		ServiceResponseDto newService = serviceService.createService(officeId, service);
		
		return ResponseEntity.ok(newService);
		
	}
	
	@PutMapping("/{serviceId}/deactivate")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> deactivateService(@PathVariable Long serviceId) {
		
		return ResponseEntity.ok(serviceService.deactivateService(serviceId));
		
	}

	@PutMapping("/{serviceId}/toggle-status")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseDto> toggleServiceStatus(@PathVariable Long serviceId) {
		return ResponseEntity.ok(serviceService.toggleServiceStatus(serviceId));
	}
		
}
