package models;

import java.util.List;

public class UpdateCartRequest

{
    private boolean merge;
    private List<CartProduct> products;

    public UpdateCartRequest(boolean merge, List<CartProduct> products) {
        this.merge = merge;
        this.products = products;
    }

    public boolean getMerge() {
        return merge;
    }

    public void setMerge(boolean merge) {
        this.merge = merge;
    }


    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct>  products) {
        this.products = products;
    }
}
