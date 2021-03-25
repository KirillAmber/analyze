package ru.farpost.analyze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.models.InputQueue;
import ru.farpost.analyze.models.OutputQueue;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ComponentScan("ru.farpost.analyze")
@Configuration
public class ContextConfig {
    @Bean
    @Scope("singleton")
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("HH:mm:ss");
    }
}
