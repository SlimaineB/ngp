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
import com.slim.ngq.model.User;
import com.slim.ngq.repository.HeroRepository;
import com.slim.ngq.repository.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
@Transactional
public class AccountService {

	private static AtomicLong hId = new AtomicLong(0L);
	private static List<Hero> HEROES = new ArrayList<Hero>(
			Arrays.asList(new Hero(hId.incrementAndGet(), "Slimaine"), new Hero(hId.incrementAndGet(), "Yasmine")));

	@Inject
	HeroRepository heroRepository;

	@Inject
	UserRepository userRepository;

	public AccountService() {
		// TODO Auto-generated constructor stub
	}

	public User updateUser(User user) {
		User userToUpdate = findUserById(user.getId());
		userToUpdate.setUsername(user.getUsername());
		userRepository.persist(userToUpdate);

		return userToUpdate;
	}

	public User deleteUserById(Long id) {
		// TODO Auto-generated method stub
		User userToDelete = findUserById(id);
		if (userToDelete != null) {
			userRepository.delete(userToDelete);
		}
		return userToDelete;
	}

	public User addUser(User user) {
		user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
		userRepository.persist(user);
		return user;
	}

	public User findUserById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll().list();
	}

	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}

}
