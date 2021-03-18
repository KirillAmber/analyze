package ru.farpost.analyze.logHandlers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.farpost.analyze.utils.GroupAnalyzerAvailability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
@RunWith(Parameterized.class)
public class GroupAnalyzerAvailabilityTest {
    public GroupAnalyzerAvailability groupAnalyzerAvailability;
    public int totalRequests;
    public int amountFailures;

    public GroupAnalyzerAvailabilityTest(int totalRequests, int amountFailures){
        groupAnalyzerAvailability = new GroupAnalyzerAvailability(45, 55);
        this.totalRequests = totalRequests;
        this.amountFailures = amountFailures;
    }

    @Parameterized.Parameters
    public static Collection testParams(){
        return Arrays.asList(
                new Object[][]{
                        //0. адекватный тест. Должен быть вывод: 90
                        {10, 1},
                        //1. 0 делится на 0. Должен быть вывод: Nan
                        {0, 0},
                        //2. total < amount. Должен быть вывод: -100.0
                        {5, 10},
                        //3. оба равны по единице. Должен быть вывод: 0.0
                        {1, 1},
                        //4. просто большие числа. Должен быть вывод: 95.44
                        {10000, 456},
                        //5. 0 ошибок. Должен быть вывод: 100.0
                        {100, 0}});
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void computeAvailability() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends GroupAnalyzerAvailability> classLogAnalyzer = groupAnalyzerAvailability.getClass();
        Method method = classLogAnalyzer.getDeclaredMethod("computeAvailability", int.class, int.class);
        method.setAccessible(true);
        System.out.println(method.invoke(groupAnalyzerAvailability, totalRequests, amountFailures));
    }
}