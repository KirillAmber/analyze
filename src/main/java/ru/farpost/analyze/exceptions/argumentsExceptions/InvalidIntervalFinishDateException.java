package ru.farpost.analyze.exceptions.argumentsExceptions;

/**
 * @author Shelyakin Kirill
 */
public class InvalidIntervalFinishDateException extends Exception {
    private static final String MESSAGE = "Wrong dateF";
    public InvalidIntervalFinishDateException() {
        super(MESSAGE);
    }
    public InvalidIntervalFinishDateException(String message){
        super(message);
    }
}
