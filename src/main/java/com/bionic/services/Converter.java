package com.bionic.services;

import com.bionic.DTO.AnswerDTO;
import com.bionic.DTO.QuestionDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.*;
import com.bionic.entities.Answer;
import com.bionic.entities.Question;
import com.bionic.entities.Test;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by User on 13.11.2015.
 */
@Service
public class Converter {

    private static boolean alreadyExecuted;

    public static TestDTO convertTestToDTO(Test test) {
        Set<QuestionDTO> questionDTOs = new HashSet<>();
        for (Question question : test.getQuestions()) {
            Set<AnswerDTO> answerDTOs = new HashSet<>();
            for (Answer answer : question.getAnswers()) {
                answerDTOs.add(new AnswerDTO(answer.getId(), answer.getAnswerText()));
            }
            if (!alreadyExecuted) answerDTOs = randomizeAnswers(answerDTOs);
            QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getQuestion(),
                    answerDTOs, question.getIsMultichoice(), question.getIsOpen());
            questionDTOs.add(questionDTO);
        }
        if (!alreadyExecuted) questionDTOs = randomizeQuestions(questionDTOs);
        TestDTO testDTO = new TestDTO(test.getId(), test.getTestName(), test.getDuration(), questionDTOs);
        alreadyExecuted = true;
        return testDTO;
    }

    public static Set<QuestionDTO> randomizeQuestions(Set<QuestionDTO> questionDTOs) {
        ArrayList<QuestionDTO> shuffledList = new ArrayList<>(questionDTOs);
        Collections.shuffle(shuffledList);
        return new LinkedHashSet<>(shuffledList);
    }

    public static Set<AnswerDTO> randomizeAnswers(Set<AnswerDTO> answerDTOs) {
        ArrayList<AnswerDTO> shuffledList = new ArrayList<>(answerDTOs);
        Collections.shuffle(shuffledList);
        return new LinkedHashSet<>(shuffledList);
    }

    public UserAnswer convertUserAnswerDTOToUserAnswer(UserAnswerDTO userAnswerDTO, Result result, Long answerId) {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuestionId(userAnswerDTO.getQuestionId());
        userAnswer.setResultId(result.getId());
        userAnswer.setUserAnswer(userAnswerDTO.getAnswerText());
        if (!(answerId == null)) userAnswer.setAnswerId(answerId);
        return userAnswer;
    }

    public ArrayList<UserAnswer> convertUserAnswerDTOsToUserAnswers(ArrayList<UserAnswerDTO> userAnswerDTOs, Result result) throws NumberFormatException {
        ArrayList<UserAnswer> userAnswers = new ArrayList<>();
        for (UserAnswerDTO userAnswerDTO : userAnswerDTOs) {
            if (!userAnswerDTO.getAnswerId().isEmpty()) {
                for (String answerId : userAnswerDTO.getAnswerId()) {
                    userAnswers.add(convertUserAnswerDTOToUserAnswer(userAnswerDTO, result, Long.parseLong(answerId)));
                }
            } else {
                userAnswers.add(convertUserAnswerDTOToUserAnswer(userAnswerDTO, result, null));
            }
        }
        return userAnswers;
    }

}
