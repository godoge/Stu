package net.lzzy.studentsattendance.models;

import android.database.Cursor;

import com.example.get.Course;

import net.lzzy.sql.Sqlable;
import net.lzzy.studentsattendance.constants.DbConstance;

import java.util.HashMap;
import java.util.Map;

public class LocalCourse extends Sqlable {
    private String raw;
    private String week;
    private String eventValidation;
    private String viewState;

    public void setEventValidation(String eventValidation) {
        this.eventValidation = eventValidation;
    }

    public String getEventValidation() {
        return eventValidation;
    }

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public void setWeekRaw(String raw) {
        this.setRaw(raw);
    }


    public String getRaw() {
        return raw;
    }

    @Override
    public String getTableName() {
        return DbConstance.TABLE_COURSE;
    }

    @Override
    public String getKeyVal() {
        return getWeek();
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Override
    public String getKeyCol() {
        return DbConstance.COL_COURSE_WEEK;
    }

    @Override
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(DbConstance.COL_COURSE_RAW, getRaw());
        map.put(DbConstance.COL_COURSE_WEEK, getWeek());
        map.put(DbConstance.COL_COURSE_VIEW_STATE, viewState);
        map.put(DbConstance.COL_COURSE_EVENT_VALIDATION, eventValidation);
        return map;
    }

    public static LocalCourse getLocalCourse(Course currCourse) {
        LocalCourse localCourse = new LocalCourse();
        localCourse.setWeek(currCourse.getWeek());
        String raw;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                raw = currCourse.getRawCourse(i + 1, j + 1);
                if (raw.trim().length() <= 0)
                    buffer.append(" ").append("##");
                else
                    buffer.append(raw).append("##");
            }
            buffer.append("@@");
        }
        localCourse.setWeekRaw(buffer.toString());
        localCourse.setEventValidation(currCourse.getEventValidation());
        localCourse.setViewState(currCourse.getViewState());
        return localCourse;
    }

    @Override
    public void setCursor(Cursor cursor) {
        setWeek(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_WEEK)));
        setRaw(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_RAW)));
        setViewState(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_VIEW_STATE)));
        setEventValidation(cursor.getString(cursor.getColumnIndex(DbConstance.COL_COURSE_EVENT_VALIDATION)));
    }


    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getWeek() {
        return week;
    }
}