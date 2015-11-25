package com.bionic.DAO;

import com.bionic.DTO.TestDTO;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
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

    public List<TestDTO> getAvailableTestsNames(long id){
        List<TestDTO> list = new ArrayList<>();
        Query query = em.createNamedQuery("getAvailableTestsNames");
        query.setParameter("userId", id);
        Iterator iterator = query.getResultList().iterator();
        while (iterator.hasNext()){
            Object[] tmp = (Object[]) iterator.next();
            TestDTO testDTO = new TestDTO((String)tmp[0],(int)tmp[1]);
            list.add(testDTO);
        }
        return list;
    }
}
