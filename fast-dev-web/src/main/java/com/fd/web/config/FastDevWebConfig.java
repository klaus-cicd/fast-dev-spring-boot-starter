package com.fd.web.config;

import com.fd.web.interceptor.LocaleInterceptor;
import com.fd.web.interceptor.ThreadInfoInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Silas
 */
public class FastDevWebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor());
        registry.addInterceptor(new ThreadInfoInterceptor());
    }
}
