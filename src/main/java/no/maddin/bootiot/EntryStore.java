package no.maddin.bootiot;

public interface EntryStore {
    Iterable<BootMeasureEntry> getAll();
}
