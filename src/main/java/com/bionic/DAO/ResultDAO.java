package com.bionic.DAO;


import com.bionic.model.Result;
import com.bionic.model.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

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
}
