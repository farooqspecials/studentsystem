package no.uio.inf5750.assignment2.gui.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

@Controller
public class ApiController {
	
	@Autowired
	StudentSystem studentSystem;
	
	@RequestMapping(value = "/api/student", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> getAllStudents() { 

		Collection<Student> students = studentSystem.getAllStudents();
		return students;
	}
	
	@RequestMapping(value = "/api/course", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Course> getAllCourses() {

		Collection<Course> courses = studentSystem.getAllCourses();
		return courses;

	}
	
	@RequestMapping(value = "/api/degree", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Degree> getAllDegrees() {

		Collection<Degree> degrees = studentSystem.getAllDegrees();
		return degrees;

	}
	
	@RequestMapping(value = "/api/student/{student}/location", method = RequestMethod.GET)
	@ResponseBody
	public Collection<Student> setStudentLocation(@PathVariable String student, @RequestParam(value="latitude") String latitude, @RequestParam(value="longitude") String longitude) {
		Student s = studentSystem.getStudent(Integer.parseInt(student));
		
		s.setLatitude(latitude);
		s.setLongitude(longitude);
		
		Collection<Student> students = studentSystem.getAllStudents();
		return students;
	}

	
	

}
