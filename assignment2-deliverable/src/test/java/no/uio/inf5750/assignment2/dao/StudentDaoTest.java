package no.uio.inf5750.assignment2.dao;
import no.uio.inf5750.assignment2.model.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class StudentDaoTest {
	
	@Autowired
    private StudentDAO studentD;
    private Student student;

    @Before
    public void init() {
        student = new Student("Farooq");
    }

    @Test
    public void testSaveStudent() throws Exception {
        int id = studentD.saveStudent(student);
        Assert.assertEquals(id, studentD.getStudent(id).getId());
    }
    
    @Test
    public void testDelStudent() throws Exception {
        studentD.saveStudent(student);
        Assert.assertNotNull(studentD.getStudent(student.getId()));
        studentD.delStudent(student);
        Assert.assertNull(studentD.getStudent(student.getId()));
    }
    
    @Test
    public void testGetAllStudents() throws Exception {
        Student s = new Student("Normen");
        studentD.saveStudent(student);
        studentD.saveStudent(s);
        Collection<Student> students = studentD.getAllStudents();
        Assert.assertTrue(students.contains(student));
        Assert.assertTrue(students.contains(s));
    }

    @Test
    public void testGetStudentByName() throws Exception {
        studentD.saveStudent(student);
        Assert.assertEquals(studentD.getStudentByName(student.getName()), student);
    }
    
    @Test
    public void testGetStudent() throws Exception {
        int id = studentD.saveStudent(student);
        Assert.assertEquals(studentD.getStudent(id), student);
    }

    

}
