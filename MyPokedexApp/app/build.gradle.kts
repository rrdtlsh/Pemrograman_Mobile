plugins {
    // PERBAIKAN: Gunakan alias untuk semua plugin
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    // PERBAIKAN INTI: Tambahkan plugin Compose yang hilang
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.mypokedexapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mypokedexapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // PERBAIKAN INTI: Hapus baris 'kotlinCompilerExtensionVersion'
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Menggunakan alias dari TOML agar versi dependensi konsisten
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.volley)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.compose.material:material-icons-extended")

    // Untuk dependensi yang belum ada di TOML Anda, kita biarkan dulu
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Retrofit (API)
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room (Database Lokal)
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Hilt (Dependency Injection)
    implementation("com.google.dagger:hilt-android:2.51.1")
    ksp("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Coil (Image Loading)
    implementation("io.coil-kt:coil-compose:2.6.0")

    // Gson (Untuk Room Type Converters)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.compose.foundation:foundation:1.6.8")
    // Untuk menyimpan preferensi tema (Mode Gelap)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    // Untuk animasi Lottie (sudah ada, pastikan versinya up-to-date)
    implementation("com.airbnb.android:lottie-compose:6.4.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    ksp(libs.moshi.kotlin.codegen)
}