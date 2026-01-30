package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.dto.OfficeRequestDto;
import com.sevaqueue.dto.OfficeResponseDto;

public interface OfficeService {

	OfficeResponseDto createOffice(OfficeRequestDto dto);
	
	List<OfficeResponseDto> getAllOffices();
	
	OfficeResponseDto getOfficeById(Long id);

	long getCounterCountByOffice(Long officeId);

	ApiResponseDto deactivateOffice(Long officeId);

	
}
