package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.utils.ArgsChecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * Класс, который загружает логи
 */
@Component
public class LogLoader {
    /**
     * Переменная, отвечающая за проверку аргументов консоли
     */
    private final ArgsChecker argsChecker;

    /**
     * @param argsChecker переменная, проверяющая аргументы
     */
    @Autowired
    public LogLoader(ArgsChecker argsChecker){
        this.argsChecker = argsChecker;
    }

    /**
     *
     * @param args аргументы консоли
     * @return возвращает BufferedReader
     * @throws ArgumentException если неправильные аргументы
     * @throws FileNotFoundException если не найден файл
     */
    public BufferedReader load(String[] args) throws ArgumentException, FileNotFoundException {
        argsChecker.check(args);
        BufferedReader input;
        //открытие файла
        if(argsChecker.getFilename().isEmpty()) {
            input = new BufferedReader(new InputStreamReader(System.in));
        } else {
            input = new BufferedReader(new FileReader((argsChecker.getFilename())));
        }
        return input;
    }

    /**
     *
     * @return возвращает переменную, которая проверяет аргументы
     */
    public ArgsChecker getArgsChecker() {
        return argsChecker;
    }
}
