import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.cleaningservice"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.3.70"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
    `maven-publish`
}

repositories {
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("cleaningservice") {
            id = "nolambda.stream.cleaningservice"
            implementationClass = "nolambda.stream.nolambda.stream.cleaningservice.CleaningServicePlugin"
        }
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":lib"))

    testImplementation("junit:junit:4.12")
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
