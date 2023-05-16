plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
android {
    namespace = "com.example.instaflix"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.instaflix"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = "\"${property("API_KEY") ?: ""}\"",
        )
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            isDebuggable = true
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val coreKtx = "androidx.core:core-ktx:1.10.1"
val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
val activityCompose = "androidx.activity:activity-compose:1.7.1"
val composeBom = "androidx.compose:compose-bom:2023.05.01"
val composeUi = "androidx.compose.ui:ui"
val composeUiGraphics = "androidx.compose.ui:ui-graphics"
val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
val composeMaterial3 = "androidx.compose.material3:material3"
val navigationCompose = "androidx.navigation:navigation-compose:2.5.3"
val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:2.9.0"
val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.10.0"
val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.15.0"
val daggerHiltAndroid = "com.google.dagger:hilt-android:2.46.1"
val daggerHiltCompiler = "com.google.dagger:hilt-compiler:2.46.1"
val mockk = "io.mockk:mockk:1.12.2"
val junit = "junit:junit:4.13.2"
val room = "androidx.room:room-ktx:2.5.1"
val roomCompiler = "androidx.room:room-compiler:2.5.1"
val coil = "io.coil-kt:coil-compose:2.3.0"
val placeholder = "com.google.accompanist:accompanist-placeholder-material:0.31.2-alpha"

dependencies {
    implementation(coreKtx)
    implementation(lifecycleRuntimeKtx)
    implementation(activityCompose)

    implementation(platform(composeBom))
    implementation(composeUi)
    implementation(composeUiGraphics)
    implementation(composeUiToolingPreview)
    implementation(composeMaterial3)
    implementation(placeholder)

    implementation(daggerHiltAndroid)
    kapt(daggerHiltCompiler)

    implementation(navigationCompose)

    implementation(retrofit)
    implementation(retrofitMoshiConverter)
    implementation(okhttpLoggingInterceptor)
    implementation(moshiKotlin)
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    implementation(room)
    kapt(roomCompiler)

    // Coil
    implementation(coil)

    // constraintlayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // Palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Jetpack
    implementation("androidx.compose.runtime:runtime-livedata:1.4.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Lotti
    implementation("com.airbnb.android:lottie-compose:6.0.0")

    // Test
    testImplementation(junit)
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
}
