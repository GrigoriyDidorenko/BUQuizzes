package com.bionic.controllers;

import com.bionic.DAO.RoleDAO;
import com.bionic.DAO.UserDAO;
import com.bionic.DTO.UserDTO;
import com.bionic.entities.User;
import com.bionic.services.AdministratorService;
import com.bionic.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * package: com.bionic.controllers
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */

@Controller
@RequestMapping(value = "/admin")
public class AdministratorController {

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

}
