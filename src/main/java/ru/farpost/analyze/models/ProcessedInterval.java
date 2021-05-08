package ru.farpost.analyze.models;


import java.util.Date;
import java.util.Objects;

//Модель для вывода интервалов времени с их долей доступности

public class ProcessedInterval extends Interval {
    private double percAvailability;

    public ProcessedInterval(Date dataS, Date dataF) {
        super(dataS, dataF);
        percAvailability = -1;
    }
    public ProcessedInterval(Date dataS, Date dataF, double percAvailability) {
        super(dataS, dataF);
        this.percAvailability = percAvailability;
    }

    public ProcessedInterval(ProcessedInterval other) {
        super(other);
        this.percAvailability = other.percAvailability;
    }

    public double getPercAvailability() {
        return percAvailability;
    }

    public void setPercAvailability(double percAvailability) {
        this.percAvailability = percAvailability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessedInterval)) return false;
        if (!super.equals(o)) return false;
        ProcessedInterval that = (ProcessedInterval) o;
        return Double.compare(that.percAvailability, percAvailability) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percAvailability);
    }

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
