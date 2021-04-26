package com.slim.ngq.repository;

import javax.enterprise.context.ApplicationScoped;

import com.slim.ngq.model.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User>{

	public UserRepository() {
		// TODO Auto-generated constructor stub
	}
	
	 public User findByUsername(String username){
	       return find("username", username).firstResult();
	   }

}
