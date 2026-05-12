package model.dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import model.Product;

public class CartItemDAO extends DBContext {

    public List<CartItem> getItemsByCartId(int cartId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT * FROM cartItems WHERE cartId = ?"; 
        ProductDAO pDao = new ProductDAO();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = pDao.getObjectById(rs.getString("productId"));
                if (p != null) {
                    CartItem ci = new CartItem(rs.getInt("cartItemId"), rs.getInt("cartId"), p, rs.getInt("quantity"));
                    list.add(ci);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addOrUpdate(int cartId, String productId, int quantity) {
        try {
            String sqlCheck = "SELECT cartItemId, quantity FROM cartItems WHERE cartId = ? AND productId = ?";
            PreparedStatement psCheck = c.prepareStatement(sqlCheck);
            psCheck.setInt(1, cartId);
            psCheck.setString(2, productId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String sqlUpdate = "UPDATE cartItems SET quantity = quantity + ? WHERE cartItemId = ?";
                PreparedStatement psUp = c.prepareStatement(sqlUpdate);
                psUp.setInt(1, quantity);
                psUp.setInt(2, rs.getInt("cartItemId"));
                psUp.executeUpdate();
            } else {
                String sqlIns = "INSERT INTO cartItems (cartId, productId, quantity) VALUES (?, ?, ?)";
                PreparedStatement psIns = c.prepareStatement(sqlIns);
                psIns.setInt(1, cartId);
                psIns.setString(2, productId);
                psIns.setInt(3, quantity);
                psIns.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int cartId, String productId, int quantity) {
        if (quantity <= 0) {
            deleteItem(cartId, productId);
            return;
        }
        String sql = "UPDATE cartItems SET quantity = ? WHERE cartId = ? AND productId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, cartId);
            ps.setString(3, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(int cartId, String productId) {
        String sql = "DELETE FROM cartItems WHERE cartId = ? AND productId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.setString(2, productId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getQuantityInCart(int cartId, String productId) {
        String sql = "SELECT quantity FROM cartItems WHERE cartId = ? AND productId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.setString(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void clearCart(int cartId) {
        String sql = "DELETE FROM cartItems WHERE cartId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
