package com.bionic.services;


import com.bionic.DAO.ResultDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.DTO.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr & Grifan
 * @date: 12.11.2015
 */
@Component
public class StudentService {

    public StudentService() {
    }

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ResultDAO resultDAO;

    public Set<TestDTO> getAvailableTests(String idStr) {
        Set<TestDTO> testDTOs = null;
        try {
            testDTOs = Converter.convertAvailableTestsToDTO(userDAO.getAvailableTestsById(getLongId(idStr)));
        } catch (Exception e) {
            System.out.println();
        }
        return testDTOs;
    }

    public Set<TestDTO> getTestsToDo(String idStr) {
        Set<TestDTO> testDTOs = null;
        try {
            testDTOs = Converter.convertTestsToDTO(userDAO.getAvailableTestsById(getLongId(idStr)));
        } catch (Exception e) {
            System.out.println();
        }
        return testDTOs;
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

    public TestDTO getCurrentTest(String idStr, String testIdStr) {
        try {
            return Converter.convertTestToDTO(resultDAO.getCurrentTest(getLongId(idStr),
                    getLongId(testIdStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}