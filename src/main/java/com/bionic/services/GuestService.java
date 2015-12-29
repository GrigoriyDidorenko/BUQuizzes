package com.bionic.services;

import com.bionic.DAO.TestDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rondo104 on 25.12.2015.
 */
@Service
public class GuestService {

    @Autowired
    private TestDAO testDAO;

    public GuestService() {
    }

    public Set<TestDTO> getAvailableOneTimeTests() {
        try {
            HashSet<TestDTO> result = new HashSet<>(testDAO.getUnarchivedOneTimeTests());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public TestDTO getCurrentTest(String testId) {
        try {
            Test test = testDAO.find(Long.valueOf(testId));
            return Converter.convertUsersTestToDTO(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}




