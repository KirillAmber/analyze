package ru.farpost.analyze.logHandlers;

import ru.farpost.analyze.models.InputQueueSingleton;

import java.io.*;
import java.util.Scanner;

public class LogReader extends Thread {
    private BufferedReader input;

    public LogReader(BufferedReader input){
        this.input = input;
    }

    @Override
    public void run() {
        super.run();
        read(input);
    }

    private void read(BufferedReader input) {
        String stringLog = null;
        while (true) {
            try {
                if ((stringLog = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputQueueSingleton.getInstance().getInputQueue().add(stringLog);
        }
    }
}

