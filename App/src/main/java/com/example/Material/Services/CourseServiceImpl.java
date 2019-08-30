package com.example.Material.Services;

import com.example.Material.MaterialRequests.CourseRequest;
import com.example.Material.MaterialResponses.CourseResponse;
import com.example.Material.Persistence.Models.CourseDAO;
import com.example.Material.Persistence.Models.TopicDAO;
import com.example.Material.Persistence.Repository.CourseRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Jedis jedis;

    @Override
    @Async
    public CompletableFuture<CourseResponse> addCourse(String topicId, CourseRequest courseRequest)
    {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setCourseId(courseRequest.getCourseId());
        courseResponse.setName(courseRequest.getName());
        courseResponse.setDescription(courseRequest.getDescription());
        CourseDAO courseDAO = new CourseDAO();
        courseDAO.setCourseId(courseRequest.getCourseId());
        courseDAO.setName(courseRequest.getName());
        courseDAO.setDescription(courseRequest.getDescription());
        TopicDAO topicDAO = new TopicDAO(topicId, "");
        courseDAO.setTopicDAO(topicDAO);
        courseRepository.saveAndFlush(courseDAO);
        return CompletableFuture.completedFuture(courseResponse);

    }

    @Override
    @Async
    public CompletableFuture<List<CourseResponse>> getCourse(String topicId)
    {
        List<CourseResponse> courseResponses = new ArrayList<>();
        Gson gson = new Gson();
        if (!jedis.exists(topicId+"courses"))
        {
            List<CourseDAO> courseDAOS;
            courseDAOS = courseRepository.findByTopicDAOTopicId(topicId);
            System.out.println(courseDAOS);
            for (CourseDAO courseDAO : courseDAOS) {
                CourseResponse courseResponse = new CourseResponse();
                courseResponse.setCourseId(courseDAO.getCourseId());
                courseResponse.setName(courseDAO.getName());
                courseResponse.setDescription(courseDAO.getDescription());
                courseResponses.add(courseResponse);
            }
            String serializedCourse = gson.toJson(courseResponses);
            jedis.set(topicId + "courses", serializedCourse);
            jedis.expire(topicId + "courses", 3000);
        }
        else
        {
            String deserializedCourse = jedis.get(topicId + "courses");
            Type listType = new TypeToken<List<CourseResponse>>(){}.getType();
            courseResponses = gson.fromJson(deserializedCourse, listType);
        }
     return CompletableFuture.completedFuture(courseResponses);
    }
}
