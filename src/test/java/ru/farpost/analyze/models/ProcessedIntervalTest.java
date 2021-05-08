package ru.farpost.analyze.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ProcessedIntervalTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void compareTo_ShouldReturn0_WhenIntervalsEquals(){
        ProcessedInterval interval1 = new ProcessedInterval(new Date(0), new Date(1), 5);
        ProcessedInterval interval2 = new ProcessedInterval(new Date(0), new Date(1), 5);
        assertEquals(0,interval1.compareTo(interval2));
    }
    @Test
    public void compareTo_ShouldReturn1_WhenInterval1IsLaterThanInterval2(){
        ProcessedInterval interval1 = new ProcessedInterval(new Date(1), new Date(2));
        ProcessedInterval interval2 = new ProcessedInterval(new Date(0), new Date(1));
        assertEquals(1,interval1.compareTo(interval2));
    }
    @Test
    public void compareTo_ShouldReturnNegative1_WhenInterval1HasLessPercThanInterval2(){
        ProcessedInterval interval1 = new ProcessedInterval(new Date(0), new Date(1), 5);
        ProcessedInterval interval2 = new ProcessedInterval(new Date(0), new Date(1),6);
        assertEquals(-1,interval1.compareTo(interval2));
    }
    @Test
    public void compareTo_ShouldReturnNegative1_WhenInterval1IsEarlierThanInterval2(){
        ProcessedInterval interval1 = new ProcessedInterval(new Date(0), new Date(1), 5);
        ProcessedInterval interval2 = new ProcessedInterval(new Date(1), new Date(2),5);
        assertEquals(-1,interval1.compareTo(interval2));
    }
}