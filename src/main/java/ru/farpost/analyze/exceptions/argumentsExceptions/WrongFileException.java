package ru.farpost.analyze.exceptions.argumentsExceptions;

/**
 * Сигнализует о том, что некорректно задан файл в коммандной строке
 */
public class WrongFileException extends ArgumentException {
    private static final String MESSAGE = "You did not specify a parameter -F <filename> (name of file) ";
    public WrongFileException(){
        super(MESSAGE);
    }
}
