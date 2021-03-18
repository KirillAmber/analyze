package ru.farpost.analyze.viewControllers;

import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.OutputQueueSingleton;
//Этот класс выводит временные интервалы,
// в которых доля отказов системы превышала указанную границу,
// а также уровень доступности.
public class IntervalsOutput extends Thread {
    private boolean isProcessing;

    public IntervalsOutput(){
        isProcessing = true;
    }


    @Override
    public void run() {
        super.run();
        display();
    }

    private void display(){
        while(isProcessing || OutputQueueSingleton.getInstance().getOutputQueue().size() > 0){
            Interval interval;
            if(OutputQueueSingleton.getInstance().outputQueue.peek()!=null) {
                interval = OutputQueueSingleton.getInstance().outputQueue.poll();
                System.out.format("%s \t %s \t %.2f\n", interval.getDataS(), interval.getDataF(), interval.getPercAvailability());
            }
        }
    }

    public void setProcessing(boolean processing) {
        isProcessing = processing;
    }
}
