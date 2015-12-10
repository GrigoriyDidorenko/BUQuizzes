package com.bionic.controllers;

import com.bionic.DAO.TestDAO;
import com.bionic.DTO.TestDTO;
import com.bionic.entities.Test;
import com.bionic.services.Converter;
import com.bionic.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * package: com.bionic.controllers
 *
 * @author: Alex Balitsky
 * @date: 12.11.2015
 */
@Controller
@RequestMapping(value = "/mentor")
public class MentorController {
    @Autowired
    private TestService testService;

    @RequestMapping(value = "/saveTest", method = RequestMethod.POST)
    public void saveTest(@RequestBody TestDTO testDTO) {
        testService.saveCreatedTest(testDTO);
    }

}