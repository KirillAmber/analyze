package ru.farpost.analyze.utils;

import org.springframework.stereotype.Service;
import ru.farpost.analyze.exceptions.argumentsExceptions.*;

import java.util.Scanner;

/**
 * Класс, который предназначен для проверки аргументов ввода;
 * Правила и возможности ввода:
 * <ul>
 *      <li>
 *          Стандартно пользователь может ввести "cat access.log | java -jar analyze -u 99.9 -t 45"
 *         и это сработает;
 *      </li>
 *      <li>
 *          При неправильных или пустых аргументах программа сообщает о проблема и завершается;
 *      </li>
 *      <li>
 *          Пользователь может ввести все эти параметры в разном порядке;
 *      </li>
 *      <li>
 *          Также предусмотрен ввод всех данных через консоль;
 *      </li>
 *      <li>
 *          Как опция для пользователя, так и для тестирования можно прописать дополнительно
 *         {@literal в аргументах -f <filename> и программа прочитает файл из аргумента}.
 *      </li>
 * </ul>
 */
@Service
public class ArgsChecker {
    /**
     * Минимальный допустимый уровень доступности в процентах.
     */
    private double minPercAvailability;
    /**
     * Приемлемое время ответа в миллисекундах.
     */
    private double millisAcceptable;
    /**
     * Название файла.
     */
    private String filename;

    /**
     * Конструктор по умолчанию; присваивает minPercAvailability и millisAcceptable 0,
     * а filename пустую строку.
     */
    public ArgsChecker(){
        minPercAvailability = 0;
        millisAcceptable = 0;
        filename = "";
    }

    /**
     * {@literal Проверяет аргументы.} Если аргументы неправильные, то выбрасывает соответствующие исключение.
     * @param args аргументы коммандной строки
     * @throws ArgumentException при неправильных аргументах;
     * выбрасывает также исключения потомков ArgumentException
     */
    public void check(String [] args) throws ArgumentException {
        if (args.length == 0) { // ввод с помощью интерактивного ввода
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the name of the file (The file must be in the same directory," +
                    " as jar file):");
            filename = in.next();
            System.out.println("Enter minimum acceptable level (percentage) of availability:");
            minPercAvailability = Double.parseDouble(in.next());
            System.out.println("Enter acceptable response time (milliseconds):");
            millisAcceptable = Double.parseDouble(in.next());
        } else if (args.length == 6) { // ввод с 3 аргументом (названия имени файла)
            checkMinPercAvailability(args);
            checkMillisAcceptable(args);
            checkFilename(args);
        } else if (args.length != 4) {
            throw new NoEnoughArgsException(args);
        } else {
            checkMinPercAvailability(args);
            checkMillisAcceptable(args);
        }
    }

    private boolean checkMinPercAvailability(String [] args) throws WrongPercException {
            if (args[0].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[1]);
            } else if (args[2].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[3]);
            } else if (args[4].equals("-u")){
                minPercAvailability = Double.parseDouble(args[5]);
            } else {
                throw new WrongPercException(args);
            }
        return true;
    }
    private boolean checkMillisAcceptable(String [] args) throws NullPointerException, NumberFormatException, WrongMillisException {
        if (args[0].equals("-t")) {
            millisAcceptable = Double.parseDouble(args[1]);
        } else if (args[2].equals("-t")) {
            millisAcceptable = Double.parseDouble(args[3]);
        } else if (args[4].equals("-t")) {
            millisAcceptable = Double.parseDouble(args[5]);
        } else {
            throw new WrongMillisException(args);
        }
        return true;
    }
    private boolean checkFilename(String [] args) throws WrongFileException {
            if (args[0].equals("-f")) {
                filename = args[1];
            } else if (args[2].equals("-f")) {
                filename = args[3];
            } else if (args[4].equals("-f")){
                filename = args[5];
            } else {
                throw new WrongFileException(args);
            }
        return true;
    }

    /**
     * @return возвращает Минимальный допустимый уровень доступности в процентах
     */
    public double getMinPercAvailability () {
            return minPercAvailability;
        }
    /**
     * @return возвращает приемлемое время ответа в миллисекундах
     */
    public double getMillisAcceptable () {
            return millisAcceptable;
        }

    /**
     * @return возвращает имя файла
     */
    public String getFilename(){
            return filename;
        }
}
