package com.example.Material.Persistence.Repository;

import com.example.Material.Persistence.Models.CourseDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseDAO, String>
{
    List<CourseDAO> findByTopicDAOTopicId(String topicId);
}
