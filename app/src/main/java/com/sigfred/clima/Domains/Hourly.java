package com.sigfred.clima.Domains;

/**
 * Represents hourly weather details including time, temperature, and icon path.
 */
public class Hourly {
    private String hour;
    private int temp;
    private String picPath;

    public Hourly(String hour, int temp, String picPath) {
        this.hour = hour;
        this.temp = temp;
        this.picPath = picPath;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        if (hour == null || hour.isEmpty()) {
            throw new IllegalArgumentException("Hour cannot be null or empty");
        }
        this.hour = hour;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        if (picPath == null || picPath.isEmpty()) {
            throw new IllegalArgumentException("PicPath cannot be null or empty");
        }
        this.picPath = picPath;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        if (temp < -50 || temp > 60) {
            throw new IllegalArgumentException("Temperature out of realistic range");
        }
        this.temp = temp;
    }
}
