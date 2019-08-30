package com.example.Material.Persistence.Repository;

import com.example.Material.Persistence.Models.TopicDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicDAO, String> {
}
