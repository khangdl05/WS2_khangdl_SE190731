package model.dao;

import dal.DBContext;
import java.sql.*;
import model.Cart;

public class CartDAO extends DBContext {

    public Cart getCartByAccount(String account) {
        String sql = "SELECT * FROM cart WHERE account = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Cart(rs.getInt("cartId"), rs.getString("account"), rs.getDate("createdDate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cart getOrCreateCart(String account) {
        Cart cart = getCartByAccount(account);
        if (cart == null) {
            String sql = "INSERT INTO cart (account, createdDate) VALUES (?, ?)";
            try {
                PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account);
                ps.setDate(2, new Date(System.currentTimeMillis()));
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Cart(id, account, new Date(System.currentTimeMillis()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cart;
    }
}
