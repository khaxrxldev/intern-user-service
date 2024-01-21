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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.intern.user.service.dto.SigninRequest;
import com.intern.user.service.dto.SigninResponse;
import com.intern.user.service.dto.StudentRequest;
import com.intern.user.service.dto.StudentResponse;
import com.intern.user.service.service.UserService;
import com.intern.user.service.utility.BaseUtility;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/welcome")
	public String welcome() {
		return "welcome to intern user service";
	}
	
	@PostMapping("/signin")
	public ResponseEntity<Response> signin(@RequestPart("signinRequest") SigninRequest signinRequest) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		SigninResponse signinResponse = userService.userSignin(signinRequest);
		
		if (BaseUtility.isBlank(signinResponse.getResponse())) {
			message_status = true;
			message_desc = "SUCCESS";
			
			object_map.put("userId", signinResponse.getUserId());
		} else {
			error_desc = signinResponse.getResponse();
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}

	@PostMapping("/academic/supervisors/filter")
	public ResponseEntity<Response> filterAcademicSupervisors(@RequestPart("academicSupervisorRequest") AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<AcademicSupervisorResponse> academicSupervisorResponses = userService.filterAcademicSupervisors(academicSupervisorRequest);
		object_map.put("academicSupervisors", academicSupervisorResponses);
		
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

				object_map.put("academicSupervisor", academicSupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/academic/supervisor")
	public ResponseEntity<Response> insertAcademicSupervisor(@RequestPart("academicSupervisorRequest") AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(academicSupervisorRequest)) {
			AcademicSupervisorResponse academicSupervisorResponse = userService.insertAcademicSupervisor(academicSupervisorRequest);

			if (BaseUtility.isObjectNotNull(academicSupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("academicSupervisor", academicSupervisorResponse);
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
	public ResponseEntity<Response> updateAcademicSupervisor(@RequestPart("academicSupervisorRequest") AcademicSupervisorRequest academicSupervisorRequest) throws Exception {
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
				
				object_map.put("academicSupervisor", academicSupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@DeleteMapping("/academic/supervisor/{academicSvId}")
	public ResponseEntity<Response> deleteAcademicSupervisorByAcademicSvId(@PathVariable("academicSvId") String academicSvId) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(academicSvId)) {
			Boolean deleteAcademicSupervisorStatus = userService.deleteAcademicSupervisorByAcademicSvId(academicSvId);
			
			if (deleteAcademicSupervisorStatus) {
				message_status = true;
				message_desc = "SUCCESS";
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
	public ResponseEntity<Response> filterIndustrySupervisors(@RequestPart("industrySupervisorRequest") IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<IndustrySupervisorResponse> industrySupervisorResponses = userService.filterIndustrySupervisors(industrySupervisorRequest);
		object_map.put("industrySupervisors", industrySupervisorResponses);
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@GetMapping("/industry/supervisors/chart")
	public ResponseEntity<Response> getTotalIndustrySupervisors() throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<List<String>> industrySupervisorChartList = userService.getTotalIndustrySupervisors();
		object_map.put("industrySupervisorChartList", industrySupervisorChartList);
		
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

				object_map.put("industrySupervisor", industrySupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}

	@PostMapping("/industry/supervisor")
	public ResponseEntity<Response> insertIndustrySupervisor(@RequestPart("industrySupervisorRequest") IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(industrySupervisorRequest)) {
			IndustrySupervisorResponse industrySupervisorResponse = userService.insertIndustrySupervisor(industrySupervisorRequest);

			if (BaseUtility.isObjectNotNull(industrySupervisorResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("industrySupervisor", industrySupervisorResponse);
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
	public ResponseEntity<Response> updateIndustrySupervisor(@RequestPart("industrySupervisorRequest") IndustrySupervisorRequest industrySupervisorRequest) throws Exception {
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
				
				object_map.put("industrySupervisor", industrySupervisorResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}

	@DeleteMapping("/industry/supervisor/{industrySvId}")
	public ResponseEntity<Response> deleteIndustrySupervisorByIndustrySvId(@PathVariable("industrySvId") String industrySvId) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(industrySvId)) {
			Boolean deleteIndustrySupervisorStatus = userService.deleteIndustrySupervisorByIndustrySvId(industrySvId);
			
			if (deleteIndustrySupervisorStatus) {
				message_status = true;
				message_desc = "SUCCESS";
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
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
			
			object_map.put("students", studentResponses);
		} else {
			message_status = false;
			message_desc = "NULL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/students/filter")
	public ResponseEntity<Response> filterStudents(@RequestPart("studentRequest") StudentRequest studentRequest) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<StudentResponse> studentResponses = userService.filterStudents(studentRequest);
		object_map.put("students", studentResponses);
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@GetMapping("/students/chart")
	public ResponseEntity<Response> getTotalStudents() throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		List<List<String>> studentChartList = userService.getTotalStudents();
		object_map.put("studentChartList", studentChartList);
		
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

				object_map.put("student", studentResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/student/register")
	public ResponseEntity<Response> registerStudent(@RequestPart("studentRequest") StudentRequest studentRequest, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile, @RequestParam(name = "mtFile", required = false) MultipartFile mtFile, @RequestParam(name = "clFile", required = false) MultipartFile clFile, @RequestParam(name = "coFile", required = false) MultipartFile coFile, @RequestParam(name = "slFile", required = false) MultipartFile slFile) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(studentRequest)) {
			StudentResponse existingStudentResponse = userService.getStudentByStudentMatricNum(studentRequest.getStudentMatricNum());
			
			if (BaseUtility.isObjectNull(existingStudentResponse)) {
				StudentResponse studentResponse = userService.insertStudent(studentRequest, cvFile, mtFile, clFile, coFile, slFile);
				
				if (BaseUtility.isObjectNotNull(studentResponse)) {
					message_status = true;
					message_desc = "SUCCESS";
					
					object_map.put("student", studentResponse);
				} else {
					error_desc = "FAIL";
				}
			} else {
				error_desc = "Student already registerd";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PostMapping("/student")
	public ResponseEntity<Response> insertStudent(@RequestPart("studentRequest") StudentRequest studentRequest, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile, @RequestParam(name = "mtFile", required = false) MultipartFile mtFile, @RequestParam(name = "clFile", required = false) MultipartFile clFile, @RequestParam(name = "coFile", required = false) MultipartFile coFile, @RequestParam(name = "slFile", required = false) MultipartFile slFile) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (BaseUtility.isObjectNotNull(studentRequest)) {
			StudentResponse studentResponse = userService.insertStudent(studentRequest, cvFile, mtFile, clFile, coFile, slFile);
			
			if (BaseUtility.isObjectNotNull(studentResponse)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("student", studentResponse);
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
	public ResponseEntity<Response> updateStudent(@RequestPart("studentRequest") StudentRequest studentRequest, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile, @RequestParam(name = "mtFile", required = false) MultipartFile mtFile, @RequestParam(name = "clFile", required = false) MultipartFile clFile, @RequestParam(name = "coFile", required = false) MultipartFile coFile, @RequestParam(name = "slFile", required = false) MultipartFile slFile) throws Exception {
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
				
				object_map.put("student", studentResponse);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@PutMapping("/students")
	public ResponseEntity<Response> updateStudentStatusList(@RequestPart("studentRequests") List<StudentRequest> studentRequests) throws Exception {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();
		
		if (!BaseUtility.isListNull(studentRequests)) {
			List<StudentResponse> studentResponses = userService.updateStudentStatusList(studentRequests);
			
			if (!BaseUtility.isListNull(studentResponses)) {
				message_status = true;
				message_desc = "SUCCESS";
				
				object_map.put("students", studentResponses);
			} else {
				error_desc = "FAIL";
			}
		} else {
			http_status = HttpStatus.BAD_REQUEST;
			error_desc = "FAIL";
		}
		
		return returnResponse(null, http_status, error_desc, message_status, message_desc, message_code, message_dev, object_map);
	}
	
	@DeleteMapping("/student/{studentMatricNum}")
	public ResponseEntity<Response> deleteStudentByStudentMatricNum(@PathVariable("studentMatricNum") String studentMatricNum) {
		HttpStatus http_status = HttpStatus.OK;
		String error_desc = null;
		Boolean message_status = false;
		String message_desc = null;
		String message_code = null;
		String message_dev = null;
		Map<Object, Object> object_map = new HashMap<Object, Object>();

		if (BaseUtility.isNotBlank(studentMatricNum)) {
			Boolean deleteStudentStatus = userService.deleteStudentByStudentMatricNum(studentMatricNum);
			
			if (deleteStudentStatus) {
				message_status = true;
				message_desc = "SUCCESS";
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
