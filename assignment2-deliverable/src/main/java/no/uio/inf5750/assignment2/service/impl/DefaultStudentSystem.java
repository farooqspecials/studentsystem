package no.uio.inf5750.assignment2.service.impl;
import no.uio.inf5750.assignment2.dao.CourseDAO;
import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import no.uio.inf5750.assignment2.service.StudentSystem;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@Transactional
public class DefaultStudentSystem implements StudentSystem {
	
	@Autowired
    private StudentDAO studentD;

    @Autowired
    private CourseDAO courseD;

    @Autowired
    private DegreeDAO degreeD;

    @Override
    public int addCourse(String courseCode, String name) {
        return courseD.saveCourse(new Course(courseCode, name));
    }

    @Override
    public void updateCourse(int courseId, String courseCode, String name) {
        Course tmp = courseD.getCourse(courseId);
        tmp.setCourseCode(courseCode);
        tmp.setName(name);
    }

    @Override
    public Course getCourse(int courseId) {
        return courseD.getCourse(courseId);

    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        return courseD.getCourseByCourseCode(courseCode);
    }

    @Override
    public Course getCourseByName(String name) {
        return courseD.getCourseByName(name);
    }

    @Override
    public Collection<Course> getAllCourses() {
        return courseD.getAllCourses();
    }

    @Override
    public void delCourse(int courseId) {
        Course course = courseD.getCourse(courseId);
           for(Student tmp : course.getAttendants()){
            tmp.getCourses().remove(course);
        }
               for(Degree tmp : degreeD.getAllDegrees()) 
               {
            removeRequiredCourseFromDegree(tmp.getId(), courseId);
        }
        courseD.delCourse(course);
    }

    @Override
    public void addAttendantToCourse(int courseId, int studentId) {
        Course cou = courseD.getCourse(courseId);
        Student stud = studentD.getStudent(studentId);
        cou.getAttendants().add(stud);
        stud.getCourses().add(cou);
    }

    @Override
    public void removeAttendantFromCourse(int courseId, int studentId) {
        Course cou = courseD.getCourse(courseId);
        Student stud = studentD.getStudent(studentId);
        cou.getAttendants().remove(stud);
        stud.getCourses().remove(cou);
    }

    @Override
    public int addDegree(String type) {
        return degreeD.saveDegree(new Degree(type));
    }

    @Override
    public void updateDegree(int degreeId, String type) {
        degreeD.getDegree(degreeId).setType(type);
    }

    @Override
    public Degree getDegree(int degreeId) {
        return degreeD.getDegree(degreeId);
    }

    @Override
    public Degree getDegreeByType(String type) {
        return degreeD.getDegreeByType(type);
    }

    @Override
    public Collection<Degree> getAllDegrees() {
        return degreeD.getAllDegrees();
    }

    @Override
    public void delDegree(int degreeId) {
        Degree degree = degreeD.getDegree(degreeId);
        for(Student tmp : studentD.getAllStudents()){
            removeDegreeFromStudent(tmp.getId(),degreeId);
        }
        degreeD.delDegree(degree);
    }

    @Override
    public void addRequiredCourseToDegree(int degreeId, int courseId) {
        degreeD.getDegree(degreeId).getRequiredCourses().add(getCourse(courseId));
    }

    @Override
    public void removeRequiredCourseFromDegree(int degreeId, int courseId) {
        degreeD.getDegree(degreeId).getRequiredCourses().remove(getCourse(courseId));
    }

    @Override
    public int addStudent(String name) {
        return studentD.saveStudent(new Student(name));
    }

    @Override
    public void updateStudent(int studentId, String name) {
        studentD.getStudent(studentId).setName(name);
    }

    @Override
    public Student getStudent(int studentId) {
        return studentD.getStudent(studentId);
    }

    @Override
    public Student getStudentByName(String name) {
        return studentD.getStudentByName(name);
    }

    @Override
    public Collection<Student> getAllStudents() {
        return studentD.getAllStudents();
    }

    @Override
    public void delStudent(int studentId) {
        Student student = studentD.getStudent(studentId);
        for(Course tmp : student.getCourses()){
            tmp.getAttendants().remove(student);
        }
        studentD.delStudent(student);
    }

    @Override
    public void addDegreeToStudent(int studentId, int degreeId) {
        studentD.getStudent(studentId).getDegrees().add(degreeD.getDegree(degreeId));
    }

    @Override
    public void removeDegreeFromStudent(int studentId, int degreeId) {
        studentD.getStudent(studentId).getDegrees().remove(degreeD.getDegree(degreeId));
    }

    @Override
    public boolean studentFulfillsDegreeRequirements(int studentId, int degreeId) {
        return studentD.getStudent(studentId).getCourses().containsAll(degreeD.getDegree(degreeId).getRequiredCourses());
    }

	@Override
	public void setStudentLocation(int studentId, String latitude, String longitude) {
		// TODO Auto-generated method stub
		Student student=studentD.getStudent(studentId);
		if(student!=null)
			{
			student.setLatitude(latitude);
			student.setLongitude(longitude);
			
			}
		}
	
		
	

}
