// src/main/java/com/example/travelproject/config/WebConfig.java
package com.example.travelproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class WebConfig {

    /**
     * 모든 요청의 인코딩을 UTF-8로 강제
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> forceUtf8EncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> bean =
                new FilterRegistrationBean<>(filter);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
