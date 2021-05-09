package ru.farpost.analyze.exceptions.argumentsExceptions;

/**
 * Сигнализирует о том, что некорректно задан аргумент минимального допустимого уровеня доступности в процентах
 */
public class WrongPercException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -u <double> (minimum acceptable level (percentage) of availability)";
    public WrongPercException(){
        super(MESSAGE);
    }
}
