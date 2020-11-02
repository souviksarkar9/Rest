package com.example.rest.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rest.demo.model.Student;
import com.example.rest.demo.repoitory.StudentRepository;
import com.example.rest.demo.service.StudentService;

@SpringBootTest
class RestDemoProjectApplicationTests {
	
	@Autowired
	private StudentRepository repo;
	
	@Autowired
	private StudentService studentService;

	@Test
	void testSave() {
		
		Student s = new Student();
		//s.setId(100);
		s.setName("souvik");
		s.setResult(100);
		
		Student s2 = repo.save(s);
		
		assertEquals(s.getName() , s2.getName());
		assertNotNull(s2);
		
		assertNotNull(repo.findById(s2.getId()));
		
		
	}
	
	@Test
	void testCache() {
		
		List<Student> studentlist = null;
		
		studentlist = studentService.getAllStudentResults();
				
		assertNotNull(studentlist);
		
		
	}

}
