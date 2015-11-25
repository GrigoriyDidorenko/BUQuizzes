package com.bionic.services;

import com.bionic.DAO.ResultDAO;
import com.bionic.DAO.UserAnswerDAO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.Answer;
import com.bionic.entities.Question;
import com.bionic.entities.Result;
import com.bionic.entities.UserAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Service
public class TestService {

    @Autowired
    private Converter converter;
    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private UserAnswerDAO userAnswerDAO;

    public TestService() {
    }

    public String processingAnswers(Collection<UserAnswerDTO> answerDTOs, long resultId) {
        Result result = resultDAO.find(resultId);
        Collection<UserAnswer> userAnswers = converter.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
        for (UserAnswer userAnswer : userAnswers) {
            userAnswerDAO.save(userAnswer);
        }
        result = calcResult(result, userAnswers);
        resultDAO.update(result);
        return result.getMark() + " isChecked " + result.isChecked();
    }

    public Result calcResult(Result result, Collection<UserAnswer> userAnswers) {
        float mark = 0.0f;
        result.setIsChecked(true);
        for (Question question : result.getTest().getQuestions()) {
            if (question.getIsOpen()) {
                result.setIsChecked(false);
                continue;
            }
            if (!question.getIsMultichoice()) {
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getAnswerText().equals(userAnswer.getUserAnswer())) {
                                if (answer.getIsCorrect()) {
                                    mark += question.getMark();
                                    break;
                                } else {
                                    break;
                                }
                            }
                        }
                        break;
                    }
                }
            }
            if (question.getIsMultichoice()) {
                int numberUserCorrectAnswers=0, numberIsCorrectInQuestion;
                for (UserAnswer selectUserAnswer : userAnswers) {
                    if (question.getId() == selectUserAnswer.getQuestionId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getAnswerText().equals(selectUserAnswer.getUserAnswer())) {
                                if (answer.getIsCorrect()) {
                                    numberUserCorrectAnswers++;
                                } else {
                                    numberUserCorrectAnswers--;
                                }
                            }
                        }
                    }
                }
                numberIsCorrectInQuestion = 0;
                for (Answer answer : question.getAnswers()) {
                    if (answer.getIsCorrect()) numberIsCorrectInQuestion++;
                }
                numberUserCorrectAnswers = numberUserCorrectAnswers < 0 ? 0 : numberUserCorrectAnswers;
                mark +=  1 / (float) numberIsCorrectInQuestion * (float) numberUserCorrectAnswers * question.getMark();
                mark = (float) (Math.round(mark * 100.0)/100.0);
            }
        }
        result.setMark(mark);
        return result;
    }

}
