package ru.farpost.analyze.exceptions.argumentsExceptions;

public class WrongMillisException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -t <double> (acceptable response time (milliseconds)) ";
    public WrongMillisException(){
        super(MESSAGE);
    }
}
