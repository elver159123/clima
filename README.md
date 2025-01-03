# Clima App - Proyecto Android

Este proyecto es una aplicación móvil de Android que muestra el clima actual y el pronóstico en tiempo real utilizando la API de OpenWeather. La aplicación obtiene la ubicación del usuario y muestra el clima según las coordenadas de latitud y longitud. Además, proporciona información detallada como la temperatura, las condiciones climáticas, la fecha actual, la hora y el pronóstico.

## Características

- **Ubicación en tiempo real**: La app obtiene la ubicación del usuario utilizando el proveedor de ubicación de Google.
- **Clima actual**: Se muestra la temperatura actual, el clima y el pronóstico de las próximas horas.
- **Fecha y hora**: Se actualizan dinámicamente la fecha y la hora en tiempo real.
- **Interfaz atractiva**: La información del clima se presenta de forma clara y accesible.

## Estructura del Proyecto

- **MainActivity.java**: La actividad principal de la aplicación, que contiene la lógica para obtener la ubicación del usuario, mostrar el clima actual y actualizar la hora y la fecha en tiempo real.
- **activity_main.xml**: El archivo de diseño para la actividad principal, que incluye `RecyclerView` para el pronóstico del clima y `TextView` para la fecha y hora.
- **RecyclerView**: Utilizado para mostrar el pronóstico del clima por horas.
- **ApiClient.java** y **WeatherApi.java**: Archivos para interactuar con la API de OpenWeather y obtener datos del clima.
- **WeatherResponse.java** y **Hourly.java**: Clases de respuesta para manejar los datos del clima y el pronóstico por horas.

## Dependencias

1. **FusedLocationProviderClient**: Utilizado para obtener la ubicación del usuario.
2. **Retrofit**: Para realizar las peticiones a la API de OpenWeather.
3. **RecyclerView**: Para mostrar el pronóstico del clima en una lista horizontal.
4. **Handler**: Para actualizar la hora y la fecha en tiempo real.
5. **SimpleDateFormat**: Para formatear la fecha y la hora de acuerdo a la localización del usuario.

## Requerimientos

- Android 6.0 (API nivel 23) o superior.
- Permiso de ubicación: La aplicación necesita permisos para acceder a la ubicación del dispositivo.
  
  - `ACCESS_FINE_LOCATION`: Para obtener la ubicación precisa del dispositivo.

## Uso

1. **Pantalla de Ubicación**: La aplicación solicita permisos de ubicación al usuario para obtener su ubicación actual.
3. **Pantalla Principal**: Una vez obtenida la ubicación, la app consulta la API de OpenWeather y muestra el clima actual en la pantalla, junto con el pronóstico por horas.
5. **Fecha y Hora**: La fecha y la hora se actualizan en tiempo real cada minuto y segundo respectivamente.
6. **Pronóstico por horas**: El pronóstico del clima por horas se presenta en un `RecyclerView` en la pantalla principal.

## Imágenes

![Pantalla bienvenida](/imagenes/bienvenida.png)
![Pantalla interfas](/imagenes/interfas.png)

## Funcionalidades

- **Obtener ubicación del usuario**: La aplicación solicita permisos para acceder a la ubicación y obtiene las coordenadas de latitud y longitud.
- **Consultar la API de OpenWeather**: Utiliza Retrofit para hacer una solicitud HTTP a la API de OpenWeather con las coordenadas obtenidas.
- **Actualizar fecha y hora**: La aplicación muestra la fecha y la hora actuales, actualizándose en tiempo real.
- **Mostrar pronóstico por horas**: Muestra el pronóstico de las próximas horas en un `RecyclerView`.

## Permisos

La aplicación necesita permisos de ubicación para funcionar correctamente. Estos permisos son solicitados en tiempo de ejecución:

- **ACCESS_FINE_LOCATION**: Para obtener la ubicación precisa del dispositivo.

## Ejemplo de Código

El siguiente es un fragmento clave de la lógica en `MainActivity.java` para obtener la ubicación y consultar la API:

```java
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
```

## API de OpenWeather

Este proyecto usa la API de OpenWeather para obtener datos meteorológicos en tiempo real. Para obtener acceso a la API, necesitas una clave de API, que puedes obtener registrándote en el sitio oficial de OpenWeather.

### Endpoints utilizados:

https://api.openweathermap.org/data/2.5/weather?lat={latitude}&lon={longitude}&appid={API_KEY}&units=metric

**Obtener clima actual por coordenadas**:

Reemplaza `{latitude}`, `{longitude}` y `{API_KEY}` con la latitud, longitud y tu clave API.

## Cómo ejecutar el proyecto

1. Clona o descarga el repositorio del proyecto.
2. Abre el proyecto en Android Studio.
3. Asegúrate de tener configurado el SDK de Android y todas las dependencias necesarias.
4. Ejecuta la aplicación en un dispositivo físico o un emulador.
5. Asegúrate de conceder permisos de ubicación cuando se te solicite.

## Contribución

Si deseas contribuir al proyecto, por favor sigue los siguientes pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama para tu característica o corrección.
3. Realiza tus cambios y haz commit.
4. Haz un pull request con una descripción detallada de los cambios.

## Autor

Desarrollado por Andy Yanacallo (https://github.com/elver159123).

