package no.uio.inf5750.assignment2.dao.hibernate;
import no.uio.inf5750.assignment2.dao.DegreeDAO;
import no.uio.inf5750.assignment2.model.Degree;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
@Transactional
public class HibernateDegreeDao implements DegreeDAO {

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
    public int saveDegree(Degree degree) {
        return (Integer) getCurrentSession().save(degree);
    }

    @Override
    public Degree getDegree(int id) {
        return (Degree) getCurrentSession().get(Degree.class, id);
    }

    @Override
    public Degree getDegreeByType(String type) {
        Criteria crit = getCriteria(Degree.class);
        crit.add(Restrictions.eq("type", type).ignoreCase());
        return (crit.list().isEmpty() ? null : (Degree) crit.list().get(0));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Degree> getAllDegrees() {
        return (Collection<Degree>) getCriteria(Degree.class).list();
    }

    @Override
    public void delDegree(Degree degree) {
        getCurrentSession().delete(degree);
    }
}
