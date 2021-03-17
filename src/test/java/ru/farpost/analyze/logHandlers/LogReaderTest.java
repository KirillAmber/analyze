package ru.farpost.analyze.logHandlers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class LogReaderTest {

    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore;
    long usedMemoryAfter;
    int coefficientMegabyte = 1000000;

    BufferedReader input = new BufferedReader(new FileReader("access.log"));
    private LogReader logReader = new LogReader(input);

    public LogReaderTest() throws FileNotFoundException {
    }

    @Before
    public void setUp() {
        usedMemoryBefore = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Used Memory before" + usedMemoryBefore);
        // working code here

    }

    @After
    public void tearDown() {
        usedMemoryAfter = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Memory increased:" + (usedMemoryAfter-usedMemoryBefore));
    }


    @Test
    public void run() {

    }

    @Test
    public void read() throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends LogReader> classLogReader = logReader.getClass();
        Method method = classLogReader.getDeclaredMethod("read", BufferedReader.class);
        method.setAccessible(true);
        method.invoke(logReader, input);

    }
}