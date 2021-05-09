package ru.farpost.analyze.models;


import java.util.*;

/**
 * Необратоанный интервал.
 */
public class RawInterval extends Interval {
    /**
     * Очередь, содержащая необработанные строки.
     */
    private Queue<String> rowsQueue;

    /**
     *  использует конструктор родительского класса
     * @param dateS Дата начала
     * @param dateF Дата конца
     * @param rowsQueue Очередь необработанных строк
     */
    public RawInterval(Date dateS, Date dateF, Queue<String> rowsQueue) {
        super(dateS, dateF);
        this.rowsQueue = rowsQueue;
    }

    /**
     * Конструктор по умолчанию.
     */
    public RawInterval(){
        super();
        this.rowsQueue = new ArrayDeque<>();
    }

    /**
     * Копирующий конструктор.
     * @param other другой RawInterval
     */
    public RawInterval(RawInterval other) {
        super(other);
        this.rowsQueue = new ArrayDeque<>(other.rowsQueue);
    }

    /**
     *
     * @return возвращает очередь необработанных строк
     */
    public Queue<String> getRowsQueue() {
        return rowsQueue;
    }

    /**
     *
     * @param rowsQueue очередь необработанные строк
     */
    public void setRowsQueue(Queue<String> rowsQueue) {
        this.rowsQueue = rowsQueue;
    }

    /**
     *  использует {@code equals(Object o)} родителя Interval и возвращает
     * false или true перед проверки очереди
     * @param o Объект, с которым сравнивают
     * @return возвращает результат сравнения дат и очереди строк
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawInterval)) return false;
        if (!super.equals(o)) return false;

        RawInterval that = (RawInterval) o;

        return rowsQueue.equals(that.rowsQueue);
    }

    /**
     *  использует также {@code hashCode()} родителя
     * @return возвращает хеш-кода this, используя {@code Objects.hash()}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rowsQueue);
    }

    /**
     *  использует также {@code toString()} родителя
     * @return возвращает строковое представление this с очередью
     */
    @Override
    public String toString() {
        return "RawInterval{" +
                "rowsQueue=" + rowsQueue +
                "} " + super.toString();
    }
}
