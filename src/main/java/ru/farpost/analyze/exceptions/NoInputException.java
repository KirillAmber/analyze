package ru.farpost.analyze.exceptions;

public class NoInputException extends Exception {
    private static final String MESSAGE = "No input";
    public NoInputException(){
        super(MESSAGE);
    }
}
