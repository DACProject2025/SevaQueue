package com.sevaqueue.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.entity.Office;
import com.sevaqueue.service.OfficeService;


@RestController
@RequestMapping("/Office")
public class OfficeController {
  
		@Autowired
		private OfficeService officeService;
		
		
		@PostMapping("/create")
		public ResponseEntity<Office> createOffice(@RequestBody Office office) {
			return ResponseEntity.ok(officeService.createOffice(office));
		}
		
		@GetMapping("/getAll")
		public ResponseEntity<List<Office>> getAllOffices() {
			return ResponseEntity.ok(officeService.getAllOffices());
		}
		
		@GetMapping("/get/{id}")
		public ResponseEntity<Office> getOfficeById(@PathVariable Long id){
			return ResponseEntity.ok(officeService.getOfficeById(id));
		}
		
		@GetMapping("/getcount/{officeId}/counter-count")
		public ResponseEntity<Long> getCounterCount(@PathVariable Long officeId){
			return ResponseEntity.ok(officeService.getCounterCountByOffice(officeId));
		}
	
}
