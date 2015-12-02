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
import java.util.*;

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

    public String processingAnswers(ArrayList<UserAnswerDTO> answerDTOs, long resultId) {
        String markResult;
        try {
            Result result = resultDAO.find(resultId);
            ArrayList<UserAnswer> userAnswers = converter.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
            for (UserAnswer userAnswer : userAnswers) {
                userAnswerDAO.save(userAnswer);
            }
            result = calcResult(result, userAnswers);
            resultDAO.update(result);
            markResult = result.getMark() + " isChecked " + result.isChecked();
        } catch (Exception e) {
            markResult = e.getMessage();
        }
        return markResult;
    }

    public Result calcResult(Result result, ArrayList<UserAnswer> userAnswers) throws Exception {
        int mark = 0;
        result.setIsChecked(true);
        int end = userAnswers.size();
        for (int i = 0; i < end; i++) {
            if (userAnswers.get(i) == null) continue;
            Answer answer = answerDAO.find(userAnswers.get(i).getAnswerId());
            if (!answer.getQuestion().getIsMultichoice() && !answer.getQuestion().getIsOpen()) {
                mark += answer.getMark();
                continue;
            } else if (answer.getQuestion().getIsMultichoice()) {
                int multiMark = answer.getMark();
                long multieQuestionId = answer.getQuestion().getId();
                for (int j = i + 1; j < end; j++) {
                    if (userAnswers.get(j).getQuestionId() == multieQuestionId) {
                        multiMark += answerDAO.find(userAnswers.get(j).getAnswerId()).getMark();
                        userAnswers.set(j, null);
                    }
                }
                multiMark = multiMark < 0 ? 0 : multiMark;
                mark += multiMark;
                continue;
            } else if (answer.getQuestion().getIsOpen()) {
                result.setIsChecked(false);
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
                HashSet<Question> questions = new HashSet<>();
                Test test = new Test();
                test.setDuration(testDTO.getDuration());
                test.setTestName(testDTO.getTestName());
                if(testDTO.getQuestions()!=null)
                for (QuestionDTO questionDTO : testDTO.getQuestions()) {
                    HashSet<Answer> answers = new HashSet<>();
                    Question question = new Question();
                    question.setTest(test);
                    question.setQuestion(questionDTO.getQuestion());
                    if(questionDTO.getAnswers()!=null)
                    for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                        Answer answer = new Answer();
                        answer.setAnswerText(answerDTO.getAnswerText());
                        answer.setQuestion(question);
                        answer.setMark(answerDTO.getMark());
                        answers.add(answer);
                    }
                    switch (answers.size()) {
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
