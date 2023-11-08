package com.university.universitycms.controllers.dataformatter;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Component
public class DateFormatter {
    public String formatDayAndDataForOneDay(){
        LocalDate today = getLocalDay();

        int dayOfMonth = today.getDayOfMonth();
        int month = today.getMonthValue();

        String dayName = today.getDayOfWeek().name();

        return dayName + "/" + dayOfMonth + "/" + month;
    }

    public List<String> formatDayAndDataMonthForWeekAndMonth(boolean week){
        LocalDate firstDay = getLocalDay();
        LocalDate lastDay;

        if (week) {
            firstDay = firstDay.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            lastDay = firstDay.plusDays(4);
        } else {
            firstDay = firstDay.withDayOfMonth(1);
            lastDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth());
        }

        List<String> result = new ArrayList<>();

        for (LocalDate currentDay = firstDay; !currentDay.isAfter(lastDay); currentDay = currentDay.plusDays(1)){

            if (currentDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                continue;
            }

            int dayOfMonth = currentDay.getDayOfMonth();
            int month = currentDay.getMonthValue();

            String dayName = getFirstLettersFromDayName(currentDay.getDayOfWeek().getValue());

            result.add(dayName + "/" + dayOfMonth + "/" + month);
        }


        return result;
    }

    public LocalDate getLocalDay(){
        LocalDate today = LocalDate.now();

        if (isWeekend(today)) {
            today = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return today;
    }

    private boolean isWeekend(LocalDate date){
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private String getFirstLettersFromDayName(int dayIndex){
        DayOfWeek dayName = DayOfWeek.of(dayIndex);

        return dayName.name().substring(0, 2).toUpperCase();
    }
}
