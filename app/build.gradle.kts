import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

val properties = Properties().apply {
    load(rootProject.file("local.properties").reader())
}

android {
    namespace = "com.capstone.bangkit.calendivity"
    compileSdk  = 32

    defaultConfig {
        applicationId = "com.capstone.bangkit.calendivity"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false

            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true

            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true

            // Includes the default ProGuard rules files that are packaged with
            // the Android Gradle plugin. To learn more, go to the section about
            // R8 configuration files.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // build config for token api
            buildConfigField(
                "String",
                "TOKEN_API",
                "${properties["API_KEY"]}"
            )

            // build config for BASE_URL
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }

        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
            isMinifyEnabled = false

            // build config for TOKEN_API
            buildConfigField(
                "String",
                "TOKEN_API",
                "${properties["API_KEY"]}"
            )

            // build config for BASE_URL
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
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
    // default implementation
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Google Mobile Services
    implementation("com.google.android.gms:play-services-auth:20.5.0")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // test implementation
    testImplementation("junit:junit:4.13.2")

    // android test implementation
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// copy debug apk when build project is done
tasks.register("copyAPKDebug", Copy::class) {
    dependsOn("test")
    val soureDir = layout.buildDirectory.dir("outputs/apk/debug/app-debug.apk")
    val destDir = "$rootDir/apk"
    from(soureDir)
    into(destDir)
    rename("app-debug.apk", "calendivityDebug.apk")

    // Untuk cek apakah aplikasi ada virus atau tidak, bisa diliath dari MD5 yang sudah di generate
    doLast {
        val filePath = File(destDir, "calendivityDebug.apk")
        ant.withGroovyBuilder {
            "checksum"("file" to filePath.path)
        }
    }
}

tasks.whenTaskAdded {
    if (this.name == "assembleDebug") {
        this.finalizedBy("copyAPKDebug")
    }
}