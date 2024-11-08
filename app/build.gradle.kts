import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin.jetbrains)
    alias(libs.plugins.android.kotlin.compose)
    alias(libs.plugins.android.kotlin.ksp)
}

android {
    namespace = "com.software.todo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.software.todo"

        minSdk = 24
        targetSdk = 35

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val proguard = getDefaultProguardFile("proguard-android-optimize.txt")

        debug {
            isMinifyEnabled = false
            proguardFiles(proguard, "proguard-rules.pro")
        }

        release {
            isMinifyEnabled = true
            proguardFiles(proguard, "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += setOf("/META-INF/{AL2.0,LGPL2.1}", "/META-INF/LICENSE.md", "/META-INF/LICENSE-notice.md")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.view.model)
    implementation(libs.androidx.extend.icons)

    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.jetpack.room.runtime)
    implementation(libs.jetpack.room.kotlin)
    annotationProcessor(libs.jetpack.room.compiler)
    ksp(libs.jetpack.room.compiler)

}