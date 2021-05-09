package ru.farpost.analyze.models;


import java.util.Date;
import java.util.Objects;

/**
 * Обработанный интервал с уровнем доступностью.
 */
public class ProcessedInterval extends Interval {
    /**
     * Процент доступности.
     */
    private double percAvailability;

    /**
     * Вызывает конструктор родительского класса; ставит percAvailability равным -1
     * @param dataS дата начала интервала
     * @param dataF дата конца интервала
     */
    public ProcessedInterval(Date dataS, Date dataF) {
        super(dataS, dataF);
        percAvailability = -1;
    }

    /**
     * Вызывает конструктор родительского класса
     * @param dataS дата начала интервала
     * @param dataF дата конца интервала
     * @param percAvailability процент доступности
     */
    public ProcessedInterval(Date dataS, Date dataF, double percAvailability) {
        super(dataS, dataF);
        this.percAvailability = percAvailability;
    }

    /**
     * Копирующий конструктор.
     * @param other другой ProcessedInterval
     */
    public ProcessedInterval(ProcessedInterval other) {
        super(other);
        this.percAvailability = other.percAvailability;
    }

    /**
     *
     * @return возвращает процент доступности
     */
    public double getPercAvailability() {
        return percAvailability;
    }

    /**
     *
     * @param percAvailability устанавливает процент доступности
     */
    public void setPercAvailability(double percAvailability) {
        this.percAvailability = percAvailability;
    }

    /**
     * Вызывает {@code equals(Object o)} родителя Interval и возращает
     * false или true перед проверкой доступности
     * @param o Объект, с которым сравнивают
     * @return возвращает результат сравнения дат и процента доступности
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessedInterval)) return false;
        if (!super.equals(o)) return false;
        ProcessedInterval that = (ProcessedInterval) o;
        return Double.compare(that.percAvailability, percAvailability) == 0;
    }

    /**
     *  использует также {@code hashCode()} родителя
     * @return возвращает хеш-кода this, используя {@code Objects.hash()}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percAvailability);
    }

    /**
     *  использует, в первую очередь, {@code compareTo(Object o)} родителя
     * @param o Другой интервал
     * @return возвращает 0, если интервалы равны, меньше 0, если интервал раньше или имеет
     * меньше процент доступности, больше 0, если интервал позже или имеет больше процент
     * доступности
     */
    @Override
    public int compareTo(Interval o) {
        int result = super.compareTo(o);
        if(result == 0){
            if(o instanceof ProcessedInterval) {
                return Double.compare(this.percAvailability, ((ProcessedInterval) o).percAvailability);
            }
        }
        return result;
    }
}
