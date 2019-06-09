package com.purpletalk.hr.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.purpletalk.hr.project.Model.HrModel;

public interface HrRepository extends JpaRepository<HrModel, Long> {

	@Query("select h from  HrModel h where h.username= :UserName")
	HrModel findByUsername(@Param("UserName") String username);

}
