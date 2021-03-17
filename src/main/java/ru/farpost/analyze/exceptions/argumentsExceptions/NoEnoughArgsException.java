package ru.farpost.analyze.exceptions.argumentsExceptions;

public class NoEnoughArgsException extends ArgumentException {
    private final static String MESSAGE = "You should provide 2 arguments: " +
            "-u <double> (minimum acceptable level (percentage) of availability) " +
            "-t <double> (acceptable response time (milliseconds))";
    public NoEnoughArgsException(){
        super(MESSAGE);
    }
}
