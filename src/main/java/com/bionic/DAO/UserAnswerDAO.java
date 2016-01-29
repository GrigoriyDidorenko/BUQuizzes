package com.bionic.DAO;

import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.Test;
import com.bionic.entities.UserAnswer;
import com.bionic.wrappers.OpenQuestionWrapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Repository("userAnswerDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class UserAnswerDAO extends AbstractDAO<UserAnswer> {
public UserAnswerDAO() {
        super(UserAnswer.class);
        }

        public List<OpenQuestionWrapper> getUncheckedTests(long userId){
                Query query = em.createNamedQuery("getUncheckedTests");
                query.setParameter("userId", userId);
                return (List<OpenQuestionWrapper>)query.getResultList();
        }

        public List<UserAnswerDTO> getUncheckedAnswersForCurrentQuestion(long questionId){
                Query query = em.createNamedQuery("getUncheckedAnswersForCurrentQuestion");
                query.setParameter("questionId", questionId);
                return (List<UserAnswerDTO>)query.getResultList();
        }
}
