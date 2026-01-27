package com.sevaqueue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.entity.Role;
import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.entity.User;
import com.sevaqueue.exception.ResourceNotFoundException;
import com.sevaqueue.repository.CounterRepository;
import com.sevaqueue.repository.OfficeServiceRepository;
import com.sevaqueue.repository.UserRepository;


@Service
public class CounterServiceImpl implements CounterService {
	
	@Autowired
	private CounterRepository counterRepo;
	
	@Autowired
	private OfficeServiceRepository serviceRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional
	public Counter assignCounter(Long serviceId, Long staffId, Integer counterNumber) {

		OfficeService service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found!"));
		
		User staff = userRepo.findById(staffId)
				.orElseThrow(() -> new ResourceNotFoundException("Staff not found!"));
		
		if(staff.getRole() != Role.STAFF) {
			throw new ResourceNotFoundException("User is not staff!");
		}
		
		Counter counter = new Counter();
		counter.setService(service);
		counter.setStaff(staff);
		counter.setCounterNumber(counterNumber);
		
		return counterRepo.save(counter);
	}

	@Override
	public List<Counter> getCountersByService(Long serviceId) {
		
		return counterRepo.findByServiceServiceId(serviceId);
	}

	@Override
	@Transactional
	public Counter updateCounterStatus(Long counterId, CounterStatus status) {
		
		Counter counter = counterRepo.findById(counterId)
				.orElseThrow(() -> new ResourceNotFoundException("Counter not found!"));
		
		counter.setStatus(status);
		return counterRepo.save(counter);
	}

}
