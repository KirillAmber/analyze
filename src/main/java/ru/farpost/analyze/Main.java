package ru.farpost.analyze;

import ru.farpost.analyze.logHandlers.LogProcessing;
import ru.farpost.analyze.logHandlers.LogReader;
import ru.farpost.analyze.viewControllers.IntervalsOutput;

import java.util.Scanner;

//��������� �������� �������� �������� access-��� � ����������� ������ ������� �������������.
public class Main {
    //������� ������������� ��� �������� ������ ����� ������.
    //���������� ���������� ������� �����������
    public static double minPercAvailability;
    //���������� ����� ������
    public static double millisAcceptable;
    public static void main(String[] args) {
        //�������� ����������
        if(args.length == 0){
            System.out.println("Enter minimum acceptable level (percentage) of availability:");
            Scanner in = new Scanner(System.in);
            minPercAvailability = in.nextDouble();
            System.out.println("Enter acceptable response time (milliseconds):");
            millisAcceptable = in.nextDouble();
        }
        else if(args.length != 4) {
            System.err.println("You should provide 2 arguments: " +
                    "-u <double> (minimum acceptable level (percentage) of availability) " +
                    "-t <double> (acceptable response time (milliseconds))");
            System.exit(-1);
        } else {
            try {
                minPercAvailability = Double.parseDouble(args[1]);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("ERROR! You entered either a blank or an incorrect percentage");
                System.exit(-1);
            }

            try {
                millisAcceptable = Double.parseDouble(args[3]);
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("ERROR! You entered either a blank or an incorrect millisAcceptable");
                System.exit(-1);
            }
        }
        //������ �������
        LogReader logReader = new LogReader();
        LogProcessing logProcessing = new LogProcessing(minPercAvailability, millisAcceptable);
        IntervalsOutput intervalsOutput = new IntervalsOutput();
        logReader.start();
        logProcessing.start();
        intervalsOutput.start();

        while (logReader.isAlive()){
            /*
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace(); //�������, ����� ���� ��������
            }

             */
        }
        logProcessing.setReading(false);
        while (logProcessing.isAlive()){
            /*
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace(); // �������, ����� ���� ��������
            }
             */
        }
        intervalsOutput.setProcessing(false);
    }
}
