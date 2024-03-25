plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("com.google.cloud.tools.jib") version "3.4.1"
    id("io.micronaut.minimal.application") version "4.3.4"
    id("io.micronaut.graalvm") version "4.3.5"
    id("io.micronaut.docker") version "4.3.4"
    id("io.micronaut.aot") version "4.3.5"
}

group = "by.vk"
version = "1.0.0-RC1"


repositories {
    mavenCentral()
}

application {
    mainClass.set("by.vk.Application")
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson:2.8.2")
    implementation("io.micronaut.data:micronaut-data-r2dbc:4.6.2")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client:3.2.1")
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    runtimeOnly("org.yaml:snakeyaml")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.3")
    runtimeOnly("io.vertx:vertx-pg-client:4.5.6")
    runtimeOnly("io.r2dbc:r2dbc-pool:1.0.1.RELEASE")
    runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.4.RELEASE")
}

tasks {
    jib {
        to {
            image = "ghcr.io/fragaly/a2b-service:${project.name}"
        }
        from {
            image = "gcr.io/distroless/java21-debian12:latest"
        }
        container {
            jvmFlags = listOf("-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:InitialRAMPercentage=50.0", "-XX:+OptimizeStringConcat", "-XX:+UseStringDeduplication", "XX:+ExitOnOutOfMemoryError", "-XX:+AlwaysActAsServerClassMachine", "-Xmx512m", "-Xms128m", "-XX:MaxMetaspaceSize=128m", "-XX:MaxDirectMemorySize=256m", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/opt/tmp/heapdump.bin", "-Djava.rmi.server.hostname=localhost", "-Dcom.sun.management.jmxremote=true", "-Dcom.sun.management.jmxremote.rmi.port=8051", "-Dcom.sun.management.jmxremote.port=8051", "-Dcom.sun.management.jmxremote.local.only=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.ssl=false")
            ports = listOf("8080", "8051")
            labels.set(mapOf("maintainer" to "Vadzim Kavalkou <vadzim.kavalkou@gmail.com>", "appname" to "a2b-service", "version" to "1.0.1-RC1"))
            creationTime.set("USE_CURRENT_TIMESTAMP")
        }
    }
}

micronaut {
    runtime("netty")
    processing {
        incremental(true)
        annotations("by.vk.*")
    }
    aot {
        cacheEnvironment = true
        optimizeServiceLoading = true
        optimizeClassLoading = true
        convertYamlToJava = true
        precomputeOperations = true
        deduceEnvironment = true
        optimizeNetty = true
    }
}
