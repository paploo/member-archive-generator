import org.gradle.kotlin.dsl.provideDelegate

val kotlin_serialization_version: String by project
val kotlin_coroutines_version: String by project
val arg_parser_version: String by project
val commons_csv_version: String by project
val kotest_version: String by project

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    application
}

group = "org.shintoinari"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlin_coroutines_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${kotlin_serialization_version}")

    implementation("com.xenomachina:kotlin-argparser:${arg_parser_version}")

    implementation("org.apache.commons:commons-csv:${commons_csv_version}")

    testImplementation("io.kotest:kotest-runner-junit5:${kotest_version}")
    testImplementation("io.kotest:kotest-assertions-core:${kotest_version}")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("org.shintoinari.memberarchivegenerator.MainKt")
}