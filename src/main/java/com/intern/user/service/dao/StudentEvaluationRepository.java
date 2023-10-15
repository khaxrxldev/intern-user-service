package com.intern.user.service.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intern.user.service.entity.StudentEvaluationEntity;

@Repository
public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluationEntity, String> {

	List<StudentEvaluationEntity> findByStudentMatricNum(String studentMatricNum);
}
