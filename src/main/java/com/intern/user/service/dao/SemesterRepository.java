package com.intern.user.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intern.user.service.entity.SemesterEntity;

public interface SemesterRepository extends JpaRepository<SemesterEntity, String> {

	SemesterEntity findBySemesterId(String semesterId);
	
	Integer deleteBySemesterId(String semesterId);
}
