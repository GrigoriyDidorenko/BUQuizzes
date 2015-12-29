package com.bionic.services;

import com.bionic.DAO.UserDAO;
import com.bionic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Sergiy on 02.12.2015.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    Md5PasswordEncoder md5PasswordEncoder;
    public void save(User user){
        user.setPassword(md5PasswordEncoder.encodePassword(user.getPassword(),null));
        userDAO.save(user);
    }

}
