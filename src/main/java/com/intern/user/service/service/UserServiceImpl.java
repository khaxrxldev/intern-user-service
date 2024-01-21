package com.intern.user.service.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.intern.user.service.dto.SemesterResponse;
import com.intern.user.service.entity.SemesterEntity;
import com.intern.user.service.dao.AcademicSupervisorRepository;
import com.intern.user.service.dao.CompanyRepository;
import com.intern.user.service.dao.IndustrySupervisorRepository;
import com.intern.user.service.dao.SemesterRepository;
import com.intern.user.service.dao.StudentEvaluationRepository;
import com.intern.user.service.dao.StudentRepository;
import com.intern.user.service.dao.StudentSemesterRepository;
import com.intern.user.service.dto.AcademicSupervisorRequest;
import com.intern.user.service.dto.AcademicSupervisorResponse;
import com.intern.user.service.dto.CompanyResponse;
import com.intern.user.service.dto.IndustrySupervisorRequest;
import com.intern.user.service.dto.IndustrySupervisorResponse;
import com.intern.user.service.dto.SigninRequest;
import com.intern.user.service.dto.SigninResponse;
import com.intern.user.service.dto.StudentRequest;
import com.intern.user.service.dto.StudentResponse;
import com.intern.user.service.dto.StudentSemesterResponse;
import com.intern.user.service.entity.AcademicSupervisorEntity;
import com.intern.user.service.entity.CompanyEntity;
import com.intern.user.service.entity.IndustrySupervisorEntity;
import com.intern.user.service.entity.StudentEntity;
import com.intern.user.service.entity.StudentEvaluationEntity;
import com.intern.user.service.entity.StudentSemesterEntity;
import com.intern.user.service.utility.BaseUtility;
import com.intern.user.service.utility.DateUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	AcademicSupervisorRepository academicSupervisorRepository;
	
	@Autowired
	IndustrySupervisorRepository industrySupervisorRepository;
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	StudentEvaluationRepository studentEvaluationRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
	@Autowired
	StudentSemesterRepository studentSemesterRepository;

	@Override
	public SigninResponse userSignin(SigninRequest signinRequest) {
		SigninResponse signinResponse = new SigninResponse();
		SigninRequest existedSigninData = null;
		
		switch (signinRequest.getUserType()) {
			case "COD":
				AcademicSupervisorEntity existedCoordinatorEntity = academicSupervisorRepository.findByAcademicSvEmailAndAcademicSvCoordinator(signinRequest.getUserEmail(), 1);
				
				if (BaseUtility.isObjectNotNull(existedCoordinatorEntity)) {
					existedSigninData = new SigninRequest();
					existedSigninData.setUserId(existedCoordinatorEntity.getAcademicSvId());
					existedSigninData.setUserEmail(existedCoordinatorEntity.getAcademicSvEmail());
					existedSigninData.setUserPassword(existedCoordinatorEntity.getAcademicSvPassword());
				}
				break;
			case "ACD":
				AcademicSupervisorEntity existedAcademicSupervisorEntity = academicSupervisorRepository.findByAcademicSvEmail(signinRequest.getUserEmail());
				
				if (BaseUtility.isObjectNotNull(existedAcademicSupervisorEntity)) {
					existedSigninData = new SigninRequest();
					existedSigninData.setUserId(existedAcademicSupervisorEntity.getAcademicSvId());
					existedSigninData.setUserEmail(existedAcademicSupervisorEntity.getAcademicSvEmail());
					existedSigninData.setUserPassword(existedAcademicSupervisorEntity.getAcademicSvPassword());
				}
				break;
			case "IND":
				IndustrySupervisorEntity existedIndustrySupervisorEntity = industrySupervisorRepository.findByIndustrySvEmail(signinRequest.getUserEmail());

				if (BaseUtility.isObjectNotNull(existedIndustrySupervisorEntity)) {
					existedSigninData = new SigninRequest();
					existedSigninData.setUserId(existedIndustrySupervisorEntity.getIndustrySvId());
					existedSigninData.setUserEmail(existedIndustrySupervisorEntity.getIndustrySvEmail());
					existedSigninData.setUserPassword(existedIndustrySupervisorEntity.getIndustrySvPassword());
				}
				break;
			case "STD":
				StudentEntity existedStudentEntity = studentRepository.findByStudentEmail(signinRequest.getUserEmail());
				
				if (BaseUtility.isObjectNotNull(existedStudentEntity)) {
					existedSigninData = new SigninRequest();
					existedSigninData.setUserId(existedStudentEntity.getStudentMatricNum());
					existedSigninData.setUserEmail(existedStudentEntity.getStudentEmail());
					existedSigninData.setUserPassword(existedStudentEntity.getStudentPassword());
				}
				break;
		}
		
		if (!ObjectUtils.isEmpty(existedSigninData)) {
			signinResponse.setUserId(existedSigninData.getUserId());
			
			if (!signinRequest.getUserPassword().equals(existedSigninData.getUserPassword())) {
				signinResponse.setResponse("Incorrect password entered");
			}
		} else {
			signinResponse.setResponse("Email does not exist");
		}
		
		return signinResponse;
	}

	@Override
	public List<AcademicSupervisorResponse> filterAcademicSupervisors(AcademicSupervisorRequest academicSupervisorRequest) {
		List<AcademicSupervisorResponse> academicSupervisorResponses = new ArrayList<AcademicSupervisorResponse>();
		List<AcademicSupervisorResponse> filteredAcademicSupervisorResponses = new ArrayList<AcademicSupervisorResponse>();
		
		List<AcademicSupervisorEntity> academicSupervisorEntities = academicSupervisorRepository.findAll();
		if (academicSupervisorEntities.size() > 0) {
			for (AcademicSupervisorEntity academicSupervisorEntity : academicSupervisorEntities) {
				AcademicSupervisorResponse academicSupervisorResponse = new AcademicSupervisorResponse();
				
				academicSupervisorResponse.setAcademicSvId(academicSupervisorEntity.getAcademicSvId());
				academicSupervisorResponse.setAcademicSvName(academicSupervisorEntity.getAcademicSvName());
				academicSupervisorResponse.setAcademicSvPhone(academicSupervisorEntity.getAcademicSvPhone());
				academicSupervisorResponse.setAcademicSvEmail(academicSupervisorEntity.getAcademicSvEmail());
				academicSupervisorResponse.setAcademicSvPassword(academicSupervisorEntity.getAcademicSvPassword());
				academicSupervisorResponse.setAcademicSvGender(academicSupervisorEntity.getAcademicSvGender());
				academicSupervisorResponse.setAcademicSvPosition(academicSupervisorEntity.getAcademicSvPosition());
				academicSupervisorResponse.setAcademicSvCoordinator(academicSupervisorEntity.getAcademicSvCoordinator());
				
				academicSupervisorResponses.add(academicSupervisorResponse);
			}
		}
		
		for (AcademicSupervisorResponse academicSupervisorResponse : academicSupervisorResponses) {
			Boolean addRow = true;
			
			if (academicSupervisorRequest.getAcademicSvCoordinator() != null && academicSupervisorRequest.getAcademicSvCoordinator() != academicSupervisorResponse.getAcademicSvCoordinator()) {
				addRow = false;
			}
			
			if (addRow) {
				filteredAcademicSupervisorResponses.add(academicSupervisorResponse);
			}
		}
		
		return filteredAcademicSupervisorResponses;
	}

	@Override
	public AcademicSupervisorResponse getAcademicSupervisorByAcademicSvId(String academicSvId) {
		AcademicSupervisorResponse academicSupervisorResponse = null;
		AcademicSupervisorEntity existedAcademicSupervisorEntity = academicSupervisorRepository.findByAcademicSvId(academicSvId);
		
		if (BaseUtility.isObjectNotNull(existedAcademicSupervisorEntity)) {
			academicSupervisorResponse = new AcademicSupervisorResponse();
			
			academicSupervisorResponse.setAcademicSvId(existedAcademicSupervisorEntity.getAcademicSvId());
			academicSupervisorResponse.setAcademicSvName(existedAcademicSupervisorEntity.getAcademicSvName());
			academicSupervisorResponse.setAcademicSvPhone(existedAcademicSupervisorEntity.getAcademicSvPhone());
			academicSupervisorResponse.setAcademicSvEmail(existedAcademicSupervisorEntity.getAcademicSvEmail());
			academicSupervisorResponse.setAcademicSvPassword(existedAcademicSupervisorEntity.getAcademicSvPassword());
			academicSupervisorResponse.setAcademicSvGender(existedAcademicSupervisorEntity.getAcademicSvGender());
			academicSupervisorResponse.setAcademicSvPosition(existedAcademicSupervisorEntity.getAcademicSvPosition());
			academicSupervisorResponse.setAcademicSvCoordinator(existedAcademicSupervisorEntity.getAcademicSvCoordinator());
		}
		
		return academicSupervisorResponse;
	}

	@Override
	public AcademicSupervisorResponse insertAcademicSupervisor(AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		AcademicSupervisorResponse academicSupervisorResponse = null;
		AcademicSupervisorEntity newAcademicSupervisorEntity = new AcademicSupervisorEntity();
		
		newAcademicSupervisorEntity.setAcademicSvId(academicSupervisorRequest.getAcademicSvId());
		newAcademicSupervisorEntity.setAcademicSvName(academicSupervisorRequest.getAcademicSvName());
		newAcademicSupervisorEntity.setAcademicSvPhone(academicSupervisorRequest.getAcademicSvPhone());
		newAcademicSupervisorEntity.setAcademicSvEmail(academicSupervisorRequest.getAcademicSvEmail());
		newAcademicSupervisorEntity.setAcademicSvPassword(academicSupervisorRequest.getAcademicSvPassword());
		newAcademicSupervisorEntity.setAcademicSvGender(academicSupervisorRequest.getAcademicSvGender());
		newAcademicSupervisorEntity.setAcademicSvPosition(academicSupervisorRequest.getAcademicSvPosition());
		newAcademicSupervisorEntity.setAcademicSvCoordinator(academicSupervisorRequest.getAcademicSvCoordinator());
		
		AcademicSupervisorEntity insertedAcademicSupervisorEntity = academicSupervisorRepository.save(newAcademicSupervisorEntity);
		
		if (BaseUtility.isObjectNotNull(insertedAcademicSupervisorEntity)) {
			academicSupervisorResponse = new AcademicSupervisorResponse();
			
			academicSupervisorResponse.setAcademicSvId(insertedAcademicSupervisorEntity.getAcademicSvId());
			academicSupervisorResponse.setAcademicSvName(insertedAcademicSupervisorEntity.getAcademicSvName());
			academicSupervisorResponse.setAcademicSvPhone(insertedAcademicSupervisorEntity.getAcademicSvPhone());
			academicSupervisorResponse.setAcademicSvEmail(insertedAcademicSupervisorEntity.getAcademicSvEmail());
			academicSupervisorResponse.setAcademicSvPassword(insertedAcademicSupervisorEntity.getAcademicSvPassword());
			academicSupervisorResponse.setAcademicSvGender(insertedAcademicSupervisorEntity.getAcademicSvGender());
			academicSupervisorResponse.setAcademicSvPosition(insertedAcademicSupervisorEntity.getAcademicSvPosition());
			academicSupervisorResponse.setAcademicSvCoordinator(insertedAcademicSupervisorEntity.getAcademicSvCoordinator());
		}

		return academicSupervisorResponse;
	}

	@Override
	public AcademicSupervisorResponse updateAcademicSupervisor(AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		AcademicSupervisorResponse academicSupervisorResponse = null;
		AcademicSupervisorEntity academicSupervisorEntity = academicSupervisorRepository.findByAcademicSvId(academicSupervisorRequest.getAcademicSvId());
		
//		academicSupervisorEntity.setAcademicSvId(BaseUtility.generateId());
		academicSupervisorEntity.setAcademicSvName(academicSupervisorRequest.getAcademicSvName());
		academicSupervisorEntity.setAcademicSvPhone(academicSupervisorRequest.getAcademicSvPhone());
		academicSupervisorEntity.setAcademicSvEmail(academicSupervisorRequest.getAcademicSvEmail());
		academicSupervisorEntity.setAcademicSvPassword(academicSupervisorRequest.getAcademicSvPassword());
		academicSupervisorEntity.setAcademicSvGender(academicSupervisorRequest.getAcademicSvGender());
		academicSupervisorEntity.setAcademicSvPosition(academicSupervisorRequest.getAcademicSvPosition());
		academicSupervisorEntity.setAcademicSvCoordinator(academicSupervisorRequest.getAcademicSvCoordinator());
		
		AcademicSupervisorEntity updatedAcademicSupervisorEntity = academicSupervisorRepository.save(academicSupervisorEntity);
		
		if (BaseUtility.isObjectNotNull(updatedAcademicSupervisorEntity)) {
			academicSupervisorResponse = new AcademicSupervisorResponse();
			
			academicSupervisorResponse.setAcademicSvId(updatedAcademicSupervisorEntity.getAcademicSvId());
			academicSupervisorResponse.setAcademicSvName(updatedAcademicSupervisorEntity.getAcademicSvName());
			academicSupervisorResponse.setAcademicSvPhone(updatedAcademicSupervisorEntity.getAcademicSvPhone());
			academicSupervisorResponse.setAcademicSvEmail(updatedAcademicSupervisorEntity.getAcademicSvEmail());
			academicSupervisorResponse.setAcademicSvPassword(updatedAcademicSupervisorEntity.getAcademicSvPassword());
			academicSupervisorResponse.setAcademicSvGender(updatedAcademicSupervisorEntity.getAcademicSvGender());
			academicSupervisorResponse.setAcademicSvPosition(updatedAcademicSupervisorEntity.getAcademicSvPosition());
			academicSupervisorResponse.setAcademicSvCoordinator(updatedAcademicSupervisorEntity.getAcademicSvCoordinator());
		}

		return academicSupervisorResponse;
	}

	@Transactional
	public Boolean deleteAcademicSupervisorByAcademicSvId(String academicSvId) {
		Integer totalSupervisorDeleted = 0;
		
		try {
			totalSupervisorDeleted = academicSupervisorRepository.deleteByAcademicSvId(academicSvId);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		
		if (totalSupervisorDeleted > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<IndustrySupervisorResponse> filterIndustrySupervisors(IndustrySupervisorRequest industrySupervisorRequest) {
		List<IndustrySupervisorResponse> industrySupervisorResponses = new ArrayList<IndustrySupervisorResponse>();
		List<IndustrySupervisorResponse> filteredIndustrySupervisorResponses = new ArrayList<IndustrySupervisorResponse>();
		
		List<IndustrySupervisorEntity> industrySupervisorEntities = industrySupervisorRepository.findAll();
		if (industrySupervisorEntities.size() > 0) {
			for (IndustrySupervisorEntity industrySupervisorEntity : industrySupervisorEntities) {
				IndustrySupervisorResponse industrySupervisorResponse = new IndustrySupervisorResponse();

				industrySupervisorResponse.setIndustrySvId(industrySupervisorEntity.getIndustrySvId());
				industrySupervisorResponse.setIndustrySvName(industrySupervisorEntity.getIndustrySvName());
				industrySupervisorResponse.setIndustrySvPhone(industrySupervisorEntity.getIndustrySvPhone());
				industrySupervisorResponse.setIndustrySvEmail(industrySupervisorEntity.getIndustrySvEmail());
				industrySupervisorResponse.setIndustrySvPassword(industrySupervisorEntity.getIndustrySvPassword());
				industrySupervisorResponse.setIndustrySvGender(industrySupervisorEntity.getIndustrySvGender());
				industrySupervisorResponse.setIndustrySvPosition(industrySupervisorEntity.getIndustrySvPosition());
				industrySupervisorResponse.setCompanyId(industrySupervisorEntity.getCompanyId());
				
				industrySupervisorResponses.add(industrySupervisorResponse);
			}
		}
		
		for (IndustrySupervisorResponse industrySupervisorResponse : industrySupervisorResponses) {
			Boolean addRow = true;
			
			if (BaseUtility.isNotBlank(industrySupervisorRequest.getCompanyId()) && !industrySupervisorRequest.getCompanyId().equals(industrySupervisorResponse.getCompanyId())) {
				addRow = false;
			}
			
			if (addRow) {
				filteredIndustrySupervisorResponses.add(industrySupervisorResponse);
			}
		}
		
		return filteredIndustrySupervisorResponses;
	}

	@Override
	public List<List<String>> getTotalIndustrySupervisors() {
		List<List<String>> chartDataList = new ArrayList<>();
		
		List<CompanyEntity> existedCompanyEntities = companyRepository.findAllByOrderByCompanyName();
		List<IndustrySupervisorEntity> existedIndustrySupervisorEntities = industrySupervisorRepository.findAll();
		
		for (CompanyEntity companyEntity : existedCompanyEntities) {
			Integer totalOngoingStatus = 0;
			
			for (IndustrySupervisorEntity industrySupervisorEntity : existedIndustrySupervisorEntities) {
				if (BaseUtility.isNotBlank(industrySupervisorEntity.getCompanyId())) {
					if (companyEntity.getCompanyId().equals(industrySupervisorEntity.getCompanyId())) {
						totalOngoingStatus++;
					}
				}
			}
			List<String> chartData = new ArrayList<String>();
			
			chartData.add(companyEntity.getCompanyName());
			chartData.add(totalOngoingStatus.toString());
			chartDataList.add(chartData);
		}
		
		return chartDataList;
	}

	@Override
	public IndustrySupervisorResponse getIndustrySupervisorByIndustrySvId(String industrySvId) {
		IndustrySupervisorResponse industrySupervisorResponse = null;
		IndustrySupervisorEntity existedIndustrySupervisorEntity = industrySupervisorRepository.findByIndustrySvId(industrySvId);
		
		if (BaseUtility.isObjectNotNull(existedIndustrySupervisorEntity)) {
			industrySupervisorResponse = new IndustrySupervisorResponse();
			
			industrySupervisorResponse.setIndustrySvId(existedIndustrySupervisorEntity.getIndustrySvId());
			industrySupervisorResponse.setIndustrySvName(existedIndustrySupervisorEntity.getIndustrySvName());
			industrySupervisorResponse.setIndustrySvPhone(existedIndustrySupervisorEntity.getIndustrySvPhone());
			industrySupervisorResponse.setIndustrySvEmail(existedIndustrySupervisorEntity.getIndustrySvEmail());
			industrySupervisorResponse.setIndustrySvPassword(existedIndustrySupervisorEntity.getIndustrySvPassword());
			industrySupervisorResponse.setIndustrySvGender(existedIndustrySupervisorEntity.getIndustrySvGender());
			industrySupervisorResponse.setIndustrySvPosition(existedIndustrySupervisorEntity.getIndustrySvPosition());
			industrySupervisorResponse.setCompanyId(existedIndustrySupervisorEntity.getCompanyId());
			
			if (BaseUtility.isNotBlank(existedIndustrySupervisorEntity.getCompanyId())) {
				CompanyEntity existedCompanyEntity = companyRepository.findByCompanyId(existedIndustrySupervisorEntity.getCompanyId());
				
				if (BaseUtility.isObjectNotNull(existedCompanyEntity)) {
					CompanyResponse companyResponse = new CompanyResponse();
					
					companyResponse.setCompanyName(existedCompanyEntity.getCompanyName());
					companyResponse.setCompanyAddress(existedCompanyEntity.getCompanyAddress());
					companyResponse.setCompanyPhone(existedCompanyEntity.getCompanyPhone());
					companyResponse.setCompanyEmail(existedCompanyEntity.getCompanyEmail());
					companyResponse.setCompanyWebsite(existedCompanyEntity.getCompanyWebsite());
					companyResponse.setCompanyBrochure(existedCompanyEntity.getCompanyBrochure());
					companyResponse.setCompanyBrochureFileName(existedCompanyEntity.getCompanyBrochureFileName());
					companyResponse.setCompanyHrName(existedCompanyEntity.getCompanyHrName());
					companyResponse.setCompanyHrPhone(existedCompanyEntity.getCompanyHrPhone());
					companyResponse.setCompanyHrEmail(existedCompanyEntity.getCompanyHrEmail());
					companyResponse.setCompanyHrGender(existedCompanyEntity.getCompanyHrGender());
					
					industrySupervisorResponse.setCompany(companyResponse);
				}
			}
		}
		
		return industrySupervisorResponse;
	}

	@Override
	public IndustrySupervisorResponse insertIndustrySupervisor(IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		IndustrySupervisorResponse industrySupervisorResponse = null;
		IndustrySupervisorEntity newIndustrySupervisorEntity = new IndustrySupervisorEntity();
		
		newIndustrySupervisorEntity.setIndustrySvId(BaseUtility.generateId());
		newIndustrySupervisorEntity.setIndustrySvName(industrySupervisorRequest.getIndustrySvName());
		newIndustrySupervisorEntity.setIndustrySvPhone(industrySupervisorRequest.getIndustrySvPhone());
		newIndustrySupervisorEntity.setIndustrySvEmail(industrySupervisorRequest.getIndustrySvEmail());
		newIndustrySupervisorEntity.setIndustrySvPassword(industrySupervisorRequest.getIndustrySvPassword());
		newIndustrySupervisorEntity.setIndustrySvGender(industrySupervisorRequest.getIndustrySvGender());
		newIndustrySupervisorEntity.setIndustrySvPosition(industrySupervisorRequest.getIndustrySvPosition());
		newIndustrySupervisorEntity.setCompanyId(industrySupervisorRequest.getCompanyId());
		
		IndustrySupervisorEntity insertedIndustrySupervisorEntity = industrySupervisorRepository.save(newIndustrySupervisorEntity);

		if (BaseUtility.isObjectNotNull(insertedIndustrySupervisorEntity)) {
			industrySupervisorResponse = new IndustrySupervisorResponse();
			
			industrySupervisorResponse.setIndustrySvId(insertedIndustrySupervisorEntity.getIndustrySvId());
			industrySupervisorResponse.setIndustrySvName(insertedIndustrySupervisorEntity.getIndustrySvName());
			industrySupervisorResponse.setIndustrySvPhone(insertedIndustrySupervisorEntity.getIndustrySvPhone());
			industrySupervisorResponse.setIndustrySvEmail(insertedIndustrySupervisorEntity.getIndustrySvEmail());
			industrySupervisorResponse.setIndustrySvPassword(insertedIndustrySupervisorEntity.getIndustrySvPassword());
			industrySupervisorResponse.setIndustrySvGender(insertedIndustrySupervisorEntity.getIndustrySvGender());
			industrySupervisorResponse.setIndustrySvPosition(insertedIndustrySupervisorEntity.getIndustrySvPosition());
			industrySupervisorResponse.setCompanyId(insertedIndustrySupervisorEntity.getCompanyId());
		}

		return industrySupervisorResponse;
	}

	@Override
	public IndustrySupervisorResponse updateIndustrySupervisor(IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		IndustrySupervisorResponse industrySupervisorResponse = null;
		IndustrySupervisorEntity industrySupervisorEntity = industrySupervisorRepository.findByIndustrySvId(industrySupervisorRequest.getIndustrySvId());
		
//		industrySupervisorEntity.setIndustrySvId(BaseUtility.generateId());
		industrySupervisorEntity.setIndustrySvName(industrySupervisorRequest.getIndustrySvName());
		industrySupervisorEntity.setIndustrySvPhone(industrySupervisorRequest.getIndustrySvPhone());
		industrySupervisorEntity.setIndustrySvEmail(industrySupervisorRequest.getIndustrySvEmail());
		industrySupervisorEntity.setIndustrySvPassword(industrySupervisorRequest.getIndustrySvPassword());
		industrySupervisorEntity.setIndustrySvGender(industrySupervisorRequest.getIndustrySvGender());
		industrySupervisorEntity.setIndustrySvPosition(industrySupervisorRequest.getIndustrySvPosition());
		industrySupervisorEntity.setCompanyId(industrySupervisorRequest.getCompanyId());
		
		IndustrySupervisorEntity updatedIndustrySupervisorEntity = industrySupervisorRepository.save(industrySupervisorEntity);

		if (BaseUtility.isObjectNotNull(updatedIndustrySupervisorEntity)) {
			industrySupervisorResponse = new IndustrySupervisorResponse();
			
			industrySupervisorResponse.setIndustrySvId(updatedIndustrySupervisorEntity.getIndustrySvId());
			industrySupervisorResponse.setIndustrySvName(updatedIndustrySupervisorEntity.getIndustrySvName());
			industrySupervisorResponse.setIndustrySvPhone(updatedIndustrySupervisorEntity.getIndustrySvPhone());
			industrySupervisorResponse.setIndustrySvEmail(updatedIndustrySupervisorEntity.getIndustrySvEmail());
			industrySupervisorResponse.setIndustrySvPassword(updatedIndustrySupervisorEntity.getIndustrySvPassword());
			industrySupervisorResponse.setIndustrySvGender(updatedIndustrySupervisorEntity.getIndustrySvGender());
			industrySupervisorResponse.setIndustrySvPosition(updatedIndustrySupervisorEntity.getIndustrySvPosition());
			industrySupervisorResponse.setCompanyId(updatedIndustrySupervisorEntity.getCompanyId());
		}

		return industrySupervisorResponse;
	}

	@Transactional
	public Boolean deleteIndustrySupervisorByIndustrySvId(String industrySvId) {
		Integer totalSupervisorDeleted = 0;
		
		try {
			totalSupervisorDeleted = industrySupervisorRepository.deleteByIndustrySvId(industrySvId);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		
		if (totalSupervisorDeleted > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public List<StudentResponse> getStudents() {
		List<StudentResponse> studentResponses = new ArrayList<StudentResponse>();
		List<StudentEntity> studentEntities = studentRepository.findAll();
		
		for (StudentEntity studentEntity : studentEntities) {
			StudentResponse studentResponse = new StudentResponse();
			
			studentResponse.setStudentMatricNum(studentEntity.getStudentMatricNum());
			studentResponse.setStudentName(studentEntity.getStudentName());
			studentResponse.setStudentAddress(studentEntity.getStudentAddress());
			studentResponse.setStudentEmail(studentEntity.getStudentEmail());
			studentResponse.setStudentPhone(studentEntity.getStudentPhone());
			studentResponse.setStudentPassword(studentEntity.getStudentPassword());
			studentResponse.setStudentCampus(studentEntity.getStudentCampus());
			studentResponse.setStudentCourse(studentEntity.getStudentCourse());
			studentResponse.setStudentClass(studentEntity.getStudentClass());
			studentResponse.setStudentProject(studentEntity.getStudentProject());
			studentResponse.setStudentStatus(studentEntity.getStudentStatus());
			studentResponse.setStudentCV(studentEntity.getStudentCV());
			studentResponse.setStudentCVFileName(studentEntity.getStudentCVFileName());
			studentResponse.setStudentMiniTranscript(studentEntity.getStudentMiniTranscript());
			studentResponse.setStudentMiniTranscriptFileName(studentEntity.getStudentMiniTranscriptFileName());
			studentResponse.setStudentCoverLetter(studentEntity.getStudentCoverLetter());
			studentResponse.setStudentCoverLetterFileName(studentEntity.getStudentCoverLetterFileName());
			studentResponse.setStudentCourseOutline(studentEntity.getStudentCourseOutline());
			studentResponse.setStudentCourseOutlineFileName(studentEntity.getStudentCourseOutlineFileName());
			studentResponse.setStudentSL(studentEntity.getStudentSL());
			studentResponse.setStudentSLFileName(studentEntity.getStudentSLFileName());
			studentResponse.setIndustrySvId(studentEntity.getIndustrySvId());
			studentResponse.setAcademicSvId(studentEntity.getAcademicSvId());
			studentResponse.setCoordinatorId(studentEntity.getCoordinatorId());
			
			studentResponses.add(studentResponse);
		}
		
		return studentResponses;
	}

	@Override
	public List<StudentResponse> filterStudents(StudentRequest studentRequest) {
		List<StudentResponse> studentResponses = new ArrayList<StudentResponse>();
		List<StudentEntity> studentEntities = studentRepository.findAll();
		
		for (StudentEntity studentEntity : studentEntities) {
			StudentResponse studentResponse = new StudentResponse();
			
			studentResponse.setStudentMatricNum(studentEntity.getStudentMatricNum());
			studentResponse.setStudentName(studentEntity.getStudentName());
			studentResponse.setStudentAddress(studentEntity.getStudentAddress());
			studentResponse.setStudentEmail(studentEntity.getStudentEmail());
			studentResponse.setStudentPhone(studentEntity.getStudentPhone());
			studentResponse.setStudentPassword(studentEntity.getStudentPassword());
			studentResponse.setStudentCampus(studentEntity.getStudentCampus());
			studentResponse.setStudentCourse(studentEntity.getStudentCourse());
			studentResponse.setStudentClass(studentEntity.getStudentClass());
			studentResponse.setStudentProject(studentEntity.getStudentProject());
			studentResponse.setStudentStatus(studentEntity.getStudentStatus());
			studentResponse.setStudentCV(studentEntity.getStudentCV());
			studentResponse.setStudentCVFileName(studentEntity.getStudentCVFileName());
			studentResponse.setStudentMiniTranscript(studentEntity.getStudentMiniTranscript());
			studentResponse.setStudentMiniTranscriptFileName(studentEntity.getStudentMiniTranscriptFileName());
			studentResponse.setStudentCoverLetter(studentEntity.getStudentCoverLetter());
			studentResponse.setStudentCoverLetterFileName(studentEntity.getStudentCoverLetterFileName());
			studentResponse.setStudentCourseOutline(studentEntity.getStudentCourseOutline());
			studentResponse.setStudentCourseOutlineFileName(studentEntity.getStudentCourseOutlineFileName());
			studentResponse.setStudentSL(studentEntity.getStudentSL());
			studentResponse.setStudentSLFileName(studentEntity.getStudentSLFileName());
			studentResponse.setIndustrySvId(studentEntity.getIndustrySvId());
			studentResponse.setAcademicSvId(studentEntity.getAcademicSvId());
			studentResponse.setCoordinatorId(studentEntity.getCoordinatorId());
			
			List<StudentEvaluationEntity> existedStudentEvaluationEntities = studentEvaluationRepository.findByStudentMatricNum(studentEntity.getStudentMatricNum());
			List<String> evaluationIds = new ArrayList<String>();
			if (existedStudentEvaluationEntities.size() > 0) {
				for (StudentEvaluationEntity existedStudentEvaluationEntity : existedStudentEvaluationEntities) {
					evaluationIds.add(existedStudentEvaluationEntity.getEvaluationId());
				}
				
				studentResponse.setEvaluationIds(evaluationIds);
			}
			
			if (BaseUtility.isNotBlank(studentEntity.getIndustrySvId())) {
				IndustrySupervisorEntity existedIndustrySupervisorEntity = industrySupervisorRepository.findByIndustrySvId(studentEntity.getIndustrySvId());
				
				if (BaseUtility.isObjectNotNull(existedIndustrySupervisorEntity)) {
					IndustrySupervisorResponse industrySupervisorResponse = new IndustrySupervisorResponse();
					
					industrySupervisorResponse.setIndustrySvId(existedIndustrySupervisorEntity.getIndustrySvId());
					industrySupervisorResponse.setIndustrySvName(existedIndustrySupervisorEntity.getIndustrySvName());
					industrySupervisorResponse.setIndustrySvPhone(existedIndustrySupervisorEntity.getIndustrySvPhone());
					industrySupervisorResponse.setIndustrySvEmail(existedIndustrySupervisorEntity.getIndustrySvEmail());
					industrySupervisorResponse.setIndustrySvPassword(existedIndustrySupervisorEntity.getIndustrySvPassword());
					industrySupervisorResponse.setIndustrySvGender(existedIndustrySupervisorEntity.getIndustrySvGender());
					industrySupervisorResponse.setIndustrySvPosition(existedIndustrySupervisorEntity.getIndustrySvPosition());
					industrySupervisorResponse.setCompanyId(existedIndustrySupervisorEntity.getCompanyId());
					
					if (BaseUtility.isNotBlank(existedIndustrySupervisorEntity.getCompanyId())) {
						CompanyEntity existedCompanyEntity = companyRepository.findByCompanyId(existedIndustrySupervisorEntity.getCompanyId());
						
						if (BaseUtility.isObjectNotNull(existedCompanyEntity)) {
							CompanyResponse companyResponse = new CompanyResponse();
							
							companyResponse.setCompanyName(existedCompanyEntity.getCompanyName());
							companyResponse.setCompanyAddress(existedCompanyEntity.getCompanyAddress());
							companyResponse.setCompanyPhone(existedCompanyEntity.getCompanyPhone());
							companyResponse.setCompanyEmail(existedCompanyEntity.getCompanyEmail());
							companyResponse.setCompanyWebsite(existedCompanyEntity.getCompanyWebsite());
							companyResponse.setCompanyBrochure(existedCompanyEntity.getCompanyBrochure());
							companyResponse.setCompanyBrochureFileName(existedCompanyEntity.getCompanyBrochureFileName());
							companyResponse.setCompanyHrName(existedCompanyEntity.getCompanyHrName());
							companyResponse.setCompanyHrPhone(existedCompanyEntity.getCompanyHrPhone());
							companyResponse.setCompanyHrEmail(existedCompanyEntity.getCompanyHrEmail());
							companyResponse.setCompanyHrGender(existedCompanyEntity.getCompanyHrGender());
							
							industrySupervisorResponse.setCompany(companyResponse);
						}
					}
					
					studentResponse.setIndustrySv(industrySupervisorResponse);
				}
			}
			
			if (BaseUtility.isNotBlank(studentEntity.getAcademicSvId())) {
				AcademicSupervisorEntity existedAcademicSupervisorEntity = academicSupervisorRepository.findByAcademicSvId(studentEntity.getAcademicSvId());
				
				if (BaseUtility.isObjectNotNull(existedAcademicSupervisorEntity)) {
					AcademicSupervisorResponse academicSupervisorResponse = new AcademicSupervisorResponse();
					
					academicSupervisorResponse.setAcademicSvId(existedAcademicSupervisorEntity.getAcademicSvId());
					academicSupervisorResponse.setAcademicSvName(existedAcademicSupervisorEntity.getAcademicSvName());
					academicSupervisorResponse.setAcademicSvPhone(existedAcademicSupervisorEntity.getAcademicSvPhone());
					academicSupervisorResponse.setAcademicSvEmail(existedAcademicSupervisorEntity.getAcademicSvEmail());
					academicSupervisorResponse.setAcademicSvPassword(existedAcademicSupervisorEntity.getAcademicSvPassword());
					academicSupervisorResponse.setAcademicSvGender(existedAcademicSupervisorEntity.getAcademicSvGender());
					academicSupervisorResponse.setAcademicSvPosition(existedAcademicSupervisorEntity.getAcademicSvPosition());
					academicSupervisorResponse.setAcademicSvCoordinator(existedAcademicSupervisorEntity.getAcademicSvCoordinator());
					
					studentResponse.setAcademicSv(academicSupervisorResponse);
				}
			}
			
			List<StudentSemesterResponse> studentSemesterResponses = new ArrayList<StudentSemesterResponse>();
			List<StudentSemesterEntity> existedStudentSemesterEntities = studentSemesterRepository.findByStudentMatricNum(studentEntity.getStudentMatricNum());
			
			for (StudentSemesterEntity existedStudentSemesterEntity : existedStudentSemesterEntities) {
				StudentSemesterResponse studentSemesterResponse = new StudentSemesterResponse();
				
				studentSemesterResponse.setStudentSemesterId(existedStudentSemesterEntity.getStudentSemesterId());
				studentSemesterResponse.setStudentMatricNum(existedStudentSemesterEntity.getStudentMatricNum());
				studentSemesterResponse.setSemesterId(existedStudentSemesterEntity.getSemesterId());
				
				if (BaseUtility.isNotBlank(existedStudentSemesterEntity.getSemesterId())) {
					SemesterEntity existedSemesterEntity = semesterRepository.findBySemesterId(existedStudentSemesterEntity.getSemesterId());
					
					if (BaseUtility.isObjectNotNull(existedSemesterEntity)) {
						SemesterResponse semesterResponse = new SemesterResponse();
						
						semesterResponse.setSemesterId(existedSemesterEntity.getSemesterId());
						semesterResponse.setSemesterCode(existedSemesterEntity.getSemesterCode());
						semesterResponse.setSemesterPart(existedSemesterEntity.getSemesterPart());
						semesterResponse.setSemesterStatus(existedSemesterEntity.getSemesterStatus());
//						semesterResponse.setSemesterStartDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterStartDate()));
//						semesterResponse.setSemesterEndDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterEndDate()));
						semesterResponse.setSemesterStartEvaluateDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterStartEvaluateDate()));
						semesterResponse.setSemesterEndEvaluateDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterEndEvaluateDate()));
						
						studentSemesterResponse.setSemester(semesterResponse);
					}
				}
				studentSemesterResponses.add(studentSemesterResponse);
			}
			
			studentResponse.setStudentSemesters(studentSemesterResponses);
			
			Boolean addRow = true;
			
			if (BaseUtility.isNotBlank(studentRequest.getStudentCampus()) && !studentRequest.getStudentCampus().equals(studentEntity.getStudentCampus())) {
				addRow = false;
			}
			if (BaseUtility.isNotBlank(studentRequest.getStudentCourse()) && !studentRequest.getStudentCourse().equals(studentEntity.getStudentCourse())) {
				addRow = false;
			}
			if (BaseUtility.isNotBlank(studentRequest.getStudentClass()) && !studentRequest.getStudentClass().equals(studentEntity.getStudentClass())) {
				addRow = false;
			}
			if (BaseUtility.isNotBlank(studentRequest.getAcademicSvId()) && !studentRequest.getAcademicSvId().equals(studentEntity.getAcademicSvId())) {
				addRow = false;
			}
			if (BaseUtility.isNotBlank(studentRequest.getIndustrySvId()) && !studentRequest.getIndustrySvId().equals(studentEntity.getIndustrySvId())) {
				addRow = false;
			}
			
			if (addRow) {
				studentResponses.add(studentResponse);
			}
		}

		return studentResponses;
	}

	@Override
	public List<List<String>> getTotalStudents() {
		List<List<String>> chartDataList = new ArrayList<>();
		
		List<CompanyEntity> existedCompanyEntities = companyRepository.findAllByOrderByCompanyName();
		List<StudentEntity> existedStudentEntities = studentRepository.findAll();
		
		for (CompanyEntity companyEntity : existedCompanyEntities) {
			Integer totalOngoingStatus = 0;
			Integer totalCompletedStatus = 0;
			
			for (StudentEntity studentEntity : existedStudentEntities) {
				if (BaseUtility.isNotBlank(studentEntity.getIndustrySvId())) {
					IndustrySupervisorEntity existedIndustrySupervisorEntity = industrySupervisorRepository.findByIndustrySvId(studentEntity.getIndustrySvId());
					
					if (BaseUtility.isObjectNotNull(existedIndustrySupervisorEntity)) {
						if (existedIndustrySupervisorEntity.getCompanyId().equals(companyEntity.getCompanyId())) {
							if (BaseUtility.isNotBlank(studentEntity.getStudentStatus())) {
								switch (studentEntity.getStudentStatus()) {
								case "ONG":
									totalOngoingStatus++;
									break;
								case "CMP":
									totalCompletedStatus++;
									break;
								default:
									break;
								}
							}
						}
					}
				}
			}
			List<String> chartData = new ArrayList<String>();
			
			chartData.add(companyEntity.getCompanyName());
			chartData.add(totalOngoingStatus.toString());
			chartData.add(totalCompletedStatus.toString());
			chartDataList.add(chartData);
		}
		
		return chartDataList;
	}

	@Override
	public StudentResponse getStudentByStudentMatricNum(String studentMatricNum) throws Exception {
		StudentResponse studentResponse = null;
		StudentEntity existedStudentEntity = studentRepository.findByStudentMatricNum(studentMatricNum);

		if (BaseUtility.isObjectNotNull(existedStudentEntity)) {
			studentResponse = new StudentResponse();
			
			studentResponse.setStudentMatricNum(existedStudentEntity.getStudentMatricNum());
			studentResponse.setStudentName(existedStudentEntity.getStudentName());
			studentResponse.setStudentAddress(existedStudentEntity.getStudentAddress());
			studentResponse.setStudentEmail(existedStudentEntity.getStudentEmail());
			studentResponse.setStudentPhone(existedStudentEntity.getStudentPhone());
			studentResponse.setStudentPassword(existedStudentEntity.getStudentPassword());
			studentResponse.setStudentCampus(existedStudentEntity.getStudentCampus());
			studentResponse.setStudentCourse(existedStudentEntity.getStudentCourse());
			studentResponse.setStudentClass(existedStudentEntity.getStudentClass());
			studentResponse.setStudentProject(existedStudentEntity.getStudentProject());
			studentResponse.setStudentStatus(existedStudentEntity.getStudentStatus());
			studentResponse.setStudentCV(existedStudentEntity.getStudentCV());
			studentResponse.setStudentCVFileName(existedStudentEntity.getStudentCVFileName());
			studentResponse.setStudentMiniTranscript(existedStudentEntity.getStudentMiniTranscript());
			studentResponse.setStudentMiniTranscriptFileName(existedStudentEntity.getStudentMiniTranscriptFileName());
			studentResponse.setStudentCoverLetter(existedStudentEntity.getStudentCoverLetter());
			studentResponse.setStudentCoverLetterFileName(existedStudentEntity.getStudentCoverLetterFileName());
			studentResponse.setStudentCourseOutline(existedStudentEntity.getStudentCourseOutline());
			studentResponse.setStudentCourseOutlineFileName(existedStudentEntity.getStudentCourseOutlineFileName());
			studentResponse.setStudentSL(existedStudentEntity.getStudentSL());
			studentResponse.setStudentSLFileName(existedStudentEntity.getStudentSLFileName());
			studentResponse.setIndustrySvId(existedStudentEntity.getIndustrySvId());
			studentResponse.setAcademicSvId(existedStudentEntity.getAcademicSvId());
			studentResponse.setCoordinatorId(existedStudentEntity.getCoordinatorId());
			
			List<StudentEvaluationEntity> existedStudentEvaluationEntities = studentEvaluationRepository.findByStudentMatricNum(existedStudentEntity.getStudentMatricNum());
			List<String> evaluationIds = new ArrayList<String>();
			if (existedStudentEvaluationEntities.size() > 0) {
				for (StudentEvaluationEntity existedStudentEvaluationEntity : existedStudentEvaluationEntities) {
					evaluationIds.add(existedStudentEvaluationEntity.getEvaluationId());
				}
				
				studentResponse.setEvaluationIds(evaluationIds);
			}
			
			if (BaseUtility.isNotBlank(existedStudentEntity.getAcademicSvId())) {
				studentResponse.setAcademicSv(getAcademicSupervisorByAcademicSvId(existedStudentEntity.getAcademicSvId()));
			}
			if (BaseUtility.isNotBlank(existedStudentEntity.getIndustrySvId())) {
				studentResponse.setIndustrySv(getIndustrySupervisorByIndustrySvId(existedStudentEntity.getIndustrySvId()));
			}
			
			List<StudentSemesterResponse> studentSemesterResponses = new ArrayList<StudentSemesterResponse>();
			List<StudentSemesterEntity> existedStudentSemesterEntities = studentSemesterRepository.findByStudentMatricNum(existedStudentEntity.getStudentMatricNum());
			
			for (StudentSemesterEntity existedStudentSemesterEntity : existedStudentSemesterEntities) {
				StudentSemesterResponse studentSemesterResponse = new StudentSemesterResponse();
				
				studentSemesterResponse.setStudentSemesterId(existedStudentSemesterEntity.getStudentSemesterId());
				studentSemesterResponse.setStudentMatricNum(existedStudentSemesterEntity.getStudentMatricNum());
				studentSemesterResponse.setSemesterId(existedStudentSemesterEntity.getSemesterId());
				
				if (BaseUtility.isNotBlank(existedStudentSemesterEntity.getSemesterId())) {
					SemesterEntity existedSemesterEntity = semesterRepository.findBySemesterId(existedStudentSemesterEntity.getSemesterId());
					
					if (BaseUtility.isObjectNotNull(existedSemesterEntity)) {
						SemesterResponse semesterResponse = new SemesterResponse();
						
						semesterResponse.setSemesterId(existedSemesterEntity.getSemesterId());
						semesterResponse.setSemesterCode(existedSemesterEntity.getSemesterCode());
						semesterResponse.setSemesterPart(existedSemesterEntity.getSemesterPart());
						semesterResponse.setSemesterStatus(existedSemesterEntity.getSemesterStatus());
//						semesterResponse.setSemesterStartDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterStartDate()));
//						semesterResponse.setSemesterEndDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterEndDate()));
						semesterResponse.setSemesterStartEvaluateDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterStartEvaluateDate()));
						semesterResponse.setSemesterEndEvaluateDate(DateUtility.convertToLocalDateTime(existedSemesterEntity.getSemesterEndEvaluateDate()));
						
						studentSemesterResponse.setSemester(semesterResponse);
					}
				}
				
				studentSemesterResponses.add(studentSemesterResponse);
			}
			
			studentResponse.setStudentSemesters(studentSemesterResponses);
			
			return studentResponse;
		}
		
		return studentResponse;
	}

	@Override
	public StudentResponse insertStudent(StudentRequest studentRequest, MultipartFile cvFile, MultipartFile mtFile, MultipartFile clFile, MultipartFile coFile, MultipartFile slFile) throws Exception {
		StudentResponse studentResponse = new StudentResponse();
		StudentEntity newStudentEntity = new StudentEntity();
		StudentEntity existedStudentEntity = studentRepository.findByStudentMatricNum(studentRequest.getStudentMatricNum());
		
		newStudentEntity.setStudentMatricNum(studentRequest.getStudentMatricNum());
		newStudentEntity.setStudentName(studentRequest.getStudentName());
		newStudentEntity.setStudentAddress(studentRequest.getStudentAddress());
		newStudentEntity.setStudentEmail(studentRequest.getStudentEmail());
		newStudentEntity.setStudentPhone(studentRequest.getStudentPhone());
		newStudentEntity.setStudentPassword(studentRequest.getStudentPassword());
		newStudentEntity.setStudentCampus(studentRequest.getStudentCampus());
		newStudentEntity.setStudentCourse(studentRequest.getStudentCourse());
		newStudentEntity.setStudentClass(studentRequest.getStudentClass());
		newStudentEntity.setStudentProject(studentRequest.getStudentProject());
		if (BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentStatus(existedStudentEntity.getStudentStatus());
		}
		
		if (cvFile != null) {
			newStudentEntity.setStudentCV(cvFile.getBytes());
			newStudentEntity.setStudentCVFileName(cvFile.getOriginalFilename());
		} else if (cvFile == null && BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentCV(existedStudentEntity.getStudentCV());
			newStudentEntity.setStudentCVFileName(existedStudentEntity.getStudentCVFileName());
		}
		
		if (mtFile != null) {
			newStudentEntity.setStudentMiniTranscript(mtFile.getBytes());
			newStudentEntity.setStudentMiniTranscriptFileName(mtFile.getOriginalFilename());
		} else if (mtFile == null && BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentMiniTranscript(existedStudentEntity.getStudentMiniTranscript());
			newStudentEntity.setStudentMiniTranscriptFileName(existedStudentEntity.getStudentMiniTranscriptFileName());
		}
		
		if (clFile != null) {
			newStudentEntity.setStudentCoverLetter(clFile.getBytes());
			newStudentEntity.setStudentCoverLetterFileName(clFile.getOriginalFilename());
		} else if (clFile == null && BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentCoverLetter(existedStudentEntity.getStudentCoverLetter());
			newStudentEntity.setStudentCoverLetterFileName(existedStudentEntity.getStudentCoverLetterFileName());
		}
		
		if (coFile != null) {
			newStudentEntity.setStudentCourseOutline(coFile.getBytes());
			newStudentEntity.setStudentCourseOutlineFileName(coFile.getOriginalFilename());
		} else if (coFile == null && BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentCourseOutline(existedStudentEntity.getStudentCourseOutline());
			newStudentEntity.setStudentCourseOutlineFileName(existedStudentEntity.getStudentCourseOutlineFileName());
		}
		
		if (slFile != null) {
			newStudentEntity.setStudentSL(slFile.getBytes());
			newStudentEntity.setStudentSLFileName(slFile.getOriginalFilename());
		} else if (slFile == null && BaseUtility.isObjectNotNull(existedStudentEntity)) {
			newStudentEntity.setStudentSL(existedStudentEntity.getStudentSL());
			newStudentEntity.setStudentSLFileName(existedStudentEntity.getStudentSLFileName());
		}
		
		newStudentEntity.setIndustrySvId(studentRequest.getIndustrySvId());
		newStudentEntity.setAcademicSvId(studentRequest.getAcademicSvId());
		newStudentEntity.setCoordinatorId(studentRequest.getCoordinatorId());
		
		StudentEntity insertedStudentEntity = studentRepository.save(newStudentEntity);
		
		if (BaseUtility.isObjectNotNull(insertedStudentEntity)) {
			studentResponse.setStudentMatricNum(insertedStudentEntity.getStudentMatricNum());
			studentResponse.setStudentName(insertedStudentEntity.getStudentName());
			studentResponse.setStudentAddress(insertedStudentEntity.getStudentAddress());
			studentResponse.setStudentEmail(insertedStudentEntity.getStudentEmail());
			studentResponse.setStudentPhone(insertedStudentEntity.getStudentPhone());
			studentResponse.setStudentPassword(insertedStudentEntity.getStudentPassword());
			studentResponse.setStudentCampus(insertedStudentEntity.getStudentCampus());
			studentResponse.setStudentCourse(insertedStudentEntity.getStudentCourse());
			studentResponse.setStudentClass(insertedStudentEntity.getStudentClass());
			studentResponse.setStudentProject(insertedStudentEntity.getStudentProject());
			studentResponse.setStudentStatus(insertedStudentEntity.getStudentStatus());
			studentResponse.setStudentCV(insertedStudentEntity.getStudentCV());
			studentResponse.setStudentCVFileName(insertedStudentEntity.getStudentCVFileName());
			studentResponse.setStudentMiniTranscript(insertedStudentEntity.getStudentMiniTranscript());
			studentResponse.setStudentMiniTranscriptFileName(insertedStudentEntity.getStudentMiniTranscriptFileName());
			studentResponse.setStudentCoverLetter(insertedStudentEntity.getStudentCoverLetter());
			studentResponse.setStudentCoverLetterFileName(insertedStudentEntity.getStudentCoverLetterFileName());
			studentResponse.setStudentCourseOutline(insertedStudentEntity.getStudentCourseOutline());
			studentResponse.setStudentCourseOutlineFileName(insertedStudentEntity.getStudentCourseOutlineFileName());
			studentResponse.setStudentSL(insertedStudentEntity.getStudentSL());
			studentResponse.setStudentSLFileName(insertedStudentEntity.getStudentSLFileName());
			studentResponse.setIndustrySvId(insertedStudentEntity.getIndustrySvId());
			studentResponse.setAcademicSvId(insertedStudentEntity.getAcademicSvId());
			studentResponse.setCoordinatorId(insertedStudentEntity.getCoordinatorId());
		} else {
			throw new Exception();
		}
		
		return studentResponse;
	}

	@Override
	public StudentResponse updateStudent(StudentRequest studentRequest, MultipartFile cvFile, MultipartFile mtFile, MultipartFile clFile, MultipartFile coFile, MultipartFile slFile) throws Exception {
		StudentResponse studentResponse = new StudentResponse();
		StudentEntity existedStudentEntity = studentRepository.findByStudentMatricNum(studentRequest.getStudentMatricNum());
		
		if (BaseUtility.isObjectNotNull(existedStudentEntity)) {
			existedStudentEntity.setStudentName(studentRequest.getStudentName());
			existedStudentEntity.setStudentAddress(studentRequest.getStudentAddress());
			existedStudentEntity.setStudentEmail(studentRequest.getStudentEmail());
			existedStudentEntity.setStudentPhone(studentRequest.getStudentPhone());
			existedStudentEntity.setStudentPassword(studentRequest.getStudentPassword());
			existedStudentEntity.setStudentCampus(studentRequest.getStudentCampus());
			existedStudentEntity.setStudentCourse(studentRequest.getStudentCourse());
			existedStudentEntity.setStudentClass(studentRequest.getStudentClass());
			existedStudentEntity.setStudentProject(studentRequest.getStudentProject());
//			existedStudentEntity.setStudentStatus(studentRequest.getStudentStatus());
			
			if (cvFile != null) {
				existedStudentEntity.setStudentCV(cvFile.getBytes());
				existedStudentEntity.setStudentCVFileName(cvFile.getOriginalFilename());
			}
			if (mtFile != null) {
				existedStudentEntity.setStudentMiniTranscript(mtFile.getBytes());
				existedStudentEntity.setStudentMiniTranscriptFileName(mtFile.getOriginalFilename());
			}
			if (clFile != null) {
				existedStudentEntity.setStudentCoverLetter(clFile.getBytes());
				existedStudentEntity.setStudentCoverLetterFileName(clFile.getOriginalFilename());
			}
			if (coFile != null) {
				existedStudentEntity.setStudentCourseOutline(coFile.getBytes());
				existedStudentEntity.setStudentCourseOutlineFileName(coFile.getOriginalFilename());
			}
			if (slFile != null) {
				existedStudentEntity.setStudentSL(slFile.getBytes());
				existedStudentEntity.setStudentSLFileName(slFile.getOriginalFilename());
			}
			existedStudentEntity.setIndustrySvId(studentRequest.getIndustrySvId());
			existedStudentEntity.setAcademicSvId(studentRequest.getAcademicSvId());
			existedStudentEntity.setCoordinatorId(studentRequest.getCoordinatorId());
			
			StudentEntity updatedStudentEntity = studentRepository.save(existedStudentEntity);
			
			if (BaseUtility.isObjectNotNull(updatedStudentEntity)) {
				studentResponse.setStudentMatricNum(updatedStudentEntity.getStudentMatricNum());
				studentResponse.setStudentName(updatedStudentEntity.getStudentName());
				studentResponse.setStudentAddress(updatedStudentEntity.getStudentAddress());
				studentResponse.setStudentEmail(updatedStudentEntity.getStudentEmail());
				studentResponse.setStudentPhone(updatedStudentEntity.getStudentPhone());
				studentResponse.setStudentPassword(updatedStudentEntity.getStudentPassword());
				studentResponse.setStudentCampus(updatedStudentEntity.getStudentCampus());
				studentResponse.setStudentCourse(updatedStudentEntity.getStudentCourse());
				studentResponse.setStudentClass(updatedStudentEntity.getStudentClass());
				studentResponse.setStudentProject(updatedStudentEntity.getStudentProject());
				studentResponse.setStudentStatus(updatedStudentEntity.getStudentStatus());
				studentResponse.setStudentCV(updatedStudentEntity.getStudentCV());
				studentResponse.setStudentCVFileName(updatedStudentEntity.getStudentCVFileName());
				studentResponse.setStudentMiniTranscript(updatedStudentEntity.getStudentMiniTranscript());
				studentResponse.setStudentMiniTranscriptFileName(updatedStudentEntity.getStudentMiniTranscriptFileName());
				studentResponse.setStudentCoverLetter(updatedStudentEntity.getStudentCoverLetter());
				studentResponse.setStudentCoverLetterFileName(updatedStudentEntity.getStudentCoverLetterFileName());
				studentResponse.setStudentCourseOutline(updatedStudentEntity.getStudentCourseOutline());
				studentResponse.setStudentCourseOutlineFileName(updatedStudentEntity.getStudentCourseOutlineFileName());
				studentResponse.setStudentSL(updatedStudentEntity.getStudentSL());
				studentResponse.setStudentSLFileName(updatedStudentEntity.getStudentSLFileName());
				studentResponse.setIndustrySvId(updatedStudentEntity.getIndustrySvId());
				studentResponse.setAcademicSvId(updatedStudentEntity.getAcademicSvId());
				studentResponse.setCoordinatorId(updatedStudentEntity.getCoordinatorId());
			} else {
				throw new Exception();
			}
			
			return studentResponse;
		} else {
			return null;
		}
	}

	@Override
	public List<StudentResponse> updateStudentStatusList(List<StudentRequest> studentRequests) throws Exception {
		List<StudentResponse> studentResponses = new ArrayList<StudentResponse>();
		
		for (StudentRequest studentRequest : studentRequests) {
			StudentEntity existedStudentEntity = studentRepository.findByStudentMatricNum(studentRequest.getStudentMatricNum());

			if (BaseUtility.isObjectNotNull(existedStudentEntity)) {
				existedStudentEntity.setStudentStatus(studentRequest.getStudentStatus());

				StudentEntity updatedStudentEntity = studentRepository.save(existedStudentEntity);
				
				if (BaseUtility.isObjectNotNull(updatedStudentEntity)) {
					StudentResponse studentResponse = new StudentResponse();
					
					studentResponse.setStudentMatricNum(updatedStudentEntity.getStudentMatricNum());
					studentResponse.setStudentName(updatedStudentEntity.getStudentName());
					studentResponse.setStudentAddress(updatedStudentEntity.getStudentAddress());
					studentResponse.setStudentEmail(updatedStudentEntity.getStudentEmail());
					studentResponse.setStudentPhone(updatedStudentEntity.getStudentPhone());
					studentResponse.setStudentPassword(updatedStudentEntity.getStudentPassword());
					studentResponse.setStudentCampus(updatedStudentEntity.getStudentCampus());
					studentResponse.setStudentCourse(updatedStudentEntity.getStudentCourse());
					studentResponse.setStudentClass(updatedStudentEntity.getStudentClass());
					studentResponse.setStudentProject(updatedStudentEntity.getStudentProject());
					studentResponse.setStudentStatus(updatedStudentEntity.getStudentStatus());
					studentResponse.setStudentCV(updatedStudentEntity.getStudentCV());
					studentResponse.setStudentCVFileName(updatedStudentEntity.getStudentCVFileName());
					studentResponse.setStudentMiniTranscript(updatedStudentEntity.getStudentMiniTranscript());
					studentResponse.setStudentMiniTranscriptFileName(updatedStudentEntity.getStudentMiniTranscriptFileName());
					studentResponse.setStudentCoverLetter(updatedStudentEntity.getStudentCoverLetter());
					studentResponse.setStudentCoverLetterFileName(updatedStudentEntity.getStudentCoverLetterFileName());
					studentResponse.setStudentCourseOutline(updatedStudentEntity.getStudentCourseOutline());
					studentResponse.setStudentCourseOutlineFileName(updatedStudentEntity.getStudentCourseOutlineFileName());
					studentResponse.setStudentSL(updatedStudentEntity.getStudentSL());
					studentResponse.setStudentSLFileName(updatedStudentEntity.getStudentSLFileName());
					studentResponse.setIndustrySvId(updatedStudentEntity.getIndustrySvId());
					studentResponse.setAcademicSvId(updatedStudentEntity.getAcademicSvId());
					studentResponse.setCoordinatorId(updatedStudentEntity.getCoordinatorId());
					
					studentResponses.add(studentResponse);
				} else {
					throw new Exception();
				}
			}
		}
		
		return studentResponses;
	}

	@Transactional
	public Boolean deleteStudentByStudentMatricNum(String studentMatricNum) {
		Integer totalStudentDeleted = 0;
		
		try {
			totalStudentDeleted = studentRepository.deleteByStudentMatricNum(studentMatricNum);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		
		if (totalStudentDeleted > 0) {
			return true;
		}
		
		return false;
	}
}
