package com.bionic.services;


import com.bionic.DAO.AnswerDAO;
import com.bionic.DAO.QuestionDAO;
import com.bionic.DAO.TestDAO;
import com.bionic.DTO.AnswerDTO;
import com.bionic.DTO.QuestionDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Answer;
import com.bionic.entities.Question;
import com.bionic.entities.Test;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * package: com.bionic.services
 * project: TestDTO
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 02.11.2015
 */
@Component
public class ImportTest {


    private TestDAO testDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;

    public TestDAO getTestDAO() {
        return testDAO;
    }

    @Autowired
    public void setTestDAO(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public QuestionDAO getQuestionDAO() {
        return questionDAO;
    }

    @Autowired
    public void setQuestionDAO(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    public AnswerDAO getAnswerDAO() {
        return answerDAO;
    }

    @Autowired
    public void setAnswerDAO(AnswerDAO answerDAO) {
        this.answerDAO = answerDAO;
    }

    public void getTrackInJSON(Set<TestDTO> testDTOs) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("d:\\file.json"), testDTOs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void JSONtoString(File file) {

        ObjectMapper mapper = new ObjectMapper();
        HashSet<TestDTO> testDTOs;
        HashSet<Answer> answers = new HashSet<>();
        HashSet<Question> questions = new HashSet<>();
        try {
            testDTOs = mapper.readValue(file,
                    new TypeReference<Set<TestDTO>>() {
                    });
            for (TestDTO testDTO : testDTOs) {
                System.out.println("I AM HERE");
                Test test = new Test();
                Question question = new Question();
                Answer answer = new Answer();
                test.setDuration(testDTO.getDuration());
                test.setTestName(testDTO.getTestName());
                for(QuestionDTO questionDTO : testDTO.getQuestions()){
                    question.setTest(test);
                    question.setQuestion(questionDTO.getQuestion());
                    for(AnswerDTO answerDTO : questionDTO.getAnswers()){
                        answer.setAnswerText(answerDTO.getAnswerText());
                        answer.setQuestion(question);
                        answers.add(answer);
                    }
                    question.setAnswers(answers);
                    questions.add(question);
                }
                test.setQuestions(questions);
                saveTest(test,questions,answers);

            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTest(Test test, HashSet<Question> questions, HashSet<Answer> answers){
        getTestDAO().save(test);
        for(Question question : questions) {
            getQuestionDAO().save(question);
            for(Answer answer : answers)
                getAnswerDAO().save(answer);
        }

    }
}

