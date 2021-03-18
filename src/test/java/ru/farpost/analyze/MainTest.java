package ru.farpost.analyze;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.farpost.analyze.exceptions.NoInputException;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class MainTest {

    String [] args;

    @Parameterized.Parameters
    public static List<String[][]> testParams(){
        return Arrays.asList(
                //0 ���������� ����
                new String[][]{new String[]{"-u", "99", "-t", "45", "-f", "access.log"}},
                //1 ������������ ��� �����
                new String[][]{new String[]{"-u", "99", "-t", "45", "-f", "access.logss"}},
                //2,3,4 ������������ ���������
                new String[][]{new String[]{"-t", "45", "-u", "99",  "-f", "access.log"}},
                new String[][]{new String[]{"-f", "access.log", "-t", "45", "-u", "99"}},
                new String[][]{new String[]{"-t", "45", "-u", "99",  "-f", "access.log"}},
                //5 ����������� ������� �������� ����������
                new String[][]{new String[]{"t", "45", "u", "99",  "-f", "access.log"}},
                //6 ������ ���������
                new String[][]{new String[]{"-t", "", "-u", ""}},
                //7 ��� �����
                new String[][]{new String[]{"-t", "45", "-u", "99"}},
                //8 � ����� ����������
                new String[][]{new String[]{"-u", "99"}});
    }
    public MainTest(String [] args){
        this.args = args;
    }
    @Before
    public void setUp(){
    }

    @After
    public void tearDown(){
    }

    @Test
    public void main() throws IOException, ArgumentException, NoInputException {
        Main.main(args);
    }
}
