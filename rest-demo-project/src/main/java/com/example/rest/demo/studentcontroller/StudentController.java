package com.example.rest.demo.studentcontroller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.demo.model.Student;
import com.example.rest.demo.service.StudentService;
import javax.validation.*;

@RestController
public class StudentController {

	private Logger logger = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> test() {
		return new ResponseEntity<String>("test", HttpStatus.OK);
	}

	@RequestMapping(value = "/student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(readOnly = true)
	@Cacheable("student-cache")
	public List<ResponseEntity<Student>> getAllStudentResults() {
		logger.info("getAllStudentResults ");
		List<Student> students = studentService.getAllStudentResults();
		List<ResponseEntity<Student>> response = new ArrayList<>();
		
		students.stream().forEach(student ->{
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(student.getId()).toUri();
			response.add(ResponseEntity.created(uri).build());
		});
		
		return response;
	}
	

	@RequestMapping(value = "/student/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> getStudentResultById(@PathVariable Long id) {
		logger.info("getStudentResultById ");
		Student student = studentService.getStudentResultById(id);
		return new ResponseEntity<Student>(student,HttpStatus.OK); 
		
	}

	@RequestMapping(value = "/student", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
		Student newstudent = studentService.saveStudentResult(student);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newstudent.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CacheEvict("student-cache")
	public ResponseEntity<String> deleteStudent(@RequestBody Student student) {
		logger.info("Deleted Student : {} ", studentService.deleteStudentResult(student).getId());
		return  new ResponseEntity<String>("Deleted Student : " + studentService.deleteStudentResult(student).getId(), HttpStatus.OK);
	}

}
