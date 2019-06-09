package com.purpletalk.hr.project.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purpletalk.hr.project.Model.HrModel;
import com.purpletalk.hr.project.Repository.HrRepository;

@Service
public class HrService {

	@Autowired
	private HrRepository hrRepository;

	//Insert Operation
	public HrModel insertEmployee(HrModel hrDetails) {
		
		return hrRepository.save(hrDetails);
	} 
	
	
}
