package com.sevaqueueauthservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sevaqueueauthservice.DTO.LoginRequestDTO;
import com.sevaqueueauthservice.DTO.ResetPasswordDTO;
import com.sevaqueueauthservice.DTO.UpdateUserDTO;
import com.sevaqueueauthservice.customexception.EmailAlreadyExistsException;
import com.sevaqueueauthservice.customexception.InvalidCredentialsException;
import com.sevaqueueauthservice.customexception.UserNotFoundException;
import com.sevaqueueauthservice.entity.Role;
import com.sevaqueueauthservice.entity.User;
import com.sevaqueueauthservice.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User register(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("Email Already registered");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		if(user.getRole() == null) {
			user.setRole(Role.CITIZEN);
		}
		
		return userRepository.save(user);
	}

	@Override
    public String login(LoginRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return "login successfully";
    }

	 @Override
	    public void delete(Long id) {
	        userRepository.deleteById(id);
	    }

	  @Override
	    public void resetPassword(ResetPasswordDTO dto) {

	        User user = userRepository.findByEmail(dto.getEmail())
	                .orElseThrow(() -> new UserNotFoundException("User not found"));

	        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
	        userRepository.save(user);
	    }
	  
	  @Override
	  public User update(Long id, UpdateUserDTO dto) {

	      User user = userRepository.findById(id)
	              .orElseThrow(() -> new UserNotFoundException("User not found"));

	      modelMapper.map(dto, user);  
	      return userRepository.save(user);
	  }

	

	
	
	
}
