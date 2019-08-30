package com.example.Material.Services;

import com.example.Material.MaterialRequests.TopicRequest;
import com.example.Material.MaterialResponses.TopicResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface TopicService
{
    public CompletableFuture<TopicResponse> addTopic(TopicRequest topicRequest) throws ExecutionException, InterruptedException;
    public CompletableFuture<TopicResponse> getTopic(String id) throws Exception;
}
