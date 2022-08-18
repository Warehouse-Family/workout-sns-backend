package com.warehouse.workout.util;

import java.time.LocalDateTime;

public class DateUtil {

    public static boolean isBetween(LocalDateTime start , LocalDateTime end, LocalDateTime standard){

        return standard.isAfter(start) && standard.isBefore(end);
    }



}
