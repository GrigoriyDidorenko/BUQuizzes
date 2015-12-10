package com.bionic.controllers;

import com.bionic.DAO.UserDAO;
import com.bionic.DTO.ResultDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.Role;
import com.bionic.entities.User;
import com.bionic.services.StudentService;
import com.bionic.services.TestService;
import com.bionic.wrappers.TestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private StudentService studentService;
    @Autowired
    private TestService testService;
    @Autowired
    private ObjectMapper objectMapper;


    public StudentController() {

    }



    @RequestMapping(value = "/tests/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Set<TestWrapper>> getAvailableTestsNames(@PathVariable("id") String id) {
        Set<TestWrapper> tests = studentService.getAvailableTestsNames(id);
        tests.addAll(studentService.getPassTests(id));
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/{id}/pass/{resultId}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestDTO> getCurrentTest(@PathVariable("id") String id, @PathVariable("resultId") String resultId) {
        studentService.setTestBeginTime(resultId);
        TestDTO testDTO = studentService.getCurrentTest(id, resultId);
        return new ResponseEntity<>(testDTO, HttpStatus.OK);
    }

    /*Example JSON [ {     "questionId" : 1 , "answerId" : 1 },
                     {     "questionId" : 2 , "answerId" : 4} ,
                     {     "questionId" : 2 , "answerId" : 6}  ,
                     {      "answerText" : "Me_Text",   "questionId" : 3, "answerId" : 7 }  ]*/
    @RequestMapping(value = "/answers/{resultId}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<ResultDTO> setAnswers(@RequestBody String JSONAnswers, @PathVariable("resultId") String resultId) {
        ResultDTO resultDTO = null;
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            ArrayList<UserAnswerDTO> userAnswerDTOs = objectMapper.readValue(JSONAnswers, typeFactory.constructCollectionType(ArrayList.class, UserAnswerDTO.class));
            resultDTO = testService.processingAnswers(userAnswerDTOs, Long.valueOf(resultId));
        } catch (NumberFormatException e) {
            resultDTO.setCheckStatus("resultId string cannot be parsed");
        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        }
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}