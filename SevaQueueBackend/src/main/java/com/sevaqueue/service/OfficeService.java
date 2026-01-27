package com.sevaqueue.service;

import java.util.List;

import com.sevaqueue.entity.Office;

public interface OfficeService {

	Office createOffice(Office office);
	
	List<Office> getAllOffices();
	
	Office getOfficeById(Long id);
	
	long getCounterCountByoffice(Long officeId);

	Office deactivateOffice(Long officeId);
	
}
