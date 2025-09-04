package providers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.CartProduct;
import org.testng.annotations.DataProvider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDataProvider {

    private static List<Map<String, Object>> loadJson(String fileName) throws Exception {
        InputStream inputStream = CartDataProvider.class.getClassLoader().getResourceAsStream("data/" + fileName);

        if (inputStream==null){
            throw new RuntimeException("Not found file: " +fileName);
        }
        ObjectMapper mapper = new ObjectMapper();
        return  mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {});
    }
//Data Provider cho api add cart
    @DataProvider(name = "addCartData")
    public static Object[][] addCartData() throws Exception {
        List<Map<String, Object>> list = loadJson("cart_add_data.json");

        Object[][] data = new Object[list.size()][4];
        for (Integer i = 0; i < list.size(); i++) {
            Map<String, Object> row = list.get(i);

            Integer userId = row.get("userId") != null ? (Integer) row.get("userId") : null;
            List<Map<String, Object>> productsMap = (List<Map<String, Object>>) row.get("products");

            List<CartProduct> products = new ArrayList<>();
            if (productsMap != null) {
                for (Map<String, Object> p : productsMap) {
                    Integer id = (Integer) p.get("id");
                    int quantity = (int) p.get("quantity");
                    products.add(new CartProduct(id, quantity));
                }
            }

            Integer expectedStatus = (Integer) row.get("expectedStatusCode");
            String note = (String) row.get("note");

            data[i][0] = userId;
            data[i][1] = products;
            data[i][2] = expectedStatus;
            data[i][3] =  note;
        }
        return data;
    }

    //Data Provider cho api update cart
    @DataProvider(name = "updateCartData")
    public static Object[][] updateCartData() throws Exception {
        List<Map<String, Object>> list = loadJson("cart_update_data.json");

        Object[][] data = new Object[list.size()][5];
        for (Integer i = 0; i < list.size(); i++) {
            Map<String, Object> row = list.get(i);

            Integer cartId = row.get("cartId") != null ? (Integer) row.get("cartId") : null;

            Boolean merge = (Boolean) row.get("merge") !=null ? (Boolean) row.get("merge") : null;

            List<Map<String, Object>> productsMap = (List<Map<String, Object>>) row.get("products");

            List<CartProduct> products = new ArrayList<>();
            if (productsMap != null) {
                for (Map<String, Object> p : productsMap) {
                    Integer id = (Integer) p.get("id");
                    int quantity = (int) p.get("quantity");
                    products.add(new CartProduct(id, quantity));
                }
            }

            Integer expectedStatus = (Integer) row.get("expectedStatusCode");

            String note = (String) row.get("note");

            data[i][0] = cartId;
            data[i][1] = merge;
            data[i][2] = products;
            data[i][3] = expectedStatus;
            data[i][4] = note;
        }
        return data;
    }

}
