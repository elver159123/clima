package com.sigfred.clima.Response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    private List<Weather> weather;
    private Main main;
    private String name;
    private Rain rain; // Nuevo atributo para lluvia
    private Wind wind; // Atributo para viento

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public Rain getRain() {
        return rain;
    }

    public Wind getWind() {
        return wind; // Método getter para el viento
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

    public static class Rain {
        @SerializedName("1h")
        private double oneHour;

        public double getOneHour() {
            return oneHour;
        }

        public void setOneHour(double oneHour) {
            this.oneHour = oneHour;
        }
    }

    public static class Wind {  // Nueva clase Wind
        private float speed; // Velocidad del viento en km/h

        public float getSpeed() {
            return speed;
        }

        public void setSpeed(float speed) {
            this.speed = speed;
        }
    }
}
