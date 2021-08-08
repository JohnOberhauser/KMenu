import org.jetbrains.compose.compose

plugins {
    `kotlin-dsl`
    id("org.jetbrains.compose")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "io.obez.kmenu.MainKt"
    }
}