package com.voron.jwtapp.details;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.voron.jwtapp.entity.Role;
import com.voron.jwtapp.entity.User;

@Component
public class CustomUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> grantedAuthoritys;
	
	public static CustomUserDetails fromUserEntityToCustomUserDetails(User user) {
		CustomUserDetails tempUser = new CustomUserDetails();
		tempUser.username = user.getUsername();
		tempUser.password = user.getPassword();
		tempUser.grantedAuthoritys = parseAuthorities(user.getRoles());
		return tempUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static Collection<? extends GrantedAuthority> parseAuthorities (List<Role> roles) {
		return roles.stream().map(role -> 
								new SimpleGrantedAuthority(role.getRoleTitle())
				).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "CustomUserDetails [username=" + username + ", password=" + password + ", grantedAuthoritys="
				+ grantedAuthoritys + "]";
	}
	
	

}
