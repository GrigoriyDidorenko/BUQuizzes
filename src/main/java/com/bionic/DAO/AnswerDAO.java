package com.bionic.DAO;


import com.bionic.model.Answer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by c266 on 28.07.2015.
 */
@Repository("answerDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class AnswerDAO extends AbstractDAO<Answer> {
    public AnswerDAO() {
        super(Answer.class);
    }

    /*public List<Answer> getAnswersByQuestionId(int questionId) {
        Query query = em.createNamedQuery("getAnswersByQuestionId");
        query.setParameter("question", questionId);
        return query.getResultList();
    }

    public List<Answer> getVisibleAnswersByQuestionId(int questionId) {
        Query query = em.createNamedQuery("getVisibleAnswersByQuestionId");
        query.setParameter("question", questionId);
        return query.getResultList();
    }

    public Answer getAnswerById(int answerId){
        Query query = em.createNamedQuery("getAnswerById");
        query.setParameter("answerId", answerId);
        return (Answer) query.getSingleResult();
    }*/
}
