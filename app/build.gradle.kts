plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "net.nfmcpwr.EasyHome"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }
    
    defaultConfig {
        applicationId = "net.nfmcpwr.EasyHome"
        minSdk = 29
        targetSdk = 36
        versionCode = 13
        versionName = "1.3"
    }
    
    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2026.08.00"))
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.appcompat:appcompat:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.11.0")
    implementation("com.google.android.material:material:1.14.0")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-fragment:2.9.8")
    implementation("androidx.navigation:navigation-ui:2.9.8")
    implementation("tools.jackson.core:jackson-databind:3.2.2")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("com.squareup.okhttp3:okhttp:5.5.0")
    implementation("io.github.z4kn4fein:semver:3.1.0")
}