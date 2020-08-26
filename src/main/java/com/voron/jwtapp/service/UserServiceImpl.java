package com.voron.jwtapp.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voron.jwtapp.dao.RoleDAO;
import com.voron.jwtapp.dao.UserDAO;
import com.voron.jwtapp.entity.Role;
import com.voron.jwtapp.entity.Status;
import com.voron.jwtapp.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}
	
	@Override
	@Transactional
	public User findByUsernameAndPassword(String username, String password) {
		System.out.println("\nNow in UserServiceImpl.findByUsernameAndPassword()");
		User user = findByUsername(username);
		System.out.println("\nFinaly we found user: " + user);
		if( user !=  null) {
			if(passwordEncoder.matches(password, user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public List<User> findAll() {
		return userDAO.findAll();
	}

	@Override
	@Transactional
	public User findById(int userId) {
		return userDAO.findById(userId).orElse(null);
	}

	@Override
	@Transactional
	public User save(User user) {
		user.setStatus(Status.ACTIVE);
		
		Role role = roleDAO.findByRoleTitle("ROLE_USER");			
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		user.setRoles(roles);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Date date = new Date();
		user.setCreated(date);
		user.setUpdated(date);
		
		System.out.print("Password: " + user.getPassword());
		userDAO.save(user);
		return user;
	}

	@Override
	@Transactional
	public void deleteById(int userId) throws UserPrincipalNotFoundException {
		User user = userDAO.findById(userId).orElse(null);
		
		if(user == null) throw new UserPrincipalNotFoundException("User with id: " + userId + " not exist");
		userDAO.delete(user);
		
	}

	@Override
	@Transactional
	public User updateUserDate(User user) {
		
		userDAO.save(user);
		return user;
	}

}
