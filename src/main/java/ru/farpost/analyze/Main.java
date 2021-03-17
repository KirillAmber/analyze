package ru.farpost.analyze;

import ru.farpost.analyze.exceptions.NoInputException;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.utils.ArgsChecker;
import ru.farpost.analyze.viewControllers.IntervalsOutput;

import java.io.*;

//“ребуетс€ написать алгоритм читающий access-лог и выполн€ющий анализ отказов автоматически.
public class Main {
    //ќчередь предназначена дл€ передачи данных через потоки.

    public static void main(String[] args) throws IOException, ArgumentException, NoInputException {
        ArgsChecker argsChecker = new ArgsChecker();
        if (argsChecker.check(args)) {
            //открытие файла
            BufferedReader input;
            if(argsChecker.getFilename().isEmpty()) {
                 input = new BufferedReader(new InputStreamReader(System.in));
            } else {
                input = new BufferedReader(new FileReader(argsChecker.getFilename()));
            }
            if(!input.ready()){
                throw new NoInputException();
            }

            LogReader logReader = new LogReader(input);
            LogProcessing logProcessing = new LogProcessing(argsChecker.getMinPercAvailability(), argsChecker.getMillisAcceptable());
            IntervalsOutput intervalsOutput = new IntervalsOutput();
            //запуск потоков
            logReader.start();
            logProcessing.start();
            logProcessing.setPriority(6);
            intervalsOutput.start();
            intervalsOutput.setPriority(6);

            while (logReader.isAlive()) {

            }
            logProcessing.setReading(false);
            while (logProcessing.isAlive()) {
            }
            intervalsOutput.setProcessing(false);
        }
    }
}
