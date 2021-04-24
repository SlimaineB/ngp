package com.slim.ngq.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

public class HeroDTO extends PanacheEntity{

	Integer id;
	String name;
	
	public HeroDTO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public HeroDTO(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
