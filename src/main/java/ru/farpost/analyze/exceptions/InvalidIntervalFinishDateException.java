package ru.farpost.analyze.exceptions;

import java.util.Date;

/**
 * @author Shelyakin Kirill
 */
public class InvalidIntervalFinishDateException extends Exception {
    private static final String MESSAGE = "Wrong dateF";
    public InvalidIntervalFinishDateException() {
        super(MESSAGE);
    }
    public InvalidIntervalFinishDateException(Date invalidParameter, Date dateS){
        super("parameter for dateF: " + invalidParameter + "\n" +
                "is lower than dateS of rawInterval:" + dateS + "\n" +
                "parameter for dateF must be greater than dateS");
    }
}
