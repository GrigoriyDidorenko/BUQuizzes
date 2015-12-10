package com.bionic.wrappers;

import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserDTO;
import com.bionic.entities.Test;
import com.bionic.entities.User;

import java.util.List;
import java.util.Set;

/**
 * package: com.bionic.wrappers
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 06.12.2015
 */
public class TestUserWrapper {

    private List<TestDTO> tests;
    private List<UserDTO> users;
    private UserDTO user;
    private TestDTO test;

    public TestUserWrapper(List<TestDTO> tests, List<UserDTO> users) {
        this.tests = tests;
        this.users = users;
    }

    public TestUserWrapper(UserDTO user, TestDTO test) {
        this.user = user;
        this.test = test;
    }

    public List<TestDTO> getTests() {
        return tests;
    }

    public void setTests(List<TestDTO> tests) {
        this.tests = tests;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public TestDTO getTest() {
        return test;
    }

    public void setTest(TestDTO test) {
        this.test = test;
    }
}
