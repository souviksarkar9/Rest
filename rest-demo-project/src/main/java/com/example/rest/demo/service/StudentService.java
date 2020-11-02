package com.example.rest.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest.demo.model.Student;
import com.example.rest.demo.repoitory.StudentRepository;

@Service
public class StudentService {

	private Logger logger = LoggerFactory.getLogger(StudentService.class);

	@Autowired
	private StudentRepository srepo;

	public List<Student> getAllStudentResults() {
		logger.info("Inside getAllStudents");
		return srepo.findAll();
	}
	
	public Student getStudentResultById(Long id) {
		logger.info("Inside getStudentById");
		return srepo.findById(id).get();
	}

	public Student saveStudentResult(Student s) {
		logger.info("Inside Save");
		return srepo.save(s);
	}

	public Student deleteStudentResult(Student s){
		logger.info("Inside Delete");
		 srepo.delete(s);
		return s;
	}

}
