package com.bionic.controllers;

import com.bionic.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

}
