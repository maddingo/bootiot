package no.maddin.bootiot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest()
public class EntryStoreImplTest {

    @Autowired
    private EntryStore entryStore;

    @Test
    public void addEntryTest() {
        entryStore.saveJson("{\"temp\":10.5, \"hum\": 40.1, \"batt\": 10.1, \"water\": 3.4, \"counter\": 1}");

        Collection<BootMeasureEntry> allEntries = entryStore.getAll();
        assertThat(allEntries, is(notNullValue()));
        assertThat(allEntries, hasSize(1));
        assertThat(allEntries, hasItem(hasProperty("counter", equalTo(1))));
        assertThat(allEntries, hasItem(hasProperty("temp", instanceOf(Double.class))));
        assertThat(allEntries, hasItem(hasProperty("hum", instanceOf(Double.class))));
        assertThat(allEntries, hasItem(hasProperty("batt", instanceOf(Double.class))));
        assertThat(allEntries, hasItem(hasProperty("water", instanceOf(Double.class))));
    }
}
