package com.yeta.mongo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HistoryRecord {

    @Id
    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    private int position;

    private String name;

    private String url;

    @Transient
    private int previousPosition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int previousPosition) {
        this.previousPosition = previousPosition;
    }

    @Override
    public String toString() {
        return "HistoryRecord{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", position=" + position +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", previousPosition=" + previousPosition +
                '}';
    }
}