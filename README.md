[![Java CI with Gradle](https://github.com/fragaLY/performance-researches/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/fragaLY/performance-researches/actions/workflows/gradle.yml)

### INTRODUCTION

The purposes of this research are next:
1. Compare the performance of most commonly used frameworks, languages, and libraries;
2. Gain the experience in different non-related to previous primary focused stack, be hands-on with neighbor technologies;
3. Share results with the global community all over the world;

------------------------------------------------------------------------------------------------------------------------

### COMMON INFORMATION

* I disabled all application levels caches to get clear performance metrics of the server as it is;
* The database and application are running in the same network to avoid net lags and penalties;
* The fetching strategy is `EAGER`. First, to do a higher load and non-optimized queries to database. Second, not to spend time to setup entity graphs.
* The average time to boot up the service will not depend on the database structure due to the fact that I am not using any database migration tools such as [Flyway](https://flywaydb.org/) or [Liquibase](https://www.liquibase.org/);
* All database structures had been already prepared and filled with faked data;
* As a load testing tool I chose [Gatling](https://gatling.io/);
* The versions of all languages, frameworks, libraries, and databases are the latest LTS at the moment of testing.

------------------------------------------------------------------------------------------------------------------------

### DATABASE STRUCTURE

![](./env/database/database-structure.png)

------------------------------------------------------------------------------------------------------------------------

### DATASET DESCRIPTION

The definition of database [init script](./env/database/docker/database_definition.sql) and [docker-compose](./env/database/docker/docker-compose.yml) file.

To connect via pgadmin follow [pgadmin](localhost:5050) enter "user@user.com" and "password".
Create server and attach to host `docker inspect $(docker ps -aqf "name=postgres") | grep IPAddress`, port "5433", db "postgres" with "user" and "password" credentials.

|       UNIT      | AMOUNT |
|:----------------|:-------|
| Countries       | 1      |
| Cities          | 6      |
| Locations       | 120    |
| Transfers       | 12000  |
| Users           | 200000 |
| Users_Transfers | 500000 |

For a generation of the dataset, I've created the other module [datagen](./datagen). I will not dive deeper into data generation process.
You could easily check out how it works in sources. After that, I got DB size, and DB dump, and created a separate docker image with the predefined dataset.
This docker image is located in every tested module directory.

The DB metrics:

* Database size: 153 MB
* Database dump size: 130.8 MB
* Docker image with predefined data: 160.01 MB

------------------------------------------------------------------------------------------------------------------------

### USE CASES

| #    | Scenario step          | Assumed operation           |
| :--: | :--------------------- | :-------------------------- |
| 1    | As a user I would like to choose the country. | [GET] The list of available countries is presented. |
| 2    | As a user I would like to choose the city. | [GET] The list of available cities is presented. |
| 3    | As a user I would like to choose the origin. | [GET] The list of available origins is presented. |
| 4    | As a user I would like to choose the destination. | [GET] The list of available destinations is presented.  |
| 5    | As a user I would like to choose the date of transfer. | [GET] [NO LOAD] The future dates for the current month are presented. |
| 6    | As a user I would like to see all available transfers from 'origin', to 'destination' for selected this date. | [GET] The list of available transfers by selected origin, destination, and date is presented. |
| 7    | As a user I would like to book a transfer. | [POST] The transfer is booked in the system. |
| 8    | As a user I would like to see all my transfers. | [GET] The list of all my transfers (COMPLETED, CANCELED, BOOKED) is presented. |
| 9    | As a user I would like to view any owned transfer. | [GET] One of the transfers (COMPLETED, CANCELED, BOOKED) is retrieved. |
| 10   | As a user I would like to edit any owned transfer in the future. | [PUT] Any (BOOKED) transfer description is updated. |
| 11   | As a user I would like to cancel any owned transfer in the future. | [PUT] Any (BOOKED) transfer is canceled (CANCELED).  |
| 12   | As a user I would like to complete any owned transfer in the past. | [PUT] Any (BOOKED) transfer is completed (COMPLETED).  |
| 13   | As a user I would like to see my profile. | [GET] User is retrieved with her/his transfers data. |
| 14   | As a user I would like to update my profile. | [PUT] User updates with her/his own data. |
| 15   | Repeat (1) - (9) 20 minutes for each user. | |

The application is a transportation service.

Let's assume that the average user has the biggest interest to book the transfer from 8:00 till 00:00.
As a result, we will have 16 per day and 112 hours of load per week.

I've described all use cases endpoints via [OpenAPI](./env/api/a2b.yaml).

------------------------------------------------------------------------------------------------------------------------

### RESULTS

|FRAMEWORK|APPLICATION TYPE|BUILD TYPE                         |BUILD TIME (s)|ARTIFACT SIZE (MB)|BOOT UP (s)|TOTAL REQUESTS|KO(%)|RPS    |RESPONSE TIME (95th pct) (ms)|SATURATION POINT|RAM (MB)|CPU (%)|THREADS (MAX)|POSTGRES CPU (%)|
|:--------|:---------------|:----------------------------------|:-------------|:-----------------|:----------|:-------------|:----|:------|:----------------------------|:---------------|:-------|:------|:------------|:---------------|
|SPRING   |WEB             |NATIVE BUILD PACK                  |751           |144.79            |1,585      |453012        |25   |374.566|47831                        |584             |310     |12.5   |64           |99              |
|         |                |NATIVE BUILD TOOLS                 |210           |116.20            |0.310      |480763        |29   |414.785|32175                        |1829            |263     |8      |52           |99              |
|         |                |UNDERTOW                           |5             |49.70             |3.59       |523756        |24   |381.127|50977                        |1611            |658     |11     |33           |99              |
|         |                |UNDERTOW IN DOCKER                 |46            |280               |5.20       |430673        |33   |448.682|29998                        |916             |840     |15     |32           |99              |
|         |REACTIVE + R2DBC|NATIVE BUILD PACK                  |1243          |98.5              |0.103      |691487        |17   |615.750|17891                        |1904            |685     |30     |14           |70              |
|         |                |NATIVE BUILD TOOLS                 |187           |71.7              |0,107      |1013549       |10   |934.147|12591                        |3038            |634     |32     |23           |70              |
|         |                |JAR                                |3.1           |40.6              |2.55       |1168782       |8    |1091.30|10406                        |4391            |1823    |8      |31           |70              |
|         |                |JAR IN DOCKER                      |39            |271               |3.95       |699180        |17   |631.599|18955                        |2250            |883     |29     |31           |70              |
|         |                |                                   |              |                  |           |              |     |       |                             |                |        |       |             |                |
|QUARKUS  |REACTIVE + R2DBC|FAST JAR                           |4             |N/A               |0.987      |828711        |13   |755.434|13686                        |1971            |1054    |9      |25           |99              |
|         |                |UBER JAR                           |8             |17.7              |1.884      |826311        |13   |753.933|14111                        |2149            |989     |5      |23           |99              |
|         |                |JIB WITH UBI                       |16            |384               |1.151      |661502        |18   |593.275|20170                        |1305            |1054    |8      |26           |70              |
|         |                |JIB WITH DISTROLESS                |14            |249               |1.088      |473991        |20   |540.492|33060                        |1339            |970     |8      |26           |93              |
|         |                |DOCKER                             |39            |416               |0.948      |609675        |28   |428.563|24206                        |1315            |262     |18     |21           |53              |
|         |                |NATIVE EXECUTABLE                  |180           |49.3              |0.223      |768017        |15   |697.563|16426                        |1967            |646     |10     |15           |99              |
|         | + UPX-MAX      |NATIVE EXECUTABLE                  |741           |15                |N/A        |N/A           |N/A  |N/A    |N/A                          |N/A             |N/A     |N/A    |N/A          |N/A             |
|         |                |NATIVE MICRO BASE IMAGE            |301           |78.6              |0.031      |570959        |22   |507.971|25637                        |1282            |690     |20     |8            |57              |
|         |                |NATIVE MINIMAL BASE IMAGE          |301           |152               |0.025      |523534        |25   |448.231|35777                        |914             |669     |17     |8            |61              |
|         |                |NATIVE DISTROLESS BASE IMAGE *     |238           |72.1              |0.032      |546371        |23   |473.458|30156                        |1747            |622     |23     |8            |45              |
|         |                |NATIVE DISTROLESS BASE IMAGE * + **|238           |72.1              |0.037      |584874        |21   |515.762|23786                        |2254            |628     |17     |8            |47              |
|         |                |                                   |              |                  |           |              |     |       |                             |                |        |       |             |                |
|MICRONAUT|REACTIVE + R2DBC|JAR                                |17            |21.4              |1.176      |683907        |17   |616.132|19540                        |2487            |4495    |10     |49           |75              |
|         |                |DEFAULT DOCKER IMAGE               |74            |346               |2.373      |488076        |27   |425.895|41730                        |1189            |586     |59     |23           |26              |
|         |                |JIB                                |21            |252               |1.767      |489590        |27   |416.672|37474                        |1023            |586     |26     |60           |31              |
|         |                |NATIVE EXECUTABLE                  |188           |78                |0.042      |604610	      |20   |534.580|25728                        |1717            |762     |38     |35           |65              |
|         |                |DEFAULT NATIVE DOCKER IMAGE        |457           |99.3              |0.115      |371624        |44   |338.455|46090                        |1072            |419.2   |50     |14           |25              |
|         |                |AOT OPTIMIZED DOCKER IMAGE         |5808          |99.4              |0.088      |339333        |49   |308.204|53506                        |1095            |381     |60     |15           |12              |
|         |                |                                   |              |                  |           |              |     |       |                             |                |        |       |             |                |

* [SPRING WEB AND SPRING WEB AS NATIVE](https://github.com/fragaLY/blog/blob/main/spring-boot-web_vs_spring-boot-web-native/SPRING-BOOT-WEB_VS_SPRING-BOOT-WEB-NATIVE.md)
* [SPRING REACTIVE AND SPRING REACTIVE AS NATIVE](https://github.com/fragaLY/blog/blob/main/spring-boot-reactive_vs_spring-boot-reactive-native/SPRING-BOOT-REACTIVE_VS_SPRING-BOOT-REACTIVE-NATIVE.md)
* [QUARKUS REACTIVE AND QUARKUS REACTIVE AS NATIVE](https://github.com/fragaLY/blog/blob/main/quarkus-reactive_vs_quarkus-reactive-native/QUARKUS-REACTIVE_VS_QUARKUS-REACTIVE-NATIVE.md)
* [MICRONAUT REACTIVE AND MICRONAUT REACTIVE AS NATIVE](https://github.com/fragaLY/blog/blob/main/micronaut-reactive_vs_micronaut-reactive-native/MICRONAUT-REACTIVE_VS_MICRONAUT-REACTIVE-NATIVE.md)

------------------------------------------------------------------------------------------------------------------------

### SUPPORT THE PROJECT

* TON: vadzimkavalkou
