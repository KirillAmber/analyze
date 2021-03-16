package ru.farpost.analyze.models;

import java.util.concurrent.ConcurrentLinkedQueue;

//��� �������� ��� ������ ������� ���������� � ��������. �� ��������� �� ������ LogProcessing ������������
//���� � �������� �� � ���� �������� Interval � ������� ������� ���������.
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
