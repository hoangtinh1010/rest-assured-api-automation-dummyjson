package endpoints;

import base.BaseRequestWithAuth;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;

public class ProductCrudApi extends BaseRequestWithAuth {
    //API CRUD cơ bản
    // Tạo mới một sản phẩm (chỉ dành cho admin)


    public static Response createProduct(String title, String description, String price,String category) {
        JSONObject productPayload = new JSONObject();
        productPayload.put("title", title);
        productPayload.put("description", description);
        productPayload.put("price", price);
        productPayload.put("category", category);
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .body(productPayload)
                .when()
                .post("/products/add")
                .then()
                .extract().response();
    }

    // Lấy thông tin chi tiết của một sản phẩm theo ID
    public static Response getProductById(int productId) {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .get("/products/" + productId)
                .then()
                .extract().response();
    }

    // Cập nhật thông tin một sản phẩm theo ID (chỉ dành cho admin)
    public static Response updateProduct(int productId, String title, String description, String price,String category) {
        JSONObject productPayload = new JSONObject();
        productPayload.put("title", title);
        productPayload.put("description", description);
        productPayload.put("price", price);
        productPayload.put("category", category);
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .body(productPayload)
                .when()
                .put("/products/" + productId)
                .then()
                .extract().response();
    }

    // Xóa một sản phẩm theo ID (chỉ dành cho admin)
    public static Response deleteProduct(int productId) {
        return given()
                .spec(BaseRequestWithAuth.getAuthSpec())
                .when()
                .delete("/products/" + productId)
                .then()
                .extract().response();
    }
}
