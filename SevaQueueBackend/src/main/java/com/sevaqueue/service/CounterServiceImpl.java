package com.sevaqueue.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.CounterRequestDto;
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

		if (staff.getRole() != Role.STAFF) {
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
				.map(counter -> {
					CounterResponseDto dto = modelMapper.map(counter, CounterResponseDto.class);
					dto.setCounterId(counter.getId());
					dto.setServiceId(counter.getService().getServiceId());
					dto.setStaffId(counter.getStaff().getId());
					dto.setStaffName(counter.getStaff().getName());
					dto.setStatus(counter.getStatus().name());
					return dto;
				})
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

	@Override
	public CounterResponseDto createCounter(CounterRequestDto dto) {

		OfficeService service = serviceRepo.findById(dto.getServiceId())
				.orElseThrow(() -> new RuntimeException("Service not found"));

		User staff = userRepo.findById(dto.getStaffId())
				.orElseThrow(() -> new RuntimeException("Staff not found"));

		Counter counter = new Counter();
		counter.setCounterNumber(dto.getCounterNumber());
		counter.setService(service);
		counter.setStaff(staff);
		counter.setStatus(CounterStatus.OPEN);

		Counter savedCounter = counterRepo.save(counter);

		// ✅ ModelMapper for simple fields
		CounterResponseDto response = modelMapper.map(savedCounter, CounterResponseDto.class);

		// ✅ Manual mapping for nested IDs (IMPORTANT)
		response.setCounterId(savedCounter.getId());
		response.setServiceId(savedCounter.getService().getServiceId());
		response.setStaffId(savedCounter.getStaff().getId());
		response.setStatus(savedCounter.getStatus().name());

		return response;
	}

	@Override
	public List<CounterResponseDto> getCountersByStaff(Long staffId) {
		System.out.println("DEBUG: Fetching counters for staffId: " + staffId);
		List<Counter> counters = counterRepo.findByStaff_Id(staffId);
		System.out.println("DEBUG: Found counters count for this staff: " + counters.size());

		if (counters.isEmpty()) {
			System.out.println(
					"DEBUG: Checking ALL counters in system to see if staff " + staffId + " appears anywhere...");
			counterRepo.findAll().forEach(c -> {
				System.out.println("DEBUG: Counter #" + c.getCounterNumber() + " belongs to staffId: "
						+ (c.getStaff() != null ? c.getStaff().getId() : "NULL"));
			});
		}

		return counters.stream()
				.map(counter -> {
					CounterResponseDto dto = modelMapper.map(counter, CounterResponseDto.class);
					dto.setCounterId(counter.getId());
					dto.setServiceId(counter.getService().getServiceId());
					dto.setServiceName(counter.getService().getServiceName());
					dto.setOfficeId(counter.getService().getOffice().getOfficeId());
					dto.setOfficeName(counter.getService().getOffice().getOfficeName());
					dto.setStaffId(counter.getStaff().getId());
					dto.setStaffName(counter.getStaff().getName());
					dto.setStatus(counter.getStatus().name());
					return dto;
				})
				.toList();
	}

}
