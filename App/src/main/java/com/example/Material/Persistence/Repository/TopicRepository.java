package com.example.Material.Persistence.Repository;

import com.example.Material.Persistence.Models.TopicDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TopicRepository extends MongoRepository<TopicDAO, String> {
}
