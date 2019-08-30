package com.example.Material.Persistence.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Course")
public class CourseDAO
{
    @Id
    private String courseId;
    private String name;
    private String description;
    @ManyToOne
    private TopicDAO topicDAO;
}
