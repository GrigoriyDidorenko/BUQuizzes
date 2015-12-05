package com.bionic.DAO;

import com.bionic.entities.UserAnswer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Repository("userAnswerDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class UserAnswerDAO extends AbstractDAO<UserAnswer> {
public UserAnswerDAO() {
        super(UserAnswer.class);
        }
}
