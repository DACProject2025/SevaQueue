package com.sevaqueue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Office;
import com.sevaqueue.entity.Service;
import com.sevaqueue.repository.OfficeRepository;
import com.sevaqueue.repository.ServiceRepository;



@org.springframework.stereotype.Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private OfficeRepository officeRepo;
	
	@Override
	public Service createService(Long officeId, Service service) {
		
		Office office = officeRepo.findById(officeId)
				.orElseThrow(() -> new RuntimeException("Office not found!"));
		
		service.setOffice(office);
		return serviceRepo.save(service);
	}

}
