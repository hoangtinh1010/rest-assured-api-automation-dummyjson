package tests.carts;

import base.BaseTest;
import endpoints.CartApi;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import models.AddCartRequest;
import models.CartProduct;
import org.testng.Assert;
import org.testng.annotations.Test;
import providers.CartDataProvider;

import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CartAddTest extends BaseTest {

    //Test add cart với DataProvider (từ JSON)
    @Test(dataProvider = "addCartData", dataProviderClass = CartDataProvider.class)
    @Severity(SeverityLevel.NORMAL)
    public void testAddCart(Integer userId, List<CartProduct> products, int expectedStatusCode, String note) {
        System.out.println("=== Running Test: "+note+" ===");

        AddCartRequest payLoad = new AddCartRequest(userId, products);
        Response response = CartApi.addCart(payLoad);

        // Verify status code
        Assert.assertEquals(response.statusCode(), expectedStatusCode);

        // Verify Response schema
        if (expectedStatusCode == 201) {
            // Verify response body contains expected data
            response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/carts/add_cart_schema.json"));
        } else {
            String message = response.jsonPath().getString("message");
            Assert.assertNotNull(message, "Error message should be present");
        }
    }
}
