package com.coordinacioncafesystem.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${media.storage-path}")
    private String uploadsPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = "file:" + (uploadsPath.endsWith("/") ? uploadsPath : uploadsPath + "/");

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(location);
    }
}

