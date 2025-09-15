plugins {
    id("java")
    id("io.gatling.gradle") version "3.14.3.8"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "by.vk"
version = "1.0.0-RC1"

repositories {
    mavenCentral()
}

dependencies {
    //region gatling
    implementation("io.gatling:gatling-http:3.14.3")
    implementation("io.gatling:gatling-core:3.14.3")
    implementation("io.gatling:gatling-app:3.14.3")
    gatlingRuntimeOnly("io.gatling:gatling-charts:3.14.3")
    gatlingRuntimeOnly("io.gatling.highcharts:gatling-charts-highcharts:3.14.3")
    //endregion
}

object JVMProps {
    const val XMX = "2G"
    const val XMS = "1024m"
    const val XSS = "256k"
    const val MAX_METASPACE_SIZE = "128m"
    const val MAX_DIRECT_MEMORY_SIZE = "128m"
    const val HEAPDUMP_PATH = "/opt/tmp/heapdump.bin"
    const val MAX_RAM_PERCENTAGE = "80"
    const val INITIAL_RAM_PERCENTAGE = "50"
}

gatling {
    systemProperties = mapOf("file.encoding" to "UTF-8")
    jvmArgs = listOf("-server",
            "-Xss${JVMProps.XSS}",
            "-Xms${JVMProps.XMS}",
            "-Xmx${JVMProps.XMX}",
            "-XX:MaxMetaspaceSize=${JVMProps.MAX_METASPACE_SIZE}",
            "-XX:MaxDirectMemorySize=${JVMProps.MAX_DIRECT_MEMORY_SIZE}",
            "-XX:MaxRAMPercentage=${JVMProps.MAX_RAM_PERCENTAGE}",
            "-XX:InitialRAMPercentage=${JVMProps.INITIAL_RAM_PERCENTAGE}",
            "-XX:+HeapDumpOnOutOfMemoryError",
            "-XX:HeapDumpPath=${JVMProps.HEAPDUMP_PATH}",
            "-XX:+ParallelRefProcEnabled",
            "-XX:MaxInlineLevel=20",
            "-XX:MaxTrivialSize=12",
            "-XX:+ExitOnOutOfMemoryError",
            "-XX:+UseStringDeduplication",
            "-XX:+OptimizeStringConcat")
}

jib {
    to {
        image = "ghcr.io/fragaly/a2b-service:${project.name}"
    }
    from {
        image = "gcr.io/distroless/java21-debian12:latest"
    }
    container {
        mainClass = ""
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
        labels.set(mapOf("appname" to "a2b-service:${project.name}", "version" to version.toString(), "maintainer" to "Vadzim Kavalkou <vadzim.kavalkou@gmail.com>"))
        creationTime.set("USE_CURRENT_TIMESTAMP")
    }
}
