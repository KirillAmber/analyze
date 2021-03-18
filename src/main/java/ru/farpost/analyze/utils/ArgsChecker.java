package ru.farpost.analyze.utils;

import ru.farpost.analyze.exceptions.argumentsExceptions.*;

import java.util.Scanner;
/*�����, ������� ������������ ��� �������� ���������� �����
    ������� � ����������� �����:
    *���������� ������������ ����� ������ "cat access.log | java -jar analyze -u 99.9 -t 45"
        � ��� ���������
    *��� ������������ ��� ������ ���������� ��������� �������� � �������� � �����������
    *������������ ����� ������ ��� ��� ��������� � ������ �������
    *����� ������������ ���� ���� ������ ����� �������
    *��� ����� ��� ������������, ��� � ��� ������������ ����� ��������� �������������
        � ���������� -f <filename> � ��������� ��������� ���� �� ���������
 */
public class ArgsChecker {
    //���������� ���������� ������� �����������
    private double minPercAvailability;
    //���������� ����� ������
    private double  millisAcceptable;
    //
    private String filename;

    public ArgsChecker(){
        minPercAvailability = 0;
        millisAcceptable = 0;
        filename = "";
    }
    //�������� ����������
    public boolean check(String [] args) throws ArgumentException {
        if (args.length == 0) { // ���� � ������� �������������� �����
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the name of the file (The file must be in the same directory," +
                    " as jar file):");
            filename = in.next();
            System.out.println("Enter minimum acceptable level (percentage) of availability:");
            minPercAvailability = Double.parseDouble(in.next());
            System.out.println("Enter acceptable response time (milliseconds):");
            millisAcceptable = Double.parseDouble(in.next());
        } else if (args.length == 6) { // ���� � 3 ���������� (�������� ����� �����)
            checkMinPercAvailability(args);
            checkMillisAcceptable(args);
            checkFilename(args);
        } else if (args.length != 4) {
            throw new NoEnoughArgsException();
        } else {
            checkMinPercAvailability(args);
            checkMillisAcceptable(args);
        }
        return true;
    }

    private boolean checkMinPercAvailability(String [] args) throws WrongPercException {
            if (args[0].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[1]);
            } else if (args[2].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[3]);
            } else if (args[4].equals("-u")){
                minPercAvailability = Double.parseDouble(args[5]);
            } else {
                throw new WrongPercException();
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
            throw new WrongMillisException();
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
                throw new WrongFileException();
            }
        return true;
    }
        public double getMinPercAvailability () {
            return minPercAvailability;
        }

        public double getMillisAcceptable () {
            return millisAcceptable;
        }

        public String getFilename(){
            return filename;
        }
}
