package com.sevaqueue.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.OfficeRequestDto;
import com.sevaqueue.dto.OfficeResponseDto;
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
	
	private ModelMapper modelMapper;
	
	public OfficeServiceImpl() {
		modelMapper = new ModelMapper();
	}

	@Override
	public OfficeResponseDto createOffice(OfficeRequestDto office) {
	
		return modelMapper.map(officeRepo.save(modelMapper.map(office, Office.class)), OfficeResponseDto.class);
	}

	@Override
	public List<OfficeResponseDto> getAllOffices() {
		
		return officeRepo.findByActiveTrue()
				.stream()
				.map(office -> modelMapper.map(office, OfficeResponseDto.class))
				.toList();
		
	}

	@Override
	public OfficeResponseDto getOfficeById(Long id) {
		
		Office office = officeRepo.findById(id).orElseThrow(()-> new RuntimeException("office not found"));
		return modelMapper.map(office, OfficeResponseDto.class);
	
	}

	@Override
	public long getCounterCountByOffice(Long officeId) {
	
		return counterRepo.countCountersByOfficeId(officeId);
	
	}

	@Override
	public ApiResponseDto deactivateOffice(Long officeId) {

		Office office = officeRepo.findById(officeId)
				.orElseThrow(() -> new ResourceNotFoundException("Office not found!"));
		
		office.setActive(false);
		officeRepo.save(office);
		return new ApiResponseDto("Counter status updated", true);
		
	}

}
