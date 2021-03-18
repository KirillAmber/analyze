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

    //������ ����� ������������ ������ �� �����
    public LogProcessing(double minPercAvailability, double millisAcceptable){
        //groupData - �������, ������� ����� ��������� ������� ��� �������� �� ���������� �������
        this.groupData = new ArrayDeque<>();
        //isReading - ������� ����������, ������� ����� ������ true, ���� bufferedReader �� ��������� ���� ����
        this.isReading = true;
        groupAnalyzerAvailability = new GroupAnalyzerAvailability(minPercAvailability, millisAcceptable);
    }

    @Override
    public void run() {
        super.run();
        process();
    }


    private void process(){
        //������� ��� ��������� ����� ������ �������
        Pattern datatimePattern = Pattern.compile("(\\d{2}[:]\\d{2}[:]\\d{2}(?:\\s))");
        Matcher datatimeMatcher;
        /*
        tempData ������������� ��� ����� ������ �� ����������.
        ����� tempData ����������, �� �������� ����� ��������
         */
        String tempData = "";
        //���� ������ �� �������
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
                    //����� �� ����������� ������ �� groupData � ��������� � map
                    else if(!tempData.equals(datatimeMatcher.group())){
                        groupData.add(InputQueueSingleton.getInstance().getInputQueue().poll());
                        //��������� �������� �������� � ���� �� ����������, �� ��������� � ������� ��� ������
                        addFailureInterval(groupAnalyzerAvailability.analyze(tempData, datatimeMatcher.group(), groupData));
                        tempData = datatimeMatcher.group();
                    }
                }
            }
        }
        //���� ����� � ����� ������ ���� ����
        if(!groupData.isEmpty()){
            addFailureInterval(groupAnalyzerAvailability.analyze(tempData, tempData, groupData));
        }

    }
    //��������� � ��������� ���������������� ����������� ���������� ��������
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
