package by.vk.scenario.a2b;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class A2BSimulation extends Simulation {
    private static final Iterator<Map<String, Object>> FEEDER = Stream.generate((Supplier<Map<String, Object>>) () -> Collections.singletonMap("userId", ThreadLocalRandom.current().nextLong(1, 200_001))).iterator();

    final HttpProtocolBuilder protocol = http
            .warmUp("https://www.google.com")
            .baseUrl("http://localhost:8080/api/v1")
            .acceptHeader("application/json")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36");

    final ScenarioBuilder scenario = scenario("A2B simulation")
            .feed(FEEDER)
            .exec(http("[GET] The list of available countries is presented.")
                    .get("/countries")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].countryId").findRandom().saveAs("countryId"))
            )
            .exitHereIfFailed()
            .pause(1, 3)
            .exec(http("[GET] The list of available cities is presented.")
                    .get("/countries/#{countryId}/cities")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].cityId").findRandom().saveAs("cityOriginId"))
            )
            .exitHereIfFailed()
            .pause(1, 3)
            .exec(http("[GET] The list of available origins is presented.")
                    .get("/countries/#{countryId}/cities/#{cityOriginId}/locations")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].locationId").findRandom().saveAs("originId"))
            )
            .pause(3, 5)
            .exec(http("[GET] The list of available cities is presented.")
                    .get("/countries/#{countryId}/cities")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].cityId").findRandom().saveAs("cityDestinationId"))
            )
            .exitHereIfFailed()
            .pause(1, 3)
            .exec(http("[GET] The list of available destinations is presented.")
                    .get("/countries/#{countryId}/cities/#{cityDestinationId}/locations")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].locationId").findRandom().saveAs("destinationId"))
            )
            .exitHereIfFailed()
            .pause(3, 5)
            .exec(http("[GET] The list of available transfers by selected origin, destination, and date is presented.")
                    .get("/transfers")
                    .queryParam("originId", "#{originId}")
                    .queryParam("destinationId", "#{destinationId}")
                    .queryParam("date", "1970-01-01")
                    .check(status().in(HttpResponseStatus.OK.code(), HttpResponseStatus.NOT_FOUND.code()))
                    .check(jsonPath("$[:].transferId").findRandom().saveAs("transferId"))
            )
            .exitHereIfFailed()
            .pause(3, 5)
            .exec(http("[POST] The transfer is booked in the system.")
                    .post("/users/#{userId}/transfers/#{transferId}")
                    .check(status().is(HttpResponseStatus.CREATED.code()))
                    .body(StringBody("{ \"description\": \"My internal uuid is " + UUID.randomUUID() + "\" }"))
                    .asJson()
            )
            .pause(1, 3)
            .exec(http("[GET] The list of all my transfers (COMPLETED, CANCELED, BOOKED) is presented.")
                    .get("/users/#{userId}/transfers")
                    .check(status().is(HttpResponseStatus.OK.code()))
                    .check(jsonPath("$[:].transfer.transferId").findRandom().saveAs("selectedUserTransferId"))
            )
            .exitHereIfFailed()
            .pause(1, 3)
            .exec(http("[GET] One of the transfers (COMPLETED, CANCELED, BOOKED) is retrieved.")
                    .get("/users/#{userId}/transfers/#{selectedUserTransferId}")
                    .check(status().is(HttpResponseStatus.OK.code()))
            )
            .pause(1, 5)
            .exec(http("[PUT] Any (BOOKED) transfer description is updated.")
                    .put("/users/#{userId}/transfers/#{selectedUserTransferId}")
                    .check(status().is(HttpResponseStatus.NO_CONTENT.code()))
                    .body(StringBody("{\"state\": \"BOOKED\", \"description\": \"My new internal UUID " + UUID.randomUUID() + "\" }"))
                    .asJson()
            )
            .pause(1, 3)
            .exec(http("[PUT] Any (BOOKED) transfer is canceled (CANCELED).")
                    .put("/users/#{userId}/transfers/#{selectedUserTransferId}")
                    .check(status().is(HttpResponseStatus.NO_CONTENT.code()))
                    .body(StringBody("{\"state\": \"CANCELED\", \"description\": \"My new internal UUID for canceled transfer " + UUID.randomUUID() + "\" }"))
                    .asJson()
            )
            .pause(1, 3)
            .exec(http("[PUT] Any (BOOKED) transfer is completed (COMPLETED).")
                    .put("/users/#{userId}/transfers/#{selectedUserTransferId}")
                    .check(status().is(HttpResponseStatus.NO_CONTENT.code()))
                    .body(StringBody("{\"state\": \"COMPLETED\", \"description\": \"My new internal UUID for completed transfer " + UUID.randomUUID() + "\" }"))
                    .asJson()
            )
            .pause(1, 3)
            .exec(http("[GET] User is retrieved with her/his transfers data.")
                    .get("/users/#{userId}")
                    .check(status().is(HttpResponseStatus.OK.code()))
            )
            .pause(1, 3)
            .exec(http("[PUT] User updates with her/his own data.")
                    .put("/users/#{userId}")
                    .check(status().is(HttpResponseStatus.NO_CONTENT.code()))
                    .body(StringBody("{ \"firstName\": \" NewFirstName#{userId} \", \"lastName\": \"NewLastName#{userId}\" }"))
                    .asJson()
            )
            .pause(1, 5);

    {
        setUp(scenario.injectOpen(rampUsersPerSec(1).to(10).during(Duration.ofMinutes(5)).randomized()).protocols(protocol)).maxDuration(Duration.ofMinutes(10));
    }

}
