package no.maddin.bootiot;

import java.util.Collection;

public interface EntryStore {
    Collection<BootMeasureEntry> getAll();

    BootMeasureEntry getLatest();
    int size();

    void saveJson(String json);

    void purgeAll();
}
