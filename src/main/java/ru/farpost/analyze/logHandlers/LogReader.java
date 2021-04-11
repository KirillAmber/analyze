package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.InputQueue;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;

//Класс предназначен для считывание строк из файла и добавление строк в очередь
@Component
@Scope("prototype")
public class LogReader extends Thread {
    private BufferedReader input;
    private InputQueue inputQueue;

    @Autowired(required = false)
    public LogReader(BufferedReader input){
        this.input = input;
    }

    public LogReader (){}

    @Override
    public void run() {
        super.run();
        try {
            read();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void read() throws InterruptedException, IOException {
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

