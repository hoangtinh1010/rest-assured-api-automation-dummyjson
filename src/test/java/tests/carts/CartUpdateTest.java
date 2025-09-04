package tests.carts;

import base.BaseTest;
import endpoints.CartApi;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import models.CartProduct;
import models.UpdateCartRequest;
import org.testng.Assert;
import org.testng.annotations.Test;
import providers.CartDataProvider;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CartUpdateTest extends BaseTest {

        // Test update cart với DataProvider (từ JSON)
        @Test(dataProvider = "updateCartData", dataProviderClass = CartDataProvider.class)
        @Severity(SeverityLevel.NORMAL)
        public void testUpdateCart(Integer cartId, Boolean merge, List<CartProduct> products, int expectedStatusCode, String note) {
            System.out.println("=== Running Test: " + note + " ===");

            UpdateCartRequest payLoad = new UpdateCartRequest(merge, products);
            Response response = CartApi.updateCartItem(cartId, payLoad);

            // Verify status code
            Assert.assertEquals(response.statusCode(), expectedStatusCode);

            // Verify Response schema
            if (expectedStatusCode == 200) {
                // Verify response body contains expected data
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/carts/update_cart_schema.json"));
            } else {
                String message = response.jsonPath().getString("message");
                Assert.assertNotNull(message, "Error message should be present");
            }
        }

    }

