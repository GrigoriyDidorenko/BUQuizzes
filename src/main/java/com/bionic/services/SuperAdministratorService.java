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
public class SuperAdministratorService {

    @Autowired
    private UserService userService;
    @Autowired
    private OtpGenerator generator;
    @Autowired
    private MailManager mailManager;

    public void addUser(UserDTO userDTO){
        try {
            userDTO.setRole(new HashSet<>(Arrays.asList(Role.findByName(userDTO.getRoleName()))));
            User user = Util.convertUserDTOToUser(userDTO);
            user.setPassword(generator.generateToken());
            mailManager.send(user.getEmail(),"test","Hello "+user.getFirstName()+
                    ", your new password:"+"\n"+user.getPassword()+"\nit's OTP, please change it");
            userService.save(user);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
