package com.bionic.services;

import com.bionic.DAO.ResultDAO;
import com.bionic.DAO.TestDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserDTO;
import com.bionic.entities.Permission;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.entities.User;
import com.bionic.wrappers.TestUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
public class TrainerService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private ResultDAO resultDAO;

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

    public TestUserWrapper getAllUsersNamesAndUnarchivedTestsNames() {
        return new TestUserWrapper(getUnarchivedTestsNames(), getAllUsersNames());
    }

    public String saveTestToUser(TestUserWrapper testUserWrapper){
        //TODO check if user already has this test
        //if(resultDAO.getResultByIds())
        resultDAO.save(new Result(false,false, Permission.PASS_THE_TEST,
                userDAO.find(testUserWrapper.getUser().getId()),testDAO.find(testUserWrapper.getTest().getId())));
        return "successful";
    }
}
