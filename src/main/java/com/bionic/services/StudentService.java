package com.bionic.services;



import com.bionic.DAO.QuestionDAO;
import com.bionic.DAO.ResultDAO;
import com.bionic.DAO.UserAnswerDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.DTO.ResultDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Question;
import com.bionic.entities.Result;
import com.bionic.entities.Test;
import com.bionic.entities.User;
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
public class StudentService {

    public StudentService() {
    }

    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private UserAnswerDAO userAnswerDAO;


    public Set<TestWrapper> getAvailableTestsNames(String idStr) {
        try {
            HashSet<TestWrapper> result = new HashSet<>();
            HashSet<TestDTO> testDTOs = new HashSet<>(resultDAO.getAvailableTestsNames(getLongId(idStr)));
            for (TestDTO testDTO : testDTOs)
                result.add(new TestWrapper(testDTO, resultDAO.getResultByIds(getLongId(idStr),
                        testDTO.getId()).longValue()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TestDTO getCurrentTest(String idStr, String resultIdStr) {
        try {
            Result result = resultDAO.find(getLongId(resultIdStr));
            Date testBeginTime = result.getBeginTime();
            testBeginTime.setMinutes(testBeginTime.getMinutes()+result.getTest().getDuration());
            if(result.getBeginTime()==null || !(new Date(System.currentTimeMillis()).after(testBeginTime))) {
                Test test = resultDAO.getCurrentTest(getLongId(idStr),
                        result.getTest().getId());
                return Converter.convertTestToDTO(test);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setTestBeginTime(String resultIdStr) {
        Result result = resultDAO.find(getLongId(resultIdStr));
        result.setBeginTime(new Date(System.currentTimeMillis()));
        //TODO ask for this method
        result.setSubmited(true);
        resultDAO.save(result);
    }

    public long getLongId(String idStr) {
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


    public HashSet<TestWrapper> getPassTests(String idStr) {
        try {
            HashSet<TestWrapper> result = new HashSet<>();
            HashSet<TestDTO> testDTOs = new HashSet<>(resultDAO.getPassTests(getLongId(idStr)));
            for (TestDTO testDTO : testDTOs){
                result.add(new TestWrapper(testDTO));}
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}