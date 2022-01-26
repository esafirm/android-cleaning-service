import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.cleaningservice.plugin"
version = "1.0.0"

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("cleaningservice") {
            id = "nolambda.stream.cleaningservice"
            implementationClass = "nolambda.stream.cleaningservice.plugin.CleaningServicePlugin"
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    shadow(project(":lib"))

    testImplementation("junit:junit:4.13.2")
    testImplementation("com.github.stefanbirkner:system-rules:1.19.0")
}


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

