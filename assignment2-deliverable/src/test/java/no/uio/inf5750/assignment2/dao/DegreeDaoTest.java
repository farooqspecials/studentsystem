package no.uio.inf5750.assignment2.dao;
import org.springframework.transaction.annotation.Transactional;
import no.uio.inf5750.assignment2.model.Degree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Collection;
@ContextConfiguration(locations={"classpath*:/META-INF/assignment2/beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DegreeDaoTest {
	
	 @Autowired
	    private DegreeDAO DegreeD;
	    private Degree degree;

	    @Before
	    public void init() {
	        degree = new Degree("Master: Informatics");
	    }

	    @Test
	    public void testSaveDegree() throws Exception {
	        int id = DegreeD.saveDegree(degree);
	        Assert.assertEquals(id, DegreeD.getDegree(id).getId());
	    }
	    
	    @Test
	    public void testGetDegree() throws Exception {
	        int id = DegreeD.saveDegree(degree);
	        Assert.assertEquals(DegreeD.getDegree(id), degree);
	    }

	    @Test
	    public void testGetDegreeByType() throws Exception {
	        DegreeD.saveDegree(degree);
	        Assert.assertEquals(DegreeD.getDegreeByType(degree.getType()), degree);
	    }
	    
	    @Test
	    public void testGetAllDegrees() throws Exception {
	        Degree d = new Degree("Master: Physics");
	        DegreeD.saveDegree(degree);
	        DegreeD.saveDegree(d);
	        Collection<Degree> degrees = DegreeD.getAllDegrees();
	        Assert.assertTrue(degrees.contains(degree));
	        Assert.assertTrue(degrees.contains(d));
	    }

	    @Test
	    public void testDelDegree() throws Exception {
	        DegreeD.saveDegree(degree);
	        Assert.assertNotNull(DegreeD.getDegree(degree.getId()));
	        DegreeD.delDegree(degree);
	        Assert.assertNull(DegreeD.getDegree(degree.getId()));
	    }


}
