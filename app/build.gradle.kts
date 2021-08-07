import org.jetbrains.compose.compose

plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(Deps.JetBrains.Kotlin.coroutines)
}

compose.desktop {
    application {
        mainClass = "io.obez.kmenu.MainKt"
    }
}