package ru.farpost.analyze.viewcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.ProcessedInterval;
import ru.farpost.analyze.models.OutputQueue;

import java.text.SimpleDateFormat;

/**
 * Этот класс предназначен для вывода в консоль время интервалов,
 * в которых доля отказов системы превышала указанную границу,
 * а также их уровень доступности.
 */
@Component
@Scope("prototype")
public class IntervalsOutput extends Thread {
    /**
     * Очередь для вывода интервалов.
     */
    private OutputQueue outputQueue;
    /**
     * Переменная для вычлинение из типа Date время в чч:мм:сс.
     */
    private SimpleDateFormat dateFormat;
    /**
     * Обработанный интервал.
     */
    private ProcessedInterval processedInterval;

    /**
     * Конструктор по умолчанию, который присваивает processedInerval null.
     */
    public IntervalsOutput(){
        processedInterval = null;
    }

    /**
     * Запускает поток.
     */
    @Override
    public void run() {
        super.run();
        try {
            display();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Выводит интервалы в консоль.
     * @throws InterruptedException если прерван поток
     */
    private void display() throws InterruptedException {
        while(true){
                processedInterval = outputQueue.getOutputQueue().take();
                synchronized (dateFormat){
                    System.out.format("%s \t %s \t %.2f\n", dateFormat.format(processedInterval.getDateS()), dateFormat.format(processedInterval.getDateF()), processedInterval.getPercAvailability());
                }

        }
    }

    /**
     * @param dateFormat формат для даты
     */
    @Autowired
    public void setDateFormat(SimpleDateFormat dateFormat){
        this.dateFormat = dateFormat;
    }

    /**
     * @param outputQueue очередь с обработанными интервалами для вывода в консоль
     */
    @Autowired
    public void setOutputQueue(OutputQueue outputQueue){
        this.outputQueue = outputQueue;
    }

}
