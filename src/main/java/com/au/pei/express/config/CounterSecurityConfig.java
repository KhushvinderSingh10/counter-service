package com.au.pei.express.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CounterSecurityConfig extends WebSecurityConfigurerAdapter{

	@Value("${basic.auth.username}")
	private String username;

	@Value("${basic.auth.pwd}")
	private String cred;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/counter-api/**").authenticated()
		.and()
		.httpBasic();		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.inMemoryAuthentication()
		.withUser(username.trim())
		.password(passwordEncoder().encode(cred.trim()))
		.roles("USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



}
