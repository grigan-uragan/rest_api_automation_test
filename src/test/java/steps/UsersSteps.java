package steps;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import model.UserPojoGen;

import java.util.List;

import static io.restassured.RestAssured.given;

public class UsersSteps {
    private static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in/api")
                    .setBasePath("/users")
                    .setContentType(ContentType.JSON)
                    .build();

    public static List<UserPojoGen> getUsers() {
        return given().spec(REQUEST_SPECIFICATION)
                .when().get()
                .then().statusCode(200).extract().jsonPath().getList("data", UserPojoGen.class);
    }
}
