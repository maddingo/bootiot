package no.maddin.bootiot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BootMeasureEntry {
    int counter;
    Double temp;   // temperature
    Double hum;    // humidity
    Double water;  // water level
    Double batt;   // battery level

}
