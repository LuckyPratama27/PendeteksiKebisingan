package com.example.pendeteksikebisingan;

public class Image {
    private String link;
    private long epoch;
    private Float db;

    public Image() {}

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getEpoch() {
        return epoch;
    }

    public void setEpoch(long epoch) {
        this.epoch = epoch;
    }

    public Float getDb() {
        return db;
    }

    public void setDb(Float db) {
        this.db = db;
    }
}
