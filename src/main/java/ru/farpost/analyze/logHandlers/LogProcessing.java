package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.utils.GroupAnalyzerAvailability;
import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.InputQueue;
import ru.farpost.analyze.models.OutputQueue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LogProcessing extends Thread {
    private  Queue<String> groupData;
    private  GroupAnalyzerAvailability groupAnalyzerAvailability;
    private SimpleDateFormat dataFormat;
    private InputQueue inputQueue;
    private OutputQueue outputQueue;
    private boolean isReading;

    //Данный класс обрабатывает строки из файла
    public LogProcessing(double minPercAvailability, double millisAcceptable){
        //groupData - очередь, которая будет содержать строчки для проверки на успешность запроса
        this.groupData = new ArrayDeque<>();
        //isReading - булевая переменная, которая будет всегда true, пока bufferedReader не прочитает весь файл
        this.isReading = true;
        this.groupAnalyzerAvailability = new GroupAnalyzerAvailability(minPercAvailability, millisAcceptable);
    }
    public LogProcessing(){
        this.groupData = new ArrayDeque<>();
        this.isReading = true;
        this.groupAnalyzerAvailability = new GroupAnalyzerAvailability();
    }

    @Override
    public void run() {
        super.run();
        try {
            process();
        } catch (ParseException | InterruptedException e) {
        }
    }


    private void process() throws ParseException, InterruptedException {
        //паттерн для выявления время начала запроса
        Pattern datatimePattern = Pattern.compile("(\\d{2}[:]\\d{2}[:]\\d{2}(?:\\s))");
        Matcher datatimeMatcher;
        /*
        tempData предназначеня для сбора данных по интервалам.
        Когда tempData изменяется, то создаётся новый интервал
         */
        Date tempData = null;
        String row = "";
        //берём данные из очереди
        while (isReading) {
            row = inputQueue.getInputQueue().take();
            datatimeMatcher = datatimePattern.matcher(row);
            if(datatimeMatcher.find()){
                //переводим строку в Date
                Date foundTime = dataFormat.parse(datatimeMatcher.group());
                if(tempData == null){
                    tempData = foundTime;
                    groupData.add(row);
                    System.out.println("oops");
                }
                else if(tempData.equals(foundTime)){
                    //выгружаем groupData, чтобы не получить OutOfMemory, когда интервал становится слишком
                    //длинным
                    if(groupData.size()>1000){
                        addFailureInterval(groupAnalyzerAvailability.analyze(tempData, foundTime, groupData));
                    } else {
                        groupData.add(row);
                    }
                }
                //здесь мы анализируем данные из groupData и выгружаем в outputQueue
                else if(!tempData.equals(foundTime)){
                    groupData.add(row);
                    //проверяет заданный интервал и если он проблемный, то добавляет в очередь для вывода
                    addFailureInterval(groupAnalyzerAvailability.analyze(tempData, foundTime, groupData));
                    tempData = foundTime;
                }
            }
        }
        //если вдруг в логах только одна дата
        if(!groupData.isEmpty()){
            addFailureInterval(groupAnalyzerAvailability.analyze(tempData, tempData, groupData));
        }

    }
    //Проверяет и добавляет несоответсвующий требованиям проблемный интервал
    private boolean addFailureInterval(Interval interval) throws InterruptedException {
        if(interval.getPercAvailability() >= 0){
            outputQueue.getOutputQueue().put(interval);
            return true;
        }
        return false;
    }

    public void setReading(boolean reading) {
        isReading = reading;
        if(!isReading){
            interrupt();
        }
    }

    @Autowired
    public void setInputQueue(InputQueue inputQueue){
        this.inputQueue = inputQueue;
    }

    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

    @Autowired
    public void setDataFormat(SimpleDateFormat dataFormat){
        this.dataFormat = dataFormat;
    }


    public GroupAnalyzerAvailability getGroupAnalyzerAvailability() {
        return groupAnalyzerAvailability;
    }
}
