package com.bionic.DAO;

import com.bionic.entities.OneTimeTest;
import com.bionic.wrappers.LeaderBoardWrapper;
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
public class OneTimeTestDAO extends AbstractDAO<OneTimeTest> {

    public OneTimeTestDAO() {
        super(OneTimeTest.class);
    }


    public List<LeaderBoardWrapper.NickMark> getLeaderBoard(long testId, long pageNumber, long pageStackSize) {

        Query query = em.createNamedQuery("getLeaderBoard");
        query.setParameter("testId", testId);
        query.setParameter("pageNumber", pageNumber);
        query.setParameter("pageStackSize", pageStackSize);
        return (List<LeaderBoardWrapper.NickMark>) query.getResultList();
    }

    public double countPositionInLeaderBoard(long testId, String userName) {
        Query query = em.createNamedQuery("countPositionInLeaderBoard");
        query.setParameter("testId", testId);
        query.setParameter("userName", userName);
        return (double) query.getSingleResult();
    }

    public BigInteger getBoardsPageCount() {
        Query query = em.createNamedQuery("getBoardsPageCount");
        return (BigInteger) query.getSingleResult();
    }

    public List<String> getEmailByNick(String nickName) {
        Query query = em.createNamedQuery("getEmailByNick");
        query.setParameter("nickName", nickName);
        return (List<String>)query.getResultList();
    }
}
