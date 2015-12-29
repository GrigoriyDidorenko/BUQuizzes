package com.bionic.controllers;

import com.bionic.DTO.GuestAnswerDTO;
import com.bionic.DTO.ResultDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.services.GuestService;
import com.bionic.services.TestService;
import com.bionic.wrappers.NickMarkWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @RequestMapping(value = "/oneTimeTests", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<Set<TestDTO>> getAvailableOneTimeTests() {
        Set<TestDTO> tests = guestService.getAvailableOneTimeTests();
        return new ResponseEntity<>(tests, HttpStatus.OK);
    }

    @RequestMapping(value = "/tests/{testId}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<TestDTO> getCurrentTest(@PathVariable("testId") String testId, @RequestParam ("email") String email,
                                           @RequestParam ("nickName") String nickName ) {
        if (guestService.getPermissionForOneTest(email, nickName)) {
            TestDTO testDTO = guestService.getCurrentTest(testId);
            return new ResponseEntity<>(testDTO, HttpStatus.OK);}
        else {
         return new ResponseEntity("Invalid data",HttpStatus.CONFLICT );
        }
    }

    @RequestMapping(value = "/leaderBoard/{testId}/{pageNumber}", method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity <NickMarkWrapper> getLeaderBoard(@PathVariable("testId") String testId,
                                                          @PathVariable("pageNumber") String pageNumber) {
        return new ResponseEntity<>(guestService.getLeaderBoard(testId, pageNumber), HttpStatus.OK);
    }

/*    {   "email" :  "rondo104@gmail.com",
        "nickName" :  "roma",
        "name" : "name",
        "userAnswerDTO" : [{"questionId":3,"answerId":[3,4],"answerText":""},
                           {"questionId":2,"answerId":[],"answerText":"bnm"},
                           {"questionId":2,"answerId":[2],"answerText":""}] }*/
    @RequestMapping(value = "/answers/{testId}", method = RequestMethod.POST, produces = "application/json")
    public
    @ResponseBody
    ResponseEntity<ResultDTO> setAnswers(@RequestBody String JSONAnswers,
                                         @PathVariable("testId") String testId){
        ResultDTO resultDTO = null;
        try {
        /*    String email = "mail";
            String nickName = "nick";
            String name = "Name" ;*/
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            GuestAnswerDTO guestAnswerDTO = objectMapper.readValue(JSONAnswers, GuestAnswerDTO.class);
            List<UserAnswerDTO> userAnswerDTOs = guestAnswerDTO.getUserAnswerDTO();
            resultDTO = testService.processingAnswersForOneTimeTest((ArrayList<UserAnswerDTO>) userAnswerDTOs, Long.valueOf(testId), guestAnswerDTO.getNickName(), guestAnswerDTO.getEmail() , guestAnswerDTO.getEmail()) ;
        } catch (NumberFormatException e) {
            resultDTO.setCheckStatus("resultId string cannot be parsed");
        } catch (Exception e) {
            resultDTO.setCheckStatus(e.getMessage());
        }
        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }
}
