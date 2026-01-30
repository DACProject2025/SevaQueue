package com.sevaqueue.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.CounterResponseDto;
import com.sevaqueue.entity.Counter;
import com.sevaqueue.entity.CounterStatus;
import com.sevaqueue.entity.OfficeService;
import com.sevaqueue.entity.Role;
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
	
	private ModelMapper modelMapper;
	
	public CounterServiceImpl() {
		modelMapper = new ModelMapper();
	}

	@Override
	@Transactional
	public CounterResponseDto assignCounter(Long serviceId, Long staffId, Integer counterNumber) {

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
		
		return modelMapper.map(counterRepo.save(counter), CounterResponseDto.class);
	}

	@Override
	public List<CounterResponseDto> getCountersByService(Long serviceId) {
		
		return counterRepo.findByServiceServiceId(serviceId)
				.stream()
				.map(counter -> modelMapper.map(counter, CounterResponseDto.class))
				.toList();
	}

	@Override
	@Transactional
	public ApiResponseDto updateCounterStatus(Long counterId, CounterStatus status) {
		
		Counter counter = counterRepo.findById(counterId)
				.orElseThrow(() -> new ResourceNotFoundException("Counter not found!"));
		
		counter.setStatus(status);
		counterRepo.save(counter);
		return new ApiResponseDto("Counter status updated", true);
	}

}
