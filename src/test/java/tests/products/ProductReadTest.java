package tests.products;
import base.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static endpoints.ProductReadApi.*;

import java.util.List;

public class ProductReadTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void testGetAllProducts() {
        Response res = getAllProducts();
        Assert.assertEquals(res.getStatusCode(), 200, "Status code should be 200");
        Assert.assertTrue(res.jsonPath().getList("products").size() > 0, "Products list should not be empty");
    }

//    @Test
//    public void testGetProductById() {
//        Response res = getProductById(1);
//        Assert.assertEquals(res.getStatusCode(), 200);
//        Assert.assertEquals(res.jsonPath().getInt("id"), 1, "Product ID should be 1");
//    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void testSearchProducts() {
        Response res = searchProducts("phone");
        Assert.assertEquals(res.getStatusCode(), 200);
        List<String> titles = res.jsonPath().getList("products.title");
        Assert.assertTrue(titles.stream().anyMatch(t -> t.toLowerCase().contains("phone")),
                "At least one product should contain 'phone' in title");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void testGetProductsWithLimitAndSkip() {
        Response res = getProductsWithLimitAndSkip(5, 5);
        Assert.assertEquals(res.getStatusCode(), 200);
        Assert.assertEquals(res.jsonPath().getList("products").size(), 5, "Should return exactly 5 products");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void testGetProductsSorted() {
        Response res = getProductsSorted("price", "asc");
        Assert.assertEquals(res.getStatusCode(), 200);

        List<Float> prices = res.jsonPath().getList("products.price");
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue(prices.get(i) <= prices.get(i + 1), "Products should be sorted by price ascending");
        }
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllCategories() {
        Response res = getProductCategoryList();
        Assert.assertEquals(res.getStatusCode(), 200);

        List<String> categories = res.jsonPath().getList("");
        Assert.assertTrue(categories.size() > 0, "Categories list should not be empty");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    public void testGetProductsByCategory() {
        Response res = getProductsByCategory("smartphones");
        Assert.assertEquals(res.getStatusCode(), 200);

        List<String> categories = res.jsonPath().getList("products.category");
        Assert.assertTrue(categories.stream().allMatch(c -> c.equals("smartphones")),
                "All products should belong to 'smartphones' category");
    }
}
