plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
    kotlin("plugin.serialization").version("1.7.20")

}

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                api(compose.materialIconsExtended)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.2")
                api("androidx.core:core-ktx:1.9.0")
                api("com.github.ldh-star:EasyingContext:1.0.4")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                api(compose.preview)
                api("org.jetbrains.compose.ui:ui-graphics-desktop:1.2.0")

            }
        }
        val desktopTest by getting

        dependencies {
            val ktor_version: String by project
            commonMainImplementation("io.ktor:ktor-client-core:$ktor_version")
            commonMainImplementation("io.ktor:ktor-client-cio:$ktor_version")
            commonMainImplementation("io.ktor:ktor-client-okhttp:$ktor_version")
            commonMainImplementation("io.ktor:ktor-client-logging:$ktor_version")
            commonMainImplementation("io.ktor:ktor-client-serialization:$ktor_version")

            commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
            commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
            commonMainImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")
            commonMainImplementation("org.jetbrains.kotlin:kotlin-reflect:1.7.20")
            commonMainImplementation("com.google.code.gson:gson:2.9.0")
            commonMainImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
            commonMainImplementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")

            commonMainImplementation("com.gitee.liang_dh:KtPref:1.0.0")

            testImplementation("junit:junit:4.13.2")
        }

    }
}

android {
    compileSdkVersion(33)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(33)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
