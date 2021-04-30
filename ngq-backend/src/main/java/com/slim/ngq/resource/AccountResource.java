package com.slim.ngq.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.security.PermitAll;
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
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.slim.ngq.model.AuthRequest;
import com.slim.ngq.model.User;
import com.slim.ngq.service.AccountService;
import static com.slim.ngq.utils.AuthoritiesConstants.*;
import com.slim.ngq.utils.JwtTokenUtils;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountResource {

	@Inject
	AccountService accountService;
	
	@ConfigProperty(name = "mp.jwt.verify.issuer") 
	public String issuer;

	public AccountResource() {
		// TODO Auto-generated constructor stub
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<User> getUsers() {
		return accountService.findAllUsers();
	}

	@GET
	@Path("/{id}")
	public User getUserById(@PathParam(value = "id") Long id) {
		return accountService.findUserById(id);
	}


	@POST
	@Path("/register")
	public User register(User user) throws Exception {
		accountService.addUser(user);
		return user;
	}
	
	@POST
	@Path("/authenticate")
	public User login(AuthRequest authRequest) throws Exception {
		
		User authentifiedUser = null;
		User user = accountService.findUserByUsername(authRequest.getUsername());
		if(user!=null && user.getPassword().equals(authRequest.getPassword())) {
			user.setToken(JwtTokenUtils.generateToken(user.getUsername(), new HashSet<String>(Arrays.asList(USER)), 3600L, issuer));
			authentifiedUser = user;
		}
		return authentifiedUser;
	}

	@DELETE
	@Path("/{id}")
	public User deleteUser(@PathParam("id") Long id) {
		return accountService.deleteUserById(id);
	}

	@PUT
	public User updateUser(User user) {
		return accountService.updateUser(user);
	}

}
