package com.bionic.services;


import com.bionic.DAO.ResultDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Permission;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.wrappers.TestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr & Grifan
 * @date: 12.11.2015
 */
@Service
public class StudentService implements TestHandler<TestWrapper> {

    public StudentService() {
    }

    @Autowired
    private ResultDAO resultDAO;

    @Override
    public Set<TestWrapper> getAvailableTestsNames(long idStr) {
        try {
            HashSet<TestWrapper> result = new HashSet<>();
            HashSet<TestDTO> testDTOs = new HashSet<>(resultDAO.getAvailableTestsNames(idStr, Permission.PASS_THE_TEST));
            for (TestDTO testDTO : testDTOs)
                result.add(new TestWrapper(testDTO, resultDAO.getResultByIds(idStr,
                        testDTO.getId()).longValue()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TestDTO getCurrentTest(long id, String resultIdStr) {
        try {
            Result result = resultDAO.find(Util.getLongId(resultIdStr));
            Date accessEndTime = result.getBeginTime();
            accessEndTime.setTime(accessEndTime.getTime() + 60000 * result.getTest().getDuration());
            if (new Date(System.currentTimeMillis()).before(accessEndTime)) {
                Test test = resultDAO.getCurrentTest(id,
                        result.getTest().getId(), Permission.PASS_THE_TEST);
                return Util.convertUsersTestToDTOForStudent(test);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTestBeginTime(String resultIdStr) {
        Result result = resultDAO.find(Util.getLongId(resultIdStr));
        if (result.getBeginTime() == null) {
            result.setBeginTime(new Date(System.currentTimeMillis()));
            resultDAO.update(result);
        }
    }

    public HashSet<TestWrapper> getPassTests(String idStr) {
        try {
            HashSet<TestWrapper> result = new HashSet<>();
            HashSet<TestDTO> testDTOs = new HashSet<>(resultDAO.getPassTests(Util.getLongId(idStr)));
            for (TestDTO testDTO : testDTOs) {
                result.add(new TestWrapper(testDTO));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<TestWrapper> getTestsForUserId(long id) {
        try {
            HashSet<TestWrapper> result = new HashSet<>(resultDAO.getTestsForUserId(id));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}