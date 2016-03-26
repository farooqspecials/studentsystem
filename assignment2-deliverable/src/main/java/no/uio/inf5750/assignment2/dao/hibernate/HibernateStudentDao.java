package no.uio.inf5750.assignment2.dao.hibernate;
import no.uio.inf5750.assignment2.dao.StudentDAO;
import no.uio.inf5750.assignment2.model.Student;
import java.util.Collection;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class HibernateStudentDao implements StudentDAO {

	@Autowired
    private SessionFactory sessionFactory;
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    private Criteria getCriteria(Class crit) {
        return getCurrentSession().createCriteria(crit);
    }

    @Override
    public int saveStudent(Student student) {
        return (Integer) getCurrentSession().save(student);
    }

    @Override
    public Student getStudent(int id) {
        return (Student) getCurrentSession().get(Student.class, id);
    }

    @Override
    public Student getStudentByName(String name) {
        Criteria crit = getCriteria(Student.class);
        crit.add(Restrictions.eq("name", name).ignoreCase());
        return (crit.list().isEmpty() ? null : (Student) crit.list().get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Student> getAllStudents() {
        return (Collection<Student>) getCriteria(Student.class).list();
    }

    @Override
    public void delStudent(Student student) {
        getCurrentSession().delete(student);
    }

}
