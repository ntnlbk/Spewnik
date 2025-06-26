plugins {
    id("com.google.devtools.ksp")
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.LibBib.spevn"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.LibBib.spevn"
        minSdk = 24
        targetSdk = 34
        versionCode = 32
        versionName = "32"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    //dagger 2
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    //room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //fragments
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.fragment.compose)

    //fragments navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic.features.fragment)
    api(libs.androidx.navigation.fragment.ktx)

    //preferences data store
    implementation(libs.androidx.datastore.preferences)

    //color picker
    implementation (libs.colorpickerview)

    //drawer layout, navigation view
    implementation(libs.androidx.drawerlayout)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    //firebase realtime database
    implementation(libs.firebase.database)

    //google ad sdk
    implementation (libs.play.services.ads)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}