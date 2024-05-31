plugins {
    java
    application
    id("org.hibernate.orm")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    id("org.graalvm.buildtools.native") version "0.10.1"
    id("com.google.cloud.tools.jib") version "3.4.3"
}

group = "by.vk"
version = "1.0.0-RC1"

springBoot {
    buildInfo()
}

repositories {
    mavenCentral()
}

dependencies {
    //region spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
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

tasks.bootBuildImage {
    buildpacks.set(listOf("gcr.io/paketo-buildpacks/java-native-image:latest"))
    builder.set("paketobuildpacks/builder:tiny")
    environment.set(mapOf("BP_NATIVE_IMAGE" to "true"))
}

hibernate {
    enhance(closureOf<org.hibernate.orm.tooling.gradle.EnhanceExtension> {
        enableLazyInitialization = false
        enableDirtyTracking = true
        enableAssociationManagement = true
        enableExtendedEnhancement = false
    })
}

jib {
    to {
        image = "ghcr.io/fragaly/a2b-service:${project.name}"
    }
    from {
        image = "gcr.io/distroless/java21-debian12:latest"
    }
    container {
        ports = listOf("8080")
        labels.set(mapOf("appname" to application.applicationName, "version" to version.toString(), "maintainer" to "Vadzim Kavalkou <vadzim.kavalkou@gmail.com>"))
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
}
