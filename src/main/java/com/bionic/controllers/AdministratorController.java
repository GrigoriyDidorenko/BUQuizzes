package com.bionic.controllers;

import com.bionic.entities.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * package: com.bionic.controllers
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 12.01.2016
 */
public class AdministratorController extends SuperAdministratorController {

    /*BETA*/

    @Override
    @RequestMapping(value = "/addUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<List<String>> getRoles() {
        return new ResponseEntity<>(new ArrayList<>(Arrays.asList(Role.RESTRICTED_TRAINER.name())), HttpStatus.OK);
    }
}
