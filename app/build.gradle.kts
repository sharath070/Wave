import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kspPlugin)
    alias(libs.plugins.hiltPlugin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sharath070.wave"
    compileSdk = 34
    android.buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.sharath070.wave"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            "String", "baseUrl", "\"${properties.getProperty("baseUrl")}\""
        )
        buildConfigField(
            "String", "apiStr", "\"${properties.getProperty("apiStrV4")}\""
        )
        buildConfigField(
            "String", "homeData", "\"${properties.getProperty("homeData")}\""
        )
        buildConfigField(
            "String", "topSearches", "\"${properties.getProperty("topSearches")}\""
        )
        buildConfigField(
            "String", "searchResults", "\"${properties.getProperty("searchResults")}\""
        )
        buildConfigField(
            "String", "fromToken", "\"${properties.getProperty("fromToken")}\""
        )
        buildConfigField(
            "String", "featuredRadio", "\"${properties.getProperty("featuredRadio")}\""
        )
        buildConfigField(
            "String", "artistRadio", "\"${properties.getProperty("artistRadio")}\""
        )
        buildConfigField(
            "String", "entityRadio", "\"${properties.getProperty("entityRadio")}\""
        )
        buildConfigField(
            "String", "radioSongs", "\"${properties.getProperty("radioSongs")}\""
        )
        buildConfigField(
            "String", "songDetails", "\"${properties.getProperty("songDetails")}\""
        )
        buildConfigField(
            "String", "playlistDetails", "\"${properties.getProperty("playlistDetails")}\""
        )
        buildConfigField(
            "String", "albumDetails", "\"${properties.getProperty("albumDetails")}\""
        )
        buildConfigField(
            "String", "getResults", "\"${properties.getProperty("getResults")}\""
        )
        buildConfigField(
            "String", "albumResults", "\"${properties.getProperty("albumResults")}\""
        )
        buildConfigField(
            "String", "artistResults", "\"${properties.getProperty("artistResults")}\""
        )
        buildConfigField(
            "String", "playlistResults", "\"${properties.getProperty("playlistResults")}\""
        )
        buildConfigField(
            "String", "getReco", "\"${properties.getProperty("getReco")}\""
        )
        buildConfigField(
            "String", "getAlbumReco", "\"${properties.getProperty("getAlbumReco")}\""
        )
        buildConfigField(
            "String", "artistOtherTopSongs", "\"${properties.getProperty("artistOtherTopSongs")}\""
        )
        buildConfigField(
            "String", "key", "\"${properties.getProperty("key")}\""
        )
        buildConfigField(
            "String", "algo", "\"${properties.getProperty("algo")}\""
        )
        buildConfigField(
            "String", "block", "\"${properties.getProperty("block")}\""
        )
        buildConfigField(
            "String", "padding", "\"${properties.getProperty("padding")}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.compose.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //Constraint layout
    implementation(libs.androidx.constraintlayout.compose)

    //Splash Api
    implementation(libs.androidx.core.splashscreen)

    //Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Coroutines
    implementation(libs.kotlinx.coroutines.android)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Media Player
    implementation(libs.androidx.media3.datasource.okhttp)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.legacy.support.v4)

    //Coil
    implementation(libs.coil.compose)

    //Glide
    implementation(libs.glide)

    //Pallete api
    implementation(libs.androidx.palette)

}