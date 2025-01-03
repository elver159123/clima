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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar las vistas
        dateText = findViewById(R.id.dateText);
        clockText = findViewById(R.id.clockText);

        // Actualizar la fecha y hora
        updateDate(); // Inicializa la actualización en tiempo real de la fecha
        updateClock(); // Inicializa la actualización en tiempo real del reloj

        // Inicializar RecyclerView
        initRecyclerview();

        // Obtener la ubicación y el clima
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        requestLocationPermissionAndFetchWeather();
    }

    private void initRecyclerview() {
        recyclerView = findViewById(R.id.view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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

        String hora = "Ahora";
        int temperatura = (int) weather.getMain().getTemp();
        String icono = getIconFromWeatherCode(weather.getWeather().get(0).getIcon());
        items.add(new Hourly(hora, temperatura, icono));

        adapterHourly = new HourlyAdapters(items, this);
        recyclerView.setAdapter(adapterHourly);
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

    // Método para actualizar la fecha cada minuto
    private void updateDate() {
        // Actualizar la fecha
        String currentDate = getCurrentDate();
        dateText.setText(currentDate);

        // Ejecutar nuevamente el método cada minuto (60000 ms)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateDate();
            }
        }, 60000);
    }

    private String getCurrentDate() {
        // Asegúrate de usar el Locale en español
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("es", "ES"));
        return dateFormat.format(new Date());
    }

    // Método para actualizar el reloj cada segundo
    private void updateClock() {
        // Actualizar la hora
        String currentTime = getCurrentTime();
        clockText.setText(currentTime);

        // Ejecutar nuevamente el método cada segundo (1000 ms)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateClock();
            }
        }, 1000);
    }

    private String getCurrentTime() {
        // Usamos un formato de hora con la hora, minuto y segundo
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", new Locale("es", "ES"));
        return timeFormat.format(new Date());
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
