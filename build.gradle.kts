import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.rest-assured:kotlin-extensions:5.1.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.create("testSucces", Test::class){
    useJUnitPlatform() {
        includeTags("success")
    }
    testLogging {
        events(org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,)
    }
}

tasks.create("failTest", Test::class){
    useJUnitPlatform() {
        includeTags("fail")
    }
    testLogging {
        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}