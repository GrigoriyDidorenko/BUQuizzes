package com.bionic.controllers;

import com.bionic.DTO.UserDTO;
import com.bionic.entities.Role;
import com.bionic.entities.User;
import com.bionic.services.SuperAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package: com.bionic.controllers
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */

@Controller
@RequestMapping(value = "/superAdmin")
public class SuperAdministratorController {

    @Autowired
    private SuperAdministratorService superAdministratorService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@ModelAttribute UserDTO user, Model model) {
        try {
            model.addAttribute("user", user);
            superAdministratorService.addUser(user);
            return "successful";
        } catch (Exception e) {
            return "You failed to upload";
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Map<Long, String>> getRoles() {
        HashMap<Long, String> roles = new HashMap<>();
        roles.put(Role.RESTRICTED_ADMINISTRATOR.getId(), Role.RESTRICTED_ADMINISTRATOR.getName());
        roles.put(Role.RESTRICTED_TRAINER.getId(), Role.RESTRICTED_TRAINER.getName());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

}
