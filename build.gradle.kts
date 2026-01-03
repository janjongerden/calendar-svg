plugins {
    kotlin("jvm") version "2.3.0"
    application
    id("com.github.ben-manes.versions") version "0.53.0"
}

group = "nl.janjongerden"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.14")
    implementation("de.focus-shift:jollyday-core:1.8.0")
    implementation("de.focus-shift:jollyday-jackson:1.8.0")
    implementation("com.github.ajalt.clikt:clikt:5.0.3")

    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "nl.janjongerden.MainKt"
}

