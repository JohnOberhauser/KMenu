import org.jetbrains.compose.compose

plugins {
    `kotlin-dsl`
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(Deps.JetBrains.Kotlin.coroutines)
    implementation(project(":common"))
}

compose.desktop {
    application {
        mainClass = "io.obez.kmenu.MainKt"
    }
}