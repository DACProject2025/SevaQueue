package com.sevaqueue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.Role;
import com.sevaqueue.entity.Service;
import com.sevaqueue.entity.User;
import com.sevaqueue.repository.CounterRepository;
import com.sevaqueue.repository.ServiceRepository;
import com.sevaqueue.repository.UserRepository;


@org.springframework.stereotype.Service
@Transactional
public class CounterServiceImpl implements CounterService {
	
	@Autowired
	private CounterRepository counterRepo;
	
	@Autowired
	private ServiceRepository serviceRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Counter assignCounter(Long serviceId, Long staffId, Integer counterNumber) {

		Service service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new RuntimeException("Service not found!"));
		
		User staff = userRepo.findById(staffId)
				.orElseThrow(() -> new RuntimeException("Staff not found!"));
		
		if(staff.getRole() != Role.STAFF) {
			throw new RuntimeException("User is not staff!");
		}
		
		Counter counter = new Counter();
		counter.setService(service);
		counter.setStaff(staff);
		counter.setCounterNumber(counterNumber);
		
		return counterRepo.save(counter);
	}

}
