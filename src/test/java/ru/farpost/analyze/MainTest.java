package ru.farpost.analyze;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(Parameterized.class)
public class MainTest {

    String [] args;

    @Parameterized.Parameters
    public static List<String[][]> testParams(){
        return Arrays.asList(
                //0 адекватный тест
                new String[][]{new String[]{"-u", "99", "-t", "45", "-f", "access.log"}},
                //1 неправильное имя файла
                new String[][]{new String[]{"-u", "99", "-t", "45", "-f", "access.logss"}},
                //2,3,4 перемешанные аргументы
                new String[][]{new String[]{"-t", "45", "-u", "99",  "-f", "access.log"}},
                new String[][]{new String[]{"-f", "access.log", "-t", "45", "-u", "99"}},
                new String[][]{new String[]{"-t", "45", "-u", "99",  "-f", "access.log"}},
                //5 неправильно заданны названия аргументов
                new String[][]{new String[]{"t", "45", "u", "99",  "-f", "access.log"}},
                //6 пустые аргументы
                new String[][]{new String[]{"-t", "", "-u", ""}},
                //7 с одним аргументом
                new String[][]{new String[]{"-u", "99"}});
    }
    public MainTest(String [] args){
        this.args = args;
    }
    @Before
    public void setUp(){
        System.out.println("Main Test started");
    }

    @After
    public void tearDown(){
        System.out.println("Main Test finished");
    }

    @Test
    public void main() throws IOException, ArgumentException, InterruptedException {
        Main.main(args);
    }

}
