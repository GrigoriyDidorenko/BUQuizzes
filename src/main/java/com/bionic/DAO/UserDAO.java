package com.bionic.DAO;

import com.bionic.entities.Test;
import com.bionic.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
 * @date: 12.11.2015
 */
@Repository("userDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class UserDAO extends AbstractDAO<User> {
    public UserDAO() {
        super(User.class);
    }

    public List<Test> getAvailableTestsById(long id){
        Query query = em.createNamedQuery("getAvailableTestsById");
        query.setParameter("id", id);
        return (List<Test>)query.getResultList();
    }
    public User getUserByEmail(String email){
        Query query=em.createNamedQuery("getUserByEmail");
        query.setParameter("email", email);
        return (User)query.getSingleResult();
    }

}