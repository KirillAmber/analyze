package ru.farpost.analyze.logHandlers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.models.InputQueueSingleton;
import ru.farpost.analyze.models.OutputQueueSingleton;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class LogReaderTest {

    BufferedReader input;
    private final LogReader logReader;

    public LogReaderTest() throws FileNotFoundException {
        input = new BufferedReader(new FileReader("bigAccessFile.log"));
        logReader = new LogReader(input);
    }

    @Before
    public void setUp(){
        System.out.println("LogReader Test started");

    }

    @After
    public void tearDown() {
        //подсчёт размера коллекции и используемый объём памяти
        System.out.println(InputQueueSingleton.getInstance().getInputQueue().size());
        Runtime rt = Runtime.getRuntime();
        long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
        System.out.println("Used mb: " + usedMB);
        System.out.println("LogReader Test finished");
    }


    @Test
    public void read() throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends LogReader> classLogReader = logReader.getClass();
        Method method = classLogReader.getDeclaredMethod("read", BufferedReader.class);
        method.setAccessible(true);
        method.invoke(logReader, input);
        InputQueueSingleton.getInstance().getInputQueue().stream().limit(30).forEach(
                System.out::println
        );

    }
}