package ru.farpost.analyze;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;

import java.io.IOException;

public class MainTest {


    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){

    }

    @Test
    public void main_ShouldPrintFailureIntervalsInOrder_WhenAllArgumentsAreCorrectly() throws IOException, ArgumentException{
        String filename = "access.log";
        String[] args = {"-u", "99", "-t", "45", "-f", filename};
        Main.main(args);
    }


}
