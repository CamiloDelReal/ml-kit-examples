import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven


object Plugins {
    private const val GRADLE_VERSION = "4.1.0"
    private const val KOTLIN_VERSION = "1.4.10"
    private const val HILT_VERSION = "2.32-alpha"
    private const val NAVIGATION_SAFE_ARGS_VERSION = "2.3.1"

    const val GRADLE = "com.android.tools.build:gradle:$GRADLE_VERSION"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val HILT = "com.google.dagger:hilt-android-gradle-plugin:$HILT_VERSION"
    const val NAVIGATION_SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_SAFE_ARGS_VERSION"
}

object Build {
    const val SDK_VERSION = 30
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 30
    const val BUILD_TOOLS_VERSION = "30.0.2"
    const val APPLICATION_ID = "org.xapps.examples.mlkitexamples"
    const val APP_NAME = "ML Kit Examples"
    const val MAJOR_VERSION = 0
    const val MINOR_VERSION = 1
    const val PATH_VERSION = 0
    const val STATUS_VERSION = ""
}

object Repositories {
    fun addBuildScriptRepositories(handler: RepositoryHandler) {
        handler.google()
        handler.jcenter()
        //handler.gradlePluginPortal()  // Unstable
        handler.maven(url = "https://plugins.gradle.org/m2")
        handler.maven(url = "https://maven.fabric.io/public")
        handler.maven(url = "https://clojars.org/repo/")
        handler.maven(url = "https://repo1.maven.org/maven2")
        handler.maven(url = "https://jitpack.io")
    }

    fun addProjectRepositories(handler: RepositoryHandler) {
        handler.google()
        handler.jcenter()
        handler.mavenCentral()
        handler.maven(url = "https://clojars.org/repo/")
        handler.maven(url = "https://repo1.maven.org/maven2")
        handler.maven(url = "https://jitpack.io")
        handler.maven(url = "http://dl.bintray.com/piasy/maven")
    }
}

object Libraries {
    object Kotlin {
        private const val CORE_VERSION = "1.3.2"
        private const val COROUTINES_VERSION = "1.4.0-M1"

        const val MODULE = "stdlib-jdk8"
        const val CORE = "androidx.core:core-ktx:$CORE_VERSION"
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINES_VERSION"
        const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    }

    object Jetpack {
        object MultiDex {
            private const val VERSION = "2.0.1"

            const val CORE = "androidx.multidex:multidex:$VERSION"
        }

        object UI {
            private const val CONSTRAINT_LAYOUT_VERSION = "2.0.4"
            private const val MATERIAL_VERSION = "1.2.1"
            private const val APP_COMPAT_VERSION = "1.2.0"

            const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION"
            const val MATERIAL = "com.google.android.material:material:$MATERIAL_VERSION"
            const val APP_COMPAT = "androidx.appcompat:appcompat:$APP_COMPAT_VERSION"
        }
        
        object Extensions {
            private const val ACTIVITY_KOTLIN_EXT_VERSION = "1.1.0"
            private const val FRAGMENT_KOTLIN_EXT_VERSION = "1.2.5"

            const val ACTIVITY_KOTLIN_EXT = "androidx.activity:activity-ktx:$ACTIVITY_KOTLIN_EXT_VERSION"
            const val FRAGMENT_KOTLIN_EXT = "androidx.fragment:fragment-ktx:$FRAGMENT_KOTLIN_EXT_VERSION"
        }

        object Navigation {
            private const val VERSION = "2.3.1"

            const val UI_KTX = "androidx.navigation:navigation-ui-ktx:$VERSION"
            const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$VERSION"
        }

        object AnnotationSupport {
            private const val ANNOTATION_VERSION = "1.1.0"
            private const val LEGACY_SUPPORT_VERSION = "1.0.0"

            const val ANNOTATION = "androidx.annotation:annotation:$ANNOTATION_VERSION"
            const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:$LEGACY_SUPPORT_VERSION"
        }

        object SharedPreferences {
            private const val VERSION = "1.1.1"

            const val CORE = "androidx.preference:preference:$VERSION"
            const val CORE_KTX = "androidx.preference:preference-ktx:$VERSION"
        }

        object Lifecycle {
            private const val VERSION = "2.2.0"

            const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$VERSION"
            const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
            const val LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:$VERSION"
            const val VIEWMODEL_SAVED_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$VERSION"
            const val COMPILER = "androidx.lifecycle:lifecycle-compiler:$VERSION"
        }
    }

    object CameraX {
        private const val CAMERAX_VERSION = "1.1.0-alpha03"
        private const val CAMERAX_VIEW_VERSION = "1.0.0-alpha23"

        const val CAMERAX = "androidx.camera:camera-camera2:$CAMERAX_VERSION"
        const val CAMERAX_LIFECYCLE = "androidx.camera:camera-lifecycle:$CAMERAX_VERSION"
        const val CAMERAX_VIEW = "androidx.camera:camera-view:$CAMERAX_VIEW_VERSION"
    }

    object MLKit {
        private const val TEXT_RECOGNITION_VERSION = "16.1.3"

        private const val BARCODE_SCANNING_BUNDLE_VERSION = "16.1.1"
        private const val BARCODE_SCANNING_VERSION = "16.1.4"

        private const val FACE_DETECTION_BUNDLE_VERSION = "16.0.4"
        private const val FACE_DETECTION_VERSION = "16.1.3"

        private const val IMAGE_LABELING_BUNDLE_VERSION = "17.0.2"
        private const val IMAGE_LABELING_VERSION = "16.0.2"

        // Dynamic download or from Manifest
        const val TEXT_RECOGNITION = "com.google.android.gms:play-services-mlkit-text-recognition:$TEXT_RECOGNITION_VERSION"

        // Bundle inside apk in compilation time
        const val BARCODE_SCANNING_BUNDLE = "com.google.mlkit:barcode-scanning:$BARCODE_SCANNING_BUNDLE_VERSION"
        // Dynamic download or from Manifest
        const val BARCODE_SCANNING = "com.google.android.gms:play-services-mlkit-barcode-scanning:$BARCODE_SCANNING_VERSION"

        // Bundle inside apk in compilation time
        const val FACE_DETECTION_BUNDLE = "com.google.mlkit:face-detection:$FACE_DETECTION_BUNDLE_VERSION"
        // Dynamic download or from Manifest
        const val FACE_DETECTION = "com.google.android.gms:play-services-mlkit-face-detection:$FACE_DETECTION_VERSION"

        // Bundle inside apk in compilation time
        const val IMAGE_LABELING_BUNDLE = "com.google.mlkit:image-labeling:$IMAGE_LABELING_BUNDLE_VERSION"
        // Dynamic download or from Manifest
        const val IMAGE_LABELING = "com.google.android.gms:play-services-mlkit-image-labeling:$IMAGE_LABELING_VERSION"
    }

    object Dagger {
        private const val VERSION = "2.29.1"

        const val COMPILER = "com.google.dagger:dagger-compiler:$VERSION"
        const val ANDROID = "com.google.dagger:dagger-android:$VERSION"
    }

    object Hilt {
        private const val VERSION = "2.32-alpha"
        private const val JETPACK_VERSION = "1.0.0-alpha03"

        const val CORE = "com.google.dagger:hilt-android:$VERSION"
        const val COMPILER = "com.google.dagger:hilt-compiler:$VERSION"
        const val VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:$JETPACK_VERSION"
        const val ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:$VERSION"
    }

    object Permissions {
        private const val DEXTER_VERSION = "6.1.0"

        const val DEXTER = "com.karumi:dexter:$DEXTER_VERSION"
    }

    object Logger {

        object Timber {
            private const val VERSION = "4.7.1"

            const val CORE = "com.jakewharton.timber:timber:$VERSION"
        }

    }

}

object Test {
    object Junit {
        private const val VERSION = "4.12"
        private const val EXT_VERSION = "1.1.1"
        const val  JUNIT = "junit:junit:$VERSION"
        const val EXT_JUNIT = "androidx.test.ext:junit:$EXT_VERSION"
    }

    object Espresso {
        private const val VERSION = "3.3.0"
        const val CORE = "androidx.test.espresso:espresso-core:$VERSION"
        const val CONTRIB = "androidx.test.espresso:espresso-contrib:$VERSION"
        const val INTENTS = "androidx.test.espresso:espresso-intents:$VERSION"
    }

    object Coroutines {
        private const val VERSION = "1.3.7"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
    }
}