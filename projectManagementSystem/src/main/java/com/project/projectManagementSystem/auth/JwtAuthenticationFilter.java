package com.project.projectManagementSystem.auth;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.projectManagementSystem.auth.services.LogInUserDetails;
import com.project.projectManagementSystem.exception.CustomException;
import com.project.projectManagementSystem.helper.JWTUtill;
import com.project.projectManagementSystem.model.Employee;
import com.project.projectManagementSystem.repo.EmployeeRepo;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtill jwtUtill;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private LogInUserDetails logInUserDetails;
	private UserDetails userDetails;

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String header = request.getHeader("Authorization");
			String userName = null;
			String jwtToken = null;
			if (header != null && header.startsWith("Bearer")) {
				jwtToken = header.substring(7);
				try {
					userName = this.jwtUtill.extractUsername(jwtToken);
				} catch (Exception e) {
					throw new CustomException("The Token Has Been Expired");
				}
				Optional<Employee> employeeByEmail = this.employeeRepo.findByEmail(userName);
				if (employeeByEmail.isPresent()) {
					this.userDetails = this.logInUserDetails.loadUserByUsername(userName);
				} else {
					throw new CustomException("The Token Has Been Expired");
				}

				if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				} else {
					throw new CustomException("The Token Has Been Expired");
				}
			}
			filterChain.doFilter(request, response);

		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json");
			response.getWriter().write("{ \"statuss\" : 500 , \"messagee\" : \"" + e.getMessage() + "\"}");
			LOGGER.info(e.getMessage());
			LOGGER.info(e.toString());
		}

	}

}
