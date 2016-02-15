package com.bionic.DAO;

import com.bionic.entities.UserGroup;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;

/**
 * package: com.bionic.DAO
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 08.02.2016
 */
@Repository("userGroupDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class UserGroupDAO extends AbstractDAO<UserGroupDAO> {
    public UserGroupDAO() {
        super(UserGroupDAO.class);
    }

    public List<UserGroup> getAllGroups(){
        Query query = em.createNamedQuery("getAllGroups");
        return (List<UserGroup>)query.getResultList();
    }

    public List<BigInteger> getUsersIdByGroup(String groupName){
        Query query = em.createNamedQuery("getUsersIdByGroup");
        query.setParameter("groupName", groupName);
        return (List<BigInteger>)query.getResultList();
    }
}
