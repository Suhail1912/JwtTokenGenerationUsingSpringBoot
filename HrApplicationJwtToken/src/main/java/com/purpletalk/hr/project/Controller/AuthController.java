package com.purpletalk.hr.project.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.purpletalk.hr.project.Model.HrLoginRequest;
import com.purpletalk.hr.project.Model.JwtAuthenticationResponse;
import com.purpletalk.hr.project.Service.HrDetailsServiceImpl;
import com.purpletalk.hr.project.Service.JwtTokenProvider;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {
	
	//Method 1
//	@Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtTokenProvider tokenProvider;
//
//    @PostMapping("/signin")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody HrLoginRequest hrLoginRequest) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                		hrLoginRequest.getUsernameOrEmail(),
//                		hrLoginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String jwt = tokenProvider.generateToken(authentication);
//        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
//    }
	
	//Method 2

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private  JwtTokenProvider tokenProvider;
	
	@Autowired
	private HrDetailsServiceImpl hrDetailsServiceImpl;
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody HrLoginRequest authenticationRequest) throws Exception {
	
		
	System.out.println("The username from HrLoginRequest is   "+authenticationRequest.getUsernameOrEmail());	
	authenticate(authenticationRequest.getUsernameOrEmail(), authenticationRequest.getPassword());
	final UserDetails userDetails = hrDetailsServiceImpl.loadUserByUsername(authenticationRequest.getUsernameOrEmail());
	final String token = tokenProvider.generateToken(userDetails);
	return ResponseEntity.ok(new JwtAuthenticationResponse(token));
	}
	private void authenticate(String username, String password) throws Exception {
	try {
	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	} catch (DisabledException e) {
	throw new Exception("USER_DISABLED", e);
	} catch (BadCredentialsException e) {
	throw new Exception("INVALID_CREDENTIALS", e);
	}
	}
	
	

}
