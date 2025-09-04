package tests.products;

import base.BaseTest;
import com.github.javafaker.Faker;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static endpoints.ProductCrudApi.*;

public class ProductCrudTest extends BaseTest {
    private Faker faker = new Faker();
    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateProductValid() {
        String title = faker.commerce().productName();
        String price = faker.commerce().price();
        String description = faker.lorem().sentence();
        String category = "electronics";

        Response response = createProduct(title, description, price, category);

        Assert.assertEquals(response.statusCode(), 201, "Status code should be 201");
        int productId = response.jsonPath().getInt("id");

    }

    @DataProvider(name = "invalidProductData")
    public Object[][] invalidProductData() {
        return new Object[][]{
                {null, "No title", 100,  "laptops"}, // title rỗng
                {"TestProduct", "Negative price", -50, "laptops"}, // price âm
                {"TestProduct", "Zero stock", 200, "laptops"}, // stock = 0
                {"TestProduct", "Invalid category", 200,"invalidCat"} // category không hợp lệ
        };
    }
    @Test(dataProvider = "invalidProductData")
    public void testCreateProductInvalid(String title, String description, double price, String category) {
        Response response = createProduct(title, description, String.valueOf(price), category);

        Assert.assertEquals(response.statusCode(), 400, "Status code should be 400 for invalid product data");

    }
}
