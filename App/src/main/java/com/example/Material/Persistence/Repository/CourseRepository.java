package com.example.Material.Persistence.Repository;

import com.example.Material.Persistence.Models.CourseDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseRepository extends MongoRepository<CourseDAO, String>
{
    List<CourseDAO> findByTopicDAOTopicId(String topicId);
}
