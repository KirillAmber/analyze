package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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

//Данный класс обрабатывает строки из файла
@Component
@Scope("prototype")
public class LogProcessing extends Thread {
    private final RowsAnalyzerAvailability rowsAnalyzerAvailability;
    private final RowsSlicer rowsSlicer;
    private SimpleDateFormat dataFormat;
    private InputQueue inputQueue;
    private OutputQueue outputQueue;

    @Autowired
    public LogProcessing(RowsAnalyzerAvailability rowsAnalyzerAvailability, RowsSlicer rowsSlicer){
        this.rowsAnalyzerAvailability = rowsAnalyzerAvailability;
        this.rowsSlicer = rowsSlicer;
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
    //Проверяет и добавляет несоответсвующий требованиям проблемный интервал
    private void addFailureInterval(ProcessedInterval processedInterval) throws InterruptedException {
        if(processedInterval.getPercAvailability() >= 0){
            outputQueue.getOutputQueue().put(processedInterval);
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

    public OutputQueue getOutputQueue() {
        return outputQueue;
    }

    @Autowired
    public void setDataFormat(SimpleDateFormat dataFormat){
        this.dataFormat = dataFormat;
    }

    public RowsAnalyzerAvailability getRowsAnalyzerAvailability() {
        return rowsAnalyzerAvailability;
    }

}
