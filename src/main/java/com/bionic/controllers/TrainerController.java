package com.bionic.controllers;

import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.entities.UserGroup;
import com.bionic.services.TestService;
import com.bionic.services.TrainerService;
import com.bionic.services.UserService;
import com.bionic.wrappers.OpenQuestionWrapper;
import com.bionic.wrappers.TestUserWrapper;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveNewTest(@RequestParam("file") MultipartFile file) {
        HashSet<TestDTO> testDTOs;
        try {
            testDTOs = objectMapper.readValue(file.getInputStream(), new TypeReference<Set<TestDTO>>() {
            });
            testService.importTest(testDTOs);
            return "success";
        } catch (JsonGenerationException e) {
            return new String(e.getMessage());
        } catch (JsonMappingException e) {
            return "Invalid format";
        } catch (IOException e) {
            return new String(e.getMessage());
        } catch (Exception e) {
            return "Duplicate row in DB";
        }
    }

    @RequestMapping(value = "/addNewTest", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveNewTest(@RequestBody String JSON) {
        TestDTO testDTO;
        try {
            testDTO = objectMapper.readValue(JSON, new TypeReference<TestDTO>() {
            });
            trainerService.testToGroup(testDTO.getTestsToGroups(), testService.saveAndImportTest(testDTO).getId());
            return "success";
        } catch (JsonGenerationException e) {
            return new String(e.getMessage());
        } catch (JsonMappingException e) {
            return "Invalid format";
        } catch (IOException e) {
            return new String(e.getMessage());
        } catch (Exception e) {
            return "Duplicate row in DB";
        }
    }


    @RequestMapping(value = "/uncheckedTests", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<OpenQuestionWrapper> getUncheckedTests() {
        try {
            return trainerService.getUncheckedTests(/*userService.getAuthorizedUser().getId()*/1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/uncheckedTests/{questionId}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<UserAnswerDTO> getUncheckedAnswersForCurrentQuestion(@PathVariable("questionId") String questionId) {
        try {
            return trainerService.getUncheckedAnswersForCurrentQuestion(questionId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/getAllGroups", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    List<UserGroup> getAllGroups() {
        try {
            return trainerService.getAllGroups();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/testToUser", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestUserWrapper> getUsersAndUnarchivedTests() {
        try {
            TestUserWrapper testUserWrapper = trainerService.getAllUsersNamesAndUnarchivedTestsNames();
            return new ResponseEntity<>(testUserWrapper, HttpStatus.OK);
        } catch (Exception e) {
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

//    @RequestMapping(value = "/saveTest", method = RequestMethod.POST)
//    public void saveTest(@RequestBody TestDTO testDTO) {
//        testService.saveCreatedTest(testDTO);
//    }


    @RequestMapping(value = "/getAllСategoryTestName", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Set<String>> categoryTestName() {
        Set<String> categoryTestName = testService.getAllСategoryTestName();
        return new ResponseEntity<>(categoryTestName, HttpStatus.OK);
    }

    /*first value - answerId*/
     /*Example JSON {"id" : 2 , "resultId" : 1, "mark" : 5}*/

    @RequestMapping(value = "/uncheckedTests/answers", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    int setAnswers(@RequestBody String JSONAnswers) {
        try {
            UserAnswerDTO userAnswerDTO = objectMapper.readValue(JSONAnswers, UserAnswerDTO.class);
            return testService.processingOpenedAnswers(userAnswerDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /*TODO: CHECK THAT*/

    @RequestMapping(value = "/updateTest", method = RequestMethod.POST)
    public
    @ResponseBody
    String updateTest(@RequestBody String test) {
        try {
            testService.updateTest(objectMapper.readValue(test, TestDTO.class));
            return "success";
        } catch (JsonGenerationException e) {
            return new String(e.getMessage());
        } catch (JsonMappingException e) {
            return "Invalid format";
        } catch (IOException e) {
            return new String(e.getMessage());
        } catch (Exception e) {
            return "Duplicate row in DB";
        }
    }

}
