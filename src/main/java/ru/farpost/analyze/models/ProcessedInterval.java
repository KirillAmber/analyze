package ru.farpost.analyze.models;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Date;

//Модель для вывода интервалов времени с их долей доступности

public class ProcessedInterval extends Interval {
    private double percAvailability;

    public ProcessedInterval(Date dataS, Date dataF) {
        super(dataS, dataF);
        percAvailability = -1;
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
        int result = super.hashCode();
        long doubleFieldBits = Double.doubleToLongBits(percAvailability);
        result = 31 * result + (int)(doubleFieldBits ^ (doubleFieldBits >>> 32));
        return result;
    }

}
