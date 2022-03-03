package Utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Timezone {

    public static LocalDateTime LocalToEastern(LocalDateTime origin){

        ZonedDateTime original = origin.atZone(ZoneId.systemDefault());
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime end = target.toLocalDateTime();

        return end;

    }

    public static LocalDateTime UTCToLocalTime(LocalDateTime origin){

        //origin = LocalDateTime.parse(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(origin));
        ZonedDateTime original = ZonedDateTime.parse(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(origin.atZone(ZoneId.of("UTC"))));//origin.atZone(ZoneId.of("UTC"));
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime end = target.toLocalDateTime();

        return end;
    }

    public static LocalDateTime LocalToUtc(LocalDateTime origin){
        ZonedDateTime original = origin.atZone(ZoneId.systemDefault());
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime end = target.toLocalDateTime();

        return end;
    }

    public static LocalDateTime UTCToEastern(LocalDateTime origin){

        ZonedDateTime original = origin.atZone(ZoneId.of("UTC"));
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime end = target.toLocalDateTime();

        return end;

    }

    public static LocalDateTime EasternToLocal(LocalDateTime origin){

        ZonedDateTime original = origin.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime target = original.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime end = target.toLocalDateTime();

        return end;
    }
}
