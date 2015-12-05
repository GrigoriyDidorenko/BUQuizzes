package com.bionic.services;

import com.bionic.DAO.RoleDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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
    private UserService userService;
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private MailManager mailManager;

    public void addUser(User user){
        try {
            user.setPassword(generator.generateToken());
            mailManager.send(user.getEmail(),"test","Hello "+user.getFirstName()+
                    ", your new password:"+"\n"+user.getPassword()+"\nit's OTP, please change it");
            userService.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
