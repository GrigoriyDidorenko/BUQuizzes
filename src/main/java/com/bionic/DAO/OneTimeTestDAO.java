package com.bionic.DAO;

import com.bionic.entities.CategoryTest;
import com.bionic.entities.OneTimeTest;
import com.bionic.wrappers.NickMarkWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by rondo104 on 26.12.2015.
 */
@Repository("oneTimeTestDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class OneTimeTestDAO extends AbstractDAO<OneTimeTest>  {

    public OneTimeTestDAO() {
        super(OneTimeTest.class);
    }


    public List<NickMarkWrapper.NickMark> getLeaderBoard(long testId, long pageNumber, long pageStackSize){

        Query query = em.createNamedQuery("getLeaderBoard");
        query.setParameter("testId", testId);
        query.setParameter("pageNumber", pageNumber);
        query.setParameter("pageStackSize", pageStackSize);
        return (List<NickMarkWrapper.NickMark>) query.getResultList();
    }

    public BigInteger getBoardsPageCount(){
        Query query = em.createNamedQuery("getBoardsPageCount");
        return (BigInteger) query.getSingleResult();
    }

    public List<OneTimeTest> getTestsForNickname(String nickName) {
        Query query = em.createNamedQuery("getTestsForNickname");
        query.setParameter("nickName", nickName);
        return (List<OneTimeTest>) query.getResultList();
    }
}
