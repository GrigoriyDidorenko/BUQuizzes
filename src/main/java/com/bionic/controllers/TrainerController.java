package com.bionic.controllers;

import com.bionic.entities.User;
import com.bionic.services.TestService;
import com.bionic.services.TrainerService;
import com.bionic.wrappers.TestUserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "/trainer")
public class TrainerController {

    @Autowired
    private TestService testService;
    @Autowired
    private TrainerService trainerService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            return testService.importTest(file);
        } catch (Exception e) {
            return "Duplicate row in DB";
        }
    }

    @RequestMapping(value = "/testToUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestUserWrapper> getUsersAndUnarchivedTests() {
       try {
           TestUserWrapper testUserWrapper = trainerService.getAllUsersNamesAndUnarchivedTestsNames();
           return new ResponseEntity<>(testUserWrapper, HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return null;
    }

    @RequestMapping(value = "/testToUser", method = RequestMethod.POST)
    public
    @ResponseBody
    String addUser(@ModelAttribute TestUserWrapper testUserWrapper, Model model) {
        try {
            model.addAttribute("testUserWrapper", testUserWrapper);
            trainerService.saveTestToUser(testUserWrapper);
            return "successful";
        } catch (Exception e) {
            return "You failed to add user";
        }
    }

}
