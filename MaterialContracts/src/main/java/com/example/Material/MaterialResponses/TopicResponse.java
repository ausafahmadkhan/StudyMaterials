package com.example.Material.MaterialResponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse
{
    private String topicId;
    private String name;
    private List<CourseResponse> courseResponses;
}
