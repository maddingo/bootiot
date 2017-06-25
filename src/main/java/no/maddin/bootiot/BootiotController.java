package no.maddin.bootiot;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;

import lombok.extern.slf4j.Slf4j;

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

    @Autowired
    private ErrorMessageSender errorMessageSender;

    private AtomicBoolean warningSent = new AtomicBoolean();

    @RequestMapping(path="/bootiot/{item}", method = RequestMethod.GET)
    public Iterable<BootMeasureEntry> entries(@PathVariable(name="item", required = false) String item) {

        switch(item == null ? "all" : item) {
            case "last":
                return Collections.singletonList(entryStore.getLatest());
            case "all":
            default:
                return entryStore.getAll();
        }
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

            ZonedDateTime now = ZonedDateTime.now();
            Duration between = Duration.between(timestamp, now);
            if (between.getSeconds() > updateinterval) {
                if (warningSent.compareAndSet(false, true)) {
                    errorMessageSender.sendMessage("Haven't heard from the boot for a while. Last message received at " + timestamp);
                }
            } else {
                warningSent.set(false);
            }
        }
        log.info("checking interval");
    }
}
