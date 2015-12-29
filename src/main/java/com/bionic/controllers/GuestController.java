package com.bionic.controllers;

import com.bionic.DTO.ResultDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.services.GuestService;
import com.bionic.services.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by rondo104 on 25.12.2015.
 */
@Controller
@RequestMapping(value = "/guest")
public class GuestController {

    @Autowired
    private TestService testService;
    @Autowired
    private GuestService guestService;
    @Autowired
    private ObjectMapper objectMapper;

    public GuestController() {
    }

    @RequestMapping(value = "/OneTimeTests", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Set<TestDTO>> getAvailableOneTimeTests() {
        Set<TestDTO> tests = guestService.getAvailableOneTimeTests();
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/{testId}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestDTO> getCurrentTest(@PathVariable("testId") String testId) {
        TestDTO testDTO = guestService.getCurrentTest(testId);
        return new ResponseEntity<>(testDTO, HttpStatus.OK);
    }

    //ToDo User, Mail, Security
    @RequestMapping(value = "/answers/{testId}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<ResultDTO> setAnswers(@RequestBody String JSONAnswers, @PathVariable("testId") String testId) {
        ResultDTO resultDTO = null;
        try {
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            ArrayList<UserAnswerDTO> userAnswerDTOs = objectMapper.readValue(JSONAnswers, typeFactory.constructCollectionType(ArrayList.class, UserAnswerDTO.class));
            resultDTO = testService.processingAnswersForOneTimeTest(userAnswerDTOs, Long.valueOf(testId)) ;
        } catch (NumberFormatException e) {
            resultDTO.setCheckStatus("resultId string cannot be parsed");
        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        }
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
