package ru.farpost.analyze.models;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//Это синглтон для чтения из файла логов. В него записываются логи и передаются в класс LogProcessing
//для обработки
public class InputQueueSingleton {

    private static volatile InputQueueSingleton instance;

    private final ConcurrentLinkedQueue inputQueue;

    private InputQueueSingleton() {
        inputQueue = new ConcurrentLinkedQueue();
    }

    public static InputQueueSingleton getInstance() {
        InputQueueSingleton result = instance;
        if (result != null) {
            return result;
        }
        synchronized(InputQueueSingleton.class) {
            if (instance == null) {
                instance = new InputQueueSingleton();
            }
            return instance;
        }
    }

    public Queue<String> getInputQueue() {
        return inputQueue;
    }

}
