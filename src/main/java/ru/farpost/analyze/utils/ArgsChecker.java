package ru.farpost.analyze.utils;

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
    public boolean check(String [] args) {
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
            System.err.println("You should provide 2 arguments: " +
                    "-u <double> (minimum acceptable level (percentage) of availability) " +
                    "-t <double> (acceptable response time (milliseconds))");
            System.exit(-1);
        } else {
            checkMinPercAvailability(args);
            checkMillisAcceptable(args);

        }
        return true;
    }

    private boolean checkMinPercAvailability(String [] args){
        try {
            if (args[0].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[1]);
            } else if (args[2].equals("-u")) {
                minPercAvailability = Double.parseDouble(args[3]);
            } else if (args[4].equals("-u")){
                minPercAvailability = Double.parseDouble(args[5]);
            } else {
                System.out.println("You did not specify a parameter -u <double> (minimum acceptable level (percentage) of availability) ");
                System.exit(-1);
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("ERROR! You entered either a blank or an incorrect percentage");
            System.exit(-1);
        }
        return true;
    }
    private boolean checkMillisAcceptable(String [] args){
        try {
            if (args[0].equals("-t")) {
                millisAcceptable = Double.parseDouble(args[1]);
            } else if (args[2].equals("-t")) {
                millisAcceptable = Double.parseDouble(args[3]);
            } else if(args[4].equals("-t")){
                millisAcceptable = Double.parseDouble(args[5]);
            }  else {
            System.out.println("You did not specify a parameter -t <double> (acceptable response time (milliseconds)) ");
            System.exit(-1);
        }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("ERROR! You entered either a blank or an incorrect millisAcceptable");
            System.exit(-1);
        }
        return true;
    }
    private boolean checkFilename(String [] args){
        try {
            if (args[0].equals("-f")) {
                filename = args[1];
            } else if (args[2].equals("-f")) {
                filename = args[3];
            } else if (args[4].equals("-f")){
                filename = args[5];
            } else {
                System.out.println("You did not specify a parameter -f <filename> (name of file)");
                System.exit(-1);
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("ERROR! You incorrect filename");
            System.exit(-1);
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
