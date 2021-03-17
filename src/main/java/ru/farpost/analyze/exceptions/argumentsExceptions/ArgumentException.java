package ru.farpost.analyze.exceptions.argumentsExceptions;

public class ArgumentException extends Exception {
    private static final String MESSAGE = "Wrong parameters";
    public ArgumentException() {
        super(MESSAGE);
    }
    public ArgumentException(String message){
        super(message);
    }
}
