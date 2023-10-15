package com.intern.user.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intern.user.service.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, String> {

	StudentEntity findByStudentMatricNum(String studentMatricNum);

	StudentEntity findByStudentEmail(String studentEmail);

	Integer deleteByStudentMatricNum(String studentMatricNum);
}
