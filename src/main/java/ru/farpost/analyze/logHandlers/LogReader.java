package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.InputQueue;

import java.io.*;
import java.util.Scanner;

//Класс предназначен для считывание строк из файла и добавление строк в очередь
@Component
public class LogReader extends Thread {
    private BufferedReader input;
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
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void read(BufferedReader input) throws InterruptedException, IOException {
        String stringLog = "";
        while (true) {
            if ((stringLog = input.readLine()) == null) break;
            if(stringLog.isEmpty()) continue;
            inputQueue.getInputQueue().put(stringLog);
        }
        input.close();
    }

    @Autowired
    public void setInputQueue(InputQueue inputQueue){
        this.inputQueue = inputQueue;
    }

    public void setInput(BufferedReader input) {
        this.input = input;
    }
}

