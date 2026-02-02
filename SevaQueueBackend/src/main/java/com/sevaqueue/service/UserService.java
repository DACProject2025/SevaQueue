package com.sevaqueue.service;

import com.sevaqueue.dto.RegisterRequestDTO;
import com.sevaqueue.entity.User;

public interface UserService {

    User addStaff(RegisterRequestDTO dto);

}

