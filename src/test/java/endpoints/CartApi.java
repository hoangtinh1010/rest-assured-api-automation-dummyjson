package endpoints;

import base.BaseRequestWithAuth;
import io.restassured.response.Response;
import models.AddCartRequest;
import models.UpdateCartRequest;

import static io.restassured.RestAssured.given;

public class CartApi extends BaseRequestWithAuth {
    // Thêm cart cho người dùng
    public static Response addCart(AddCartRequest payLoad) {

        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .body(payLoad)
                .when()
                .post("/carts/add")
                .then()
//                .log().all()
                .extract().response();
    }

    // Xem giỏ hàng theo userId
    public static Response viewCart(int userId) {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .get("/carts/user/" + userId)
                .then()
//                .log().all()
                .extract().response();

    }

    // Xem giỏ hàng theo cartId
    public static Response viewCartById(int cartId) {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .get("/carts/" + cartId)
                .then()
                .extract().response();
    }
    // Update giỏ hàng cho người dùng
    public static Response updateCartItem(int cartId, UpdateCartRequest payLoad) {


        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .body(payLoad)
                .when()
                .put("/carts/" + cartId)
                .then()
//                .log().all()
                .extract().response();

    }

    // Xóa sản phẩm khỏi giỏ hàng
    public static Response deleteCart(int cartId) {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .delete("/carts/" + cartId)
                .then()
                .extract().response();

    }

    // Lấy tất cả giỏ hàng (chỉ dành cho admin)
    public static Response getAllCarts() {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .get("/carts")
                .then()
                .extract().response();
    }
}
