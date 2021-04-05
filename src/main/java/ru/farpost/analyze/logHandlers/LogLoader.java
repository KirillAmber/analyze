package ru.farpost.analyze.logHandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.farpost.analyze.exceptions.argumentsExceptions.ArgumentException;
import ru.farpost.analyze.utils.ArgsChecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

@Component
public class LogLoader {
    private final ArgsChecker argsChecker;

    @Autowired
    public LogLoader(ArgsChecker argsChecker){
        this.argsChecker = argsChecker;
    }

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

    public ArgsChecker getArgsChecker() {
        return argsChecker;
    }
}
