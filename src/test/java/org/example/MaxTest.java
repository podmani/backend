package org.example;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import sun.plugin.javascript.navig5.JSObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.*;

public class MaxTest {

        @BeforeAll
static void setup () throws IOException {

        RestAssured.baseURI = "https://reqres.in/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Access-Control-Allow-Credentials", "true")
                .build();

        requestSpecification = new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.ANY)
                .build();


    }

    @Test
    void getListUsersTest() {
        given(requestSpecification)
                .when()
                .get("https://reqres.in/api/users?page=1")
                .prettyPeek()
                .then().spec(responseSpecification);
    }

    @Test
    void getSingleUserTest() {
        given(requestSpecification)
                .when()
                .request(Method.GET, "https://reqres.in/api/users/2")
                .prettyPeek()
                .then().spec(responseSpecification);

    }

    @Test
    void getSingleUserNotFoundTest() {
        given(requestSpecification)
                .when()
                .request(Method.GET, "https://reqres.in/api/users/23")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getListResourceTest() {
        given(requestSpecification)
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown")
                .prettyPeek()
                .then().spec(responseSpecification);
    }

    @Test
    void getSingleResourceTest() {
        given(requestSpecification)
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown/2")
                .prettyPeek()
                .then().spec(responseSpecification);

    }

    @Test
    void getSingleResourceNotFoundTest() {
        given(requestSpecification)
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown/23")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest() {

            MaxDTO maxDTO = new MaxDTO();
            maxDTO.setId("88");
            maxDTO.setEmail("admin@f1.nnov.ru");
            maxDTO.setFirstName("Matvey");
            maxDTO.setLastName("Safronov");
            maxDTO.setAvatar("https://reqres.in/img/faces/5-image.jpg");


        Response response = given()
                .log()
                .all()
                .contentType("application/json")
                .when().body(maxDTO)
                .post("/users")
                .prettyPeek()
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("admin@f1.nnov.ru", response.jsonPath().getString("email"));
        Assertions.assertEquals("Matvey", response.jsonPath().getString("first_name"));
        Assertions.assertEquals("Safronov", response.jsonPath().getString("last_name"));
        Assertions.assertEquals("88", response.jsonPath().getString("id"));
    }

    @Test
    void updateUserTest() {

        MaxDTO maxDTO = new MaxDTO();
        maxDTO.setId("88");
        maxDTO.setEmail("result@f1.nnov.ru");
        maxDTO.setFirstName("Petr");
        maxDTO.setLastName("Ivanov");
        maxDTO.setAvatar("https://reqres.in/img/faces/4-image.jpg");


                 given()
                .header("Content-type", "application/json")
                .body(maxDTO)
                .log()
                .body()
                .when()
                .put("/users/2")
                .prettyPeek()
                .then().spec(responseSpecification);

    }

    @Test
    void deleteUserTest() {

        Response response = given()

                .header("Content-type", "application/json")
                .log()
                .all()
                .when()
                .delete("/users/2")
                .prettyPeek()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());

    }


}



