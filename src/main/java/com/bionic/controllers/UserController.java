package com.bionic.controllers;

import com.bionic.DAO.UserDAO;
import com.bionic.DTO.ProfileDTO;
import com.bionic.DTO.UserDTO;
import com.bionic.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Sergiy on 08.12.2015.
 */
@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<ProfileDTO> getCurrentUserInfo(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User currentUser=userDAO.getUserByEmail(auth.getName());
        ProfileDTO profileDTO=new ProfileDTO(currentUser.getFirstName(),currentUser.getLastName());
        return new ResponseEntity<>(profileDTO,HttpStatus.OK);
    }
}
