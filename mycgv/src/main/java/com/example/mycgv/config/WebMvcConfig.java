package com.example.mycgv.config;

import com.example.mycgv.utils.AuthInterceptor;
import com.example.mycgv.utils.MyCgvInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AllArgsConstructor
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;
    private MyCgvInterceptor myCgvInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:C:/upload/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/admin*");
        registry.addInterceptor(myCgvInterceptor)
                .addPathPatterns("/mycgv*");
    }

}