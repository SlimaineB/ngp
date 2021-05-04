package com.slim.ngq;


import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import com.slim.ngq.model.Hero;
import com.slim.ngq.model.User;
import com.slim.ngq.repository.HeroRepository;
import com.slim.ngq.repository.UserRepository;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
@Transactional
public class AppLifecycleBean {
	
	@Inject
	HeroRepository heroRepository;
	
	@Inject
	UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    void onStart(@Observes StartupEvent ev) {               
        LOGGER.info("The NGQ application is starting...");
        //Insert some data a startup
        heroRepository.persist(Stream.of(new Hero("Slimaine"), 
        		new Hero("Yasmine"),
        		new Hero("Nail"),
        		new Hero("Jean")));
        
        userRepository.persist(new User("admin",  "admin", "Administrator","Administrator", null));
        userRepository.persist(new User("user",  "admin", "User","User", null));
        
    }

    void onStop(@Observes ShutdownEvent ev) {               
        LOGGER.info("The NGQ application is stopping...");
    }

}