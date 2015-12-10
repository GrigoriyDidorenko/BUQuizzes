package com.bionic.services;

import com.bionic.DAO.*;
import com.bionic.DTO.*;
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
import java.util.*;

/**
 * Created by rondo104 on 25.11.2015.
 */
@Service
public class TestService {


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

    public ResultDTO processingAnswers(ArrayList<UserAnswerDTO> answerDTOs, long resultId) {
        ResultDTO resultDTO = null;
        Result result = resultDAO.find(resultId);
        try {
            ArrayList<UserAnswer> userAnswers = Converter.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
            for (UserAnswer userAnswer : userAnswers) {
                userAnswerDAO.save(userAnswer);
            }
            result = calcResult(result, userAnswers);

        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        }finally {
            result.setSubmited(true);
            resultDAO.update(result);
            resultDTO = new ResultDTO(result.getMark(),String.valueOf(result.isChecked()));
        }
        return resultDTO;
    }

    public Result calcResult(Result result, ArrayList<UserAnswer> userAnswers) throws Exception {
        int mark = 0;
        result.setIsChecked(true);
        for (Question question : result.getTest().getQuestions()) {
            if (question.getIsOpen()) {
                result.setIsChecked(false);
                continue;
            }
            if (!question.getIsMultichoice()) {
                boolean flag = false;
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getId() == userAnswer.getAnswerId()) {
                                mark += answer.getMark();
                                flag = true;
                                break;
                            }
                        }
                    }
                    if (flag) break;
                }
                continue;
            }
            if (question.getIsMultichoice()) {
                int preMark = 0;
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getId() == userAnswer.getAnswerId()) {
                                preMark += answer.getMark();
                            }
                        }
                    }
                }
                preMark = preMark < 0 ? 0 : preMark;
                mark += preMark;
                continue;
            }
        }
        result.setMark(mark);
        return result;
    }

    public String importTest(MultipartFile file) throws Exception {
        HashSet<TestDTO> testDTOs;
        try {
            testDTOs = mapper.readValue(file.getInputStream(),
                    new TypeReference<Set<TestDTO>>() {
                    });
            for (TestDTO testDTO : testDTOs) {
                HashSet<Question> questions = new HashSet<>();
                Test test = new Test();
                test.setDuration(testDTO.getDuration());
                test.setTestName(testDTO.getTestName());
                if (testDTO.getQuestions() != null)
                    for (QuestionDTO questionDTO : testDTO.getQuestions()) {
                        int positiveMark = 0;
                        HashSet<Answer> answers = new HashSet<>();
                        Question question = new Question();
                        question.setTest(test);
                        question.setQuestion(questionDTO.getQuestion());
                        if (questionDTO.getAnswers() != null)
                            for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                                Answer answer = new Answer();
                                answer.setAnswerText(answerDTO.getAnswerText());
                                answer.setQuestion(question);
                                answer.setMark(answerDTO.getMark());
                                answers.add(answer);
                                if(answer.getMark()>0)
                                    positiveMark++;
                            }
                        if(answers.size()!=0 && positiveMark == 0)
                            throw new Exception("Question should have at least one answer with positive" +
                                    " mark or doesn't have any answers at all");
                        switch (positiveMark) {
                            case 0:
                                question.setIsOpen(true);
                                question.setIsMultichoice(false);
                                break;
                            case 1:
                                question.setIsOpen(false);
                                question.setIsMultichoice(false);
                                break;
                            default:
                                question.setIsOpen(false);
                                question.setIsMultichoice(true);
                                break;
                        }
                        question.setAnswers(answers);
                        questions.add(question);
                    }
                test.setQuestions(questions);
                testDAO.save(test);
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            return "Invalid format";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "successful";
    }
}
