package ru.farpost.analyze.logHandlers;

import ru.farpost.analyze.models.InputQueueSingleton;

import java.io.*;
//����� ������������ ��� ���������� ����� �� ����� � ���������� ����� � �������
public class LogReader extends Thread {
    private final BufferedReader input;

    public LogReader(BufferedReader input){
        this.input = input;
    }

    @Override
    public void run() {
        super.run();
        read(input);
    }

    private void read(BufferedReader input) {
        String stringLog = "";
        while (true) {
            try {
                if ((stringLog = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(stringLog.isEmpty()) continue;
            InputQueueSingleton.getInstance().getInputQueue().add(stringLog);
        }
    }
}

