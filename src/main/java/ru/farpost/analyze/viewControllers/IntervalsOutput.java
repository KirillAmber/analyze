package ru.farpost.analyze.viewControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.OutputQueue;

import java.text.SimpleDateFormat;

//Этот класс выводит временные интервалы,
// в которых доля отказов системы превышала указанную границу,
// а также уровень доступности.
@Component
public class IntervalsOutput extends Thread {
    private boolean isProcessing;
    private OutputQueue outputQueue;
    private SimpleDateFormat dateFormat;

    public IntervalsOutput(){
        isProcessing = true;
    }

    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

    @Autowired
    public void setDateFormat(SimpleDateFormat dateFormat){
        this.dateFormat = dateFormat;
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
        while(isProcessing){
                Interval interval = outputQueue.getOutputQueue().take();
                System.out.format("%s \t %s \t %.2f\n", dateFormat.format(interval.getDataS()), dateFormat.format(interval.getDataF()), interval.getPercAvailability());

        }
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
        if(!isProcessing){
            interrupt();
        }
    }
}
