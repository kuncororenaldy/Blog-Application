package com.blog.app;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.app.dao.entity.Role;
import com.blog.app.dao.entity.Role.ERole;
import com.blog.app.dao.entity.User;
import com.blog.app.dao.repository.UserRepository;

@SpringBootApplication
public class BlogAppsApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setId(1L);
		user.setUsername("admin");
		user.setPassword(passwordEncoder.encode("12345678"));
		user.setEmail("admin@email.com");
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(1,ERole.ROLE_ADMIN));
		user.setRoles(roles);

		userRepository.save(user);
	}

}
