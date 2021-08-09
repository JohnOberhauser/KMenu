import org.jetbrains.compose.compose

plugins {
    `kotlin-dsl`
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
}