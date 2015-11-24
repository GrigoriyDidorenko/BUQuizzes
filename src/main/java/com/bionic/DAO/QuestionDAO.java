package com.bionic.DAO;


import com.bionic.entities.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by c266 on 28.07.2015.
 */

@Repository("questionDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionDAO extends AbstractDAO<Question> {
    public QuestionDAO() {
        super(Question.class);
    }
/*
    public List<Question> getQuestionsByTestId(int testId) {
    Query query = em.createNamedQuery("getQuestionsByTestId");
    query.setParameter("test", testId);
    return query.getResultList();
}

    public Question getQuestionByText(String questionText) {
        Query query = em.createNamedQuery("getQuestionByText");
        query.setParameter("questionText", questionText);
        return (Question) query.getSingleResult();
    }

    public Question getQuestionByAnswerId(int answerId){
        Query query = em.createNamedQuery("getQuestionByAnswerId");
        query.setParameter("answer", answerId);
        return (Question)query.getSingleResult();
    }

    public List<Question> getVisibleQuestionsByTestId(int testId) {
        Query query = em.createNamedQuery("getVisibleQuestionsByTestId");
        query.setParameter("test", testId);
        return query.getResultList();
    }*/
}
