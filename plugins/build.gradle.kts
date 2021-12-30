import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.16.0"
}

java.sourceCompatibility = JavaVersion.VERSION_11

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val kotlinVersion = "1.5.21"
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
}

group= "io.github.jhvictor4"
version= "1.0.0"

pluginBundle {
    website = "https://github.com/Jhvictor4/gradle-plugins"
    vcsUrl = "https://github.com/Jhvictor4/gradle-plugins"
    tags = listOf("docker", "easy build")
}

gradlePlugin {
    plugins {
        create("dockerPlugin") {
            id = "io.github.jhvictor4.docker"
            displayName = "docker"
            description = "basic docker build & publishing plugin"
            implementationClass = "com.github.jhvictor4.plugin.DockerPlugIn"
        }
    }
}