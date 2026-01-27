package com.sevaqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevaqueue.entity.Office;
import com.sevaqueue.exception.ResourceNotFoundException;
import com.sevaqueue.repository.CounterRepository;
import com.sevaqueue.repository.OfficeRepository;

@Service
public class OfficeServiceImpl implements OfficeService {
	
	@Autowired
	private OfficeRepository officeRepo;
	
	@Autowired
	private CounterRepository counterRepo;

	@Override
	public Office createOffice(Office office) {
	
		return officeRepo.save(office);
	}

	@Override
	public List<Office> getAllOffices() {
		
		return officeRepo.findByActiveTrue();
		
	}

	@Override
	public Office getOfficeById(Long id) {
		
		return officeRepo.findById(id).orElseThrow(()-> new RuntimeException("office not found"));
	
	}

	@Override
	public long getCounterCountByoffice(Long officeId) {
	
		return counterRepo.countCountersByOfficeId(officeId);
	
	}

	@Override
	public Office deactivateOffice(Long officeId) {

		Office office = officeRepo.findById(officeId)
				.orElseThrow(() -> new ResourceNotFoundException("Office not found!"));
		
		office.setActive(false);
		return officeRepo.save(office);
		
	}
	
	

}
