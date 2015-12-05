package com.bionic.controllers;

import com.bionic.entities.Role;
import com.bionic.entities.User;
import com.bionic.services.AdministratorService;
import com.bionic.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

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
    private AdministratorService administratorService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@ModelAttribute User user, Model model) {
        try {
            model.addAttribute("user", user);
            administratorService.addUser(user);
            return "successful";
        } catch (Exception e) {
            return "You failed to upload";
        }
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<List<String>> getRoles() {
        List<String> roles = Arrays.asList(Role.RESTRICTED_ADMINISTRATOR.getName(), Role.RESTRICTED_TRAINER.getName());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

/*    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    String getCurrentTest(@PathVariable("id") String id, @PathVariable("resultId") String resultId) {
        return "hihi";
    }*/

}
