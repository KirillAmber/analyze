package ru.farpost.analyze;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.farpost.analyze.config.ContextConfig;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.logHandlers.LogLoader;
import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.utils.RowsAnalyzerAvailability;
import ru.farpost.analyze.utils.RowsSlicer;
import ru.farpost.analyze.viewcontrollers.IntervalsOutput;

import java.io.IOException;


//Требуется написать алгоритм читающий access-лог и выполняющий анализ отказов автоматически.
public class Main {
    public static void main(String[] args) throws IOException, ArgumentException{
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(ContextConfig.class);

        LogLoader logLoader = ctx.getBean(LogLoader.class);
        //инициализация потоков
        LogReader logReader = ctx.getBean(LogReader.class, logLoader.load(args));
        LogProcessing logProcessing = ctx.getBean(LogProcessing.class,
                new RowsAnalyzerAvailability(logLoader.getArgsChecker().getMinPercAvailability(), logLoader.getArgsChecker().getMillisAcceptable()), new RowsSlicer());

        IntervalsOutput intervalsOutput = ctx.getBean(IntervalsOutput.class);
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
        logProcessing.interrupt();
        try {
            logProcessing.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intervalsOutput.interrupt();

    }
}
