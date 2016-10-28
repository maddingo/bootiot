package no.maddin.bootiot;

public class EntryStoreImpl implements EntryStore {

    @Override
    public Iterable<BootMeasureEntry> getAll() {
        return java.util.Collections.singletonList(new BootMeasureEntry(1, 11.3f, 40.1f, 0f, 11.2f));
    }
}
