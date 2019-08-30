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
@Table(name = "Topic")
public class TopicDAO
{
    @Id
    private String topicId;
    private String name;
}
