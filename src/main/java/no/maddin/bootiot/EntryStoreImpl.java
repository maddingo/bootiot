package no.maddin.bootiot;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;


public class EntryStoreImpl implements EntryStore {

    private final Deque<BootMeasureEntry> list = new LinkedList<>();

    @Autowired
    private JsonParser jsonParser;

    @Override
    public Collection<BootMeasureEntry> getAll() {
        return list;
    }

    @Override
    public void saveJson(String json) {
        Map<String, Object> map = jsonParser.parseMap(json);

        synchronized(list) {
            Integer counter = (Integer) map.get("counter");
            Double temp = (Double) map.get("temp");
            Double hum = (Double) map.get("hum");
            Double water = (Double) map.get("water");
            Double batt = (Double) map.get("batt");

            list.addFirst(new BootMeasureEntry(counter == null ? list.size() : counter, temp, hum, water, batt, new Date()));
        }
    }
}
