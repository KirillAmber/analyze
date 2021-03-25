package ru.farpost.analyze.models;


import java.util.Date;

//Модель для вывода интервалов времени с их долей доступности
public class Interval implements Comparable<Interval> {
    private Date dataS;
    private Date dataF;
    private double percAvailability;

    public Interval(Date dataS, Date dataF) {
        this.dataS = dataS;
        this.dataF = dataF;
        percAvailability = -1;
    }

    public Date getDataS() {
        return dataS;
    }

    public void setDataS(Date dataS) {
        this.dataS = dataS;
    }

    public Date getDataF() {
        return dataF;
    }

    public void setDataF(Date dataF) {
        this.dataF = dataF;
    }

    public double getPercAvailability() {
        return percAvailability;
    }

    public void setPercAvailability(double percAvailability) {
        this.percAvailability = percAvailability;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "dataS=" + dataS +
                ", dataF=" + dataF +
                ", percAvailability=" + percAvailability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return dataS.equals(interval.dataS) &&
                dataF.equals(interval.dataF);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + (dataS == null ? 0 : dataS.hashCode());
        result = 31*result + (dataS == null ? 0 : dataF.hashCode());
        return result;
    }


    @Override
    public int compareTo(Interval o) {
        return this.getDataS().compareTo(o.getDataS());
    }
}
