package com.mino.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        MustacheViewResolver resolver = new MustacheViewResolver();
//        resolver.setCharset("UTF-8");
        resolver.setCharset(StandardCharsets.UTF_8.name());
//        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setContentType(MediaType.TEXT_HTML_VALUE+";charset="+StandardCharsets.UTF_8);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");

        registry.viewResolver(resolver);
    }


}
