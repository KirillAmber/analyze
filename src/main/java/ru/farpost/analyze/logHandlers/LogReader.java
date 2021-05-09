package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.models.InputQueue;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс предназначен для считывание строк из файла и добавление строк в очередь
 */
@Component
@Scope("prototype")
public class LogReader extends Thread {
    /**
     * переменная, из которой считываются строки из файла
     */
    private BufferedReader input;
    /**
     * Входящая очередь, в которую записываются строки из файла
     */
    private InputQueue inputQueue;

    /**
     * @param input откуда считываются строкиз из файла
     */
    @Autowired(required = false)
    public LogReader(BufferedReader input){
        this.input = input;
    }

    /**
     * Пустой конструктор по умолчанию
     */
    public LogReader (){}

    /**
     * Запускает поток
     */
    @Override
    public void run() {
        super.run();
        try {
            read();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Считывает строки
     * @throws InterruptedException если поток прерван
     * @throws IOException
     */
    private void read() throws InterruptedException, IOException {
        String stringLog = "";
        while (true) {
            if ((stringLog = input.readLine()) == null) break;
            if(stringLog.isEmpty()) continue;
            inputQueue.getInputQueue().put(stringLog);
        }
        input.close();
    }

    /**
     * Устанавливает входящую очередь
     * @param inputQueue входящая очередь
     */
    @Autowired
    public void setInputQueue(InputQueue inputQueue){
        this.inputQueue = inputQueue;
    }

    /**
     * Устанавливает переменную, откуда считываются строки из файла
     * @param input откуда, считываются строки из файла
     */
    public void setInput(BufferedReader input) {
        this.input = input;
    }
}

