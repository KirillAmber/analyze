package ru.farpost.analyze.logHandlers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.RawInterval;
import ru.farpost.analyze.utils.RowsAnalyzerAvailability;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

import static org.junit.Assert.*;

public class RowsAnalyzerAvailabilityTest {
    private static final double DELTA = 1e-15;
    private RowsAnalyzerAvailability rowsAnalyzerAvailability;

    @Before
    public void setUp() {
        rowsAnalyzerAvailability = new RowsAnalyzerAvailability(45, 50);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void computeAvailability_ShouldReturn90_WhenTotalIs10AndAmountIs1() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<? extends RowsAnalyzerAvailability> classLogAnalyzer = rowsAnalyzerAvailability.getClass();
        Method method = classLogAnalyzer.getDeclaredMethod("computeAvailability", int.class, int.class);
        method.setAccessible(true);
        double result = (Double) method.invoke(rowsAnalyzerAvailability, 10, 1);
        assertEquals(90.0, result, DELTA);
    }
    @Test
    public void computeAvailability_ShouldReturnNan_WhenTotalIs0AndAmountIs0() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<? extends RowsAnalyzerAvailability> classLogAnalyzer = rowsAnalyzerAvailability.getClass();
        Method method = classLogAnalyzer.getDeclaredMethod("computeAvailability", int.class, int.class);
        method.setAccessible(true);
        double result = (Double) method.invoke(rowsAnalyzerAvailability, 0, 0);
        assertEquals(Double.NaN, result, DELTA);
    }
    @Test
    public void computeAvailability_ShouldReturnMinus100_WhenTotalIsLessAmount() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Class<? extends RowsAnalyzerAvailability> classLogAnalyzer = rowsAnalyzerAvailability.getClass();
        Method method = classLogAnalyzer.getDeclaredMethod("computeAvailability", int.class, int.class);
        method.setAccessible(true);
        double result = (Double) method.invoke(rowsAnalyzerAvailability, 5, 10);
        assertEquals(-100.0, result, DELTA);
    }
    @Test
    public void computeAvailability_ShouldReturnMinus100_WhenTotalIs100AndAmountIs0() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Class<? extends RowsAnalyzerAvailability> classLogAnalyzer = rowsAnalyzerAvailability.getClass();
        Method method = classLogAnalyzer.getDeclaredMethod("computeAvailability", int.class, int.class);
        method.setAccessible(true);
        double result = (Double) method.invoke(rowsAnalyzerAvailability, 100, 0);
        assertEquals(100.0, result, DELTA);
    }

    @Test
    public void analyze_ShouldReturnMinus1_WhenIntervalIsWithinTheLimits(){
        Queue<String> queue = new ArrayDeque<>();
        //60 perc
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 50.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 2.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 2.13131 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 100.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 4.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 10.510983 \"-\" \"@list-item-updater\" prio:0");

        RawInterval rawInterval = new RawInterval(new Date(1), new Date(2), queue);
        ProcessedInterval result = rowsAnalyzerAvailability.analyze(rawInterval);
        assertEquals(-1.0, result.getPercAvailability(), DELTA);
    }
    @Test
    public void analyze_ShouldReturnPercAvailabilityEquals40_WhenIntervalNotIsWithinTheLimits(){
        Queue<String> queue = new ArrayDeque<>();
        //40 perc
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 50.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 2.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 2.13131 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 100.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 4.510983 \"-\" \"@list-item-updater\" prio:0");
        queue.add("192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"/PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 100.510983 \"-\" \"@list-item-updater\" prio:0");

        RawInterval rawInterval = new RawInterval(new Date(1), new Date(2), queue);
        ProcessedInterval result = rowsAnalyzerAvailability.analyze(rawInterval);
        assertEquals(40.0, result.getPercAvailability(), DELTA);
    }
}

