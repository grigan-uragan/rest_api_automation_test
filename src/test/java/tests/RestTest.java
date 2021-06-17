package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import model.CreateUserRequest;
import model.CreateUserResponse;
import model.UserPojoGen;
import org.junit.Test;
import steps.UsersSteps;
import utils.RestWrapper;
import utils.UserGenerator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class RestTest {
    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
            .setBaseUri("https://reqres.in/api")
            .setBasePath("/users")
            .setContentType(ContentType.JSON)
            .build();

    private RestWrapper api = RestWrapper.loginAs("eve.holt@reqres.in", "cityslicka");

    @Test
    public void shouldGetUsersList() {
        given().spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldGetUsersListContainsUserWithNameJanet() {
        given().spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("data.find{it.email == 'janet.weaver@reqres.in'}.first_name",
                        equalTo("Janet"));
    }

    @Test
    public void shouldGetData() {
        List<String> list = given().spec(REQUEST_SPECIFICATION)
                .when()
                .get()
                .then()
                .statusCode(200).extract()
                .jsonPath()
                .getList("data.email");
        assertTrue(list.contains("janet.weaver@reqres.in"));
    }

    @Test
    public void shouldGetPojoList() {
        List<UserPojoGen> list = api.getUsers();
        assertThat(list).extracting(UserPojoGen::getEmail).contains("janet.weaver@reqres.in");
    }

    @Test
    public void shouldCreateUser() {
        CreateUserRequest createUserRequest = UserGenerator.getSimpleUser();
        CreateUserResponse response = given().spec(REQUEST_SPECIFICATION)
                .body(createUserRequest)
                .when().post()
                .then().statusCode(201).extract().as(CreateUserResponse.class);
        assertThat(response)
                .isNotNull()
                .extracting(CreateUserResponse::getName).isEqualTo(createUserRequest.getName());
    }
}
