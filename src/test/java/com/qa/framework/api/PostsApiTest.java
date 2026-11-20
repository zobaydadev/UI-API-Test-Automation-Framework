package com.qa.framework.api;

import com.qa.framework.config.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

public class PostsApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
    }

    @Test(description = "GET /posts returns the full list")
    public void listPosts() {
        given().when().get("/posts")
                .then().statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test(description = "GET /posts/1 returns the first post")
    public void getSinglePost() {
        given().when().get("/posts/1")
                .then().statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1));
    }

    @Test(description = "GET /posts?userId=1 filters by user")
    public void filterByUser() {
        given().queryParam("userId", 1)
                .when().get("/posts")
                .then().statusCode(200)
                .body("$", hasSize(10))
                .body("userId", org.hamcrest.Matchers.everyItem(equalTo(1)));
    }

    @Test(description = "POST /posts creates a post and echoes the body")
    public void createPost() {
        Map<String, Object> body = Map.of("title", "framework test", "body", "created by TestNG", "userId", 1);

        given().contentType(ContentType.JSON).body(body)
                .when().post("/posts")
                .then().statusCode(201)
                .body("title", equalTo("framework test"))
                .body("id", greaterThan(100));
    }

    @Test(description = "GET on a missing post returns 404")
    public void missingPost() {
        given().when().get("/posts/99999")
                .then().statusCode(404);
    }
}
