package model;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private int cartId;
    private String account;
    private Date createdDate;
    private Map<String, CartItem> items = new HashMap<>();

    public Cart() {
    }

    public Cart(int cartId, String account, Date createdDate) {
        this.cartId = cartId;
        this.account = account;
        this.createdDate = createdDate;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Map<String, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<String, CartItem> items) {
        this.items = items;
    }

    public void addItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            return;
        }
        String pid = product.getProductId();
        if (items.containsKey(pid)) {
            CartItem item = items.get(pid);
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            items.put(pid, new CartItem(0, cartId, product, quantity));
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            total += item.getSubTotal();
        }
        return total;
    }

    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public void clear() {
        if (items != null) {
            items.clear();
        }
    }
}
