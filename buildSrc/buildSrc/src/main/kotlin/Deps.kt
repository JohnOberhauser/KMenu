object Deps {
    object JetBrains {
        object Kotlin {
            const val VERSION = "1.5.10"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1"
        }
        object Compose {
            private const val VERSION = "0.5.0-build228"
            const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$VERSION"
        }
    }
}