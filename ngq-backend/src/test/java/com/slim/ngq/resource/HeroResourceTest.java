package com.slim.ngq.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;;

@QuarkusTest
public class HeroResourceTest {

    @Test
    public void testGetHeroEndpoint() {
        given()
          .when().get("/api/heroes/1")
          .then()
             .statusCode(200)
             .body(containsString("Slimaine"));
    }

}