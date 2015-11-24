package com.bionic.controller;

import com.bionic.DTO.AnswerDTO;
import com.bionic.DTO.TestDTO;
import com.bionic.DTO.UserAnswerDTO;
import com.bionic.model.*;
import com.bionic.services.StudentService;
import com.bionic.services.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
 * @date: 12.11.2015
 */
@RestController
@Path("/student")
public class StudentRest {
    public StudentRest() {
    }

    @GET
    @Path("/tests_todo/{id}")
    @Produces("application/json")
    public Response getTestsToDo(@PathParam("id") String id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        StudentService studentService = (StudentService)context.getBean("studentService");
        Set<TestDTO> testDTOs = studentService.getTestsToDo(id);

        return Response.status(200).entity(testDTOs).build();
    }

    @GET
    @Path("/available_tests/{id}")
    @Produces("application/json")
    public Response getAvailableTests(@PathParam("id") String id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        StudentService studentService = (StudentService)context.getBean("studentService");
        Set<TestDTO> testDTOs = studentService.getAvailableTests(id);

        return Response.status(200).entity(testDTOs).build();
    }

    @GET
    @Path("/tests/{id}/pass/{testId}")
    @Produces("application/json")
    public Response getCurrentTest(@PathParam("id") String id,@PathParam("testId") String testId){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        StudentService studentService = (StudentService)context.getBean("studentService");
        TestDTO testDTO = studentService.getCurrentTest(id, testId);

        return Response.status(200).entity(testDTO).build();


    }


    // JSON REQUEST FOR TESTING ANSWERS
/*    [ {
        "id" : 1,
                "answerText" : "answerTex1?",
                "questionId" : 1,
                "picture" : "picture1",
                "userId" : 1,
                "correct" : true,
                "archived" : false
    }, {
        "id" : 2,
                "answerText" : "answerTex2?",
                "questionId" : 1,
                "picture" : "picture2",
                "userId" : 1,
                "correct" : true,
                "archived" : false
    } ]*/
    // JSON to Object
    @POST
    @Path("/answers/{resultId}")
    @Produces("application/json")
    public Response setAnswers(@RequestBody String JSONAnswers,@PathParam("resultId") String resultId) throws IOException {
     //   public Response setAnswers(@RequestBody String JSONAnswers,@RequestParam String resultId) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        List<UserAnswerDTO> userAnswerDTOs = mapper.readValue(JSONAnswers, typeFactory.constructCollectionType(List.class, UserAnswerDTO.class));
        /*@Autowired
        TestService testService;*/
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        TestService testService = (TestService)context.getBean("testService");
        String result = String.valueOf(testService.processingAnswers(userAnswerDTOs,Long.valueOf(resultId)));
        return Response.status(200).entity(result).build();
    }

    //Test Object to JSON
    @POST
    @Path("/answers1")
    @Produces("application/json")
    public Response setAnswers() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Collection<UserAnswerDTO> answers = new ArrayList<>();

        // UserAnswerDTO(String answerText, int questionId, int userId)
        UserAnswerDTO userAnswerDTO1 = new UserAnswerDTO("answerTex2?",1);
        UserAnswerDTO userAnswerDTO2 = new UserAnswerDTO("answerTex2?",2);
        answers.add(userAnswerDTO1);
        answers.add(userAnswerDTO2);
        String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answers);
        return Response.status(200).entity(jsonInString).build();
    }

}