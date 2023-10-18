package com.intern.user.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intern.user.service.entity.StudentSemesterEntity;

public interface StudentSemesterRepository extends JpaRepository<StudentSemesterEntity, String> {

	List<StudentSemesterEntity> findByStudentMatricNum(String studentMatricNum);
}
