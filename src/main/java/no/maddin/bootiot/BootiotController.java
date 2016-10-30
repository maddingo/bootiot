package no.maddin.bootiot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootiotController {

    @Autowired
    private EntryStore entryStore;

    @RequestMapping(path="/bootiot", method = RequestMethod.GET)
    public Iterable<BootMeasureEntry> entries() {
        return entryStore.getAll();
    }

    @Bean
    public InfoContributor version() {
        return builder -> builder.withDetail("version", "1.2.3");
    }
}
