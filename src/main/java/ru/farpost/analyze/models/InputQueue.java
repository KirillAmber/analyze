package ru.farpost.analyze.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

//Это синглтон для чтения из файла логов. В него записываются логи и передаются в класс LogProcessing
//для обработки
@Service
@Scope("singleton")
public class InputQueue {

    private final BlockingQueue<String> inputQueue;

    public InputQueue() {
        inputQueue = new SynchronousQueue<>();
    }


    public BlockingQueue<String> getInputQueue() {
        return inputQueue;
    }

}
