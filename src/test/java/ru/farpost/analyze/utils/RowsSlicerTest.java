package ru.farpost.analyze.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.util.AssertionErrors;
import ru.farpost.analyze.exceptions.InvalidIntervalFinishDateException;

import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;
import static ru.farpost.analyze.models.Interval.DEFAULT_DATE;

public class RowsSlicerTest {
    RowsSlicer rowsSlicer;
    static final Date TEST_DATE = new Date(10);
    static final int SIZE_DATE_ARRAY = 5;
    Date[] testDateArray;


    @Before
    public void setUp() throws Exception {
        rowsSlicer = new RowsSlicer();
        testDateArray = new Date[SIZE_DATE_ARRAY];
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addDate_ShouldCompleteSuccessfully_WhenAnArrayOfIdenticalDatesIsSupplied() throws InvalidIntervalFinishDateException {
        Arrays.fill(testDateArray, TEST_DATE);
        for (Date date : testDateArray) {
            rowsSlicer.addDate(date);
        }
        Date dateS = rowsSlicer.getRawInterval().getDateS();
        Date dateF = rowsSlicer.getRawInterval().getDateF();
        //должна заполниться только dateS
        assertEquals(dateS, TEST_DATE);
        assertEquals(dateF, DEFAULT_DATE);
        assertFalse(rowsSlicer.isReadyForSlicing());
    }
    //TODO надо доделать тест
    @Test
    public void addDate_ShouldCompleteSuccessfully_WhenArrayWithVariousDataSortedInAscendingOrder(){
        for(int i = 1; i<=testDateArray.length; i++){
            testDateArray[i] = new Date(i);
        }

    }

    @Test
    public void slice() {
    }
}