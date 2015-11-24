package com.bionic.rest;

import com.bionic.DTO.TestDTO;
import com.bionic.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
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
    Response getAvailableTestsNames(@PathVariable("id") String id){
        Set<TestDTO> testDTOs = studentService.getAvailableTestsNames(id);

        return Response.status(200).entity(testDTOs).build();
    }


    @RequestMapping(value = "/tests/{id}/pass/{testId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    Response getCurrentTest(@PathVariable("id") String id,@PathVariable("testId") String testId){
        TestDTO testDTO = studentService.getCurrentTest(id,testId);

        return Response.status(200).entity(testDTO).build();


    }
}