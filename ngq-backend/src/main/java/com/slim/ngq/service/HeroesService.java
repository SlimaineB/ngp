package com.slim.ngq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.PathParam;

import com.slim.ngq.model.Hero;

@ApplicationScoped
public class HeroesService {

	private static AtomicInteger hId = new AtomicInteger(0);
	private static List<Hero> HEROES = new ArrayList<Hero>(
			Arrays.asList(new Hero(hId.incrementAndGet(), "Slimaine"), new Hero(hId.incrementAndGet(), "Yasmine")));

	public HeroesService() {
		// TODO Auto-generated constructor stub
	}

	public List<Hero> findAllHeroes() {
		return HEROES;
	}
	
	public List<Hero> findHeroByMatchingName(String term) {
		return HEROES.stream().filter(e -> e.getName().contains(term)).collect(Collectors.toList());
	}

	public Hero findHeroById(Integer id) {
		return HEROES.stream().filter(e -> e.getId().equals(id)).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
	}
	

	public Hero addHero(Hero hero) {
		
		hero.setId(hId.incrementAndGet());
		HEROES.add(hero);
		return hero;
	}
	
	public Hero deleteHeroById( Integer id) {
		
		Hero heroToDelete = HEROES.stream().filter(e -> e.getId().equals(id)).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
		
		HEROES.removeIf(e -> e.getId().equals(heroToDelete.getId()));
		return heroToDelete;
	}
		
	

	public Hero updateHero(Hero hero) {
		Hero heroToUpdate = HEROES.stream().filter(e -> e.getId().equals(hero.getId())).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
		
		heroToUpdate.setName(hero.getName());
		return hero;
	}

}
