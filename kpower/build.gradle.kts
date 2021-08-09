import org.jetbrains.compose.compose

plugins {
    `kotlin-dsl`
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(project(":common"))
}

compose.desktop {
    application {
        mainClass = "io.obez.kpower.MainKt"
    }
}