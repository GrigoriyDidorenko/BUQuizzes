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
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private MailManager mailManager;

    public void addUser(User user){
        try {
            user.setPassword(generator.generateToken());
            user.setRole(roleDAO.find(user.getRole().getId()));
            mailManager.send("grigoriy.didorenko@gmail.com","test","Hello "+user.getFirstName()+
                    ", your new password:"+"\n"+user.getPassword()+"\nit's OTP, please change it");
            userDAO.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
