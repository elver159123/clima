plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.sigfred.clima"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sigfred.climaapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {

    implementation(libs.material)
    implementation(libs.tracing.perfetto.handshake)
    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.airbnb.android:lottie:3.4.0")
}