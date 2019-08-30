package com.example.Material.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class RedisWrapper
{
    @Value("${redis-host}")
    private String host;

    @Value("${redis-port}")
    private int port;

    @Bean
    public Jedis getJedisClient()
    {
        return new Jedis(host, port);
    }
}
