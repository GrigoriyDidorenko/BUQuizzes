package com.bionic.services;

import com.bionic.DAO.*;
import com.bionic.DTO.AnswerDTO;
import com.bionic.DTO.QuestionDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;

    public TestService() {
    }

    public String processingAnswers(Collection<UserAnswerDTO> answerDTOs, long resultId) {
        String markResult;
        try {
            Result result = resultDAO.find(resultId);
            Collection<UserAnswer> userAnswers = converter.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
            for (UserAnswer userAnswer : userAnswers) {
                userAnswerDAO.save(userAnswer);
            }
            result = calcResult(result, userAnswers);
            resultDAO.update(result);
            markResult= result.getMark() + " isChecked " + result.isChecked();
        } catch (Exception e) {
           markResult = e.getMessage();
        }
        return markResult;
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
                int numberUserCorrectAnswers = 0, numberIsCorrectInQuestion;
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
                mark += 1 / (float) numberIsCorrectInQuestion * (float) numberUserCorrectAnswers * question.getMark();
                mark = (float) (Math.round(mark * 100.0) / 100.0);
            }
        }
        result.setMark(mark);
        return result;
    }

    public String importTest(MultipartFile file) {
        HashSet<TestDTO> testDTOs;
        try {
            testDTOs = mapper.readValue(file.getInputStream(),
                    new TypeReference<Set<TestDTO>>() {
                    });
            for (TestDTO testDTO : testDTOs) {
                HashSet<Answer> answers = new HashSet<>();
                HashSet<Question> questions = new HashSet<>();
                Test test = new Test();
                Question question = new Question();
                Answer answer = new Answer();
                test.setDuration(testDTO.getDuration());
                test.setTestName(testDTO.getTestName());
                for (QuestionDTO questionDTO : testDTO.getQuestions()) {
                    question.setTest(test);
                    question.setQuestion(questionDTO.getQuestion());
                    for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                        answer.setAnswerText(answerDTO.getAnswerText());
                        answer.setQuestion(question);
                        answers.add(answer);
                    }
                    question.setAnswers(answers);
                    questions.add(question);
                }
                test.setQuestions(questions);
                saveTest(test, questions, answers);
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "successful";
    }

    //TODO: Transaction Rollback
    public void saveTest(Test test, HashSet<Question> questions, HashSet<Answer> answers) {
        testDAO.save(test);
        for (Question question : questions) {
            questionDAO.save(question);
            for (Answer answer : answers)
                answerDAO.save(answer);
        }
    }
}
