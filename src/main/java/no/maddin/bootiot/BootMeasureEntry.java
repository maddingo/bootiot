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
    float temp;   // temperature
    float hum;    // humidity
    float water;  // water level
    float batt;   // battery level

}
