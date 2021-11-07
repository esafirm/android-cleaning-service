import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.stream.cleaningservice"
version = "1.0.0"

plugins {
    kotlin("jvm") version "1.5.21"
    `maven-publish`
}

repositories {
    mavenCentral()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    implementation("org.jdom:jdom2:2.0.6")

    val kotestVersion = "4.6.3"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}
