package com.momentum.batch.util;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.ChronoField.*;

public class DateTimeUtils {

    private static final DateTimeFormatter hlDateTimeFormatter = new DateTimeFormatterBuilder().appendValue(YEAR, 4)
            .appendValue(DAY_OF_YEAR, 3)
            .appendValue(HOUR_OF_DAY, 2)
            .appendValue(MINUTE_OF_HOUR, 2)
            .appendValue(SECOND_OF_MINUTE, 2)
            .appendValue(MILLI_OF_SECOND, 3)
            .toFormatter();

    private DateTimeUtils() {
    }

    /**
     * Converts Dates in HLAGs custom Format [year][day of year][hours][minutes][centiseconds]
     *
     * @param dateTime dae/time to convert
     * @return Hapag-Lloyd date time format for last_change columns.
     */
    public static Long toHlagTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0L;
        }
        return Long.parseLong(hlDateTimeFormatter.format(dateTime)) / 10;
    }

    public static long getCutOff(int cutOffDays) {
        return cutOffDays > 0 ? DateTimeUtils.toHlagTimestamp(LocalDateTime.now().minus(cutOffDays, ChronoUnit.DAYS)) : 0;
    }

    public static long getCutOffHours(int cutOffHours) {
        return cutOffHours > 0 ? DateTimeUtils.toHlagTimestamp(LocalDateTime.now().minus(cutOffHours, ChronoUnit.HOURS)) : 0;
    }

    public static Date getCutOffDate(int cutOffDays) {
        return cutOffDays > 0 ? new Date(new Date().getTime() - cutOffDays * 86400000) : Date.from(Instant.MIN);
    }

    public static Timestamp getCutOffTimestampMidnight(int cutOffDays) {
        long midnight = Instant.now().truncatedTo(ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).toEpochSecond() - (cutOffDays * 86400);
        return cutOffDays > 0 ? new Timestamp(midnight * 1000) : new Timestamp(0);
    }

    public static long getCutOffUnixtime(int cutOffDays) {
        return cutOffDays > 0 ? new Date().getTime() / 1000 - cutOffDays * 86400 : 0;
    }

    public static long getRunningTime(JobExecution jobExecution) {
        return jobExecution.getEndTime().getTime() - jobExecution.getStartTime().getTime();
    }

    public static long getRunningTime(StepExecution stepExecution) {
        return stepExecution.getEndTime() != null ? (stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime()) : 0;
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        calendar.set(year, month, day, 0, 0, 0);
        return calendar.getTime();
    }
}
