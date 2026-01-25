package com.sevaqueue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sevaqueue.entity.Office;
import com.sevaqueue.repository.CounterRepository;
import com.sevaqueue.repository.OfficeRepository;

@Service
public class OfficeServiceImpl implements OfficeService {
	
	private OfficeRepository officeRepo;
	
	private CounterRepository counterRepo;

	@Override
	public Office createOffice(Office office) {
	
		return officeRepo.save(office);
	}

	@Override
	public List<Office> getAllOffices() {
		// TODO Auto-generated method stub
		return officeRepo.findAll();
	}

	@Override
	public Office getOfficeById(Long id) {
		return officeRepo.findById(id).orElseThrow(()-> new RuntimeException("office not found"));
	}

	@Override
	public long getCounterCountByoffice(Long officeId) {
	
		return counterRepo.countCountersByOfficeId(officeId);
	}
	
	

}
