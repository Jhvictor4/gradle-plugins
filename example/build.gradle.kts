plugins {
    id("com.github.jhvictor4.docker")
    id("org.springframework.boot") version "2.6.0" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

apply {
    plugin("org.jetbrains.kotlin.jvm")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("kotlin-allopen")
    plugin("kotlin-spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
}