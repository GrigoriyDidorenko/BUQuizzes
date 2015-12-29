package com.bionic.services;

import com.bionic.DTO.*;
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

    public static Test convertTestDTOToTest(TestDTO testDTO){
        Test test = new Test();
        test.setTestName(testDTO.getTestName());
        test.setDuration(testDTO.getDuration());
        Set<Question> questions = new HashSet<>();
        for (QuestionDTO questionDTO : testDTO.getQuestions()){
            Question question = new Question();
            question.setQuestion(questionDTO.getQuestion());
            if (questionDTO.getAnswers().size()==0 /* || questionDTO.getAnswers() == null*/){
                question.setIsOpen(true);
                question.setIsMultichoice(false);
                question.setTest(test);
                questions.add(question);
                continue;
            }
            Set<Answer> answers = new HashSet<>();
            int isMultichoice = 0;
            for (AnswerDTO answerDTO : questionDTO.getAnswers()){
                Answer answer = new Answer();
                answer.setAnswerText(answerDTO.getAnswerText());
                answer.setMark(answerDTO.getMark());
                answer.setQuestion(question);
                answers.add(answer);
                if (answerDTO.getMark() > 0){
                    isMultichoice++;
                }
            }
            if (isMultichoice > 1){
                question.setIsMultichoice(true);
            }
            question.setIsOpen(false);
            question.setAnswers(answers);
            question.setTest(test);
            questions.add(question);
        }
        test.setQuestions(questions);
        return test;
    }

    public static TestDTO convertUsersTestToDTO(Test test) {
        TestDTO testDTO = new TestDTO();
        Set<QuestionDTO> questionDTOs = new HashSet<>();
        for (Question question : test.getQuestionsNotArchived()) {
            Set<AnswerDTO> answerDTOs = new HashSet<>();
            for (Answer answer : question.getAnswersNotArchived()) {
                answerDTOs.add(new AnswerDTO(answer.getId(), answer.getAnswerText()));
            }
            if (!alreadyExecuted) answerDTOs = randomizeAnswers(answerDTOs);
            if (!question.getIsArchived())
                questionDTOs.add(new QuestionDTO(question.getId(), question.getQuestion(),
                        answerDTOs, question.getIsMultichoice(), question.getIsOpen()));
        }
        if (!alreadyExecuted) questionDTOs = randomizeQuestions(questionDTOs);
        if (!test.isArchived())
            testDTO = new TestDTO(test.getId(), test.getTestName(), test.getDuration(), questionDTOs);
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

    public static UserAnswer convertUserAnswerDTOToUserAnswer(UserAnswerDTO userAnswerDTO, Result result, Long answerId) {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setQuestionId(userAnswerDTO.getQuestionId());
        userAnswer.setResultId(result.getId());
        userAnswer.setUserAnswer(userAnswerDTO.getAnswerText());
        if (!(answerId == null)) userAnswer.setAnswerId(answerId);
        return userAnswer;
    }

    public static ArrayList<UserAnswer> convertUserAnswerDTOsToUserAnswers(ArrayList<UserAnswerDTO> userAnswerDTOs, Result result) throws NumberFormatException {
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

    public static User convertUserDTOToUser(UserDTO userDTO) {
        try {
            return new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                    userDTO.getCell(), userDTO.getPosition(), userDTO.getRole());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<UserAnswer> convertUserAnswerDTOsToTempUserAnswers(ArrayList<UserAnswerDTO> guestAnswerDTOs) {
        ArrayList<UserAnswer> userAnswers = new ArrayList<>();
        for (UserAnswerDTO userAnswerDTO : guestAnswerDTOs) {
            if (!userAnswerDTO.getAnswerId().isEmpty()) {
                for (String answerId : userAnswerDTO.getAnswerId()) {
                    UserAnswer userAnswer = new UserAnswer();
                    userAnswer.setAnswerId(Long.valueOf(answerId)) ;
                    userAnswer.setQuestionId(userAnswerDTO.getQuestionId());
                    userAnswers.add(userAnswer);
                }
            } else {
                continue;
            }
        }
        return userAnswers;
    }

    public static long getLongId(String idStr) {
        long id = 0;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException e) {
            System.out.println("incorrect id");
        }
        if (id == 0)
            throw new RuntimeException("invalid id");
        return id;
    }
}
