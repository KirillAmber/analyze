
package ru.farpost.analyze.models;

import java.util.Date;

/**
 *
 * Временной интервал.
 */
public class Interval implements Comparable<Interval>{
    /**
     * Дата по умолчанию
     */
    public static final Date DEFAULT_DATE = new Date(0);
    /**
     * Дата начала
     */
    private Date dateS;
    /**
     * Дата конца
     */
    private Date dateF;

    /**
     * Так как тип Date - не безопасный устаревший класс и лучше всего здесь было бы
     * использовать LocalTime, используются защищённые копиями.
     * @param dateS Дата начала инетвала
     * @param dateF Дата конца интервала
     */
    public Interval(Date dateS, Date dateF){
        this.dateS = new Date(dateS.getTime());
        this.dateF = new Date(dateF.getTime());
    }

    /**
     * Конструктор по умолчинию.
     */
    public Interval(){
        dateS = new Date(DEFAULT_DATE.getTime());
        dateF = new Date(DEFAULT_DATE.getTime());
    }

    /**
     *
     * @param other Другой интервал
     */
    public Interval(Interval other) {
        this.dateS = new Date(other.dateS.getTime());
        this.dateF = new Date(other.dateF.getTime());
    }

    /**
     *
     * @return возвращает строку this, показывающую начало интервала и конец
     */
    @Override
    public String toString() {
        return "Interval{" +
                "dateS=" + dateS +
                ", dateF=" + dateF +
                '}';
    }

    /**
     * Сравниваются даты начал
     * @param o Объект, с которым сравнивают
     * @return {code true} если даты начала равны, в иных случая  {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return dateS.equals(interval.dateS);
    }

    /**
     * @return Возвращает хеш-код; генерация происходит не с помощью {@code Objects.hashCode(value)}
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31*result + (dateS == null ? 0 : dateS.hashCode());
        result = 31*result + (dateS == null ? 0 : dateF.hashCode());
        return result;
    }

    /**
     * @return Возвращает копию даты начала this, а не сам экземпляр
     */
    public Date getDateS() {
        return new Date(dateS.getTime());
    }
    /**
     * @return Возвращает копию даты конца this, а не сам экземпляр
     */
    public Date getDateF() {
        return new Date(dateF.getTime());
    }
    /**
     * Устанавливает новую дату начала this; установка происходит с помощью защищённой копии
     * @param dateS переменная Даты
     */
    public void setDateS(Date dateS) {
        this.dateS = new Date(dateS.getTime());
    }
    /**
     * Устанавливает новую дату конца this; установка происходит с помощью защищённой копии
     * @param dateF переменная Даты
     */
    public void setDateF(Date dateF) {
        this.dateF = new Date(dateF.getTime());
    }

    /**
     * Сравниваются даты начал интервалов
     * @param o Другой интервал
     * @return возвращает 0, если даты начал равны, больше 0, если дата this
     * позже, чем другой интервал, меньше 0, если дата this раньше, дата другого интервала
     */
    @Override
    public int compareTo(Interval o) {
        return this.getDateS().compareTo(o.getDateS());
    }

}
