package com.example.Material.Services;

import com.example.Material.MaterialRequests.CourseRequest;
import com.example.Material.MaterialResponses.CourseResponse;
import com.example.Material.Persistence.Models.CourseDAO;
import com.example.Material.Persistence.Models.TopicDAO;
import com.example.Material.Persistence.Repository.CourseRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(CourseServiceImpl.class);


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
        courseRepository.save(courseDAO);
        logger.info("Saving the course : {}", courseDAO);
        return CompletableFuture.completedFuture(courseResponse);

    }

    @Override
    @Async
    public CompletableFuture<List<CourseResponse>> getCourse(String topicId)
    {
        List<CourseResponse> courseResponses = new ArrayList<>();
        Gson gson = new Gson();
        if (!jedis.exists("courses_"+topicId))
        {
            logger.info("No course present in Redis for topicId : {}", topicId);
            List<CourseDAO> courseDAOS;
            courseDAOS = courseRepository.findByTopicDAOTopicId(topicId);
            logger.info("Fetched course from Db : {}", courseDAOS);
            if (courseDAOS != null) {
                for (CourseDAO courseDAO : courseDAOS) {
                    CourseResponse courseResponse = new CourseResponse();
                    courseResponse.setCourseId(courseDAO.getCourseId());
                    courseResponse.setName(courseDAO.getName());
                    courseResponse.setDescription(courseDAO.getDescription());
                    courseResponses.add(courseResponse);
                }
                String serializedCourse = gson.toJson(courseResponses);
                jedis.setex(topicId + "courses",3000, serializedCourse);
            }
        }
        else
        {
            String deserializedCourse = jedis.get("courses_" + topicId);
            Type listType = new TypeToken<List<CourseResponse>>(){}.getType();
            courseResponses = gson.fromJson(deserializedCourse, listType);
            logger.info("Found Courses in Redis : {}", courseResponses);
        }
     return CompletableFuture.completedFuture(courseResponses);
    }
}
