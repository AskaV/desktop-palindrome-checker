plugins {
    id("java")
    id("application")
    id("com.diffplug.spotless") version "8.4.0"

}

group = "com.aska.palindrom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

application {
    mainClass.set("com.aska.palindrom.Main")
}

spotless {
    java {
        googleJavaFormat()
    }
}