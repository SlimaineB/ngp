package com.slim.ngq.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.slim.ngq.model.Hero;
import com.slim.ngq.service.HeroesService;

@Path("/api/heroes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HeroesResources {

	@Inject
	private HeroesService service;

	public HeroesResources() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hero> getHeroes() {
		return service.findAllHeroes();
	}

	@GET
	@Path("/name={term}")
	public List<Hero> searchHeroes(@PathParam(value = "term") String term) {
		return service.findHeroByMatchingName(term);
	}

	@GET
	@Path("/{id}")
	public Hero getHero(@PathParam(value = "id") Integer id) {
		return service.findHeroById(id);
	}

	@POST
	public Hero addHero(Hero hero) {
		return service.addHero(hero);
	}

	@DELETE
	@Path("/{id}")
	public Hero deleteHero(@PathParam("id") Integer id) {
		return service.deleteHeroById(id);
	}

	@PUT
	public Hero updateHero(Hero hero) {
		return service.updateHero(hero);
	}

}
