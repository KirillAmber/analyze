package ru.farpost.analyze.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//Это синглтон для чтения из файла логов. В него записываются логи и передаются в класс LogProcessing
//для обработки
@Service
@Scope("singleton")
public class InputQueue {

    private final ConcurrentLinkedQueue<String> inputQueue;

    public InputQueue() {
        inputQueue = new ConcurrentLinkedQueue<String>();
    }


    public Queue<String> getInputQueue() {
        return inputQueue;
    }

}
