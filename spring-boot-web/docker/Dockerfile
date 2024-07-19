FROM gradle:jdk21-alpine AS builder
WORKDIR build
COPY --chown=gradle:gradle . /home/gradle/src
RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine AS layers
WORKDIR layer
COPY --from=builder /build/libs/spring-boot-web-1.0.0-RC1.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app
COPY --from=layers /layer/dependencies/ ./
COPY --from=layers /layer/spring-boot-loader/ ./
COPY --from=layers /layer/snapshot-dependencies/ ./
COPY --from=layers /layer/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]