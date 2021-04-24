package com.slim.ngq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.slim.ngq.model.Hero;
import com.slim.ngq.repository.HeroRepository;

@ApplicationScoped
public class HeroService {

	private static AtomicLong hId = new AtomicLong(0);
	private static List<Hero> HEROES = new ArrayList<Hero>(
			Arrays.asList(new Hero(hId.incrementAndGet(), "Slimaine"), new Hero(hId.incrementAndGet(), "Yasmine")));
	

	public HeroService() {
		// TODO Auto-generated constructor stub
	}

	public List<Hero> findAllHeroes() {
		return HEROES;
	}
	
	public List<Hero> findHeroByMatchingName(String term) {
		return HEROES.stream().filter(e -> e.getName().contains(term)).collect(Collectors.toList());
	}

	public Hero findHeroById(Integer id) {
		return HEROES.stream().filter(e -> e.id.equals(id)).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
	}
	

	public Hero addHero(Hero hero) {
		
		hero.id = hId.incrementAndGet();
		HEROES.add(hero);
		return hero;
	}
	
	public Hero deleteHeroById( Integer id) {
		
		Hero heroToDelete = HEROES.stream().filter(e -> e.id.equals(id)).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
		
		HEROES.removeIf(e -> e.id.equals(heroToDelete.id));
		return heroToDelete;
	}
		
	

	public Hero updateHero(Hero hero) {
		Hero heroToUpdate = HEROES.stream().filter(e -> e.id.equals(hero.id)).reduce((a, b) -> {
			throw new IllegalStateException("Multiple users find: " + a + ", " + b);
		}).get();
		
		heroToUpdate.setName(hero.getName());
		return hero;
	}

}
