package ru.farpost.analyze.utils;


import ru.farpost.analyze.models.Interval;

import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//���� ����� ��������� �������� �� ������� ������ � �� ���� �����������
public class GroupAnalyzerAvailability {
    private final String ERROR_SERVER_0 = "500";
    private final String ERROR_SERVER_99 = "599";
    //������� ��� ��������� ���� ������ �������
    private final Pattern CODE_PATTERN = Pattern.compile("(?<=\\s)\\d{3}(?=\\s)");
    //������� ��� ��������� ������������ �������
    private final Pattern TIME_RESPONSE_PATTERN = Pattern.compile("(?<=\\s)(\\d+[.]\\d+)(?=\\s)");
    private double minPercAvailability;
    private double millisAcceptable;
    private Matcher codeMatcher;
    //������� ��� ��������� ������������ �������
    private Matcher timeResponseMatcher;

    private int totalRequests;
    private int amountFailures;

    public GroupAnalyzerAvailability (){

    }

    public GroupAnalyzerAvailability(double minPercAvailability,
                                     double millisAcceptable){
        this.minPercAvailability = minPercAvailability;
        this.millisAcceptable = millisAcceptable;
    }

    //��������� ���� ����������� � ���� ��������� � ���������� ������ Interval � ���������� �����
    public Interval analyze(String dataS, String dataF, Queue<String> groupData){
        //analyze
        Interval failureInterval = new Interval(dataS, dataF);
        totalRequests = groupData.size();
        amountFailures = 0;
        int compareToError0;
        int compareToError99;
        double percAvailabilityGroup;
        while (!groupData.isEmpty()) {
            codeMatcher = CODE_PATTERN.matcher(groupData.peek());
            timeResponseMatcher = TIME_RESPONSE_PATTERN.matcher(groupData.peek());
            if (codeMatcher.find()) {
                if(timeResponseMatcher.find()) {
                    compareToError0 = codeMatcher.group().compareTo(ERROR_SERVER_0);
                    compareToError99 = codeMatcher.group().compareTo(ERROR_SERVER_99);
                    double millisResponse = Double.parseDouble(timeResponseMatcher.group());
                    if ((compareToError0 >= 0 && compareToError99 <= 0)) {
                        amountFailures += 1;
                    }
                    else if(millisResponse > millisAcceptable){
                        amountFailures += 1;
                    }
                }
            }
            groupData.poll();
        }
        //���������� ���� ����������� ������
        percAvailabilityGroup = computeAvailability(totalRequests, amountFailures);
        if(percAvailabilityGroup < minPercAvailability) {
            failureInterval.setPercAvailability(percAvailabilityGroup);
        }
        //���� �������� �� ������� �� �������, �� �������� ������������ � percAvailability = -1
        return failureInterval;
    }

    //���������� ����������� �����������
    private double computeAvailability(int totalRequests, int amountFailures){
        return 100.0 - ((double)amountFailures/totalRequests * 100);
    }
}
