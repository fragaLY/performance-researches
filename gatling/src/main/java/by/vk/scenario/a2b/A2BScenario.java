package by.vk.scenario.a2b;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

public class A2BScenario extends Simulation {

    private static final Logger LOGGER = Logger.getLogger(A2BScenario.class.getSimpleName());

    private Iterator<Map<String, Object>> feeder;

    @Override
    public void before() {
        LOGGER.info("[A2B] Simulation starting.");
        this.feeder = Stream.generate((Supplier<Map<String, Object>>) () -> Collections.singletonMap("userId", ThreadLocalRandom.current().nextLong(1, 200_001))).iterator();
    }

    final HttpProtocolBuilder protocol = http
            .warmUp("https://www.google.com")
            .baseUrl("http://localhost:8080/api/v1")
            .acceptHeader("application/json")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Safari/537.36");

    final ScenarioBuilder scenario = scenario("A2B simulation")
            .feed(feeder)
            .exec(http("[GET] The list of available countries is presented.")
                    .get("/countries")
                    .check(jsonPath("$..countryId").findRandom().saveAs("countryId"))
            )
            .pause(1, 3)
            .exec(http("[GET] The list of available cities is presented.")
                    .get("/countries/{countryId}/cities")
                    .check(jsonPath("$..cityId").findRandom().saveAs("cityOriginId"))
            )
            .pause(1, 3)
            .exec(http("[GET] The list of available origins is presented.")
                    .get("/countries/{countryId}/cities/{cityOriginId}/locations")
                    .check(jsonPath("$..locationId").findRandom().saveAs("originId"))
            )
            .pause(3, 5)
            .exec(http("[GET] The list of available cities is presented.")
                    .get("/countries/{countryId}/cities")
                    .check(jsonPath("$..cityId").findRandom().saveAs("cityDestinationId"))
            )
            .pause(1, 3)
            .exec(http("[GET] The list of available destinations is presented.")
                    .get("/countries/{countryId}/cities/{cityDestinationId}/locations")
                    .check(jsonPath("$..locationId").findRandom().saveAs("destinationId"))
            )
            .pause(3, 5)
            .exec(http("[GET] The list of available transfers by selected origin, destination, and date is presented.")
                    .get("/transfers")
                    .queryParam("originId", "{originId}")
                    .queryParam("destinationId", "{destinationId}")
                    .queryParam("date", "1970-01-01")
                    .check(jsonPath("$..transferId").findRandom().saveAs("transferId"))
            )
            .pause(3, 5)
            .exec(http("[POST] The transfer is booked in the system.")
                    .post("/users/{userId}/transfers/{transferId}")
                    .body(StringBody("{ \"description\": \"My internal uuid is " + UUID.randomUUID() + "\" }"))
            )
            .pause(1, 3)
            .exec(http("[GET] The list of all my transfers (COMPLETED, CANCELED, BOOKED) is presented.")
                    .get("/users/{userId}/transfers")
                    .check(jsonPath("$..transfer.transferId").saveAs("selectedUserTransferId"))
            )
            .pause(1, 3)
            .exec(http("[GET] One of the transfers (COMPLETED, CANCELED, BOOKED) is retrieved.")
                    .get("/users/{userId}/transfers/{selectedUserTransferId}")
            )
            .pause(1, 5)
            .exec(http("[PUT] Any (BOOKED) transfer description is updated.")
                    .put("/users/{userId}/transfers/{selectedUserTransferId}")
                    .body(StringBody("{\"state\": \"BOOKED\", \"description\": \"My new internal UUID " + UUID.randomUUID() + "\" }"))
            )
            .pause(1, 3)
            .exec(http("[PUT] Any (BOOKED) transfer is canceled (CANCELED).")
                    .put("/users/{userId}/transfers/{selectedUserTransferId}")
                    .body(StringBody("{\"state\": \"CANCELED\", \"description\": \"My new internal UUID for canceled transfer " + UUID.randomUUID() + "\" }"))
            )
            .pause(1, 3)
            .exec(http("[PUT] Any (BOOKED) transfer is completed (COMPLETED).")
                    .put("/users/{userId}/transfers/{selectedUserTransferId}")
                    .body(StringBody("{\"state\": \"COMPLETED\", \"description\": \"My new internal UUID for completed transfer " + UUID.randomUUID() + "\" }"))
            )
            .pause(1, 3)
            .exec(http("[GET] User is retrieved with her/his transfers data.")
                    .post("/users/{userId}")
            )
            .pause(1, 3)
            .exec(http("[PUT] User updates with her/his own data.")
                    .post("/users/{userId}")
                    .body(StringBody("{ \"firstName\": \" NewFirstName{userId} \", \"lastName\": \"NewLastName{userId}\" }"))
            )
            .pause(1, 5);

    {
        setUp(scenario.injectOpen(rampUsersPerSec(100).to(200_000).during(Duration.ofHours(1)).randomized()).protocols(protocol));
    }

    @Override
    public void after() {
        LOGGER.info("[A2B] Simulation is finished.");
    }
}
