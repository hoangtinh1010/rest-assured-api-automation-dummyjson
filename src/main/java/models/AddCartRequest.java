package models;

import java.util.List;

public class AddCartRequest {
    private Integer userId;
    private List<CartProduct> products;

    public AddCartRequest(Integer userId, List<CartProduct> products) {
        this.userId = userId;
        this.products = products;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }
}
