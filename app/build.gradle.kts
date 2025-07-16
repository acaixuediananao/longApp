plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.newangle.healthy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.newangle.healthy"
        minSdk = 28
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas".toString())
            }
        }
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
    viewBinding {
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.androidx.lifecycle.runtime.compose)
    // MVVM架构组件
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    // 网络请求（Retrofit + Kotlin序列化）
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.logging.interceptor) // 日志拦截器
    // 依赖注入（Hilt）
    implementation(libs.dagger.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.dagger.hilt.navigation.compose) // Compose导航集成

    // 本地存储（Room）
    implementation(libs.room.runtime)
    implementation(libs.room.runtime.ktx)
    kapt(libs.androidx.room.compiler)
    implementation(libs.datastore.preferences) // 轻量数据存储

    // 辅助工具
    implementation(libs.coroutines.core) // 协程
    implementation (libs.logger)
}