package com.example.Material.Controller;

import com.example.Material.Services.CourseService;
import com.example.Material.Services.TopicService;
import com.example.Material.MaterialRequests.CourseRequest;
import com.example.Material.MaterialRequests.TopicRequest;
import com.example.Material.MaterialResponses.CourseResponse;
import com.example.Material.MaterialResponses.ResponseModel;
import com.example.Material.MaterialResponses.TopicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/StudyMaterials")
public class StudyMaterialsController
{
    @Autowired
    private TopicService topicService;

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/addTopic", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    CompletableFuture<ResponseEntity<ResponseModel<TopicResponse>>> addTopic(@RequestBody TopicRequest topicRequest) throws ExecutionException, InterruptedException {
        CompletableFuture<TopicResponse> topicResponse = topicService.addTopic(topicRequest);
        CompletableFuture<ResponseEntity<ResponseModel<TopicResponse>>> resultTask = topicResponse.thenApply(t -> new ResponseEntity<ResponseModel<TopicResponse>>(new ResponseModel<TopicResponse>(t), HttpStatus.OK));
        return resultTask;
    }

    @RequestMapping(value = "/getTopic/{topicId}", method = RequestMethod.GET, produces = {"application/json"})
    CompletableFuture<ResponseEntity<ResponseModel<TopicResponse>>> getTopic(@PathVariable("topicId") String topicId) throws Exception
    {
        CompletableFuture<TopicResponse> topicResponse = topicService.getTopic(topicId);
        CompletableFuture<ResponseEntity<ResponseModel<TopicResponse>>> resultTask = topicResponse.thenApply(t -> new ResponseEntity(new ResponseModel<TopicResponse>(t), HttpStatus.OK));
        return resultTask;
    }

    @RequestMapping(value = "/addCourse/{topicId}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    CompletableFuture<ResponseEntity<ResponseModel<CourseResponse>>> addCourse(@PathVariable("topicId") String topicId,  @RequestBody CourseRequest courseRequest)
    {
        CompletableFuture<CourseResponse> courseResponse = courseService.addCourse(topicId, courseRequest);
        CompletableFuture<ResponseEntity<ResponseModel<CourseResponse>>> resultTask = courseResponse.thenApply(t -> new ResponseEntity<ResponseModel<CourseResponse>>(new ResponseModel<CourseResponse>(t), HttpStatus.OK));
        return resultTask;
    }

    @RequestMapping(value = "/getCourseByTopic/{topicId}", method = RequestMethod.GET, produces = {"application/json"})
    CompletableFuture<ResponseEntity<ResponseModel<List<CourseResponse>>>> getCourse(@PathVariable("topicId") String topicId)
    {
        CompletableFuture<List<CourseResponse>> courseResponses = courseService.getCourse(topicId);
        CompletableFuture<ResponseEntity<ResponseModel<List<CourseResponse>>>> resultTask = courseResponses.thenApply(t -> new ResponseEntity<ResponseModel<List<CourseResponse>>>(new ResponseModel<List<CourseResponse>>(t), HttpStatus.OK));
        return resultTask;
    }
}
