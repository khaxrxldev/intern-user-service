package com.intern.user.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intern.user.service.entity.CompanyEntity;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {

	List<CompanyEntity> findAllByOrderByCompanyName();
	
	CompanyEntity findByCompanyId(String companyId);
	
	Integer deleteByCompanyId(String companyId);
}
