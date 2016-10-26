package no.maddin.bootiot;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class BootiotController {

    @RequestMapping(path="/bootiot", method = RequestMethod.GET)
    public Iterable<BootMeasureEntry> entries() {
        return Arrays.asList(new BootMeasureEntry(1, 11.3f, 40.1f, 0f, 11.2f));
    }

    @Bean
    public InfoContributor version() {
        return builder -> builder.withDetail("version", "1.2.3");
    }
}
