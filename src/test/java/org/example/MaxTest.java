package org.example;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
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

import static io.restassured.RestAssured.given;

public class MaxTest {

    static Map<String, String> headers = new HashMap<>();

    static Properties prop = new Properties();


    @BeforeAll
static void setup () throws IOException {

        RestAssured.baseURI = "https://reqres.in/api";

       // RestAssured.filters(new AllureRestAssured());
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
       // FileInputStream fis;
     //   fis = new FileInputStream("src/test/resources/my.properties");

      //  prop.load(fis);
    }

    @Test
    void getListUsersTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users?page=1")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleUserTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .request(Method.GET, "https://reqres.in/api/users/2")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .request(Method.GET, "https://reqres.in/api/users/23")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void getListResourceTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleResourceTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown/2")
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Test
    void getSingleResourceNotFoundTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .request(Method.GET, "https://reqres.in/api/unknown/23")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest() {

        JSONObject jsonObj = new JSONObject()
                .put("id","88")
                .put("email","admin@f1.nnov.ru")
                .put("first_name", "Gzegosh")
                .put("last_name", "Kryhovyak")
                .put("avatar", "https://reqres.in/img/faces/5-image.jpg");

        Response response = given()

                .header("Content-type", "application/json")
                .body(jsonObj.toString())
                .log()
                .body()
                .when()
                .post("/users")
                .prettyPeek()
                .then()
                .extract().response();

        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("admin@f1.nnov.ru", response.jsonPath().getString("email"));
        Assertions.assertEquals("Gzegosh", response.jsonPath().getString("first_name"));
        Assertions.assertEquals("Kryhovyak", response.jsonPath().getString("last_name"));
        Assertions.assertEquals("88", response.jsonPath().getString("id"));
    }

    @Test
    void updateUserTest() {

        JSONObject jsonObj = new JSONObject()
                .put("id","88")
                .put("email","result@f1.nnov.ru")
                .put("first_name", "Petr")
                .put("last_name", "Ivanov")
                .put("avatar", "https://reqres.in/img/faces/4-image.jpg");

        Response response = given()

                .header("Content-type", "application/json")
                .body(jsonObj.toString())
                .log()
                .body()
                .when()
                .put("/users/2")
                .prettyPeek()
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("result@f1.nnov.ru", response.jsonPath().getString("email"));
        Assertions.assertEquals("Petr", response.jsonPath().getString("first_name"));
        Assertions.assertEquals("Ivanov", response.jsonPath().getString("last_name"));
        Assertions.assertEquals("88", response.jsonPath().getString("id"));
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



