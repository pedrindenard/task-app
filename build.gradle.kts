// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin.jetbrains) apply false
    alias(libs.plugins.android.kotlin.compose) apply false
    alias(libs.plugins.android.kotlin.ksp) apply false
}