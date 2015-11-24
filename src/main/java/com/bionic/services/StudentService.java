package com.bionic.services;


import com.bionic.DAO.ResultDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    public Set<TestDTO> getAvailableTestsNames(String idStr) {
        Set<TestDTO> testDTOs = null;
        try {
            testDTOs = Converter.convertAvailableTestsToDTO(resultDAO.getAvailableTestsNames(getLongId(idStr)));
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
            Test test = resultDAO.getCurrentTest(getLongId(idStr),
                    getLongId(testIdStr));
            return Converter.convertTestToDTO(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}