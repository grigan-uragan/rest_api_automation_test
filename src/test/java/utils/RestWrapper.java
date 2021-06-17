package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import model.UserLogin;
import model.UserPojoGen;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RestWrapper {
    private static RequestSpecification REQUEST_SPECIFICATION;
    private static final String BASE_URI = "https://reqres.in/api";
    private Cookies cookies;

    private RestWrapper(Cookies cookies) {
        this.cookies = cookies;
        REQUEST_SPECIFICATION = new RequestSpecBuilder()
                .addCookies(cookies)
                .setBaseUri(BASE_URI)
                .setBasePath("/users")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RestWrapper loginAs(String email, String password) {
        Cookies cookies = given().contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath("/login")
                .body(new UserLogin(email, password))
                .post()
                .getDetailedCookies();
        return new RestWrapper(cookies);
    }


    public List<UserPojoGen> getUsers() {
        return given().spec(REQUEST_SPECIFICATION)
                .when().get()
                .then().statusCode(200).extract().jsonPath().getList("data", UserPojoGen.class);
    }


}
