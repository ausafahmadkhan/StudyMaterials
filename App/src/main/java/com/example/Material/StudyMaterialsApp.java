package com.example.Material;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudyMaterialsApp
{
    private static Logger logger = LogManager.getLogger(StudyMaterialsApp.class);
    public static void main(String args[])
    {
        logger.info("Started StudyMaterials App");
        SpringApplication.run(StudyMaterialsApp.class, args);
    }
}
