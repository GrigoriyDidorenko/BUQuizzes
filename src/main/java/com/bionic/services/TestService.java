package com.bionic.services;

import com.bionic.DAO.*;
import com.bionic.DTO.*;
import com.bionic.entities.*;
import com.bionic.exceptions.UserException;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
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
    private TestDAO testDAO;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private AnswerDAO answerDAO;
    @Autowired
    private MailManager mailManager;
    @Autowired
    private CategoryTestDAO categoryTestDAO;
    @Autowired
    private OneTimeTestDAO oneTimeTestDAO;

    public TestService() {
    }

    public ResultDTO processingAnswers(ArrayList<UserAnswerDTO> answerDTOs, long resultId) {
        ResultDTO resultDTO = null;
        try {
            Result result = resultDAO.find(resultId);
            ArrayList<UserAnswer> userAnswers = Util.convertUserAnswerDTOsToUserAnswers(answerDTOs, result);
            for (UserAnswer userAnswer : userAnswers) {
                userAnswerDAO.save(userAnswer);
            }
            result = calcResult(result, userAnswers);
            result.setSubmited(true);
            resultDAO.update(result);
            resultDTO = new ResultDTO(result.getMark(), String.valueOf(result.isChecked()));
        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        } finally {
            return resultDTO;
        }
    }

    public int processingOpenedAnswers(UserAnswerDTO answerDTO) {
        Result result = resultDAO.find(answerDTO.getResultId());
        UserAnswer answer = userAnswerDAO.find(answerDTO.getId());
        answer.setIsChecked(true);
        result.setMark(result.getMark() + answerDTO.getMark());
        userAnswerDAO.update(answer);
        int counter = resultDAO.hasUncheckedAnswers(answerDTO.getResultId()).intValue();
        if (counter == 0) {
            result.setIsChecked(true);
            resultDAO.update(result);
        }
        return counter;
    }

    public void saveCreatedTest(TestDTO testDTO) {
        try {
            Test test = Util.convertTestDTOToTest(testDTO);
            testDAO.save(test);
        } catch (Exception e) {
            System.out.println("failed to save test");
        }
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
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getId() == userAnswer.getAnswerId()) {
                               mark+= answer.getMark()>=0 ? answer.getMark() : 0;
                            }
                        }
                    }
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

    @Transactional
    public void importTest(HashSet<TestDTO> testDTOs) throws UserException {
        for (TestDTO testDTO : testDTOs) {
            saveAndImportTest(testDTO);
        }
    }

    @Transactional
    public Test importTest(TestDTO testDTO) throws UserException {
        if (testDTO.getDuration() == 0 && !testDTO.isOneTime())
            throw new UserException("If test isn't open, duration can't be 0");
        HashSet<Question> questions = new HashSet<>();
        Test test = new Test();
        test.setId(testDTO.getId());
        test.setDuration(testDTO.getDuration());
        test.setTestName(testDTO.getTestName());
        test.setOneTime(testDTO.isOneTime());
        CategoryTest categoryTest = categoryTestDAO.getСategoryTestByTestName(testDTO.getCategoryTestName());
        if (categoryTest == null)
            categoryTest = new CategoryTest(testDTO.getCategoryTestName());
        test.setCategoryTest(categoryTest);
        if (testDTO.getQuestions() != null)
            for (QuestionDTO questionDTO : testDTO.getQuestions()) {
                int positiveMark = 0;
                HashSet<Answer> answers = new HashSet<>();
                Question question = new Question();
                question.setTest(test);
                question.setQuestion(questionDTO.getQuestion());
                    question.setId(questionDTO.getId());
                if (questionDTO.getAnswers() != null)
                    for (AnswerDTO answerDTO : questionDTO.getAnswers()) {
                        Answer answer = new Answer();
                        answer.setAnswerText(answerDTO.getAnswerText());
                        answer.setQuestion(question);
                        answer.setMark(answerDTO.getMark());
                        answer.setId(answerDTO.getId());
                        answers.add(answer);
                        if (answer.getMark() > 0)
                            positiveMark++;
                    }

                /*TODO : заглушка, исправить + проверка на дьюрейшн*/

/*                if (answers.size() != 0 && positiveMark == 0)
                    throw new UserException("Question should have at least one answer with positive" +
                            " mark or doesn't have any answers at all");*/
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
        return test;
    }

    /*TODO: CHECK IT*/

    public void updateTest(TestDTO testDTO) throws UserException {
        Test test = importTest(testDTO);
        testDAO.update(test);
        if (testDTO.getTestsToGroups()!=null)
            for (TestDTO.TestToGroup testToGroup : testDTO.getTestsToGroups()) {
                List<BigInteger> resultIds = new ArrayList<>(resultDAO.getResultIdsByGroupAndTest(test.getId(), testToGroup.getGroupName()));
                if (!resultIds.isEmpty())
                    for (BigInteger eachResult : resultIds) {
                        Result result = resultDAO.find(eachResult.intValue());
                        result.setAccessBegin(testToGroup.getAccessBegin());
                        result.setAccessEnd(testToGroup.getAccessEnd());
                        resultDAO.update(result);
                    }
                else {
                    trainerService.testToGroup(Collections.singletonList(testToGroup), test);
                }
            }
    }

    public Test saveAndImportTest(TestDTO testDTO) throws UserException {
        Test test = importTest(testDTO);
        testDAO.save(test);
        return test;
    }

    public ResultDTO processingAnswersForOneTimeTest(ArrayList<UserAnswerDTO> answerDTOs, final long testId, final String nickName, final String email, final String name, final String host) {
        OneTimeTest oneTimeTest = new OneTimeTest(name, nickName, email, testId);
        Test test = testDAO.find(testId);
        final ResultDTO resultDTO = new ResultDTO();
        try {
            ArrayList<UserAnswer> userAnswers = Util.convertUserAnswerDTOsToTempUserAnswers(answerDTOs);
            oneTimeTest.setMark(calcResultForOneTimeTest(userAnswers, test));
            resultDTO.setMark(oneTimeTest.getMark());
            oneTimeTestDAO.save(oneTimeTest);
            resultDTO.setCheckStatus(name + ",відправився результат на поштову скриньку:" + email);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mailManager.send(email, "Passing test " + testDAO.find(testId).getTestName(),
                            "<div style='margin:0 auto; width:60%; height:60px; margin-top:-30px; margin-bottom:10px;'><img src='http://cs630017.vk.me/v630017821/ef59/DY0m5ySG0iQ.jpg' style='padding:0; margin:0; padding-left:20px;'></div>" +
                                    "<div style='margin:0 auto;width:60%;height:32px;background-color:#0090b9;margin-bottom:20px;padding-top:15px;'><span style='color:white; font-size: 18px; padding-top:8px; padding-left:20px;'>TEST RESULTS</span></div>" +
                                    "<div style='margin:0 auto; width:60%; padding-left:40px;margin-bottom:5px;'><span>You have successfully passed test: " + "</span><span style ='color:#0090b9;'>" + testDAO.find(testId).getTestName() + "</span></div>" +
                                    "<div style='margin:0 auto; width:60%; padding-left:40px;margin-bottom:5px;'><span>Your mark: " + "</span><span style ='color:#0090b9;'>" + String.valueOf(resultDTO.getMark()) + "%</span></div>" +
                                    "<div style='margin:0 auto; width:60%; padding-left:40px;'><span>You are able to check your result: " + "</span><span style ='color:#0090b9; font-decoration:none;'>" + host + "/pages/openTests/LeaderBoard.html?testId=" + testDAO.find(testId).getId() + "&page=" + getUserPageInLeaderBoard(testId, nickName) + "&nickName=" + nickName);

                }
            }).start();
        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        } finally {
            return resultDTO;
        }
    }

    public String getUserPageInLeaderBoard(long testId, String userName) {
        /*TODO: BETA*/
        double userPositionInLeaderBoard = oneTimeTestDAO.countPositionInLeaderBoard(testId, userName);
        try {
            int page = (int) (Math.ceil(userPositionInLeaderBoard / GuestService.getPageStackSize()));
            return String.valueOf(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Integer calcResultForOneTimeTest(ArrayList<UserAnswer> userAnswers, Test test) {
        int mark = 0, maxmark = 0;
        for (Question question : test.getQuestions()) {
            if (question.getIsOpen()) {
                continue;
            }
            if (!question.getIsMultichoice()) {
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getMark() > 0) maxmark += answer.getMark();
                            if (answer.getId() == userAnswer.getAnswerId()) {
                                mark += answer.getMark()>=0 ? answer.getMark() : 0;
                            }
                        }
                    }
                }
                continue;
            }
            if (question.getIsMultichoice()) {
                int preMark = 0;
                for (UserAnswer userAnswer : userAnswers) {
                    if (userAnswer.getQuestionId() == question.getId()) {
                        for (Answer answer : question.getAnswers()) {
                            if (answer.getMark() > 0) maxmark += answer.getMark();
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
        mark = Math.round(((float) mark / (float) maxmark) * 100);
        return mark;
    }

    public Set<String> getAllСategoryTestName() {
        return categoryTestDAO.getAllСategoryTestName();
    }
}