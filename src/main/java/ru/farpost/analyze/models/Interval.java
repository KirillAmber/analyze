package ru.farpost.analyze.models;


//Модель для вывода интервалов времени с их долей доступности
public class Interval implements Comparable<Interval> {
    private String dataS;
    private String dataF;
    private double percAvailability;

    public Interval(String dataS, String dataF) {
        this.dataS = dataS;
        this.dataF = dataF;
        percAvailability = -1;
    }

    public String getDataS() {
        return dataS;
    }

    public void setDataS(String dataS) {
        this.dataS = dataS;
    }

    public String getDataF() {
        return dataF;
    }

    public void setDataF(String dataF) {
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
                "dataS='" + dataS + '\'' +
                ", dataF='" + dataF + '\'' +
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
