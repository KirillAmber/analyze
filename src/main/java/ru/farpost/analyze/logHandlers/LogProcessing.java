package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.exceptions.InvalidIntervalFinishDateException;
import ru.farpost.analyze.utils.RowsAnalyzerAvailability;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.InputQueue;
import ru.farpost.analyze.models.OutputQueue;
import ru.farpost.analyze.utils.RowsSlicer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Данный класс обрабатывае строки из файла
 */
@Component
@Scope("prototype")
public class LogProcessing extends Thread {
    /**
     * Анализатор строк
     */
    private final RowsAnalyzerAvailability rowsAnalyzerAvailability;
    /**
     * Переменная, которая разделяет строки на интервалы
     */
    private final RowsSlicer rowsSlicer;
    /**
     * Формат дат
     */
    private SimpleDateFormat dataFormat;
    /**
     * Входная очередь, откуда берутся строки из логов
     */
    private InputQueue inputQueue;
    /**
     * Выходная очередь, откуда выходят обработанные интервалы
     */
    private OutputQueue outputQueue;

    /**
     * @param rowsAnalyzerAvailability Анализатор строк
     * @param rowsSlicer интервальный разделитель
     */
    @Autowired
    public LogProcessing(RowsAnalyzerAvailability rowsAnalyzerAvailability, RowsSlicer rowsSlicer){
        this.rowsAnalyzerAvailability = rowsAnalyzerAvailability;
        this.rowsSlicer = rowsSlicer;
    }

    /**
     * Запускает поток.
     */
    @Override
    public void run() {
        super.run();
        try {
            process();
        } catch (ParseException | InterruptedException | InvalidIntervalFinishDateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Обрабатывает строки.
     * @throws ParseException если некорректный формат даты
     * @throws InterruptedException если поток прерван
     * @throws InvalidIntervalFinishDateException если дата начала интервала раньше, чем дата конца
     */
    private void process() throws ParseException, InterruptedException, InvalidIntervalFinishDateException {
        //паттерн для выявления время начала запроса
        Pattern datatimePattern = Pattern.compile("(\\d{2}[:]\\d{2}[:]\\d{2}(?:\\s))");
        Matcher datatimeMatcher;
        String row = "";
        //берём данные из очереди
        while (true) {
            row = inputQueue.getInputQueue().take();
            datatimeMatcher = datatimePattern.matcher(row);
            if(datatimeMatcher.find()){
                //переводим строку в Date
                Date foundTime;
                synchronized (dataFormat) {

                    foundTime = dataFormat.parse(datatimeMatcher.group().trim());
                }
                if (rowsSlicer.isReadyForSlicing()){
                    addFailureInterval(rowsAnalyzerAvailability.analyze(rowsSlicer.slice()));
                } else {
                    rowsSlicer.addDate(foundTime);
                    rowsSlicer.addRow(row);
                }
            }
        }
    }

    /**
     * @param processedInterval Обработанный интервал
     * @throws InterruptedException если поток прерван
     */
    private void addFailureInterval(ProcessedInterval processedInterval) throws InterruptedException {
        if(processedInterval.getPercAvailability() >= 0){
            outputQueue.getOutputQueue().put(processedInterval);
        }
    }

    /**
     * Устанавливает входящую очередь.
     * @param inputQueue входящая очередь
     */
    @Autowired
    public void setInputQueue(InputQueue inputQueue){
        this.inputQueue = inputQueue;
    }

    /**
     * Устанавливает выходящую очередь.
     * @param outputQueue выходящая очередь
     */
    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

    /**
     * @return возвращает выходяющую очередь
     */
    public OutputQueue getOutputQueue() {
        return outputQueue;
    }

    /**
     * Устанавливает формат дат
     * @param dataFormat формат даты
     */
    @Autowired
    public void setDataFormat(SimpleDateFormat dataFormat){
        this.dataFormat = dataFormat;
    }

    /**
     *
     * @return возвращает анализатор доступности
     */
    public RowsAnalyzerAvailability getRowsAnalyzerAvailability() {
        return rowsAnalyzerAvailability;
    }

}
