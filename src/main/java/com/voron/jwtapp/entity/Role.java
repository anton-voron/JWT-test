package com.voron.jwtapp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column( name = "role_title")
	private String roleTitle;
	

	
	
	public Role () {}


	public Role(String roleTitle, List<User> users) {
		this.roleTitle = roleTitle;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getRoleTitle() {
		return roleTitle;
	}


	public void setRoleTitle(String roleTitle) {
		this.roleTitle = roleTitle;
	}

	
	
}
