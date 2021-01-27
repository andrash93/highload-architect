package ru.otus.architect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.otus.architect.service.JWTService;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final JWTService jwtService;

    public WebConfig(JWTService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver(jwtService));
    }

}
