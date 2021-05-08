package ru.farpost.analyze.models;

import java.util.Date;

public class Interval implements Comparable<Interval>, Cloneable {
    public static final Date DEFAULT_DATE = new Date(0);
    private Date dateS;
    private Date dateF;
    /*
        так как Date - небезопасный, устаревший класс и лучше всего здесь было бы
        использовать LocalTime, но можно обойтись и защищёнными копиями.
     */
    public Interval(Date dateS, Date dateF){
        this.dateS = new Date(dateS.getTime());
        this.dateF = new Date(dateF.getTime());
    }

    public Interval(){
        dateS = new Date(DEFAULT_DATE.getTime());
        dateF = new Date(DEFAULT_DATE.getTime());
    }

    public Interval(Interval other) {
        this.dateS = new Date(other.dateS.getTime());
        this.dateF = new Date(other.dateF.getTime());
    }


    @Override
    public String toString() {
        return "Interval{" +
                "dateS=" + dateS +
                ", dateF=" + dateF +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return dateS.equals(interval.dateS) &&
                dateF.equals(interval.dateF);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + (dateS == null ? 0 : dateS.hashCode());
        result = 31*result + (dateS == null ? 0 : dateF.hashCode());
        return result;
    }

    public Date getDateS() {
        return dateS;
    }

    public void setDateS(Date dateS) {
        this.dateS = dateS;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    @Override
    public int compareTo(Interval o) {
        return this.getDateS().compareTo(o.getDateS());
    }

}
