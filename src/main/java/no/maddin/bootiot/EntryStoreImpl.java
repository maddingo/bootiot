package no.maddin.bootiot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Map;


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
            int counter = list.size() + 1;
            Double temp = (Double) map.get("temp");
            Double hum = (Double) map.get("hum");
            Double water = (Double) map.get("water");
            Double batt = (Double) map.get("batt");

            list.addFirst(new BootMeasureEntry(counter, temp, hum, water, batt));
        }
    }
}
