package model;

public class CartItem {
    private int cartItemId;
    private int cartId;
    private Product product;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int cartItemId, int cartId, Product product, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }
    
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getCartId() { return cartId; }
    public void setCartId(int cartId) { this.cartId = cartId; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getSubTotal() {
        if (product == null) return 0;
        double discountAmount = product.getPrice() * product.getDiscount() / 100.0;
        return (product.getPrice() - discountAmount) * quantity;
    }
}
