package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.entity.OfficeService;

public interface OfficeServiceService {

	OfficeService createService(Long officeId, OfficeService service);

	List<OfficeService> getServiceByOffice(Long officeId);
	
}
