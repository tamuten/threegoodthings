package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/webjars/**", "/css/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**")
			.permitAll()
			.anyRequest()
			.authenticated();

		http.formLogin()
			.loginPage("/login")
			.permitAll()
			.usernameParameter("mailaddress")
			.passwordParameter("password")
			.defaultSuccessUrl("/index");
	}

}
