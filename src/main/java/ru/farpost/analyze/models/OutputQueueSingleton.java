package ru.farpost.analyze.models;

import java.util.concurrent.ConcurrentLinkedQueue;

//Это синглтон для вывода очереди интервалов в терминал. Он принимает из класса LogProcessing обработанные
//логи и помещает их в виде объектов Interval в очередь данного синглтона.
public class OutputQueueSingleton {

    private static volatile OutputQueueSingleton instance;

    public ConcurrentLinkedQueue<Interval> outputQueue;

    private OutputQueueSingleton() {
        outputQueue = new ConcurrentLinkedQueue<>();
    }

    public static OutputQueueSingleton getInstance() {
        OutputQueueSingleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized(OutputQueueSingleton.class) {
            if (instance == null) {
                instance = new OutputQueueSingleton();
            }
            return instance;
        }
    }

    public ConcurrentLinkedQueue<Interval> getOutputQueue() {
        return outputQueue;
    }

}
