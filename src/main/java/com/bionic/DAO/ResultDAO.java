package com.bionic.DAO;

import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.wrappers.TestWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * package: com.bionic.DAO
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 16.11.2015
 */
@Repository("resultDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class ResultDAO extends AbstractDAO<Result> {

    public ResultDAO(){
        super(Result.class);
    }

    public Test getCurrentTest(long id, long testId){
        Query query = em.createNamedQuery("getCurrentTestById");
        query.setParameter("userId", id);
        query.setParameter("testId", testId);
        return (Test)query.getSingleResult();
    }

    public List<TestWrapper> getAvailableTestsNames(long id){
        Query query = em.createNamedQuery("getAvailableTestsNames");
        query.setParameter("userId", id);
        return (List<TestWrapper>)query.getResultList();
    }
}
