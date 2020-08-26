package com.voron.jwtapp.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import com.voron.jwtapp.entity.User;

public interface UserService {
	
	public User findByUsername(String username);
	
	public User findByUsernameAndPassword(String username, String password);
	
	public List<User> findAll();
	
	public User findById(int userId);
	
	public User save(User user);
	
	public User updateUserDate(User user);
	
	public void deleteById(int userId) throws UserPrincipalNotFoundException;
}
