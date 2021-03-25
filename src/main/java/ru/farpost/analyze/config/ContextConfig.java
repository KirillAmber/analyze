package ru.farpost.analyze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.models.InputQueue;
import ru.farpost.analyze.models.OutputQueue;

@ComponentScan("ru.farpost.analyze")
@Configuration
public class ContextConfig {

}
