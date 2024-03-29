plugins {
    java
    id("io.quarkus") version "3.9.0"
}

group = "by.vk"
version = "1.0.0-RC1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    //region quarkus
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.9.1"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-config-yaml")
    implementation("io.quarkus:quarkus-logging-json")
    implementation("io.quarkus:quarkus-arc")
//    implementation("io.quarkus:quarkus-container-image-jib") // remove it if you want to build fast or uber jar
//    implementation("io.quarkus:quarkus-container-image-docker") // remove it if you want to build fast or uber jar
//    implementation("io.quarkus:quarkus-container-image-s2i") // remove it if you want to build fast or uber jar
//    implementation("io.quarkus:quarkus-container-image-buildpack") // remove it if you want to build fast or uber jar
    //endregion
    //region lombok
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.projectlombok:lombok:1.18.32")
    //endregion
}

tasks.quarkusBuild {
    nativeArgs {
        "container-build" to true
        "builder-image" to "quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21"
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
