package com.sevaqueue.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.ServiceRequestDto;
import com.sevaqueue.dto.ServiceResponseDto;
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

	private ModelMapper modelMapper;

	public OfficeServiceServiceImpl() {

		this.modelMapper = new ModelMapper();
		System.out.println("ModelMapper instance: " + modelMapper);
		System.out.println("ModelMapper class: " + modelMapper.getClass());

	}

	@Override
	@Transactional
	public ServiceResponseDto createService(Long officeId, ServiceRequestDto service) {

		Office office = officeRepo.findById(officeId)
				.orElseThrow(() -> new ResourceNotFoundException("Office not found!"));
		OfficeService officeService = modelMapper.map(service, OfficeService.class);
		officeService.setOffice(office);
		return modelMapper.map(serviceRepo.save(officeService), ServiceResponseDto.class);

	}

	@Override
	public List<ServiceResponseDto> getServiceByOffice(Long officeId) {

		return serviceRepo.findByOfficeOfficeIdAndActiveTrue(officeId)
				.stream()
				.map(service -> modelMapper.map(service, ServiceResponseDto.class))
				.toList();

	}

	@Override
	public List<ServiceResponseDto> getAllServicesByOffice(Long officeId) {

		return serviceRepo.findByOfficeOfficeId(officeId)
				.stream()
				.map(service -> modelMapper.map(service, ServiceResponseDto.class))
				.toList();
	}

	@Override
	@Transactional
	public ApiResponseDto deactivateService(Long serviceId) {

		OfficeService service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found!"));

		service.setActive(false);
		serviceRepo.save(service);
		return new ApiResponseDto("Service deactivated successfully", true);

	}

	@Override
	@Transactional
	public ApiResponseDto toggleServiceStatus(Long serviceId) {
		OfficeService service = serviceRepo.findById(serviceId)
				.orElseThrow(() -> new ResourceNotFoundException("Service not found!"));

		service.setActive(!service.isActive());
		serviceRepo.save(service);

		String msg = service.isActive() ? "Service activated successfully" : "Service deactivated successfully";
		return new ApiResponseDto(msg, true);
	}

}
