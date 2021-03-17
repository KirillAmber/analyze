package ru.farpost.analyze.logHandlers;

import ru.farpost.analyze.models.InputQueueSingleton;

import java.io.*;
import java.util.Scanner;

public class LogReader extends Thread {

    public LogReader(){

    }

    @Override
    public void run() {
        super.run();
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try {
            if(!input.ready()){
                System.out.println("¬ведите название файла (‘айл должен быть в том же каталоге," +
                        " что и .jar:");
                Scanner in = new Scanner(System.in);
                String fileName = in.next();
                File logFile = new File(fileName);
                System.out.println(logFile.getAbsolutePath());

                input = new BufferedReader(new FileReader(logFile));
            }
            read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void read(BufferedReader input) {
        String stringLog = null;
        while (true) {
            try {
                if ((stringLog = input.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputQueueSingleton.getInstance().inputQueue.add(stringLog);
        }
    }
}

