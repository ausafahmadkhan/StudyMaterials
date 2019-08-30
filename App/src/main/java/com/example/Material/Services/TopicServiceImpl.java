package com.example.Material.Services;

import com.example.Material.MaterialRequests.TopicRequest;
import com.example.Material.MaterialResponses.CourseResponse;
import com.example.Material.MaterialResponses.TopicResponse;
import com.example.Material.Persistence.Models.TopicDAO;
import com.example.Material.Persistence.Repository.TopicRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class TopicServiceImpl implements TopicService
{
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private Jedis jedis;

    @Override
    @Async
    public CompletableFuture<TopicResponse> addTopic(TopicRequest topicRequest) throws ExecutionException, InterruptedException {
        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setTopicId(topicRequest.getTopicId());
        topicResponse.setName(topicRequest.getName());
        CompletableFuture<List<CourseResponse>> courseResponses = courseService.getCourse(topicRequest.getTopicId());
        topicResponse.setCourseResponses(courseResponses.get());
        TopicDAO topicDAO = new TopicDAO();
        topicDAO.setTopicId(topicRequest.getTopicId());
        topicDAO.setName(topicRequest.getName());
        topicRepository.saveAndFlush(topicDAO);
        return CompletableFuture.completedFuture(topicResponse);
    }

    @Override
    @Async
    public CompletableFuture<TopicResponse> getTopic(String topicId) throws Exception
    {
        TopicResponse topicResponse = new TopicResponse();
        Gson gson = new Gson();
        if (!jedis.exists(topicId))
        {
            TopicDAO topicDAO = topicRepository.findById(topicId).orElse(null);
            topicResponse.setTopicId(topicDAO.getTopicId());
            topicResponse.setName(topicDAO.getName());
            CompletableFuture<List<CourseResponse>> courseResponses = courseService.getCourse(topicId);
            topicResponse.setCourseResponses(courseResponses.get());
            String deserializedTopic = gson.toJson(topicResponse);
            jedis.set(topicId, deserializedTopic);
            jedis.expire(topicId, 3000);
        }
        else
        {
            String deserializedTopic = jedis.get(topicId);
            topicResponse = gson.fromJson(deserializedTopic, TopicResponse.class);
        }
        return CompletableFuture.completedFuture(topicResponse);
    }
}
