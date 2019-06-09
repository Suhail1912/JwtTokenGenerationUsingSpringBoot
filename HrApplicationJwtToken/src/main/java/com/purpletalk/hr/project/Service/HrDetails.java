package com.purpletalk.hr.project.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.purpletalk.hr.project.Model.HrModel;

public class HrDetails implements UserDetails {

	private HrModel hrModel;
	
	HrDetails(HrModel hrModel)
	{
		this.hrModel=hrModel;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return hrModel.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return hrModel.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
