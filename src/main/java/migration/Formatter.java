package migration;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static final Formatter INSTANCE = new Formatter();
    private Formatter(){}

    public String formatInstant(Instant instant) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME
                .withZone(ZoneId.from(ZoneOffset.UTC))
                .format(instant);
    }
}
