package com.sevaqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.entity.Service;
import com.sevaqueue.service.ServiceService;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
	
	@Autowired
	private ServiceService serviceService;
	
	@PostMapping
	public ResponseEntity<Service> createService(
			@RequestParam Long officeId,
			@RequestBody Service service ) {
		
		Service newService = serviceService.createService(officeId, service);
		
		return ResponseEntity.ok(newService);
		
	}
		
}
