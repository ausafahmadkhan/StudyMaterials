package com.example.Material.Persistence.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class CourseDAO
{
    @Id
    private String courseId;
    private String name;
    private String description;
    @DBRef
    private TopicDAO topicDAO;
}
