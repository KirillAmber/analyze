package ru.farpost.analyze.utils;


import org.springframework.stereotype.Service;
import ru.farpost.analyze.exceptions.InvalidIntervalFinishDateException;
import ru.farpost.analyze.models.RawInterval;
import java.util.Date;
import static ru.farpost.analyze.models.Interval.DEFAULT_DATE;

/**
 * Класс, предназначенный для раздивение логов на интервалы.
 */
@Service
public class RowsSlicer {
    /**
     * Необработанный интервал.
     */
    private final RawInterval rawInterval;

    /**
     * {@literal Конструктор по умолчанию.} Присваивает объект RawInterval.
     */
    public RowsSlicer(){
        rawInterval = new RawInterval();
    }

    /**
     * Присваивает даты к переменным DateS и DateF экземпляра класса RawInterval;
     *  интервал сформируется только тогда, когда добавится дата, отличная
     * от первой добавленной даты, иначе повторяющиеся даты будут добавляться в очередь
     * @param date Дата, которую нужно добавить в интервал
     * @throws InvalidIntervalFinishDateException если параметр для даты конца интервала меньше, чем даты началы, то
     * выбрасывается исключение
     */
    public void addDate(Date date) throws InvalidIntervalFinishDateException{
        if (rawInterval.getDateS().equals(DEFAULT_DATE)){
            rawInterval.setDateS(date);
        } else if(rawInterval.getDateS() != date){
            rawInterval.setDateF(date);
        } else if(rawInterval.getDateS().compareTo(date)>0){
            throw new InvalidIntervalFinishDateException(date, rawInterval.getDateS());
        }

    }

    /**
     * Добавляет строку лога в очередь.
     * @param row строка лога
     */
    public void addRow(String row){
        rawInterval.getRowsQueue().add(row);
    }

    /**
     * @return возвращает сформированный необработанный интервал
     */
    public RawInterval slice(){
        RawInterval slicedInterval = new RawInterval(rawInterval);
        rawInterval.getRowsQueue().clear();
        rawInterval.setDateS(DEFAULT_DATE);
        rawInterval.setDateF(DEFAULT_DATE);
        return slicedInterval;
    }

    /**
     * @return возвращает true, если интервал сформирован и false, если ещё нет
     */
    public boolean isReadyForSlicing(){
        return rawInterval.getDateS().compareTo(rawInterval.getDateF()) < 0;
    }

    /**
     * Осторожно, может вернуть несформированный интервал(даты будут по умолчанию) и лучше всего
     * использовать в комбинации с методом {@code isReadyForSlicing}.
     * @return возвращает необработанный интервал
     */
    public RawInterval getRawInterval() {
        return rawInterval;
    }
}
