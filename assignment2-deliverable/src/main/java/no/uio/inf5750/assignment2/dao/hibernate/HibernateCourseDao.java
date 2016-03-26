package no.uio.inf5750.assignment2.dao.hibernate;


import no.uio.inf5750.assignment2.model.Course;
import no.uio.inf5750.assignment2.dao.CourseDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Transactional
public class HibernateCourseDao implements CourseDAO {

	@Autowired
    private SessionFactory sessionFactory;

    //Get current session from hibernate
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    //Create criteria
    private Criteria getCriteria(Class crit) {
        return getCurrentSession().createCriteria(crit);
    }

    @Override
    public int saveCourse(Course course) {
        return (Integer) getCurrentSession().save(course);
    }

    @Override
    public Course getCourse(int id) {
        return (Course) getCurrentSession().get(Course.class, id);
    }

    @Override
    public Course getCourseByCourseCode(String courseCode) {
        Criteria crit = getCriteria(Course.class);
        crit.add(Restrictions.eq("courseCode", courseCode).ignoreCase());
        return (crit.list().isEmpty() ? null : (Course) crit.list().get(0));
    }

    @Override
    public Course getCourseByName(String name) {
        Criteria crit = getCriteria(Course.class);
        crit.add(Restrictions.eq("name", name).ignoreCase());
        return (crit.list().isEmpty() ? null : (Course) crit.list().get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Course> getAllCourses() {
        return (Collection<Course>) getCriteria(Course.class).list();
    }

    @Override
    public void delCourse(Course course) {
        getCurrentSession().delete(course);
    }

}
