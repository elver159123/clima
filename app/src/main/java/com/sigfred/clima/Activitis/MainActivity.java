package com.sigfred.clima.Activitis;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sigfred.clima.Adapters.HourlyAdapters;
import com.sigfred.clima.api.ApiClient;
import com.sigfred.clima.api.WeatherApi;
import com.sigfred.clima.Domains.Hourly;
import com.sigfred.clima.R;
import com.sigfred.clima.Response.WeatherResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;
    private Handler handler = new Handler(); // Para actualizar la fecha y hora
    private TextView dateText; // Para mostrar la fecha
    private TextView clockText; // Para mostrar el reloj

    // Nuevos elementos de UI para lluvia, viento y humedad
    private TextView rainText, rainDesc, windText, windDesc, humidityText, humidityDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las vistas
        dateText = findViewById(R.id.dateText);
        clockText = findViewById(R.id.clockText);

        // Inicializar nuevos elementos de UI
        rainText = findViewById(R.id.textViewRain);
        rainDesc = findViewById(R.id.textViewRainDesc);
        windText = findViewById(R.id.textViewWind);
        windDesc = findViewById(R.id.textViewWindDesc);
        humidityText = findViewById(R.id.textViewHumidity);
        humidityDesc = findViewById(R.id.textViewHumidityDesc);

        // Actualizar la fecha y hora
        updateDate();
        updateClock();

        // Inicializar RecyclerView
        initRecyclerview();

        // Obtener la ubicación y el clima
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermissionAndFetchWeather();

        // Inicia la actualización del clima en tiempo real
        scheduleWeatherUpdates();
    }

    private void initRecyclerview() {
        recyclerView = findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterHourly = new HourlyAdapters(new ArrayList<>(), this);
        recyclerView.setAdapter(adapterHourly);
    }

    private void requestLocationPermissionAndFetchWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // Usar la ubicación para obtener el clima
                    fetchWeatherData(latitude, longitude);
                } else {
                    Toast.makeText(MainActivity.this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchWeatherData(double latitude, double longitude) {
        WeatherApi api = ApiClient.getWeatherApi();
        Call<WeatherResponse> call = api.getWeatherByCoordinates(latitude, longitude, "983547c127f454471422318c3118a5a6", "metric");

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Error al obtener datos del clima", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(WeatherResponse weather) {
        ArrayList<Hourly> items = new ArrayList<>();

        // Obtener la temperatura y el código de icono de clima
        int temperatura = (int) Math.round(weather.getMain().getTemp());
        String weatherIcon = getIconFromWeatherCode(weather.getWeather().get(0).getIcon());

        // Agregar un nuevo objeto Hourly a la lista de items
        items.add(new Hourly("Ahora", temperatura, weatherIcon));

        // Actualizar el RecyclerView con los nuevos datos
        if (adapterHourly instanceof HourlyAdapters) {
            ((HourlyAdapters) adapterHourly).updateData(items);
        }

        // Actualizar la temperatura principal en la pantalla
        TextView mainTemperatureTextView = findViewById(R.id.textView3);
        mainTemperatureTextView.setText(String.format(Locale.getDefault(), "%d°", temperatura));

        // Actualizar los nuevos elementos dinámicos (lluvia, viento y humedad)
        if (weather.getRain() != null) {
            rainText.setText(String.format(Locale.getDefault(), "%.1f mm", weather.getRain().getOneHour()));
        } else {
            rainText.setText("0 mm");
        }

        windText.setText(String.format(Locale.getDefault(), "%.1f km/h", weather.getWind().getSpeed())); // Usando la velocidad del viento
        humidityText.setText(String.format(Locale.getDefault(), "%d%%", weather.getMain().getHumidity()));

        // Agregar descripciones estáticas
        rainDesc.setText("Lluvias");
        windDesc.setText("Velocidad viento");
        humidityDesc.setText("Humedad");
    }

    private String getIconFromWeatherCode(String weatherCode) {
        switch (weatherCode) {
            case "01d":
            case "01n":
                return "sunny";
            case "02d":
            case "02n":
                return "cloudy";
            case "09d":
            case "09n":
                return "rainy";
            case "10d":
            case "10n":
                return "rain";
            case "11d":
            case "11n":
                return "storm";
            default:
                return "default";
        }
    }

    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("es", "ES"));
        String currentDate = dateFormat.format(new Date());
        dateText.setText(currentDate);
    }

    private void updateClock() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String currentTime = timeFormat.format(new Date());
                clockText.setText(currentTime);

                // Actualizar cada segundo
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void scheduleWeatherUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getCurrentLocation();
                scheduleWeatherUpdates();
            }
        }, 3600000); // Actualización cada hora
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
