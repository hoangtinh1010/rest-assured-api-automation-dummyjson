package models;

public class CartProduct {
    private int id;
    private int quantity;

    public CartProduct(Integer id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setProductId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // ✅ Override toString() để hiển thị đẹp trong test report
    @Override
    public String toString() {
        return "{id=" + id + ", qty=" + quantity + "}";
    }
}
