package com.project.projectManagementSystem.auth;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.project.projectManagementSystem.auth.services.LogInUserDetails;

@SuppressWarnings("all")
@Configuration
@EnableWebMvc
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String[] PUBLIC_URLS = { "/v3/api-docs", "/v2/api-docs", "/swagger-resources/**",
			"/swagger-ui/**", "/webjars/**" };

	@Autowired
	private LogInUserDetails userDetails;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.cors()
		.disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
//		.antMatchers(HttpMethod.POST, "/auth/addEmployee").permitAll()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET, "/employee/getAllEmployee").hasAnyAuthority("ADMIN", "HR")
		.antMatchers(HttpMethod.PUT, "/employee/updateEmployee").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.PUT, "/employee/deleteEmployeeById").hasAnyAuthority("ADMIN", "USER")
		.antMatchers(HttpMethod.POST, "/project/addProject").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/project/updateProject").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/project/deleteProjectById").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/project/getAllProject").hasAnyAuthority("ADMIN", "HR")
		.antMatchers(HttpMethod.GET, "/project/projectList").hasAnyAuthority("ADMIN", "HR")
		.antMatchers(HttpMethod.POST, "/projectAssign/addAssignProject").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.PUT, "/projectAssign/updateAssignProject").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/projectAssign/getAllAssignProjects").hasAnyAuthority("ADMIN", "HR")
		.antMatchers(HttpMethod.POST, "/projectAssign/deleteAssignProjectByProjectIdAndEmployeeId").hasAuthority("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling();
//		.authenticationEntryPoint(unauthorizedEntryPoint());
//		.accessDeniedPage("/403");
		
	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
//	@Bean
//	public AuthenticationEntryPoint unauthorizedEntryPoint() {
//	    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//	}
}
