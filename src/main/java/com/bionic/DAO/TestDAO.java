package com.bionic.DAO;



import com.bionic.entities.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("testDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TestDAO extends AbstractDAO<Test> {
    public TestDAO() {
        super(Test.class);
    }

/*    public ShowAll findTestByName(String name){
        Query query = em.createNamedQuery("getTestByName");
        query.setParameter("testName", name);
        return (ShowAll) query.getSingleResult();
    }

    public List<ShowAll> getVisibleTests(){
        Query query = em.createNamedQuery("getVisibleTests");
        return query.getResultList();
    }*/

}
