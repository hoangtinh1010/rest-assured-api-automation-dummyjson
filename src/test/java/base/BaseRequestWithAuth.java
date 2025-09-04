package base;

import configs.ConfigManager;
import endpoints.AuthApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseRequestWithAuth {
    private static String accToken;
    private static RequestSpecification authSpec;

    public static RequestSpecification getAuthSpec() {
        // Nếu chưa có token hoặc spec thì login
        if (accToken == null || authSpec == null) {
            Response loginResponse = AuthApi.login(
                    ConfigManager.get("admin.username"),
                    ConfigManager.get("admin.password")
            );
            accToken = loginResponse.jsonPath().getString("accessToken");

            authSpec = new RequestSpecBuilder()
                    .setContentType("application/json")
                    .addHeader("Authorization", "Bearer " + accToken)
                    .log(io.restassured.filter.log.LogDetail.ALL)
                    .build();
        }
        return authSpec;
    }

    // Optional: làm hàm refresh token nếu muốn clear token khi hết hạn
    public static void resetToken() {
        accToken = null;
        authSpec = null;
    }
}
