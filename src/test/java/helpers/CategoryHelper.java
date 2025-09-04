package helpers;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;
import java.util.Random;

import static endpoints.ProductReadApi.getProductCategoryList;

public class CategoryHelper {
    private static List<String> categories;

    // Lấy random 1 category từ list category
    public static String getRandomCategory() {
        if (categories == null) {
            Response response = getProductCategoryList();
            categories = response.jsonPath().getList("");
        }
        return categories.get(new Random().nextInt(categories.size()));
    }
}

