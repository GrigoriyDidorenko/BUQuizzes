package com.bionic.services;

import com.bionic.DAO.RoleDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */
@Service
public class AdministratorService {


    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;

    public void addUser(User user){
        try {
            user.setRole(roleDAO.find(user.getRole().getId()));
            userDAO.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
