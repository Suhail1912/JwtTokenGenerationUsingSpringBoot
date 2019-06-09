package com.purpletalk.hr.project.Configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.purpletalk.hr.project.Service.HrDetailsServiceImpl;
import com.purpletalk.hr.project.Service.JwtAuthenticationEntryPoint;
import com.purpletalk.hr.project.Service.JwtTokenAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	//Method 1
//	@Autowired
//	private HrDetailsServiceImpl hrDetailsServiceImpl;
//	
//	@Autowired
//    private JwtAuthenticationEntryPoint unauthorizedHandler;
//	
//
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth.userDetailsService(hrDetailsServiceImpl).passwordEncoder(passwordEncoder());
//	}
//	
//	@Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		
////		http.httpBasic()
////	      .and().authorizeRequests()
////	    	 .antMatchers("/hr/**").authenticated()
////	      .and()
////	      .csrf().disable();
//		
////		http.cors()
////		  .and()
////		  .csrf().disable()
////	      .authorizeRequests()
////	      .antMatchers("/api/auth/**").permitAll()
////	      .antMatchers("/hr/**").authenticated()
////	      .and()
////	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
////	      .and()
////        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		
//		http
//        .cors()
//            .and()
//        .csrf()
//            .disable()
//        .exceptionHandling()
//            .authenticationEntryPoint(unauthorizedHandler)
//            .and()
//        .sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//         .authorizeRequests()
//         .antMatchers("/api/auth/**").permitAll()
//	      .antMatchers("/hr/**").authenticated();
//         	
////		 http
////		  .csrf().disable()
////		  .authorizeRequests()
////          .antMatchers("/hr/signin").authenticated()
////          .anyRequest().permitAll()
////          .and()
////	      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
////	      .and()
////          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//	    http
//	    .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
//	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//    public JwtTokenAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtTokenAuthenticationFilter();
//    }

	
	//Method 2
	
	@Autowired
	private HrDetailsServiceImpl hrDetailsServiceImpl;
	
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		System.out.println("Inside AuthenticationManger");
		auth.userDetailsService(hrDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
	// We don't need CSRF for this example
	httpSecurity.csrf().disable()
	// dont authenticate this particular request
	.authorizeRequests().antMatchers("/api/auth/**").permitAll().
	// all other requests need to be authenticated
	anyRequest().authenticated().and().
	// make sure we use stateless session; session won't be used to
	// store user's state.
	exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	// Add a filter to validate the tokens with every request
	httpSecurity.addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	

}
