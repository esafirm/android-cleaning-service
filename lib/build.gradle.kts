import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "nolambda.stream"
version = "1.0.0"

plugins {
    kotlin("jvm")
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
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "cleaningservice"
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jdom:jdom2:2.0.6")

    val kotestVersion = "4.6.3"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}
