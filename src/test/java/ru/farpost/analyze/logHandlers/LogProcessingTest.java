package ru.farpost.analyze.logHandlers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.farpost.analyze.models.InputQueue;
import ru.farpost.analyze.models.OutputQueue;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class LogProcessingTest {

    LogProcessing logProcessing;


    public LogProcessingTest(){
        logProcessing = new LogProcessing(66.7, 55);
        logProcessing.setReading(false);

    }
    @Before
    public void setUp() {
/*
        System.out.println("LogProcessing Test started");
        //должно пройти 1/3 -> 33.33 доступность
        InputQueue.getInstance().getInputQueue().add(
                "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e156f7e HTTP/1.1\" 200 2 69.669118 \"-\" \"@list-item-updater\" prio:0");
        InputQueue.getInstance().getInputQueue().add(
                "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e156f7e HTTP/1.1\" 599 2 22.669118 \"-\" \"@list-item-updater\" prio:0");
        InputQueue.getInstance().getInputQueue().add(
                "192.168.32.181 - - [14/06/2017:16:47:03 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e156f7e HTTP/1.1\" 200 2 33.669118 \"-\" \"@list-item-updater\" prio:0");
*/

    }

    @After
    public void tearDown() {
        System.out.println("LogProcessing Test finished");
    }


   /* @Test
    public void process() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends LogProcessing> classLogProcessing = logProcessing.getClass();
        Method method = classLogProcessing.getDeclaredMethod("process");
        method.setAccessible(true);
        method.invoke(logProcessing);
        OutputQueue.getInstance().outputQueue.stream().limit(30).forEach(
                element->System.out.println(element.toString())
        );
    }*/

}
