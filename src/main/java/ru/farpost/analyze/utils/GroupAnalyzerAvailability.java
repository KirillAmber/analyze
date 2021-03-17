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
    private int amountFailure;

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
        amountFailure = 0;
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
                        amountFailure += 1;
                    }
                    else if(millisResponse > millisAcceptable){
                        amountFailure += 1;
                    }
                }
            }
            groupData.poll();
        }
        //���������� ���� ����������� ������
        percAvailabilityGroup = 100.0 - ((double)amountFailure/totalRequests * 100);
        if(percAvailabilityGroup < minPercAvailability) {
            failureInterval.setPercAvailability(percAvailabilityGroup);
        }
        //���� �������� �� ������� �� �������, �� �������� ������������ � percAvailability = -1
        return failureInterval;
    }

}
