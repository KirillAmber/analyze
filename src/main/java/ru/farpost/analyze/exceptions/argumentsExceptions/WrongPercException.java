package ru.farpost.analyze.exceptions.argumentsExceptions;

public class WrongPercException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -u <double> (minimum acceptable level (percentage) of availability)";
    public WrongPercException(){
        super(MESSAGE);
    }
}
