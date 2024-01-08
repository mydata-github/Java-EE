package com.sunbeam.pojos;

import java.util.Date;

public class Movies {
    int id;
    String title;
    Date release;

    public Movies() {
    }
    public Movies(int id, String title, Date release) {
        this.id = id;
        this.title = title;
        this.release = release;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release=" + release +
                '}';
    }
}