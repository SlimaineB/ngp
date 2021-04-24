package com.slim.ngq.repository;

import javax.enterprise.context.ApplicationScoped;

import com.slim.ngq.model.Hero;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class HeroRepository implements PanacheRepository<Hero>{

	public HeroRepository() {
		// TODO Auto-generated constructor stub
	}

}
