package com.sevaqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Office;
import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.exception.ResourceNotFoundException;
import com.sevaqueue.repository.OfficeRepository;
import com.sevaqueue.repository.OfficeServiceRepository;



@Service
public class OfficeServiceServiceImpl implements OfficeServiceService {

	@Autowired
	private OfficeServiceRepository serviceRepo;
	
	@Autowired
	private OfficeRepository officeRepo;
	
	@Override
	@Transactional
	public OfficeService createService(Long officeId, OfficeService service) {
		
		Office office = officeRepo.findById(officeId)
				.orElseThrow(() -> new ResourceNotFoundException("Office not found!"));
		
		service.setOffice(office);
		return serviceRepo.save(service);
		
	}

	@Override
	public List<OfficeService> getServiceByOffice(Long officeId) {
		
		return serviceRepo.findByOfficeOfficeId(officeId);
	}

}
