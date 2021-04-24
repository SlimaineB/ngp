package com.slim.ngq.repository;

import java.util.stream.Stream;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import com.slim.ngq.model.Hero;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

//Will override HeroRepository for our tests

@Priority(1)
@Alternative
@ApplicationScoped
public class TestHeroRepository implements PanacheRepository<Hero>{

	public TestHeroRepository() {
		persist(Stream.of(new Hero(1L, "Hero1"), new Hero(2L, "Hero2")));
	}

}
