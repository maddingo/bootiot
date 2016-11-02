package no.maddin.bootiot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.temporal.Temporal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BootMeasureEntry {
    int counter;
    Double temp;   // temperature
    Double hum;    // humidity
    Double water;  // water level
    Double batt;   // battery level
    String timestamp; // timestamp

}
