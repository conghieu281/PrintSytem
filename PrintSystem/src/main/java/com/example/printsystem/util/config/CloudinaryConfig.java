package com.example.printsystem.util.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> cloud = new HashMap<>();
        cloud.put("cloud_name", "dver4o2bs");
        cloud.put("api_key", "193583578296948");
        cloud.put("api_secret", "CRw5SLzhf-FSxVB7lPRTBjuYInc");
        return new Cloudinary(cloud);
    }
}
