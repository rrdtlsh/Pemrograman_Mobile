// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // PERBAIKAN: Menggunakan alias untuk Hilt dan KSP
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
}