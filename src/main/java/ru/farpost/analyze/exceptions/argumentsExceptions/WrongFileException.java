package ru.farpost.analyze.exceptions.argumentsExceptions;

public class WrongFileException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -F <filename> (name of file) ";
    public WrongFileException(){
        super(MESSAGE);
    }
}
