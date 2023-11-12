package com.intern.user.service.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.intern.user.service.dto.AcademicSupervisorRequest;
import com.intern.user.service.dto.AcademicSupervisorResponse;
import com.intern.user.service.dto.IndustrySupervisorRequest;
import com.intern.user.service.dto.IndustrySupervisorResponse;
import com.intern.user.service.dto.SigninRequest;
import com.intern.user.service.dto.SigninResponse;
import com.intern.user.service.dto.StudentRequest;
import com.intern.user.service.dto.StudentResponse;

public interface UserService {
	
	SigninResponse userSignin(SigninRequest signinRequest);

	// Academic Supervisor
	
	List<AcademicSupervisorResponse> filterAcademicSupervisors(AcademicSupervisorRequest academicSupervisorRequest);
	
	AcademicSupervisorResponse getAcademicSupervisorByAcademicSvId(String academicSvId);
	
	AcademicSupervisorResponse insertAcademicSupervisor(AcademicSupervisorRequest academicSupervisorRequest) throws Exception;
	
	AcademicSupervisorResponse updateAcademicSupervisor(AcademicSupervisorRequest academicSupervisorRequest) throws Exception;
	
	Boolean deleteAcademicSupervisorByAcademicSvId(String academicSvId);

	// Industry Supervisor
	
	List<IndustrySupervisorResponse> filterIndustrySupervisors(IndustrySupervisorRequest industrySupervisorRequest);
	
	List<List<String>> getTotalIndustrySupervisors();
	
	IndustrySupervisorResponse getIndustrySupervisorByIndustrySvId(String industrySvId);
	
	IndustrySupervisorResponse insertIndustrySupervisor(IndustrySupervisorRequest industrySupervisorRequest) throws Exception;
	
	IndustrySupervisorResponse updateIndustrySupervisor(IndustrySupervisorRequest industrySupervisorRequest) throws Exception;
	
	Boolean deleteIndustrySupervisorByIndustrySvId(String industrySvId);
	
	// Student
	
	List<StudentResponse> getStudents();
	
	List<StudentResponse> filterStudents(StudentRequest studentRequest);
	
	List<List<String>> getTotalStudents();
	
	StudentResponse getStudentByStudentMatricNum(String studentMatricNum) throws Exception;
	
	StudentResponse insertStudent(StudentRequest studentRequest, MultipartFile cvFile, MultipartFile mtFile, MultipartFile clFile, MultipartFile coFile, MultipartFile slFile) throws Exception;
	
	StudentResponse updateStudent(StudentRequest studentRequest, MultipartFile cvFile, MultipartFile mtFile, MultipartFile clFile, MultipartFile coFile, MultipartFile slFile) throws Exception;

	List<StudentResponse> updateStudentStatusList(List<StudentRequest> studentRequests) throws Exception;
	
	Boolean deleteStudentByStudentMatricNum(String studentMatricNum);
}
