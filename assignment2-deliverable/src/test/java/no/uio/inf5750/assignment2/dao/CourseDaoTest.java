package no.uio.inf5750.assignment2.dao;
import no.uio.inf5750.assignment2.model.Course;
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
public class CourseDaoTest {
	 @Autowired
	    private CourseDAO courseD;
	    private Course course;

	    @Before
	    public void init() {
	        course = new Course("INF5750", "Open Source Software development");
	    }
	    
	    @Test
	    public void testGetCourse() throws Exception {
	        int id = courseD.saveCourse(course);
	        Assert.assertEquals(courseD.getCourse(id), course);
	    }
	    
	    @Test
	    public void testSaveCourse() throws Exception {
	        int id = courseD.saveCourse(course);
	        Assert.assertEquals(id, courseD.getCourse(id).getId());
	    }
	    
	    @Test
	    public void testDelCourse() throws Exception {
	        courseD.saveCourse(course);
	        Assert.assertNotNull(courseD.getCourse(course.getId()));
	        courseD.delCourse(course);
	        Assert.assertNull(courseD.getCourse(course.getId()));
	    }
	    
	    @Test
	    public void testGetAllCourses() throws Exception {
	        Course c = new Course("INF4820", "Algoritmer for kunstig intelligens og naturlige sprÃ¥k");
	        courseD.saveCourse(course);
	        courseD.saveCourse(c);
	        Collection<Course> courses = courseD.getAllCourses();
	        Assert.assertTrue(courses.contains(course));
	        Assert.assertTrue(courses.contains(c));
	    }

	    @Test
	    public void testGetCourseByCourseCode() throws Exception {
	        courseD.saveCourse(course);
	        Assert.assertEquals(courseD.getCourseByCourseCode(course.getCourseCode()), course);
	    }
	

	    @Test
	    public void testGetCourseByName() throws Exception {
	        courseD.saveCourse(course);
	        Assert.assertEquals(courseD.getCourseByName(course.getName()), course);
	    }

}
