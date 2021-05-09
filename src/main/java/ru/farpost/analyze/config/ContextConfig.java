package ru.farpost.analyze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.text.SimpleDateFormat;

/**
 * Конфигурация бинов.
 */
@ComponentScan("ru.farpost.analyze")
@Configuration
public class ContextConfig {
    @Bean
    @Scope("singleton")
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }
}
