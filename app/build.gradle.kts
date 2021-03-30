import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.text.SimpleDateFormat
import java.util.*


plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion(Build.SDK_VERSION)
    buildToolsVersion = Build.BUILD_TOOLS_VERSION

    defaultConfig {
        applicationId = Build.APPLICATION_ID

        minSdkVersion(Build.MIN_SDK_VERSION)
        targetSdkVersion(Build.TARGET_SDK_VERSION)

        versionCode =
            ((Build.MAJOR_VERSION * 10000) + (Build.MINOR_VERSION * 100) + Build.PATH_VERSION)
        versionName =
            "${Build.MAJOR_VERSION}.${Build.MINOR_VERSION}.${Build.PATH_VERSION}${Build.STATUS_VERSION}"

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
//        dexOptions.incremental = true
        dexOptions.preDexLibraries = false
        dexOptions.javaMaxHeapSize = "4g"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    (this as ExtensionAware).configure < KotlinJvmOptions > {
        jvmTarget = "1.8"
    }

    android.buildFeatures.dataBinding = true

    sourceSets["main"].java.srcDirs("src/main/kotlin")
    sourceSets["androidTest"].java.srcDirs("src/androidTest/kotlin")
    sourceSets["test"].java.srcDirs("src/test/kotlin")
    sourceSets["debug"].java.srcDirs("src/debug/kotlin")

    buildTypes["debug"].apply {
        isMinifyEnabled = false
    }

    buildTypes["release"].apply {
        isMinifyEnabled = true

        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )

        applicationVariants.all(object: Action<ApplicationVariant> {
            override fun execute(variant: ApplicationVariant) {
                variant.outputs.all(object: Action<BaseVariantOutput> {
                    override fun execute(output: BaseVariantOutput) {
                        val outputImpl = output as BaseVariantOutputImpl
                        val currentDate = Date()
                        val formatter = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                        val timestamp = formatter.format(currentDate)
                        val fileName =
                            "${Build.APP_NAME} ${Build.MAJOR_VERSION}.${Build.MINOR_VERSION}.${Build.PATH_VERSION}${Build.STATUS_VERSION} $timestamp.apk"
                        outputImpl.outputFileName = fileName
                    }
                })
            }
        })
    }

}

dependencies {
    // Custom JARs
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))

    // Kotlin
    implementation(kotlin(Libraries.Kotlin.MODULE, KotlinCompilerVersion.VERSION))
    implementation(Libraries.Kotlin.CORE)
    implementation(Libraries.Kotlin.COROUTINES_CORE)
    implementation(Libraries.Kotlin.COROUTINES_ANDROID)

    // Jetpack MultiDex
    implementation(Libraries.Jetpack.MultiDex.CORE)
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${rootProject.extra["kotlin_version"]}")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // Jetpack Annotations Support
    kapt(Libraries.Jetpack.AnnotationSupport.ANNOTATION)
    implementation(Libraries.Jetpack.AnnotationSupport.LEGACY_SUPPORT)

    // Jetpack UI
    implementation(Libraries.Jetpack.UI.CONSTRAINT_LAYOUT)
    implementation(Libraries.Jetpack.UI.MATERIAL)
    implementation(Libraries.Jetpack.UI.APP_COMPAT)

    // Jetpack Extensions
    implementation(Libraries.Jetpack.Extensions.ACTIVITY_KOTLIN_EXT)
    implementation(Libraries.Jetpack.Extensions.FRAGMENT_KOTLIN_EXT)

    // Jetpack Navigation
    implementation(Libraries.Jetpack.Navigation.UI_KTX)
    implementation(Libraries.Jetpack.Navigation.FRAGMENT_KTX)

    // Jetpack Shared Preferences
    implementation(Libraries.Jetpack.SharedPreferences.CORE_KTX)

    // Jetpack Lifecycle
    implementation(Libraries.Jetpack.Lifecycle.RUNTIME_KTX)
    implementation(Libraries.Jetpack.Lifecycle.EXTENSIONS)
    implementation(Libraries.Jetpack.Lifecycle.VIEWMODEL_KTX)
    implementation(Libraries.Jetpack.Lifecycle.VIEWMODEL_SAVED_STATE)
    implementation(Libraries.Jetpack.Lifecycle.LIVEDATA_KTX)
    kapt(Libraries.Jetpack.Lifecycle.COMPILER)

    // Dagger
    kapt(Libraries.Dagger.COMPILER)
    implementation(Libraries.Dagger.ANDROID)

    // Hilt
    implementation(Libraries.Hilt.CORE)
    kapt(Libraries.Hilt.COMPILER)
    implementation(Libraries.Hilt.VIEWMODEL)
    kapt(Libraries.Hilt.ANDROID_COMPILER)

    // Permissions
    implementation(Libraries.Permissions.DEXTER)

    // CameraX
    implementation(Libraries.CameraX.CAMERAX)
    implementation(Libraries.CameraX.CAMERAX_LIFECYCLE)
    implementation(Libraries.CameraX.CAMERAX_VIEW)

    // ML Kit
    implementation(Libraries.MLKit.TEXT_RECOGNITION)
    implementation(Libraries.MLKit.BARCODE_SCANNING)
    implementation(Libraries.MLKit.FACE_DETECTION)
    implementation(Libraries.MLKit.IMAGE_LABELING)

    // Logger
    implementation(Libraries.Logger.Timber.CORE)

//    // JUnit
//    testImplementation(Test.Junit.JUNIT)
//    testImplementation(Test.Junit.EXT_JUNIT)
//
//    // Test
//    androidTestImplementation (Test.Espresso.CORE)
//    androidTestImplementation (Test.Espresso.CONTRIB)
//    androidTestImplementation (Test.Espresso.INTENTS)
}