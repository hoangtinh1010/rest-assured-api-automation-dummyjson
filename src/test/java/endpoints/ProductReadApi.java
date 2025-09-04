package endpoints;

import base.BaseRequest;
import base.BaseRequestWithAuth;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductReadApi extends BaseRequest {


    // Lấy danh sách tất cả sản phẩm
    public static Response getAllProducts() {
        return given()
                .spec(BaseRequest.getNoAuth())
                .when()
                .get("/products")
                .then()
                .extract().response();
    }

    // Tìm kiếm sản phẩm theo từ khóa  (/products/search?q=...)
    public static Response searchProducts(String query) {
        return given()
                .spec(BaseRequest.getNoAuth())
                .queryParam("q", query)
                .when()
                .get("/products/search")
                .then()
                .log().all()
                .extract().response();
    }

    // Limit & Skip (/products?limit=10&skip=10)
    public static Response getProductsWithLimitAndSkip(int limit, int skip) {
        return given()
                .spec(BaseRequest.getNoAuth())
                .queryParam("limit", limit)
                .queryParam("skip", skip)
                .when()
                .get("/products")
                .then()
                .extract().response();
    }

    //Sort
    public static Response getProductsSorted(String sortBy, String order) {
        return given()
                .spec(BaseRequest.getNoAuth())
                .queryParam("sortBy", sortBy)
                .queryParam("order", order)
                .when()
                .get("/products")
                .then()
                .extract().response();
    }

    // Lấy Danh mục sản phẩm (/products/categories
    public static Response getProductCategoryList() {
        return given()
                .spec(BaseRequest.getNoAuth())
                .when()
                .get("/products/category-list")
                .then()
                .extract().response();
    }

    // Lấy sản phẩm theo danh mục (/products/category/{category})
    public static Response getProductsByCategory(String categoryName) {
        return given()
                .spec(BaseRequest.getNoAuth())
                .when()
                .get("/products/category/" + categoryName)
                .then()
                .extract().response();
    }
}
