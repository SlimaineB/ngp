package com.slim.ngq.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.slim.ngq.model.Hero;
import com.slim.ngq.repository.HeroRepository;

@ApplicationScoped
@Transactional
public class HeroService {

	private static AtomicLong hId = new AtomicLong(0L);
	private static List<Hero> HEROES = new ArrayList<Hero>(
			Arrays.asList(new Hero(hId.incrementAndGet(), "Slimaine"), new Hero(hId.incrementAndGet(), "Yasmine")));
	
	@Inject
	HeroRepository heroRepository;

	public HeroService() {
		// TODO Auto-generated constructor stub
	}

	public List<Hero> findAllHeroes() {
		return heroRepository.findAll().list();
	}
	
	public List<Hero> findHeroByMatchingName(String term) {
		return HEROES.stream().filter(e -> e.getName().contains(term)).collect(Collectors.toList());
	}

	public Hero findHeroById(Long id) {
		return heroRepository.findById(id);
	}
	

	public Hero addHero(Hero hero) {
		
		heroRepository.persist(hero);
		return hero;
	}
	
	public Hero deleteHeroById(Long id) {
		
		Hero heroToDelete = findHeroById(id);
		heroRepository.deleteById(id);
		return heroToDelete;
	}
		
	

	public Hero updateHero(Hero hero) {
	
		Hero heroToUpdate = findHeroById(hero.id);
		heroToUpdate.setName(hero.getName());
		heroRepository.persist(heroToUpdate);
		
		return heroToUpdate;
	}

}
