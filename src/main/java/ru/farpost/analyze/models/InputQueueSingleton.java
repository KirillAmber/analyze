package ru.farpost.analyze.models;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

//��� �������� ��� ������ �� ����� �����. � ���� ������������ ���� � ���������� � ����� LogProcessing
//��� ���������
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
