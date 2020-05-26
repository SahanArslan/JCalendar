package com.example.jcalendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Event {
    private String name;
    private String content;
    private String type;
    private CalendarDay start;
    private CalendarDay end;
    private ArrayList<Calendar> reminders;
    private ArrayList<Date> repeats;
    private String adress;

    public Event() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String detail) {
        this.content = detail;
    }

    public CalendarDay getStart() {
        return start;
    }

    public void setStart(CalendarDay start) {
        this.start = start;
    }

    public CalendarDay getEnd() {
        return end;
    }

    public void setEnd(CalendarDay end) {
        this.end = end;
    }

    public ArrayList<Calendar> getReminders() {
        return reminders;
    }

    public void setReminders(ArrayList<Calendar> reminders) {
        this.reminders = reminders;
    }

    public ArrayList<Date> getRepeats() {
        return repeats;
    }

    public void setRepeats(ArrayList<Date> repeats) {
        this.repeats = repeats;
    }
}
