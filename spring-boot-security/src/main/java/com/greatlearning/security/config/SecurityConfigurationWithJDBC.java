package com.greatlearning.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfigurationWithJDBC extends WebSecurityConfigurerAdapter{
@Autowired
DataSource dataSource;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema()
		/*.withUser(User.withUsername("samarth").password("samarth").roles("STORE_OWNER"))
		.withUser(User.withUsername("a").password("a").roles("STORE_CLERK"));
		the following is to encrypt
		*/
		.withUser(User.withUsername("samarth").password(getPasswordEncoder().encode("samarth")).roles("STORE_OWNER"));
	}
	/*@Bean
	PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	to encrypt password, we have to write this
	*/
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().formLogin();
	}*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/organicVeggies/viewFinancials","/organicVeggies/makeAnnouncement").hasRole("STORE_OWNER")
		.antMatchers("/").permitAll().and().formLogin();
	}
}
