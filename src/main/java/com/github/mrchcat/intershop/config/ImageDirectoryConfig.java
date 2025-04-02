package com.github.mrchcat.intershop.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "application.items.load.enabled")
public class ImageDirectoryConfig {
//
//    @Value("${application.items.images.directory}")
//    private String IMAGE_DIRECTORY;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path imageDirectory = Path.of(IMAGE_DIRECTORY);
//        try {
//            if (Files.notExists(imageDirectory)) {
//                Files.createDirectories(imageDirectory);
//            }
//            registry.addResourceHandler("/images/**").addResourceLocations("file:" + IMAGE_DIRECTORY);
//            WebMvcConfigurer.super.addResourceHandlers(registry);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
