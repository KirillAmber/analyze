package ru.farpost.analyze.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.SynchronousQueue;

//Это синглтон для чтения из файла логов. В него записываются логи и передаются в класс LogProcessing
//для обработки
@Service
@Scope("singleton")
public class InputQueue {

    private final SynchronousQueue<String> inputQueue;

    public InputQueue() {
        inputQueue = new SynchronousQueue<>();
    }


    public SynchronousQueue<String> getInputQueue() {
        return inputQueue;
    }

}
