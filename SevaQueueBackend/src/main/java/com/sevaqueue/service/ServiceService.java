package com.sevaqueue.service;

import com.sevaqueue.entity.Service;

public interface ServiceService {

	Service createService(Long officeId, Service service);
	
}
