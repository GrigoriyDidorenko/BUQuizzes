package com.bionic.rest;

import com.bionic.DTO.TestDTO;
import com.bionic.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Set;

/**
 * package: com.bionic.services
 *
 * @author: Grigoriy Didorenko
 * @date: 12.11.2015
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    public StudentController() {

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getSample(@PathVariable String id){
        return id;
    }


    @RequestMapping(value = "/tests/{id}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<Set<TestDTO>> getAvailableTestsNames(@PathVariable("id") String id){
        Set<TestDTO> testDTOs = studentService.getAvailableTestsNames(id);

        return new ResponseEntity<>(testDTOs, HttpStatus.OK);
    }


    @RequestMapping(value = "/tests/{id}/pass/{testId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseEntity<TestDTO> getCurrentTest(@PathVariable("id") String id,@PathVariable("testId") String testId){
        TestDTO testDTO = studentService.getCurrentTest(id,testId);

        return new ResponseEntity<>(testDTO, HttpStatus.OK);


    }
}