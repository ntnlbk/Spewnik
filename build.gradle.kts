// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.5.1" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
