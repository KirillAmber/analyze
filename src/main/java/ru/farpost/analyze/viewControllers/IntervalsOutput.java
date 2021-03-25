package ru.farpost.analyze.viewControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.OutputQueue;
//Этот класс выводит временные интервалы,
// в которых доля отказов системы превышала указанную границу,
// а также уровень доступности.
@Component
public class IntervalsOutput extends Thread {
    private boolean isProcessing;
    private OutputQueue outputQueue;

    public IntervalsOutput(){
        isProcessing = true;
    }

    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        super.run();
        display();
    }

    private void display(){
        while(isProcessing || outputQueue.getOutputQueue().size() > 0){
            Interval interval;
            if(outputQueue.getOutputQueue().peek()!=null) {
                interval = outputQueue.getOutputQueue().poll();
                System.out.format("%s \t %s \t %.2f\n", interval.getDataS(), interval.getDataF(), interval.getPercAvailability());
            }
        }
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
    }
}
