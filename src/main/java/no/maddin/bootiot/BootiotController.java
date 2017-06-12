package no.maddin.bootiot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@Slf4j
public class BootiotController {

    private  ThreadLocal<SimpleDateFormat> dtFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    @Value("${app.version}")
    private String appVersion;

    @Value("${twilio.updateinterval}")
    private long updateinterval;

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
        return builder -> builder.withDetail("storeSize", entryStore.size());
    }

    @Scheduled(fixedRate = 180000)
    private void checkLast() {
        BootMeasureEntry latestEntry = entryStore.getLatest();
        if (latestEntry != null) {
            ZonedDateTime timestamp = latestEntry.getTimestamp();

            ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
            Duration between = Duration.between(timestamp, now);
            if (between.getSeconds() > updateinterval) {
                sendEmailWarning();
            }
        }
        log.info("checking interval");
    }

    private void sendEmailWarning() {

    }
}
