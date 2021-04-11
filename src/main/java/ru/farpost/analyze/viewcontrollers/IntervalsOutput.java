package ru.farpost.analyze.viewcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.OutputQueue;

import java.text.SimpleDateFormat;

//Этот класс выводит временные интервалы,
// в которых доля отказов системы превышала указанную границу,
// а также уровень доступности.
@Component
@Scope("prototype")
public class IntervalsOutput extends Thread {
    private OutputQueue outputQueue;
    private SimpleDateFormat dateFormat;
    private ProcessedInterval processedInterval;

    public IntervalsOutput(){
        processedInterval = null;
    }

    @Override
    public void run() {
        super.run();
        try {
            display();
        } catch (InterruptedException e) {
        }
    }

    private void display() throws InterruptedException {
        while(true){
                processedInterval = outputQueue.getOutputQueue().take();
                synchronized (dateFormat){
                    System.out.format("%s \t %s \t %.2f\n", dateFormat.format(processedInterval.getDateS()), dateFormat.format(processedInterval.getDateF()), processedInterval.getPercAvailability());
                }

        }
    }
    @Autowired
    public void setDateFormat(SimpleDateFormat dateFormat){
        this.dateFormat = dateFormat;
    }
    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

}
