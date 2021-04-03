package ru.farpost.analyze;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.farpost.analyze.config.ContextConfig;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.utils.ArgsChecker;
import ru.farpost.analyze.viewControllers.IntervalsOutput;

import java.io.*;
//Требуется написать алгоритм читающий access-лог и выполняющий анализ отказов автоматически.
public class Main {
    public static void main(String[] args) throws IOException, ArgumentException{
        //сначала проверяем аргументы
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ContextConfig.class);
        ArgsChecker argsChecker = ctx.getBean("argsChecker", ArgsChecker.class);

        if (!argsChecker.check(args)) {
            return;
        }
            //открытие файла
            BufferedReader input;
            if(argsChecker.getFilename().isEmpty()) {
                 input = new BufferedReader(new InputStreamReader(System.in));
            } else {
                input = new BufferedReader(new FileReader(argsChecker.getFilename()));
            }
            //инициализация потоков
            LogReader logReader = ctx.getBean("logReader", LogReader.class);
            logReader.setInput(input);

            LogProcessing logProcessing = ctx.getBean("logProcessing", LogProcessing.class);
            logProcessing.getGroupAnalyzerAvailability().setMillisAcceptable(argsChecker.getMillisAcceptable());
            logProcessing.getGroupAnalyzerAvailability().setMinPercAvailability(argsChecker.getMinPercAvailability());

            IntervalsOutput intervalsOutput = ctx.getBean("intervalsOutput", IntervalsOutput.class);
            //запуск потоков
            logReader.start();
            logProcessing.start();
            intervalsOutput.start();
            //последовательное отключение потоков
        try {
            logReader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logProcessing.setReading(false);
        try {
            logProcessing.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intervalsOutput.setProcessing(false);

    }
}
