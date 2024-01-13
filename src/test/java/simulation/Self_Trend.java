package simulation;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Self_Trend extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("https://api.uat.uplight.com/")
            .acceptHeader("application/vnd.uplight+json;version=v1")
            .header("Uplight-Tenant-Id","e8a63d46-35bf-5e1c-acec-5d2495b7ae59")
            .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IkgzMUZMLTZsT0tVSVUwSC16ZHIyRyJ9.eyJpc3MiOiJodHRwczovL3VwbGlnaHQtZXh0ZXJuYWwtdWF0LnVzLmF1dGgwLmNvbS8iLCJzdWIiOiIwZUhWUFJySkNsc29XOXFOa2F1eklWZThpOFlJV214N0BjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9hcGkudXBsaWdodC5pbyIsImlhdCI6MTcwNTEyODM1MSwiZXhwIjoxNzA1MTMxOTUxLCJhenAiOiIwZUhWUFJySkNsc29XOXFOa2F1eklWZThpOFlJV214NyIsInNjb3BlIjoidXJuOnVwbGlnaHQ6YmNzOnRyZW5kX2NhbGN1bGF0b3Itcm9vdDpyZWFkIHVybjp1cGxpZ2h0OmJjczpiY3MtZmUtdXNlciIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyIsInBlcm1pc3Npb25zIjpbInVybjp1cGxpZ2h0OmJjczp0cmVuZF9jYWxjdWxhdG9yLXJvb3Q6cmVhZCIsInVybjp1cGxpZ2h0OmJjczpiY3MtZmUtdXNlciJdfQ.KCq8JMeTaebG-fI2PP_A4TgyQUE9tGAD8UUhj6wDn5vTRjpoLe3o4wm6_C3RAtV0Ku30k-dhR8BgbS2rKhsAH2uHNpe1GDi_TaSaoMmXAVg7m9tVWpNjkWw9Ydg00iX5ep9CPM7W3GgcfzoniuoGEllf_c1UPlWqIiL643I2F82rbz9WXWLk-TukoQ9mqdlugjQqUB-zoCAJ8TlO1NQPk48TJ202UNnIv3jmFN_UBQmnkpiz-7aLtZzPYfvTJv_d3DpS-2sMqeCwDh15gqJzCkPIlzTIegD8KxICnkEY25vCPy7Jnuesfvhl05os4utABYW1Wyhb2X6m_92-tLnAuA");

    private static ChainBuilder getSelfTrend =
            exec(http("Get Self Trend")
                    .get("/trend_calculator/trends/billed_usage/calculate_self_trend?account_id=02710390-a002-5e8d-9be3-55556cf6e6a2&fuel_type=electric")
                    .queryParam("service_location_id","ee3b060c-bc1c-5b9e-a009-6642f82915f8"));

    private ScenarioBuilder scn = scenario("Calculate Self Trend")
            .exec(getSelfTrend);

    {
        setUp(
                scn.injectClosed(
                        rampConcurrentUsers(1).to(10).during(120),
                        constantConcurrentUsers(10).during(900)
                ).protocols(httpProtocol)
        );
    }
}
