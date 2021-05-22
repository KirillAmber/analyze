package ru.farpost.analyze.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.exceptions.InvalidIntervalFinishDateException;
import ru.farpost.analyze.models.RawInterval;

import java.util.*;

import static org.junit.Assert.*;
import static ru.farpost.analyze.models.Interval.DEFAULT_DATE;

public class RowsSlicerTest {
    RowsSlicer rowsSlicer;
    static final Date TEST_DATE = new Date(1000);
    static final int CAPACITY_DATE_QUEUE = 5;
    Queue<Date> testQueue;


    @Before
    public void setUp(){
        rowsSlicer = new RowsSlicer();
        testQueue = new ArrayDeque<>(CAPACITY_DATE_QUEUE);
    }

    @After
    public void tearDown(){

    }

    @Test
    public void addDate_RowsSlicerShouldNotBeReadyForSlicing_WhenAnArrayOfIdenticalDatesIsSupplied() throws InvalidIntervalFinishDateException {
        for(int i = 0; i < CAPACITY_DATE_QUEUE; i++){
            testQueue.add(TEST_DATE);
        }
        for (Date date : testQueue) {
            rowsSlicer.addDate(date);
        }
        Date dateS = rowsSlicer.getRawInterval().getDateS();
        Date dateF = rowsSlicer.getRawInterval().getDateF();
        //должна заполниться только dateS
        assertEquals(TEST_DATE, dateS);
        assertEquals(DEFAULT_DATE, dateF);
        assertFalse(rowsSlicer.isReadyForSlicing());
    }
    @Test
    public void slice_ShouldSliceDatesIntoTwoIntervals_WhenArrayWithVariousDataSortedInAscendingOrder() throws InvalidIntervalFinishDateException {
        Queue<RawInterval> outputQueue = new ArrayDeque<>();
        for(int i = 0; i < CAPACITY_DATE_QUEUE; i++){
            testQueue.add(new Date(i));
        }
        for(Date date : testQueue){
            rowsSlicer.addDate(date);
            if(rowsSlicer.isReadyForSlicing()){
                outputQueue.add(rowsSlicer.slice());
            }
        }
        assertEquals(2,outputQueue.size());
    }

}