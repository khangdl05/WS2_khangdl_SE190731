package model.dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;
import model.Product;

public class OrderDetailDAO extends DBContext {

    public void insertOrderDetail(OrderDetail detail) {
        String sql = "INSERT INTO orderDetails (orderId, productId, quantity, price, discount) "
                   + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, detail.getOrder().getOrderId());
            ps.setString(2, detail.getProduct().getProductId());
            ps.setInt(3, detail.getQuantity());
            ps.setInt(4, detail.getPrice());
            ps.setInt(5, detail.getDiscount());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<OrderDetail> getDetailsByOrderId(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM orderDetails WHERE orderId = ?";
        ProductDAO pDao = new ProductDAO();
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail d = new OrderDetail();
                d.setQuantity(rs.getInt("quantity"));
                d.setPrice(rs.getInt("price"));
                d.setDiscount(rs.getInt("discount"));
                
                Product p = pDao.getObjectById(rs.getString("productId"));
                d.setProduct(p);
                
                list.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
