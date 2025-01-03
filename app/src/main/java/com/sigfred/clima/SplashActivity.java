package com.sigfred.clima;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.sigfred.clima.Activitis.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Temporizador para esperar 2 segundos antes de iniciar MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cuando termine el tiempo, redirigimos a la actividad principal
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Finaliza la actividad de bienvenida para que no regrese cuando presionen el botón atrás
            }
        }, 2000); // 2000 ms = 2 segundos
    }
}
