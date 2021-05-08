package ru.farpost.analyze.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

//Необработанный интервал имеют исходную строку из лога
public class RawInterval extends Interval {
    private Queue<String> rowsQueue;
    

    public RawInterval(Date dateS, Date dateF, Queue<String> rowsQueue) {
        super(dateS, dateF);
        this.rowsQueue = rowsQueue;
    }

    public RawInterval(){
        super();
        this.rowsQueue = new ArrayDeque<>();
    }

    public RawInterval(RawInterval other) {
        super(other);
        this.rowsQueue = new ArrayDeque<>(other.rowsQueue);
    }

    public Queue<String> getRowsQueue() {
        return rowsQueue;
    }

    public void setRowsQueue(Queue<String> rowsQueue) {
        this.rowsQueue = rowsQueue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RawInterval)) return false;
        if (!super.equals(o)) return false;
        RawInterval that = (RawInterval) o;
        return rowsQueue.equals(that.rowsQueue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rowsQueue);
    }

    @Override
    public String toString() {
        return "RawInterval{" +
                "rowsQueue=" + rowsQueue +
                '}';
    }


}
