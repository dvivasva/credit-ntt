package com.dvivasva.credit.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static Date toDate(LocalDateTime dateTimeToday){
        return Date.from(dateTimeToday.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    public LocalDateTime stringToLocalDate(String source) {
        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        return LocalDateTime.parse(source, formatter);
    }
}
