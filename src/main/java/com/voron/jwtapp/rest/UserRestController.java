package com.voron.jwtapp.rest;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voron.jwtapp.controller.AuthRequest;
import com.voron.jwtapp.controller.AuthResponse;
import com.voron.jwtapp.entity.User;
import com.voron.jwtapp.jwt.JwtProvider;
import com.voron.jwtapp.service.UserService;

@RestController
@RequestMapping("/api")
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@PostMapping("/registration")
	public User registrationUser (@RequestBody User userRequest) {
		System.out.println(userRequest);
		User tempUser = userService.save(userRequest);
		return tempUser;
	}
	
	@PostMapping("/login")
	public AuthResponse auth(@RequestBody AuthRequest request) {
		System.out.print("=======================>> HERE, REQUEST IS: " + request);
		User user = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
		
		System.out.println("Than we take user form db: " + user + " \n");
		String token = jwtProvider.generateToken(user.getUsername(), user.getRoles());
		System.out.println("\nToken: " + token);
		return new AuthResponse(user.getUsername(), user.getPassword(), token);
	}
	
	
	@GetMapping("/users")
	public List<User> findAll () {
		return userService.findAll();
	}
	
	
	@GetMapping(value = "/users/{username}")
	public User findByUsername (@PathVariable String username) {
		return userService.findByUsername(username);
	}
	
	@PutMapping("/users")
	public User updateUserDate(@RequestBody User user) throws UserPrincipalNotFoundException {
		User tempUser = userService.findById(user.getId());
		if(tempUser == null) throw new UserPrincipalNotFoundException("User not found");
		return 	userService.updateUserDate(tempUser);
	}
	
	
	@DeleteMapping("/users/{userId}")
	public User deleteUser(@PathVariable int userId) throws UserPrincipalNotFoundException {
		User tempUser = userService.findById(userId);
		if(tempUser == null) throw new UserPrincipalNotFoundException("User not found");
		userService.deleteById(userId);
		return tempUser;
	}
	
	
	@GetMapping("/admin/get")
	public String adminPanel () {
		return "Hello admin!";
	}
	
	@GetMapping("/users/get")
	public String usersPage () {
		return "Hello user!";
	}
	

	
}
