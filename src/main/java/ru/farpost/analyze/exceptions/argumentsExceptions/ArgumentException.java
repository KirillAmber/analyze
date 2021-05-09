package ru.farpost.analyze.exceptions.argumentsExceptions;

/**
 * Сигнализирует, что аргументы в коммандной строке некорректны
 */
public class ArgumentException extends Exception {
    private static final String MESSAGE = "Wrong arguments";
    public ArgumentException() {
        super(MESSAGE);
    }
    public ArgumentException(String message){
        super(message);
    }
}
