package com.sigfred.clima.Response;

import java.util.List;

public class WeatherResponse {
    private List<Weather> weather;
    private Main main;
    private String name;

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public static class Weather {
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int humidity;

        public double getTemp() {
            return temp;
        }

        public double getFeelsLike() {
            return feels_like;
        }

        public double getTempMin() {
            return temp_min;
        }

        public double getTempMax() {
            return temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public int getHumidity() {
            return humidity;
        }
    }
}
