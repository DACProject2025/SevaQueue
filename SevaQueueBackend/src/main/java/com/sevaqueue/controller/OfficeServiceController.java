package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.service.OfficeServiceService;

@RestController
@RequestMapping("/api/services")
public class OfficeServiceController {
	
	@Autowired
	private OfficeServiceService serviceService;
	
	@GetMapping("/office/{officeId}")
	public List<OfficeService> getServicesByOffice(@PathVariable Long officeId) {
		return serviceService.getServiceByOffice(officeId);
	}
	
	@PostMapping
	public ResponseEntity<OfficeService> createService(
			@RequestParam Long officeId,
			@RequestBody OfficeService service ) {
		
		OfficeService newService = serviceService.createService(officeId, service);
		
		return ResponseEntity.ok(newService);
		
	}
		
}
