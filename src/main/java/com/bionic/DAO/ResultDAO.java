package com.bionic.DAO;

import com.bionic.DTO.TestDTO;
import com.bionic.entities.Permission;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.wrappers.TestWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigInteger;
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

    public ResultDAO() {
        super(Result.class);
    }

    public BigInteger hasUncheckedAnswers(long resultId) {
        Query query = em.createNamedQuery("hasUncheckedAnswers");
        query.setParameter("resultId", resultId);
        return (BigInteger) query.getSingleResult();
    }

    public Test getCurrentTest(long id, long testId, Permission permission) {
        Query query = null;
        if (permission == Permission.PASS_THE_TEST)
            query = em.createNamedQuery("getCurrentTestByIdForStudent");
        if (permission == Permission.EDIT_THE_TEST)
            query = em.createNamedQuery("getCurrentTestByIdForTrainer");
        query.setParameter("userId", id);
        query.setParameter("testId", testId);
        query.setParameter("permission", permission);
        return (Test) query.getResultList().get(0);
    }

    public BigInteger countCurrentTestGivenToUser(long testId, long userId, Permission permission) {
        Query query = em.createNamedQuery("countCurrentTestGivenToUser");
        query.setParameter("testId", testId);
        query.setParameter("userId", userId);
        query.setParameter("permission", permission);
        return (BigInteger) query.getSingleResult();
    }

    public List<TestDTO> getAvailableTestsNames(long id, Permission permission) {
        List<TestDTO> list = new ArrayList<>();
        Query query = null;
        if (permission == Permission.PASS_THE_TEST)
            query = em.createNamedQuery("getAvailableTestsNamesForStudent");
        if (permission == Permission.EDIT_THE_TEST)
            query = em.createNamedQuery("getAvailableTestsNamesForTrainer");
        query.setParameter("userId", id);
        query.setParameter("permission", permission);
        Iterator iterator = query.getResultList().iterator();
        while (iterator.hasNext()) {
            Object[] tmp = (Object[]) iterator.next();
            TestDTO testDTO = new TestDTO((long) tmp[0], (String) tmp[1], (int) tmp[2]);
            list.add(testDTO);
        }
        return list;
    }

    public BigInteger getResultByIds(long userId, long testId) {
        Query query = em.createNamedQuery("getResultByIds");
        query.setParameter("testId", testId);
        query.setParameter("userId", userId);
        return (BigInteger) query.getSingleResult();
    }

    public List<TestDTO> getPassTests(long id) {
        List<TestDTO> list = new ArrayList<>();
        Query query = em.createNamedQuery("getPassTests");
        query.setParameter("userId", id);
        Iterator iterator = query.getResultList().iterator();
        while (iterator.hasNext()) {
            Object[] tmp = (Object[]) iterator.next();
            TestDTO testDTO = new TestDTO((long) tmp[0], (String) tmp[1], (int) tmp[2], (boolean) tmp[3]);
            list.add(testDTO);
        }
        return list;
    }

    public List<TestWrapper> getTestsForUserId(long id) {
        List<TestWrapper> list = new ArrayList<>();
        Query query = em.createNamedQuery("getTestsForUserId");
        query.setParameter("userId", id);
        Iterator iterator = query.getResultList().iterator();
        while (iterator.hasNext()) {
            Object[] tmp = (Object[]) iterator.next();
            TestDTO testDTO = new TestDTO((long) tmp[0], (String) tmp[1], (int) tmp[2], (boolean) tmp[3], (boolean) tmp[4], (int) tmp[6]);
            list.add(new TestWrapper(testDTO, (long) tmp[5]));
        }
        return list;
    }
}
