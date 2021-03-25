package ru.farpost.analyze.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;

//Это синглтон для вывода очереди интервалов в терминал. Он принимает из класса LogProcessing обработанные
//логи и помещает их в виде объектов Interval в очередь данного синглтона.
@Service
@Scope("singleton")
public class OutputQueue {


    private final ConcurrentLinkedQueue<Interval> outputQueue;

    public OutputQueue() {
        outputQueue = new ConcurrentLinkedQueue<>();
    }


    public ConcurrentLinkedQueue<Interval> getOutputQueue() {
        return outputQueue;
    }

}
