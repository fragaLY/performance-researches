plugins {
    java
    application
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
    id("com.google.cloud.tools.jib") version "3.4.2"
}

group = "by.vk"
version = "1.0.0-RC1"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

springBoot {
    buildInfo()
}

repositories {
    mavenCentral()
}

dependencies {
    //region spring
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework:spring-context-indexer")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    //endregion
    //region logback
    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    //endregion
    //region lombok
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.projectlombok:lombok")
    //endregion
    //region postgres
    runtimeOnly("org.postgresql:postgresql")
    //endregion
}

object JVMProps {
    const val XMX = "512m"
    const val XMS = "256m"
    const val XSS = "256k"
    const val MAX_METASPACE_SIZE = "128m"
    const val MAX_DIRECT_MEMORY_SIZE = "128m"
    const val HEAPDUMP_PATH = "/opt/tmp/heapdump.bin"
    const val MAX_RAM_PERCENTAGE = "80"
    const val INITIAL_RAM_PERCENTAGE = "50"
}

jib {
    to {
        image = "ghcr.io/fragaly/a2b-service:${project.name}"
    }
    from {
        image = "gcr.io/distroless/java21-debian12:latest"
    }
    container {
        jvmFlags = listOf(
                "-Xss${JVMProps.XSS}",
                "-Xmx${JVMProps.XMX}",
                "-Xms${JVMProps.XMS}",
                "-XX:MaxMetaspaceSize=${JVMProps.MAX_METASPACE_SIZE}",
                "-XX:MaxDirectMemorySize=${JVMProps.MAX_DIRECT_MEMORY_SIZE}",
                "-XX:MaxRAMPercentage=${JVMProps.MAX_RAM_PERCENTAGE}",
                "-XX:InitialRAMPercentage=${JVMProps.INITIAL_RAM_PERCENTAGE}",
                "-XX:+HeapDumpOnOutOfMemoryError",
                "-XX:HeapDumpPath=${JVMProps.HEAPDUMP_PATH}",
                "-XX:+UseContainerSupport",
                "-XX:+OptimizeStringConcat",
                "-XX:+UseStringDeduplication",
                "-XX:+ExitOnOutOfMemoryError",
                "-XX:+AlwaysActAsServerClassMachine")
        ports = listOf("8080")
        labels.set(mapOf("appname" to application.applicationName, "version" to version.toString(), "maintainer" to "Vadzim Kavalkou <vadzim.kavalkou@gmail.com>"))
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
}
