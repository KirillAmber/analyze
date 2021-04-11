package ru.farpost.analyze.utils;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.farpost.analyze.models.Interval;
import ru.farpost.analyze.models.RawInterval;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

import static ru.farpost.analyze.models.Interval.DEFAULT_DATE;

@Service
public class RowsSlicer {
    private RawInterval rawInterval;

    public RowsSlicer(){
        rawInterval = new RawInterval();
    }

    public void addDate(Date date){
        if (rawInterval.getDateS().equals(DEFAULT_DATE)){
            rawInterval.setDateS(date);
        }
        if(rawInterval.getDateS() != date){
            rawInterval.setDateF(date);
        }
    }
    public void addRow(String row){
        rawInterval.getRowsQueue().add(row);
    }

    //вырезает текущую очередь (интервал) и возвращает её
    public RawInterval slice(){
        RawInterval slicedInterval = new RawInterval(rawInterval);
        rawInterval.getRowsQueue().clear();
        rawInterval.setDateS(DEFAULT_DATE);
        rawInterval.setDateF(DEFAULT_DATE);
        return slicedInterval;
    }

    public boolean isReadyForSlicing(){
        return rawInterval.getDateS().compareTo(rawInterval.getDateF()) < 0;
    }

    public RawInterval getRawInterval() {
        return rawInterval;
    }
}
