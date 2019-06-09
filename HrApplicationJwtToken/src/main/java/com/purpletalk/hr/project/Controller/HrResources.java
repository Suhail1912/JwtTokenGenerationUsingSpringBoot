package com.purpletalk.hr.project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purpletalk.hr.project.Configurations.BcryptGenerator;
import com.purpletalk.hr.project.Model.HrModel;
import com.purpletalk.hr.project.Service.HrService;

@RestController
@RequestMapping("/hr")
public class HrResources {
	

	@Autowired
	private BcryptGenerator passwordEncoder;
	
	@Autowired
	private HrService hrService;
	
	@GetMapping("/list")
	public String getDetails()
	{
		System.out.println("employee List");
		return "Employee Listtt>>>>>>>>>>>>>>>>>>>>>>>>>>";
	}
	
	@PostMapping("/insert")
	public HrModel insertEmployee(@RequestBody HrModel  hrDetails)
	{
//		String password=hrDetails.getPassword();
//		String encodedPassword=passwordEncoder.passwordEncoder().encode(password);
//		System.out.println(encodedPassword);
//		hrDetails.setPassword(encodedPassword);
//		return hrService.insertEmployee(hrDetails);
		System.out.println("The new insertion completed");
		return  null;
		
	}
	
	
}
