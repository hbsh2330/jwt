package com.hbsh.jwt.config;

import com.hbsh.jwt.filter.MyFilter;
import com.hbsh.jwt.filter.MyFilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter> filter(){
        FilterRegistrationBean<MyFilter> bean = new FilterRegistrationBean<>(new MyFilter());
        bean.addUrlPatterns("/*"); // 모든 요청에서 필터가 실행
        bean.setOrder(0);//낮은 번호가 필터중에서 가장 먼저 실행됨.
        return bean;
    }
    @Bean
    public FilterRegistrationBean<MyFilter2> filter2(){
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*"); // 모든 요청에서 필터가 실행
        bean.setOrder(1);//낮은 번호가 필터중에서 가장 먼저 실행됨.
        return bean;
    }
}
