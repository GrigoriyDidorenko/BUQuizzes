package com.bionic.services;

import com.bionic.DAO.OneTimeTestDAO;
import com.bionic.DAO.TestDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Test;
import com.bionic.wrappers.LeaderBoardWrapper;
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

    private static double pageStackSize = 10.0;

    public GuestService() {
    }

    public LeaderBoardWrapper getLeaderBoard(String testId, String pageNumber){
        try {
            long limitCounter = (Util.getLongId(pageNumber)-1)*(long)pageStackSize;
            return new LeaderBoardWrapper(
                    oneTimeTestDAO.getLeaderBoard(Util.getLongId(testId),limitCounter, (long)pageStackSize),
                    (long)Math.ceil(oneTimeTestDAO.getBoardsPageCount().longValue()/pageStackSize),
                    Util.getLongId(pageNumber));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /*TODO: REWRITE*/

    public Set<TestDTO> getAvailableOneTimeTests() {
        try {
            HashSet<TestDTO> result = new HashSet<>(testDAO.getUnarchivedOneTimeTests());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean getPermissionForOneTest(String email, String nickName){
        boolean permission = true;
        List<String> emails = oneTimeTestDAO.getEmailByNick(nickName);
        if(!emails.isEmpty())
            if (email.equals(oneTimeTestDAO.getEmailByNick(nickName).get(0))) permission = true;
            else permission = false;
        return permission;
    }

    /*TODO: REWRITE*/

    public TestDTO getCurrentTest(String testId) {
        try {
            Test test = testDAO.find(Long.valueOf(testId));
            return Util.convertUsersTestToDTO(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double getPageStackSize() {
        return pageStackSize;
    }

    public static void setPageStackSize(double pageStackSize) {
        GuestService.pageStackSize = pageStackSize;
    }
}




