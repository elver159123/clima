# ClimaApp

ClimaApp es una aplicación Android que proporciona información del clima en tiempo real y pronósticos del clima de manera simple y visual. Muestra el clima actual, las condiciones meteorológicas como la temperatura, la humedad, el viento y la lluvia. También incluye un pronóstico detallado del clima a través de un RecyclerView y una interfaz gráfica que facilita la visualización de los datos.

## Características

- **Clima Actual**: Muestra la temperatura actual, las condiciones meteorológicas y las temperaturas máxima y mínima.
- **Condiciones del Clima**: Información detallada sobre lluvia, viento y humedad.
- **Pronóstico**: Predicción del clima en tiempo real para las próximas horas/días.
- **Interfaz de Usuario**: Visualización limpia y moderna con imágenes e iconos representativos del clima.

## Requisitos

- **Android Studio**: Para abrir, compilar y ejecutar la aplicación.
- **Android SDK**: Asegúrate de tener el SDK de Android actualizado para el desarrollo.
- **Permisos de Internet**: Necesarios para hacer peticiones de datos de clima a un servicio en línea.

## Configuración del Proyecto

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/tu-usuario/climaapp.git
2. Abrir en Android Studio:

- Abre Android Studio.
- Selecciona "Open an existing Android Studio project" y selecciona la carpeta donde clonaste el repositorio.

3. **Configurar dependencias**:

- Asegúrate de que las dependencias en el archivo build.gradle estén actualizadas. Si es necesario, sincroniza el proyecto con los archivos Gradle.
4. **Obtener API Key (si es necesario)**:

- Si tu aplicación utiliza una API para obtener datos del clima (por ejemplo, OpenWeatherMap o WeatherStack), obtén una clave API y configúralo en tu archivo de configuración (por ejemplo, strings.xml o directamente en el código).
5. **Compilar y ejecutar**:

- Haz clic en el botón "Run" de Android Studio para compilar y ejecutar la aplicación en un emulador o dispositivo físico.
## Estructura del Proyecto

- **MainActivity.java**: La actividad principal de la aplicación que contiene la interfaz de usuario y la lógica para mostrar el clima actual y el pronóstico.
- **SplashActivity.java**: Una pantalla de bienvenida que se muestra antes de la actividad principal.
- **activity_main.xml**: El archivo de diseño para la actividad principal, que utiliza un `ScrollView` y `RecyclerView` para mostrar el clima y el pronóstico.
- **activity_splash.xml**: El diseño de la pantalla de bienvenida con el logo y el texto "Bienvenido a Clima".
- **drawable/**: Contiene todos los recursos gráficos utilizados en la app, como íconos de clima, fondo, etc.
- **strings.xml**: Archivo de cadenas que contiene los textos utilizados en la interfaz de usuario.

## Dependencias

1. **RecyclerView**: Para mostrar el pronóstico del clima en forma de lista.
2. **ConstraintLayout**: Para organizar los elementos en la pantalla de manera eficiente.
3. **Lottie (opcional)**: Si deseas agregar animaciones Lottie para mejorar la experiencia visual.
4. **Glide (opcional)**: Para cargar imágenes de manera eficiente en la interfaz de usuario.

## Uso

1. **Pantalla de Bienvenida**: Al abrir la aplicación, se muestra una pantalla de bienvenida con el logo y el texto "Bienvenido a Clima".
   
![Pantalla bienvenida](/imagenes/bienvenida.png)

4. **Pantalla Principal**: La pantalla principal muestra la información del clima actual, incluyendo la temperatura, las condiciones meteorológicas y un pronóstico detallado. El usuario puede ver la información en tiempo real, así como los detalles sobre lluvia, viento y humedad.
   
![Pantalla interfas](/imagenes/interfas.png)

