plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "nl.janjongerden"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("de.focus-shift:jollyday-core:0.24.0")
    implementation("de.focus-shift:jollyday-jackson:0.23.2")
    implementation("com.github.ajalt.clikt:clikt:4.2.2")

    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

application {
    // Specify the main class for the project
    mainClass = "nl.janjongerden.MainKt"
}

