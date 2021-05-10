package ru.farpost.analyze.exceptions.argumentsExceptions;

import java.util.Arrays;

/**
 * Сигнализирует, что аргументы в коммандной строке некорректны
 */
public class ArgumentException extends Exception {
    private static final String MESSAGE = "Wrong arguments";
    public ArgumentException() {
        super(MESSAGE);
    }
    public ArgumentException(String message, String[] args){
        super("Your arguments: " + Arrays.toString(args) + "\n" +
                message);
    }
}
