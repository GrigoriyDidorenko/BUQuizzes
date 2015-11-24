package com.bionic.services;

import com.bionic.DTO.AnswerDTO;
import com.bionic.DTO.QuestionDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Answer;
import com.bionic.entities.Question;
import com.bionic.entities.Test;
import com.bionic.wrappers.TestWrapper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by User on 13.11.2015.
 */
@Service
public class Converter {

    private static boolean alreadyExecuted;

    public static Set<TestDTO> convertAvailableTestsToDTO(List<TestWrapper> tests){
        Set<TestDTO> testDTOs = new HashSet<>();
        for (TestWrapper test : tests){
            TestDTO testDTO = new TestDTO(test.getName(), test.getDuration());
            testDTOs.add(testDTO);
        }
        return testDTOs;
    }
    
    public static TestDTO convertTestToDTO(Test test){
        Set<QuestionDTO> questionDTOs = new HashSet<>();
        for (Question question : test.getQuestions()){
            Set<AnswerDTO> answerDTOs = new HashSet<>();
            for (Answer answer : question.getAnswers()){
                answerDTOs.add(new AnswerDTO(answer.getAnswerText()));
            }
            if(!alreadyExecuted) answerDTOs = randomizeAnswers(answerDTOs);
            QuestionDTO questionDTO = new QuestionDTO(question.getQuestion(),answerDTOs);
            questionDTOs.add(questionDTO);
        }
        if(!alreadyExecuted) questionDTOs = randomizeQuestions(questionDTOs);
        TestDTO testDTO = new TestDTO(test.getId(),test.getTestName(),test.getDuration(), questionDTOs);
        alreadyExecuted = true;
        return testDTO;
    }

    public static Set<QuestionDTO> randomizeQuestions(Set<QuestionDTO> questionDTOs){
        ArrayList<QuestionDTO> shuffledList = new ArrayList<>(questionDTOs);
        Collections.shuffle(shuffledList);
        return new LinkedHashSet<>(shuffledList);
    }
    public static Set<AnswerDTO> randomizeAnswers(Set<AnswerDTO> answerDTOs){
        ArrayList<AnswerDTO> shuffledList = new ArrayList<>(answerDTOs);
        Collections.shuffle(shuffledList);
        return new LinkedHashSet<>(shuffledList);
    }
}
