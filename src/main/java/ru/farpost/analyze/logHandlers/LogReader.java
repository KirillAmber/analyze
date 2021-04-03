package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.InputQueue;

import java.io.*;
//Класс предназначен для считывание строк из файла и добавление строк в очередь
@Component
public class LogReader extends Thread {
    private  BufferedReader input;
    private InputQueue inputQueue;

    public LogReader(BufferedReader input){
        this.input = input;
    }

    public LogReader(){}

    @Override
    public void run() {
        super.run();
        try {
            read(input);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void read(BufferedReader input) throws InterruptedException {
        String stringLog = "";
        while (true) {
            try {
                if ((stringLog = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(stringLog.isEmpty()) continue;
            inputQueue.getInputQueue().put(stringLog);
        }
    }

    @Autowired
    public void setInputQueue(InputQueue inputQueue){
        this.inputQueue = inputQueue;
    }

    public void setInput(BufferedReader input) {
        this.input = input;
    }
}

