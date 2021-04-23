package com.treinamento.framework.util;


import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtils {
    private DateUtils() {}

    public static ZonedDateTime getNow() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public static ZonedDateTime format(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
    }
}