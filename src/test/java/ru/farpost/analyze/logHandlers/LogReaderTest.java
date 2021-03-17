package ru.farpost.analyze.logHandlers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class LogReaderTest {

    Runtime runtime = Runtime.getRuntime();
    long usedMemoryBefore;
    long usedMemoryAfter;
    int coefficientMegabyte = 1000000;

    BufferedReader input = new BufferedReader(new FileReader("access.log"));
    private LogReader logReader = new LogReader(input);

    LogReaderTest() throws FileNotFoundException {
    }

    @BeforeEach
    void setUp() {
        usedMemoryBefore = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Used Memory before" + usedMemoryBefore);
        // working code here

    }

    @AfterEach
    void tearDown() {
        usedMemoryAfter = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Memory increased:" + (usedMemoryAfter-usedMemoryBefore));
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    void run() {

    }

    @Test
    void read() throws  NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends LogReader> classLogReader = logReader.getClass();
        Method method = classLogReader.getDeclaredMethod("read", BufferedReader.class);
        method.setAccessible(true);
        method.invoke(logReader, input);

    }
}