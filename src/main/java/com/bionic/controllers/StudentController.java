package com.bionic.controllers;

import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.services.StudentService;
import com.bionic.services.TestService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

//    /*Test method*/
//    @RequestMapping(value = "/import", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    String getSample(){
//        return testService.importTest(new File("d:\\file.json"));
//    }


    @RequestMapping(value = "/tests/{id}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Set<TestDTO>> getAvailableTestsNames(@PathVariable("id") String id) {
        Set<TestDTO> testDTOs = studentService.getAvailableTestsNames(id);

        return new ResponseEntity<>(testDTOs, HttpStatus.OK);
    }


    @RequestMapping(value = "/tests/{id}/pass/{testId}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestDTO> getCurrentTest(@PathVariable("id") String id, @PathVariable("testId") String testId) {
        TestDTO testDTO = studentService.getCurrentTest(id, testId);

        return new ResponseEntity<>(testDTO, HttpStatus.OK);


    }

    /*Example JSON [ {   "answerText" : "1",   "questionId" : 1 },
                     {   "answerText" : "1",   "questionId" : 2 } ,
                     {   "answerText" : "11",   "questionId" : 2 }  ,
                     {   "answerText" : "Me_Text",   "questionId" : 3 }  ]*/
    @RequestMapping(value = "/answers/{resultId}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    String setAnswers(@RequestBody String JSONAnswers, @PathVariable("resultId") String resultId) {
        String result;
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            List<UserAnswerDTO> userAnswerDTOs = objectMapper.readValue(JSONAnswers, typeFactory.constructCollectionType(List.class, UserAnswerDTO.class));
            result = testService.processingAnswers(userAnswerDTOs, Long.valueOf(resultId));
        } catch (NumberFormatException e) {
            result = "resultId string cannot be parsed";
        } catch (IOException e) {
            result = "I/O problem";
        }
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public
    @ResponseBody
    String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    String handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            return testService.importTest(file);
        } catch (Exception e) {
            return "You failed to upload";
        }
    }


}