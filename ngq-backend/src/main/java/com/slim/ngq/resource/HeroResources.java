package com.slim.ngq.resource;

import java.util.List;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.slim.ngq.model.Hero;
import com.slim.ngq.service.HeroService;
import static com.slim.ngq.utils.AuthoritiesConstants.*;

@Path("/api/heroes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class HeroResources {

	private static final Logger LOGGER = Logger.getLogger(HeroResources.class);
	
	@Inject
	HeroService heroService;

	@Inject
	JsonWebToken jwt;

	public HeroResources() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@RolesAllowed({USER })
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hero> getHeroes(@Context SecurityContext ctx) {
		LOGGER.info(">> Is Secure: "+ctx.isSecure());
		LOGGER.info(">> JWT Name: "+jwt.getName());
		LOGGER.info(">> Context Name: "+ctx.getUserPrincipal().getName());
		
		return heroService.findAllHeroes();
	}

	@GET
	@Path("/name={term}")
	public List<Hero> searchHeroes(@PathParam(value = "term") String term) {
		return heroService.findHeroByMatchingName(term);
	}

	@GET
	@Path("/{id}")
	public Hero getHero(@PathParam(value = "id") Long id) {
		return heroService.findHeroById(id);
	}

	@POST
	public Hero addHero(Hero hero) {
		return heroService.addHero(hero);
	}

	@DELETE
	@Path("/{id}")
	public Hero deleteHero(@PathParam("id") Long id) {
		return heroService.deleteHeroById(id);
	}

	@PUT
	public Hero updateHero(Hero hero) {
		return heroService.updateHero(hero);
	}

}
