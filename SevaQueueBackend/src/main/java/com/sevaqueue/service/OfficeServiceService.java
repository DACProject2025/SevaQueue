package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.ServiceRequestDto;
import com.sevaqueue.dto.ServiceResponseDto;

public interface OfficeServiceService {

	ServiceResponseDto createService(Long officeId, ServiceRequestDto service);

	List<ServiceResponseDto> getServiceByOffice(Long officeId);

	ApiResponseDto deactivateService(Long serviceId);
	
}
