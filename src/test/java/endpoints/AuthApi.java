package endpoints;

import base.BaseTest;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

public class AuthApi extends BaseTest {

    public static Response login(String username, String password) {
        JSONObject authPayload = new JSONObject();
        authPayload.put("username", username);
        authPayload.put("password", password);
        return given()
                .contentType("application/json")
                .body(authPayload)
                .when()
                .post("/auth/login")
                .then()
                .extract().response();

    }

    public static Response getCurrentAuthUser(String token) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when().get("/auth/me")
                .then().extract().response();
    }

    public static Response refreshToken(String token, int expiresInMins) {
        JSONObject body = new JSONObject();
         body.put("refreshToken", token);
         body.put("expiresInMins", expiresInMins);
        return given()
                .contentType("application/json")
                .body(body.toString())
                .when().post("/auth/refresh")
                .then().extract().response();
    }

}
