package com.intern.user.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "intern_student_evaluation")
public class StudentEvaluationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "student_evaluation_id")
	private String studentEvaluationId;

	@Column(name = "student_evaluation_date")
	private Date studentEvaluationDate;

	@Column(name = "student_evaluation_status")
	private String studentEvaluationStatus;

	@Lob
	@Column(name = "student_evaluation_attach")
	private byte[] studentEvaluationAttach;

	@Column(name = "evaluation_id")
	private String evaluationId;

	@Column(name = "student_matric_num")
	private String studentMatricNum;

	@Column(name = "academic_sv_id")
	private String academicSvId;

	@Column(name = "industry_sv_id")
	private String industrySvId;

//	@Column(name = "coordinator_id")
//	private String coordinatorId;

	public String getStudentEvaluationId() {
		return studentEvaluationId;
	}

	public void setStudentEvaluationId(String studentEvaluationId) {
		this.studentEvaluationId = studentEvaluationId;
	}

	public Date getStudentEvaluationDate() {
		return studentEvaluationDate;
	}

	public void setStudentEvaluationDate(Date studentEvaluationDate) {
		this.studentEvaluationDate = studentEvaluationDate;
	}

	public String getStudentEvaluationStatus() {
		return studentEvaluationStatus;
	}

	public void setStudentEvaluationStatus(String studentEvaluationStatus) {
		this.studentEvaluationStatus = studentEvaluationStatus;
	}

	public byte[] getStudentEvaluationAttach() {
		return studentEvaluationAttach;
	}

	public void setStudentEvaluationAttach(byte[] studentEvaluationAttach) {
		this.studentEvaluationAttach = studentEvaluationAttach;
	}

	public String getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(String evaluationId) {
		this.evaluationId = evaluationId;
	}

	public String getStudentMatricNum() {
		return studentMatricNum;
	}

	public void setStudentMatricNum(String studentMatricNum) {
		this.studentMatricNum = studentMatricNum;
	}

	public String getAcademicSvId() {
		return academicSvId;
	}

	public void setAcademicSvId(String academicSvId) {
		this.academicSvId = academicSvId;
	}

	public String getIndustrySvId() {
		return industrySvId;
	}

	public void setIndustrySvId(String industrySvId) {
		this.industrySvId = industrySvId;
	}

//	public String getCoordinatorId() {
//		return coordinatorId;
//	}
//
//	public void setCoordinatorId(String coordinatorId) {
//		this.coordinatorId = coordinatorId;
//	}
}
