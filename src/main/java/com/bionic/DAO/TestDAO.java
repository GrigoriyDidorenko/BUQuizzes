package com.bionic.DAO;



import com.bionic.DTO.TestDTO;
import com.bionic.entities.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository("testDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class TestDAO extends AbstractDAO<Test> {
    public TestDAO() {
        super(Test.class);
    }

    public List<TestDTO> getUnarchivedTestsNames(){
        Query query = em.createNamedQuery("getUnarchivedTestsNames");
        return (List<TestDTO>)query.getResultList();
    }

  public List<TestDTO> getUnarchivedOneTimeTests(){
        List<TestDTO> list = new ArrayList<>();
        Query query = em.createNamedQuery("getUnarchivedOneTimeTests");
        Iterator iterator = query.getResultList().iterator();
        while (iterator.hasNext()) {
            Object[] tmp = (Object[]) iterator.next();
            TestDTO testDTO = new TestDTO((String) tmp[3],(long) tmp[0], (String) tmp[1], (int) tmp[2]);
            list.add(testDTO );
        }
        return list;
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
