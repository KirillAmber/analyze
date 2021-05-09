package ru.farpost.analyze.utils;


import org.springframework.stereotype.Service;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.RawInterval;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Этот класс проверяет интервал на наличие ошибок и на долю доступности.
 */
@Service
public class RowsAnalyzerAvailability {
    /**
     * Код ошибки сервера 500.
     */
    public static final String ERROR_SERVER_0 = "500";
    /**
     * код ошибки серева 599.
     */
    public static final String ERROR_SERVER_99 = "599";
    /**
     * Pattern для выявления кода ответа сервера.
     */
    public static final Pattern CODE_PATTERN = Pattern.compile("(?<=\\s)\\d{3}(?=\\s)");
    /**
     * Pattern для выявления длительности запроса.
     */
    public static final Pattern TIME_RESPONSE_PATTERN = Pattern.compile("(?<=\\s)(\\d+[.]\\d+)(?=\\s)");
    /**
     * Минимальный допустимый уровень доступности в процентах.
     */
    private double minPercAvailability;
    /**
     * Приемлемое время ответа в миллисекундах.
     */
    private double millisAcceptable;

    /**
     * Конструктор по умолчанию; присваивает millisAcceptable и minPercAvailability -1
     */
    public RowsAnalyzerAvailability(){
        millisAcceptable = -1;
        minPercAvailability = -1;
    }

    /**
     *
     * @param minPercAvailability Минимальный допустимый уровень доступности в процентах
     * @param millisAcceptable Приемлемое время ответа в миллисекундах
     */
    public RowsAnalyzerAvailability(double minPercAvailability,
                                    double millisAcceptable){
        this.minPercAvailability = minPercAvailability;
        this.millisAcceptable = millisAcceptable;
    }

    /**
     * Проверяет долю доступности в этом интервале.
     * @param rawInterval необработанный интервал
     * @return возвращает объект ProcessedInterval с выявленной долей доступности
     */
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

    /**
     * Вычисление процентного соотношения.
     * @param totalRequests общее количество запросов
     * @param amountFailures количество провалов
     * @return возвращает процент доступности
     */
    private double computeAvailability(int totalRequests, int amountFailures){
        return 100.0 - ((double)amountFailures/totalRequests * 100);
    }

    /**
     * @return возвращает Минимальный допустимый уровень доступности в процентах
     */
    public double getMinPercAvailability() {
        return minPercAvailability;
    }

    /**
     * @return возвращает приемлемое время ответа в миллисекундах
     */
    public double getMillisAcceptable() {
        return millisAcceptable;
    }

    /**
     * @param minPercAvailability Минимальный допустимый уровень доступности в процентах
     */
    public void setMinPercAvailability(double minPercAvailability) {
        this.minPercAvailability = minPercAvailability;
    }

    /**
     *
     * @param millisAcceptable приемлемое время ответа в миллисекундах
     */
    public void setMillisAcceptable(double millisAcceptable) {
        this.millisAcceptable = millisAcceptable;
    }
}
