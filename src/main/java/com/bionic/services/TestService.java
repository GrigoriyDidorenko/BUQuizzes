package com.bionic.services;

import com.bionic.DAO.ResultDAO;
import com.bionic.DAO.UserAnswerDAO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Created by rondo104 on 17.11.2015.
 */
@Component
public class TestService {

    @Autowired
    private Converter converter;
    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private UserAnswerDAO userAnswerDAO;

    public int processingAnswers(Collection<UserAnswerDTO> answerDTOs, long resultId){
        Result result = resultDAO.find(resultId);
        Set<Question> questions = result.getTest().getQuestions();
        int mark;
        Collection<UserAnswer> userAnswers = converter.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
        for(UserAnswer userAnswer : userAnswers){
            userAnswerDAO.save(userAnswer);
        }
        mark=CalculationAnswersForTest.calcMark(result.getUser(),result.getTest());
///*        for(Question question : questions){
//            if (question.getIsOpen() == true ) result.setIsChecked(false);
//        }*/
        result.setMark(mark);
        resultDAO.update(result);
        return mark;
    }




}
