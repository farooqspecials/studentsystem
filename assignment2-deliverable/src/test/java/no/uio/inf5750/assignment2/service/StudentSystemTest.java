package no.uio.inf5750.assignment2.service;
import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.model.Degree;
import no.uio.inf5750.assignment2.model.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional

public class StudentSystemTest {
	
	@Autowired
    private StudentSystem studentSystem;

    private Student student;
    private Course course;
    private Degree degree;

    @Before
    public void init() {
        student = new Student("David");
        course = new Course("INF5750","Open source software development");
        degree = new Degree("Master: Electronics");
    }
    // Assignment 3 Task  Test 
    @Test
	public void testLocation() {
		int id = studentSystem.addStudent("a");
		Student student = studentSystem.getStudent(id);
		student.setLongitude("93");
		student.setLatitude("104");
		student = studentSystem.getStudent(id);
		assertTrue(student.getLongitude().equals("93") && student.getLatitude().equals("104"));
	}

    @Test
    public void testAddCourse() throws Exception {
        int id = studentSystem.addCourse(course.getCourseCode(), course.getName());
        Course testCourse = studentSystem.getCourse(id);
        Assert.assertEquals(course.getCourseCode(), testCourse.getCourseCode());
        Assert.assertEquals(course.getName(), testCourse.getName());
    }

    @Test
    public void testUpdateCourse() throws Exception {
        int id = studentSystem.addCourse(course.getCourseCode(), course.getName());
        Course testCourse = studentSystem.getCourse(id);
        Assert.assertEquals(course.getName(), testCourse.getName());
        String newName = "Open source";
        studentSystem.updateCourse(id, testCourse.getCourseCode(), newName);
        Assert.assertEquals(newName, testCourse.getName());
    }

    @Test
    public void testGetCourse() throws Exception {
        int id = studentSystem.addCourse(course.getCourseCode(), course.getName());
        Course testCourse = studentSystem.getCourse(id);
        Assert.assertEquals(course, testCourse);
    }

    @Test
    public void testGetCourseByCourseCode() throws Exception {
        studentSystem.addCourse(course.getCourseCode(), course.getName());
        Course testCourse = studentSystem.getCourseByCourseCode(course.getCourseCode());
        Assert.assertEquals(course, testCourse);
    }

    @Test
    public void testGetCourseByName() throws Exception {
        studentSystem.addCourse(course.getCourseCode(), course.getName());
        Course testCourse = studentSystem.getCourseByName(course.getName());
        Assert.assertEquals(course, testCourse);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        String courseCode = "INF4820";
        String name = "A";
        Course testCourse = new Course(courseCode, name);
        studentSystem.addCourse(course.getCourseCode(), course.getName());
        studentSystem.addCourse(courseCode, name);
        Collection<Course> testSet = new ArrayList<Course>();
        testSet.add(course);
        testSet.add(testCourse);
        Assert.assertTrue(studentSystem.getAllCourses().containsAll(testSet));
    }

    @Test
    public void testDelCourse() throws Exception {
        int id = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int studId = studentSystem.addStudent(student.getName());
        int degId = studentSystem.addDegree(degree.getType());
        int testStudId = studentSystem.addStudent("Ole Stumpf");
        int testDegId = studentSystem.addDegree("Master");
        studentSystem.addAttendantToCourse(id, studId);
        studentSystem.addAttendantToCourse(id, testStudId);
        studentSystem.addRequiredCourseToDegree(degId, id);
        studentSystem.addRequiredCourseToDegree(testDegId, id);
        Assert.assertNotNull(studentSystem.getCourse(id));
        studentSystem.delCourse(id);
        Assert.assertNull(studentSystem.getCourse(id));
    }

    @Test
    public void testAddAttendantToCourse() throws Exception {
        int courseId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int studId = studentSystem.addStudent(student.getName());
        studentSystem.addAttendantToCourse(courseId, studId);
        Assert.assertTrue(studentSystem.getCourse(courseId).getAttendants().contains(student));
    }

    @Test
    public void testRemoveAttendantFromCourse() throws Exception {
        int courseId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int studId = studentSystem.addStudent(student.getName());
        studentSystem.addAttendantToCourse(courseId, studId);
        Assert.assertTrue(studentSystem.getCourse(courseId).getAttendants().contains(student));
        studentSystem.removeAttendantFromCourse(courseId, studId);
        Assert.assertFalse(studentSystem.getCourse(courseId).getAttendants().contains(student));
    }

    @Test
    public void testAddDegree() throws Exception {
        int id = studentSystem.addDegree(degree.getType());
        Degree testDegree = studentSystem.getDegree(id);
        Assert.assertEquals(degree, testDegree);
    }

    @Test
    public void testUpdateDegree() throws Exception {
        int id = studentSystem.addDegree(degree.getType());
        Degree testDegree = studentSystem.getDegree(id);
        String newType = "Master";
        studentSystem.updateDegree(id, newType);
        Assert.assertEquals(newType, testDegree.getType());
    }

    @Test
    public void testGetDegree() throws Exception {
        int id = studentSystem.addDegree(degree.getType());
        Degree testDegree = studentSystem.getDegree(id);
        Assert.assertEquals(degree, testDegree);
    }

    @Test
    public void testGetDegreeByType() throws Exception {
        studentSystem.addDegree(degree.getType());
        Degree testDegree = studentSystem.getDegreeByType(degree.getType());
        Assert.assertEquals(degree, testDegree);
    }

    @Test
    public void testGetAllDegrees() throws Exception {
        String type = "Master";
        Degree testDegree = new Degree(type);
        studentSystem.addDegree(type);
        studentSystem.addDegree(degree.getType());
        Collection<Degree> testSet = new ArrayList<Degree>();
        testSet.add(degree);
        testSet.add(testDegree);
        Assert.assertTrue(studentSystem.getAllDegrees().containsAll(testSet));
    }

    @Test
    public void testDelDegree() throws Exception {
        int id = studentSystem.addDegree(degree.getType());
        int studId = studentSystem.addStudent(student.getName());
        int couId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        studentSystem.addRequiredCourseToDegree(id, couId);
        studentSystem.addDegreeToStudent(studId, id);
        Assert.assertNotNull(studentSystem.getDegree(id));
        studentSystem.delDegree(id);
        Assert.assertNull(studentSystem.getDegree(id));
    }

    @Test
    public void testAddRequiredCourseToDegree() throws Exception {
        int courseId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int degreeId = studentSystem.addDegree(degree.getType());
        studentSystem.addRequiredCourseToDegree(degreeId, courseId);
        Assert.assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses().contains(course));
    }

    @Test
    public void testRemoveRequiredCourseFromDegree() throws Exception {
        int courseId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int degreeId = studentSystem.addDegree(degree.getType());
        studentSystem.addRequiredCourseToDegree(degreeId, courseId);
        Assert.assertTrue(studentSystem.getDegree(degreeId).getRequiredCourses().contains(course));
        studentSystem.removeRequiredCourseFromDegree(degreeId, courseId);
        Assert.assertFalse(studentSystem.getDegree(degreeId).getRequiredCourses().contains(course));
    }

    @Test
    public void testAddStudent() throws Exception {
        int id = studentSystem.addStudent(student.getName());
        Assert.assertNotNull(studentSystem.getStudent(id));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        int id = studentSystem.addStudent(student.getName());
        String newName = "A";
        studentSystem.updateStudent(id, newName);
        Assert.assertNotNull(studentSystem.getStudentByName(newName));
    }

    @Test
    public void testGetStudent() throws Exception {
        int id = studentSystem.addStudent(student.getName());
        Assert.assertNotNull(studentSystem.getStudent(id));
    }

    @Test
    public void testGetStudentByName() throws Exception {
        studentSystem.addStudent(student.getName());
        Assert.assertNotNull(studentSystem.getStudentByName(student.getName()));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        String name = "A";
        Student testStudent = new Student(name);
        studentSystem.addStudent(student.getName());
        studentSystem.addStudent(name);
        Collection<Student> testSet = new ArrayList<Student>();
        testSet.add(student);
        testSet.add(testStudent);
        Assert.assertTrue(studentSystem.getAllStudents().containsAll(testSet));
    }

    @Test
    public void testDelStudent() throws Exception {
        int id = studentSystem.addStudent(student.getName());
        int couId = studentSystem.addCourse(course.getCourseCode(), course.getName());
        int degId = studentSystem.addDegree(degree.getType());
        studentSystem.addDegreeToStudent(id, degId);
        studentSystem.addAttendantToCourse(couId, id);
        studentSystem.delStudent(id);
        Assert.assertNull(studentSystem.getStudent(id));
    }

    @Test
    public void testAddDegreeToStudent() throws Exception {
        int studId = studentSystem.addStudent(student.getName());
        int degId = studentSystem.addDegree(degree.getType());
        studentSystem.addDegreeToStudent(studId, degId);
        Assert.assertTrue(studentSystem.getStudent(studId).getDegrees().contains(degree));
    }

    @Test
    public void testRemoveDegreeFromStudent() throws Exception {
        int studId = studentSystem.addStudent(student.getName());
        int degId = studentSystem.addDegree(degree.getType());
        studentSystem.addDegreeToStudent(studId, degId);
        Assert.assertTrue(studentSystem.getStudent(studId).getDegrees().contains(degree));
        studentSystem.removeDegreeFromStudent(studId, degId);
        Assert.assertFalse(studentSystem.getStudent(studId).getDegrees().contains(degree));
    }

    @Test
    public void testStudentFulfillsDegreeRequirements() throws Exception {
        int studId = studentSystem.addStudent(student.getName());
        int degId = studentSystem.addDegree(degree.getType());
        int courseId = studentSystem.addCourse(course.getCourseCode(),course.getName());
        studentSystem.addRequiredCourseToDegree(degId, courseId);
        Assert.assertFalse(studentSystem.studentFulfillsDegreeRequirements(studId, degId));
        studentSystem.addAttendantToCourse(courseId, studId);
        Assert.assertTrue(studentSystem.studentFulfillsDegreeRequirements(studId, degId));
    }

}
