package ru.farpost.analyze.logHandlers;

import ru.farpost.analyze.models.InputQueueSingleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LogReader extends Thread {

    public LogReader(){

    }

    @Override
    public void run() {
        super.run();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String stringLog = null;
        while(true) {
            try {
                if ((stringLog = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputQueueSingleton.getInstance().inputQueue.add(stringLog);
        }
    }
}

