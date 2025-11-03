package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(uploadPath);
        if (!uploadDir.isAbsolute()) {
            uploadDir = Paths.get(System.getProperty("user.home"), uploadPath).toAbsolutePath();
        }

        String location = "file:" + (StringUtils.trimTrailingCharacter(uploadDir.toString().replace('\\','/'), '/')) + "/";

        registry
                .addResourceHandler("/images/**")
                .addResourceLocations(location);
    }
}

