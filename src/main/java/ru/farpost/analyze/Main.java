package ru.farpost.analyze;

import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.utils.ArgsChecker;
import ru.farpost.analyze.viewControllers.IntervalsOutput;

import java.io.*;
import java.util.Scanner;

//Требуется написать алгоритм читающий access-лог и выполняющий анализ отказов автоматически.
public class Main {
    //Очередь предназначена для передачи данных через потоки.

    public static void main(String[] args) throws FileNotFoundException {
        ArgsChecker argsChecker = new ArgsChecker();
        if (argsChecker.check(args)) {
            //запуск потоков
            BufferedReader input;
            if(argsChecker.getFilename().isEmpty()) {
                 input = new BufferedReader(new InputStreamReader(System.in));
            } else {
                input = new BufferedReader(new FileReader(argsChecker.getFilename()));
            }
            LogReader logReader = new LogReader(input);
            LogProcessing logProcessing = new LogProcessing(argsChecker.getMinPercAvailability(), argsChecker.getMillisAcceptable());
            IntervalsOutput intervalsOutput = new IntervalsOutput();
            logReader.start();
            logProcessing.start();
            intervalsOutput.start();

            while (logReader.isAlive()) {

            }
            logProcessing.setReading(false);
            while (logProcessing.isAlive()) {
            }
            intervalsOutput.setProcessing(false);
        }
    }
}
