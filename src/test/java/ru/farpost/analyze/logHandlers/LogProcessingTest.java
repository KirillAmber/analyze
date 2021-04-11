package ru.farpost.analyze.logHandlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import ru.farpost.analyze.config.ContextConfig;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ContextConfig.class)
public class LogProcessingTest {
    @Autowired
    LogProcessing logProcessing;


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void run() {
        logProcessing.start();
    }
}