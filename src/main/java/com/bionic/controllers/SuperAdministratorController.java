package com.bionic.controllers;

import com.bionic.DTO.UserDTO;
import com.bionic.entities.Role;
import com.bionic.services.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * package: com.bionic.controllers
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */

@Controller
@Secured("ROLE_SUPERADMIN")
@RequestMapping(value = "/superAdmin")
public class SuperAdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@ModelAttribute UserDTO userDTO, Model model) {
        try {
            model.addAttribute("user", userDTO);
            administratorService.addUser(userDTO);
            return "successful";
        } catch (Exception e) {
            return "You failed to upload";
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<List<String>>(new ArrayList<>(Arrays.asList(Role.RESTRICTED_ADMINISTRATOR.name()
                , Role.RESTRICTED_TRAINER.name())), HttpStatus.OK);
    }

}
