package ru.farpost.analyze.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.exceptions.argumentsExceptions.NoEnoughArgsException;

public class ArgsCheckerTest {
    final String filename = "access.log";
    ArgsChecker argsChecker;

    public ArgsCheckerTest(){
        argsChecker = new ArgsChecker();
    }


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void check_ShouldNotThrowAnException_WhenInputIsCorrect() throws ArgumentException {
        String[] args = {"-u", "99", "-t", "45", "-f", filename};
        argsChecker.check(args);
    }
    @Test
    public void check_ShouldNotThrowAnException_WhenArgumentsAreMixed() throws ArgumentException {
        String[] argsVariation1 = {"-t", "45", "-u", "99",  "-f", filename};
        String[] argsVariation2 = {"-f", filename, "-t", "45", "-u", "99"};
        String[] argsVariation3 = {"-t", "45", "-u", "99",  "-f", filename};
        argsChecker.check(argsVariation1);
        argsChecker.check(argsVariation2);
        argsChecker.check(argsVariation3);
    }
    @Test(expected = ArgumentException.class)
    public void check_ShouldThrowArgumentException_WhenArgumentsAreNamedWrongly() throws ArgumentException {
        String[] args = {"t", "45", "u", "99",  "-f", filename};
        argsChecker.check(args);
    }
    @Test(expected = NumberFormatException.class)
    public void check_ShouldThrowNumberFormatException_WhenArgumentsAreEmpty() throws ArgumentException {
        String[] args = {"-t", "", "-u", ""};
        argsChecker.check(args);
    }
    @Test(expected = NoEnoughArgsException.class)
    public void check_ShouldThrowNoEnoughException_WhenArgumentsAreNotEnough() throws ArgumentException {
        String[] args = {"-u", "99"};
        argsChecker.check(args);
    }

}