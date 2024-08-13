package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// this implemented method does database authentication internally
	@Override
	public UserDetails loadUserByUsername(String usernameorEmail) throws UsernameNotFoundException {

		// loading user from DB
		// passed usernameorEmail twice because of username and email
		User user = userRepository.findByUserNameOrEmail(usernameorEmail, usernameorEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email" + usernameorEmail));

		// getting set of roles and converting into set of granted authorities
		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
