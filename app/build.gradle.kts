import com.android.utils.TraceUtils.simpleId

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.encryptednotes"
    compileSdk = 34

    defaultConfig {
        applicationId ="com.example.encryptednotes"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }
    testOptions {
        // Used for Unit testing Android dependent elements in /test folder
        unitTests.isIncludeAndroidResources  = true
        unitTests.isReturnDefaultValues = true
    }
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.json)
// LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
// KSP
    implementation(libs.symbol.processing.api)

    //room
    implementation(libs.androidx.room.runtime)
    androidTestImplementation(libs.androidx.core.testing)

    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    // See Add the KSP plugin to your project
    ksp(libs.androidx.room.compiler)

//    // If this project only uses Java source, use the Java annotationProcessor
//    // No additional plugins are necessary
//    annotationProcessor(libs.androidx.room.compiler)

//    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)


//
//    // optional - Test helpers
    testImplementation(libs.room.testing)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.android)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.room.testing)
//    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.robolectric)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.mockito.mockito.android)


//    androidTestImplementation(libs.mockito.inline)
//    androidTestImplementation(libs.mockito.android)
//    androidTestImplementation(libs.dexmaker.mockito.inline)
//
//    // optional - Paging 3 Integration
//    implementation(libs.room.paging)

//    implementation("androidx.room:room-runtime:2.5.0")
//    kapt("androidx.room:room-compiler:2.5.0")
    //annotationProcessor("android.arch.persistence.room:compiler:1.0.0"
// To use Kotlin Symbol Processing (KSP) ***Keep commented until build passes
// ksp("androidx.room:room-compiler:$room_version")
}