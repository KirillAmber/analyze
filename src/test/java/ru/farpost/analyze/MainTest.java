package ru.farpost.analyze;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.farpost.analyze.logHandlers.LogReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    Runtime runtime;
    long usedMemoryBefore;
    long usedMemoryAfter;
    int coefficientMegabyte = 1000000;
    String [] args = {"-", "99", "-t", "45", "-f", "access.log"};

    @BeforeEach
    void setUp() {
        runtime = Runtime.getRuntime();
        long usedMemoryBefore = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Used Memory before:" + usedMemoryBefore);
    }

    @AfterEach
    void tearDown() {
        usedMemoryAfter = (runtime.totalMemory() - runtime.freeMemory())/coefficientMegabyte;
        System.out.println("Memory increased:" + (usedMemoryAfter-usedMemoryBefore));
    }

    @Test
    void main() throws FileNotFoundException {
        Main.main(args);
    }
}