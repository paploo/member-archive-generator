import org.gradle.kotlin.dsl.provideDelegate
import com.adarshr.gradle.testlogger.theme.ThemeType

val kotlin_serialization_version: String by project
val kotlin_coroutines_version: String by project
val arg_parser_version: String by project
val commons_csv_version: String by project
val kotest_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.serialization") version "2.3.0"
    application
    id("com.adarshr.test-logger") version "4.0.0"
}

group = "org.shintoinari"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlin_coroutines_version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${kotlin_serialization_version}")

    implementation("ch.qos.logback:logback-classic:${logback_version}")
    implementation("com.xenomachina:kotlin-argparser:${arg_parser_version}")
    implementation("org.apache.commons:commons-csv:${commons_csv_version}")

    testImplementation("io.kotest:kotest-runner-junit5:${kotest_version}")
    testImplementation("io.kotest:kotest-assertions-core:${kotest_version}")
}

tasks.test {
    useJUnitPlatform()
    testlogger {
        //theme = ThemeType.STANDARD_PARALLEL
        theme = ThemeType.PLAIN_PARALLEL
    }
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("org.shintoinari.memberarchivegenerator.MainKt")
}