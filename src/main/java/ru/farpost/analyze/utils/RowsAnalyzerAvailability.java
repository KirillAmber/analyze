package ru.farpost.analyze.utils;


import org.springframework.stereotype.Service;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.RawInterval;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Этот класс проверяет интервал на наличие ошибок и на долю доступности
@Service
public class RowsAnalyzerAvailability {
    public static final String ERROR_SERVER_0 = "500";
    public static final String ERROR_SERVER_99 = "599";
    //паттерн для выявления кода ответа сервера
    public static final Pattern CODE_PATTERN = Pattern.compile("(?<=\\s)\\d{3}(?=\\s)");
    //паттерн для выявления длительности запроса
    public static final Pattern TIME_RESPONSE_PATTERN = Pattern.compile("(?<=\\s)(\\d+[.]\\d+)(?=\\s)");
    private double minPercAvailability;
    private double millisAcceptable;

    public RowsAnalyzerAvailability(){
        millisAcceptable = -1;
        minPercAvailability = -1;
    }

    public RowsAnalyzerAvailability(double minPercAvailability,
                                    double millisAcceptable){
        this.minPercAvailability = minPercAvailability;
        this.millisAcceptable = millisAcceptable;
    }

    //проверяет долю доступности в этом интервале и возвращает объект Interval с выявленной долей
    public ProcessedInterval analyze(RawInterval rawInterval){
        ProcessedInterval failureProcessedInterval = new ProcessedInterval(rawInterval.getDateS(), rawInterval.getDateF());
        Queue<String> rowsQueue = new ArrayDeque<>(rawInterval.getRowsQueue());
        int totalRequests = rowsQueue.size();
        int amountFailures = 0;
        int resultCompareToError0;
        int resultCompareToError99;
        double percAvailabilityGroup;

        while (!rowsQueue.isEmpty()) {
            Matcher codeMatcher = CODE_PATTERN.matcher(rowsQueue.peek());
            Matcher timeResponseMatcher = TIME_RESPONSE_PATTERN.matcher(rowsQueue.peek());
            if (codeMatcher.find()) {
                if(timeResponseMatcher.find()) {
                    resultCompareToError0 = codeMatcher.group().compareTo(ERROR_SERVER_0);
                    resultCompareToError99 = codeMatcher.group().compareTo(ERROR_SERVER_99);
                    double millisResponse = Double.parseDouble(timeResponseMatcher.group());
                    if ((resultCompareToError0 >= 0 && resultCompareToError99 <= 0)) {
                        amountFailures += 1;
                    }
                    else if(millisResponse > millisAcceptable){
                        amountFailures += 1;
                    }
                }
            }
            rowsQueue.poll();
        }
        //вычисление доли доступности группы
        percAvailabilityGroup = computeAvailability(totalRequests, amountFailures);
        if(percAvailabilityGroup < minPercAvailability) {
            failureProcessedInterval.setPercAvailability(percAvailabilityGroup);
        }
        //если интервал не выходит за пределы, то интервал возвращается с percAvailability = -1
        return failureProcessedInterval;
    }

    //вычисление процентного соотношения
    private double computeAvailability(int totalRequests, int amountFailures){
        return 100.0 - ((double)amountFailures/totalRequests * 100);
    }

    public double getMinPercAvailability() {
        return minPercAvailability;
    }

    public double getMillisAcceptable() {
        return millisAcceptable;
    }

    public void setMinPercAvailability(double minPercAvailability) {
        this.minPercAvailability = minPercAvailability;
    }

    public void setMillisAcceptable(double millisAcceptable) {
        this.millisAcceptable = millisAcceptable;
    }
}
