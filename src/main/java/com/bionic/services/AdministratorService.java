package com.bionic.services;

import com.bionic.DTO.UserDTO;
import com.bionic.entities.Role;
import com.bionic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

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

    public void addUser(UserDTO userDTO) {
        try {
            userDTO.setRole(new HashSet<>(Arrays.asList(Role.findByName(userDTO.getRoleName()))));
            User user = Util.convertUserDTOToUser(userDTO);
            user.setPassword(generator.generateToken());
            mailManager.send(user.getEmail(), "One time password", "<div style='margin:0 auto; width:60%; height:60px; margin-top:-30px; margin-bottom:10px;'><img src='http://cs630017.vk.me/v630017821/ef59/DY0m5ySG0iQ.jpg' style='padding:0; margin:0; padding-left:20px;'></div>" +
                    "<div style='margin:0 auto;width:60%;height:32px;background-color:#0090b9;margin-bottom:20px;padding-top:15px;'><span style='color:white; font-size: 18px; padding-top:8px; padding-left:20px;'>ONE TIME PASSWORD</span></div>" +
                    "<div style='margin:0 auto; width:60%; padding-left:40px;margin-bottom:5px;'><span>Hello, " + "</span><span style ='color:#0090b9;'>" + user.getFirstName() + "</span></div>" +
                    "<div style='margin:0 auto; width:60%; padding-left:40px;margin-bottom:5px;'><span>Your new one time password: " + "</span><span style ='color:#0090b9;'>" + user.getPassword() + "%</span></div>" +
                    "<div style='margin:0 auto; width:60%; padding-left:40px;'><span>You should change it as you won't be able to use website. " + "</span>");


            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
