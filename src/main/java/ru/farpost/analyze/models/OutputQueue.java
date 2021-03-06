package ru.farpost.analyze.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Это синглтон для вывода очереди интервалов в терминал; он принимает из класса LogProcessing обработанные
 * логи и помещает их в виде объектов Interval в очередь данного синглтона.
 */
@Service
@Scope("singleton")
public class OutputQueue {
    /**
     * Блокирующая очередь.
     */
    private final BlockingQueue<ProcessedInterval> outputQueue;

    /**
     * Конструктор по умолчанию; присваивает SynchronousQueue
     */
    public OutputQueue() {
        outputQueue = new SynchronousQueue<>();
    }


    public BlockingQueue<ProcessedInterval> getOutputQueue() {
        return outputQueue;
    }

}
