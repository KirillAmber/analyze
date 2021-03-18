package ru.farpost.analyze.logHandlers;

import ru.farpost.analyze.utils.GroupAnalyzerAvailability;
import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.InputQueueSingleton;
import ru.farpost.analyze.models.OutputQueueSingleton;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogProcessing extends Thread {
    private final Queue<String> groupData;
    private final GroupAnalyzerAvailability groupAnalyzerAvailability;
    private boolean isReading;

    //Данный класс обрабатывает строки из файла
    public LogProcessing(double minPercAvailability, double millisAcceptable){
        //groupData - очередь, которая будет содержать строчки для проверки на успешность запроса
        this.groupData = new ArrayDeque<>();
        //isReading - булевая переменная, которая будет всегда true, пока bufferedReader не прочитает весь файл
        this.isReading = true;
        groupAnalyzerAvailability = new GroupAnalyzerAvailability(minPercAvailability, millisAcceptable);
    }

    @Override
    public void run() {
        super.run();
        process();
    }


    private void process(){
        //паттерн для выявления время начала запроса
        Pattern datatimePattern = Pattern.compile("(\\d{2}[:]\\d{2}[:]\\d{2}(?:\\s))");
        Matcher datatimeMatcher;
        /*
        tempData предназначеня для сбора данных по интервалам.
        Когда tempData изменяется, то создаётся новый интервал
         */
        String tempData = "";
        //берём данные из очереди
        while (isReading || InputQueueSingleton.getInstance().getInputQueue().size()> 0) {
            if(InputQueueSingleton.getInstance().getInputQueue().peek()!=null){
                datatimeMatcher = datatimePattern.matcher(InputQueueSingleton.getInstance().getInputQueue().peek());
                if(datatimeMatcher.find()){
                    if(tempData.isEmpty()){
                        tempData = datatimeMatcher.group();
                        groupData.add(InputQueueSingleton.getInstance().getInputQueue().poll());
                    }
                    else if(tempData.equals(datatimeMatcher.group())){
                        groupData.add(InputQueueSingleton.getInstance().getInputQueue().poll());
                    }
                    //здесь мы анализируем данные из groupData и выгружаем в map
                    else if(!tempData.equals(datatimeMatcher.group())){
                        groupData.add(InputQueueSingleton.getInstance().getInputQueue().poll());
                        //проверяет заданный интервал и если он проблемный, то добавляет в очередь для вывода
                        addFailureInterval(groupAnalyzerAvailability.analyze(tempData, datatimeMatcher.group(), groupData));
                        tempData = datatimeMatcher.group();
                    }
                }
            }
        }
        //если вдруг в логах только одна дата
        if(!groupData.isEmpty()){
            addFailureInterval(groupAnalyzerAvailability.analyze(tempData, tempData, groupData));
        }

    }
    //Проверяет и добавляет несоответсвующий требованиям проблемный интервал
    private boolean addFailureInterval(Interval interval){
        if(interval.getPercAvailability() >= 0){
            OutputQueueSingleton.getInstance().getOutputQueue().add(interval);
            return true;
        }
        return false;
    }

    public void setReading(boolean reading) {
        isReading = reading;
    }

}
