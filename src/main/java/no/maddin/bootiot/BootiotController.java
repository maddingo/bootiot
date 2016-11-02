package no.maddin.bootiot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BootiotController {

    @Value("${app.version}")
    private String appVersion;
    
    @Autowired
    private EntryStore entryStore;

    @RequestMapping(path="/bootiot", method = RequestMethod.GET)
    public Iterable<BootMeasureEntry> entries() {
        return entryStore.getAll();
    }

    @RequestMapping(path = "/purge", method = RequestMethod.DELETE)
    public void purgeAll() {
        entryStore.purgeAll();
    }

    @Bean
    public InfoContributor version() {
        return builder -> builder.withDetail("version", appVersion);
    }

    @Bean
    public InfoContributor storeSize() {
        return builder -> builder.withDetail("storeSize", entryStore.getAll().size());
    }
}
