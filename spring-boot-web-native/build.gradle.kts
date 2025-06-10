plugins {
    java
    application
    id("org.hibernate.orm") version "6.6.7.Final"
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.10.6"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "by.vk"
version = "1.0.0-RC1"

springBoot {
    buildInfo()
}

//hibernate {
//    enhancement {
//        enableDirtyTracking.set(true)
//        enableLazyInitialization.set(false)
//        enableAssociationManagement.set(true)
//        enableExtendedEnhancement.set(false)
//    }
//}

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

jib {
    to {
        image = "fragaly/a2b-service:${project.name}"
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
