import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.stream"
version = "1.0.0"

plugins {
    kotlin("jvm")
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "0.10.1"
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
    withSourcesJar()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":lib"))

    testImplementation("junit:junit:4.13.2")
}


val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

