package Utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class Timezone {

    public static LocalDateTime ToEastern(LocalDateTime origin){

        ZonedDateTime original = origin.atZone(ZoneId.of("UTC"));
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.of("US/Eastern"));
        LocalDateTime end = target.toLocalDateTime();

        return end;

    }

    public static LocalDateTime ToLocalTime(LocalDateTime origin, ZoneId zone){
        ZonedDateTime original = origin.atZone(ZoneId.of("UTC"));
        ZonedDateTime target = original.withZoneSameInstant(zone);
        LocalDateTime end = target.toLocalDateTime();

        return end;
    }
}
