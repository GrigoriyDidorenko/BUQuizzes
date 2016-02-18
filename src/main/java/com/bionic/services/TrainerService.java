package com.bionic.services;

import com.bionic.DAO.*;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.DTO.UserDTO;
import com.bionic.entities.Permission;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.entities.UserGroup;
import com.bionic.wrappers.OpenQuestionWrapper;
import com.bionic.wrappers.TestUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 06.12.2015
 */
@Service
public class TrainerService implements TestHandler<TestDTO> {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private UserAnswerDAO userAnswerDAO;
    @Autowired
    private UserGroupDAO userGroupDAO;
    @Autowired
    private UserService userService;

    public List<UserDTO> getAllUsersNames() {
        try {
            return userDAO.getUsersNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TestDTO> getUnarchivedTestsNames() {
        try {
            return testDAO.getUnarchivedTestsNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OpenQuestionWrapper> getUncheckedTests(long userId) {
        try {
            return userAnswerDAO.getUncheckedTests(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserAnswerDTO> getUncheckedAnswersForCurrentQuestion(String questionId) {
        try {
            return userAnswerDAO.getUncheckedAnswersForCurrentQuestion(Util.getLongId(questionId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestUserWrapper getAllUsersNamesAndUnarchivedTestsNames() {
        return new TestUserWrapper(getUnarchivedTestsNames(), getAllUsersNames());
    }

    public String saveTestToUser(TestUserWrapper testUserWrapper) {
        //TODO check if user already has this test
        Permission permission = Permission.PASS_THE_TEST;
        long userId = testUserWrapper.getUser().getId();
        long testId = testUserWrapper.getTest().getId();
        if (resultDAO.countCurrentTestGivenToUser(testId, userId, permission).intValue() == 0)
            resultDAO.save(new Result(false, false, permission,
                    userDAO.find(userId), testDAO.find(testId)));
        return "successful";
    }

    public List<UserGroup> getAllGroups() {
        return userGroupDAO.getAllGroups();
    }

    public List<BigInteger> getUsersIdByGroup(String groupName) {
        return userGroupDAO.getUsersIdByGroup(groupName);
    }


    public void testToGroup(List<TestDTO.TestToGroup> testsToGroups, Test test) {
        if (testsToGroups != null && !test.isOneTime()) {
            for (TestDTO.TestToGroup testToGroup : testsToGroups) {
                if (!testToGroup.getGroupName().equals(""))
                    for (BigInteger studentId : getUsersIdByGroup(testToGroup.getGroupName())) {
                        resultDAO.save(new Result(false, false, testToGroup.getAccessBegin(),
                                testToGroup.getAccessEnd(), Permission.PASS_THE_TEST, userDAO.find(studentId.intValue()), test));
                    }
            }
        }
        trainersPermissionToTest(test);
    }

    public void trainersPermissionToTest(Test test){
        Result result = new Result(false, false, Permission.EDIT_THE_TEST, userService.getAuthorizedUser(),test);
        resultDAO.save(result);
    }



    @Override
    public Set<TestDTO> getAvailableTestsNames(long idStr) {
        return new LinkedHashSet<>(resultDAO.getAvailableTestsNames(idStr, Permission.EDIT_THE_TEST));
    }

    @Override
    public TestDTO getCurrentTest(long id, String testIdStr) {
        TestDTO result = Util.convertUsersTestToDTO(resultDAO.getCurrentTest(id, Util.getLongId(testIdStr), Permission.EDIT_THE_TEST));
        result.setTestsToGroups(resultDAO.getGroupsForCurrentTest(Util.getLongId(testIdStr)));
        return result;

    }
}
