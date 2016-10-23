package no.maddin.bootiot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

@RequestMapping("/biot")
@RestController
public class BootiotController {

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<BootMeasureEntry> entries() {
        return Arrays.asList(new BootMeasureEntry(1, 11.3f, 40.1f, 0f, 11.2f));
    }
}
