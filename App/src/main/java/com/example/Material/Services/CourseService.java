package com.example.Material.Services;

import com.example.Material.MaterialRequests.CourseRequest;
import com.example.Material.MaterialResponses.CourseResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CourseService
{
    public CompletableFuture<CourseResponse> addCourse(String topicId, CourseRequest courseRequest);
    public CompletableFuture<List<CourseResponse>> getCourse(String topicId);
}
