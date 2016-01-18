package com.bionic.DAO;

import com.bionic.entities.CategoryTest;
import com.bionic.entities.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rondo104 on 23.12.2015.
 */
@Repository("categoryTestDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryTestDAO extends AbstractDAO<CategoryTest> {
    public CategoryTestDAO() {
        super(CategoryTest.class);
    }

    public CategoryTest getСategoryTestByTestName(String categoryTestName) {
        try {
            Query query = em.createNamedQuery("getСategoryTestByTestName");
            query.setParameter("categoryTestName", categoryTestName);
            return (CategoryTest) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Set<String> getAllСategoryTestName() {
        try {
            Query query = em.createNamedQuery("getAllСategoryTestName");
            Set<String> allСategoryTestName = new HashSet<>((List<String>) query.getResultList());
            return  allСategoryTestName;
        } catch (NoResultException e) {
            return null;
        }
    }

}
