package com.intern.user.service.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.intern.user.service.dto.AcademicSupervisorRequest;
import com.intern.user.service.dto.AcademicSupervisorResponse;
import com.intern.user.service.dto.IndustrySupervisorRequest;
import com.intern.user.service.dto.IndustrySupervisorResponse;
import com.intern.user.service.dto.Response;
import com.intern.user.service.dto.StudentRequest;
import com.intern.user.service.dto.StudentResponse;
import com.intern.user.service.service.UserService;
import com.intern.user.service.utility.BaseUtility;

@RestController
@RequestMapping("/user/reactive")
public class UserControllerReactive {

	@Autowired
	UserService userService;
	
	@GetMapping("/students")
	public ResponseEntity<Response> getStudents() {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<StudentResponse> studentResponses = userService.getStudents();

		if (!BaseUtility.isListNull(studentResponses)) {
			message_status = true;
			
			object_map.put("response", studentResponses);
		} else {
			message_status = false;
			message_desc = "NULL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@GetMapping("/student/{studentMatricNum}")
	public ResponseEntity<Response> getStudentByStudentMatricNum(@PathVariable("studentMatricNum") String studentMatricNum) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(studentMatricNum)) {
			StudentResponse studentResponse = userService.getStudentByStudentMatricNum(studentMatricNum);
			
			if (BaseUtility.isObjectNotNull(studentResponse)) {
				message_status = true;
				message_desc = "SUCCESS";

				object_map.put("response", studentResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PutMapping("/student")
	public ResponseEntity<Response> updateStudent(@RequestPart("request") StudentRequest studentRequest, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile, @RequestParam(name = "mtFile", required = false) MultipartFile mtFile, @RequestParam(name = "clFile", required = false) MultipartFile clFile, @RequestParam(name = "coFile", required = false) MultipartFile coFile, @RequestParam(name = "slFile", required = false) MultipartFile slFile) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(studentRequest)) {
			StudentResponse studentResponse = userService.updateStudent(studentRequest, cvFile, mtFile, clFile, coFile, slFile);
			
			if (BaseUtility.isObjectNotNull(studentResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("response", studentResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/academic/supervisors/filter")
	public ResponseEntity<Response> filterAcademicSupervisors(@RequestPart("request") AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<AcademicSupervisorResponse> academicSupervisorResponses = userService.filterAcademicSupervisors(academicSupervisorRequest);
		object_map.put("response", academicSupervisorResponses);
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}

	@GetMapping("/academic/supervisor/{academicSvId}")
	public ResponseEntity<Response> getAcademicSupervisorByAcademicSvId(@PathVariable("academicSvId") String academicSvId) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(academicSvId)) {
			AcademicSupervisorResponse academicSupervisorResponse = userService.getAcademicSupervisorByAcademicSvId(academicSvId);
			
			if (BaseUtility.isObjectNotNull(academicSupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";

				object_map.put("response", academicSupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PutMapping("/academic/supervisor")
	public ResponseEntity<Response> updateAcademicSupervisor(@RequestPart("request") AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(academicSupervisorRequest)) {
			AcademicSupervisorResponse academicSupervisorResponse = userService.updateAcademicSupervisor(academicSupervisorRequest);

			if (BaseUtility.isObjectNotNull(academicSupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("response", academicSupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/industry/supervisors/filter")
	public ResponseEntity<Response> filterIndustrySupervisors(@RequestPart("request") IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<IndustrySupervisorResponse> industrySupervisorResponses = userService.filterIndustrySupervisors(industrySupervisorRequest);
		object_map.put("response", industrySupervisorResponses);
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@GetMapping("/industry/supervisor/{industrySvId}")
	public ResponseEntity<Response> getIndustrySupervisorByIndustrySvId(@PathVariable("industrySvId") String industrySvId) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(industrySvId)) {
			IndustrySupervisorResponse industrySupervisorResponse = userService.getIndustrySupervisorByIndustrySvId(industrySvId);
			
			if (BaseUtility.isObjectNotNull(industrySupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";

				object_map.put("response", industrySupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}

	@PutMapping("/industry/supervisor")
	public ResponseEntity<Response> updateIndustrySupervisor(@RequestPart("request") IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(industrySupervisorRequest)) {
			IndustrySupervisorResponse industrySupervisorResponse = userService.updateIndustrySupervisor(industrySupervisorRequest);

			if (BaseUtility.isObjectNotNull(industrySupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("response", industrySupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	public ResponseEntity<Response> returnResponse(Errors errors, HttpStatus http_status, String error_desc, Boolean msg_status, String msg_desc, String msg_code, String msg_dev, Map<Object, Object> object_map) {
		if (BaseUtility.isObjectNotNull(errors)) {
			if (errors.hasErrors()) {
				for (ObjectError objectError: errors.getAllErrors()) {
					error_desc = error_desc + objectError.getDefaultMessage();
					
					if (errors.getErrorCount() > 1) {
						error_desc = error_desc + ", ";
					}
				}
			}
		}
		
		Response response = new Response();
		response.setTime_stamp(LocalDateTime.now());
		response.setStatus_code(http_status.value());
		response.setStatus_desc(http_status);
		response.setError_desc(error_desc);
		response.setMessage_status(msg_status);
		response.setMessage_desc(msg_desc);
		response.setMessage_code(msg_code);
		response.setMessage_dev(msg_dev);
		response.setData(object_map);
		
		return ResponseEntity.ok(response);
	}
}
