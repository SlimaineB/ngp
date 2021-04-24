package com.slim.ngq.model;

public class Hero {

	Integer id;
	String name;
	
	public Hero() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Hero(Integer id, String name) {
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
