package ru.farpost.analyze;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.farpost.analyze.config.ContextConfig;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.logHandlers.LogLoader;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.viewControllers.IntervalsOutput;

import java.io.IOException;


//Требуется написать алгоритм читающий access-лог и выполняющий анализ отказов автоматически.
public class Main {
    public static void main(String[] args) throws IOException, ArgumentException{
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ContextConfig.class);

        LogLoader logLoader = ctx.getBean(LogLoader.class);
        //инициализация потоков
        LogReader logReader = ctx.getBean("logReader", LogReader.class);
        logReader.setInput(logLoader.load(args));

        LogProcessing logProcessing = ctx.getBean("logProcessing", LogProcessing.class);
        logProcessing.getGroupAnalyzerAvailability().setMillisAcceptable(logLoader.getArgsChecker()
                .getMillisAcceptable());
        logProcessing.getGroupAnalyzerAvailability().setMinPercAvailability(logLoader.getArgsChecker()
                .getMinPercAvailability());

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
