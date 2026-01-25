package com.sevaqueue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sevaqueue.service.AdminService;
import com.sevaqueue.service.AdminServiceImpl;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	

}
