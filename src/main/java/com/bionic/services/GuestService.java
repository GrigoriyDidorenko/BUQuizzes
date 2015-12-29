package com.bionic.services;

import com.bionic.DAO.OneTimeTestDAO;
import com.bionic.DAO.TestDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.OneTimeTest;
import com.bionic.entities.Permission;
import com.bionic.entities.Test;
import com.bionic.wrappers.NickMarkWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rondo104 on 25.12.2015.
 */
@Service
public class GuestService {

    @Autowired
    private TestDAO testDAO;
    @Autowired
    private OneTimeTestDAO oneTimeTestDAO;

    public GuestService() {
    }

    public NickMarkWrapper getLeaderBoard(String testId, String pageNumber){
        try {
            long limitCounter = (Converter.getLongId(pageNumber)-1)*1;
            return new NickMarkWrapper(oneTimeTestDAO.getLeaderBoard(Converter.getLongId(testId),
                    limitCounter),oneTimeTestDAO.getBoardsPageCount().longValue());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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

    public boolean getPermisionForOneTest(String email, String nickName){
        boolean permision = false ;
        String conditional = "table.nickname =" + "'" + nickName + "'";
        List<OneTimeTest> oneTimeTests = oneTimeTestDAO.findByTableWhere(conditional);
        for (OneTimeTest oneTimeTest : oneTimeTests){
            if (email.equals(oneTimeTest.getEmail())){
                permision = true ;
            }
        }
        return permision;
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




