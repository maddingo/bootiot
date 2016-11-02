package no.maddin.bootiot;

import java.util.Collection;

public interface EntryStore {
    Collection<BootMeasureEntry> getAll();

    void saveJson(String json);

    void purgeAll();
}
