package utils;

import model.CreateUserRequest;

public class UserGenerator {
    public static CreateUserRequest getSimpleUser() {
        CreateUserRequest user = new CreateUserRequest();
        user.setName("simple");
        user.setJob("automation");
        return user;
    }
}
