package no.maddin.bootiot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
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
            Integer counter = (Integer) map.get("counter");
            Double temp = (Double) map.get("temp");
            Double hum = (Double) map.get("hum");
            Double water = (Double) map.get("water");
            Double batt = (Double) map.get("batt");
            ZonedDateTime timestamp = ZonedDateTime.now(ZoneOffset.UTC);

            BootMeasureEntry entry = new BootMeasureEntry(counter == null ? list.size() : counter, temp, hum, water, batt, timestamp.toString());
            if (!list.offerFirst(entry)) {
                list.removeLast();
                list.addFirst(entry);
            }
        }
    }

    @Override
    public void purgeAll() {
        synchronized (list) {
            list.clear();
        }
    }
}
