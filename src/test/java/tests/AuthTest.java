package tests;

import base.BaseTest;
import configs.ConfigManager;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static endpoints.AuthApi.*;
import static org.testng.Assert.assertEquals;

import providers.LoginDataProviderJson;

public class AuthTest extends BaseTest {
    private static String authToken;
    private static String refreshToken;

    @Test
    @Severity(SeverityLevel.BLOCKER)
    public void testUserLoginSuccess() {

        Response response = login(ConfigManager.get("admin.username"), ConfigManager.get("admin.password"));

        // Kiem tra trạng thái trả về
        assertEquals(response.getStatusCode(), 200);

        // Kiem tra token không null
        authToken = response.jsonPath().getString("accessToken");
        assertEquals(authToken != null, true);

        // Lấy refreshToken
        refreshToken = response.jsonPath().getString("refreshToken");
    }

    @Test(dataProvider = "loginDataJson", dataProviderClass = LoginDataProviderJson.class)
    @Severity(SeverityLevel.CRITICAL)
    public void testUserLogin_InValid(String username, String password, int expectedStatusCode) {
        Response response = login(username,password);

        //Kiem tra trạng thái trả về
        assertEquals(response.getStatusCode(), expectedStatusCode);

    }

    @Test(dependsOnMethods = "testUserLoginSuccess")
    @Severity(SeverityLevel.NORMAL)
    public void testGetCurrentAuthUser() {
        Response response = getCurrentAuthUser(authToken);
        // Kiem tra trạng thái trả về
        assertEquals(response.getStatusCode(), 200);

        // Kiem tra thông tin người dùng
        String username = response.jsonPath().getString("username");
        assertEquals(username, ConfigManager.get("admin.username"));
    }

    @Test(dependsOnMethods = "testUserLoginSuccess")
    @Severity(SeverityLevel.NORMAL)
    public void testRefreshToken() {
        int expiresInMins = Integer.parseInt(ConfigManager.get("expiresInMins")); // Thời gian sống mới cho token
        Response response = refreshToken(refreshToken, expiresInMins);
        // Kiem tra trạng thái trả về
        assertEquals(response.getStatusCode(), 200);

        // Kiem tra access token mới không null và khác token cũ
        String newAccessToken = response.jsonPath().getString("accessToken");
        assertEquals(newAccessToken != null && !newAccessToken.equals(authToken), true);

        //Kiểm tra refresh token không null và khác refresh token cũ
        String newRefreshToken = response.jsonPath().getString("refreshToken");
        assertEquals(newRefreshToken != null && !newRefreshToken.equals(refreshToken), true);

    }

}
