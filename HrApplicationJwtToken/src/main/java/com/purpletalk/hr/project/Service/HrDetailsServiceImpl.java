package com.purpletalk.hr.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.purpletalk.hr.project.Model.HrModel;
import com.purpletalk.hr.project.Repository.HrRepository;

@Service
public class HrDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private HrRepository hrRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println("the username the we entered is "+username);
		HrModel  hrModel=hrRepository.findByUsername(username);
		if (hrModel == null) {
            throw new UsernameNotFoundException(username);
        }
		return new HrDetails(hrModel);
	}

	

	
}
