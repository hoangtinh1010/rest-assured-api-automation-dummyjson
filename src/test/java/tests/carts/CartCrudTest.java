package tests.carts;

import base.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import models.AddCartRequest;
import models.UpdateCartRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static endpoints.CartApi.*;

public class CartCrudTest extends BaseTest {

    // Test full CRUD flow end-to-end (create → read → update → delete)
    private int cartId;

    // Add cart
    @Test ( priority = 1,description = "Thêm giỏ hàng thành công")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddCartSuccess() {
        AddCartRequest payLoad = new AddCartRequest(1, Arrays.asList(
                new models.CartProduct(144, 4),
                new models.CartProduct(98, 1)
        ));
        Response response = addCart(payLoad);

        cartId = response.jsonPath().getInt("id");

        // Verify status code is 201
        Assert.assertEquals(response.statusCode(), 201);

        // Verify response body contains expected data
        List<Object> products = response.jsonPath().getList("products");
        Assert.assertEquals(products.size(), 2);
    }

    // View cart by cartId
    @Test (priority = 2, dependsOnMethods = "testAddCartSuccess")
    @Severity(SeverityLevel.CRITICAL)
    public  void testViewCartByCartIdSuccess() {

        Response response = viewCartById(cartId);

        Assert.assertEquals(response.statusCode(), 200);
        // Verify response body contains expected data
        List<Object> products = response.jsonPath().getList("products");
        Assert.assertEquals(products.size(), 2);
    }

    // Update cart by cartId
    @Test (priority =3,dependsOnMethods = "testAddCartSuccess")
    @Severity(SeverityLevel.CRITICAL)
    public  void testUpdateCartByCartIdSuccess() {
        cartId = 1; // Thay đổi cartId theo dữ liệu thực tế
        UpdateCartRequest payLoad = new UpdateCartRequest(false,Arrays.asList(
                new models.CartProduct(144, 5)
        ));
        Response response = updateCartItem(cartId, payLoad);
        Assert.assertEquals(response.statusCode(), 200);

        // Verify response body contains expected data
        List<Object> products = response.jsonPath().getList("products");
        Assert.assertEquals(products.size(), 1);
    }

    // Delete cart by cartId
    @Test (priority =4,dependsOnMethods = "testAddCartSuccess")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteCartByCartIdSuccess() {

        Response response = deleteCart(cartId);
        Assert.assertEquals(response.statusCode(), 200);

        // Verify the cart is deleted by attempting to retrieve it
        Response getResponse = viewCartById(cartId);
        Assert.assertEquals(getResponse.statusCode(), 404);
    }

    // View all carts
    @Test(priority =5)
    @Severity(SeverityLevel.NORMAL)
    public void testViewAllCartSuccess() {

        Response response = getAllCarts();
        Assert.assertEquals(response.statusCode(), 200);
        // Verify response body contains expected data
        List<Object> carts = response.jsonPath().getList("carts");
        Assert.assertTrue(carts.size() > 0);

    }

}
