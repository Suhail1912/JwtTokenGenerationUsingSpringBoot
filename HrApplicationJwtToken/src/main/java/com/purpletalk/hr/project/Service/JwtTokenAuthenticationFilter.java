package com.purpletalk.hr.project.Service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	//Method 1 
//    @Autowired
//    private JwtTokenProvider tokenProvider;
//
//    @Autowired
//    private HrDetailsServiceImpl hrDetailsServiceImpl;
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        try {
//            String jwt = getJwtFromRequest(request);
//
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                String username = tokenProvider.getUserNameFromJWT(jwt);
//
//                UserDetails userDetails = hrDetailsServiceImpl.loadUserByUsername(username);
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                logger.info("authenticated user " + username + ", setting security context");
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        } catch (Exception ex) {
//            logger.error("Could not set user authentication in security context", ex);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private String getJwtFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7, bearerToken.length());
//        }
//        return null;
//    }
	
	//Method 2
	
	
	@Autowired
  private JwtTokenProvider tokenProvider;

  @Autowired
  private HrDetailsServiceImpl hrDetailsServiceImpl;

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
	final String requestTokenHeader = request.getHeader("Authorization");
	String username = null;
	String jwtToken = null;
	// JWT Token is in the form "Bearer token". Remove Bearer word and get
	// only the Token
	if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
	jwtToken = requestTokenHeader.substring(7);
	try {
	username = tokenProvider.getUsernameFromToken(jwtToken);
	} catch (IllegalArgumentException e) {
	System.out.println("Unable to get JWT Token");
	} catch (ExpiredJwtException e) {
	System.out.println("JWT Token has expired");
	}
	} else {
	logger.warn("JWT Token does not begin with Bearer String");
	}
	
	// Once we get the token validate it.
	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	UserDetails userDetails = this.hrDetailsServiceImpl.loadUserByUsername(username);
	// if token is valid configure Spring Security to manually set
	// authentication
	if (tokenProvider.validateToken(jwtToken, userDetails)) {
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	userDetails, null, userDetails.getAuthorities());
	usernamePasswordAuthenticationToken
	.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	// After setting the Authentication in the context, we specify
	// that the current user is authenticated. So it passes the
	// Spring Security Configurations successfully.
	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
	}
	filterChain.doFilter(request, response);
	}

	
}