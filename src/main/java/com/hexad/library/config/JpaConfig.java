package com.hexad.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import  com.hexad.library.service.AuditorAwareImpl;
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
class JpaConfig {
	
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
