package ru.farpost.analyze.exceptions.argumentsExceptions;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Сигнализирует о том, что некорректно задан аргумент минимального допустимого уровеня доступности в процентах
 */
public class WrongPercException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -u <double> (minimum acceptable level (percentage) of availability)";
    public WrongPercException(String[] args){
        super(MESSAGE, args);
    }
}
