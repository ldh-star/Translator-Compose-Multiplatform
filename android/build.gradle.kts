plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    testImplementation("junit:junit:4.13.2")

}

android {
    compileSdkVersion(33)
    defaultConfig {
        applicationId = "com.liangguo.translator.android"
        minSdkVersion(24)
        targetSdkVersion(33)
        versionCode = 3
        versionName = "1.1.3"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    lintOptions {
        //打包release报错 ClassNotFound com.android.tools.lint.client.api.Vendor,加入这句试试
        isCheckReleaseBuilds = false
    }

}
