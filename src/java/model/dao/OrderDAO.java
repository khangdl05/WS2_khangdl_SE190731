package model.dao;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Order;

public class OrderDAO extends DBContext {

    public int insertOrder(Order order) {
        String sql = "INSERT INTO orders (account, custName, custAddr, custPhone, createdDate, totalValue, ordState) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, order.getAccount().getAccount());
            ps.setString(2, order.getCustName());
            ps.setString(3, order.getCustAddr());
            ps.setString(4, order.getCustPhone());
            ps.setDate(5, order.getCreatedDate());
            ps.setInt(6, order.getTotalValue());
            ps.setInt(7, order.getOrdState());
            
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Order> getOrdersByAccount(String account) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE account = ? ORDER BY createdDate DESC";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, account);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                o.setAccount(acc);
                o.setCustName(rs.getString("custName"));
                o.setCustAddr(rs.getString("custAddr"));
                o.setCustPhone(rs.getString("custPhone"));
                o.setCreatedDate(rs.getDate("createdDate"));
                o.setTotalValue(rs.getInt("totalValue"));
                o.setOrdState(rs.getInt("ordState"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE orderId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                o.setAccount(acc);
                o.setCustName(rs.getString("custName"));
                o.setCustAddr(rs.getString("custAddr"));
                o.setCustPhone(rs.getString("custPhone"));
                o.setCreatedDate(rs.getDate("createdDate"));
                o.setTotalValue(rs.getInt("totalValue"));
                o.setOrdState(rs.getInt("ordState"));
                return o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int countOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY createdDate DESC";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                o.setAccount(acc);
                o.setCustName(rs.getString("custName"));
                o.setCustAddr(rs.getString("custAddr"));
                o.setCustPhone(rs.getString("custPhone"));
                o.setCreatedDate(rs.getDate("createdDate"));
                o.setTotalValue(rs.getInt("totalValue"));
                o.setOrdState(rs.getInt("ordState"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateOrderState(int orderId, int state) {
        String sql = "UPDATE orders SET ordState = ? WHERE orderId = ?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrder(int orderId) {
        // Need to delete orderDetails first due to foreign key constraint
        String sqlDetails = "DELETE FROM orderDetails WHERE orderId = ?";
        String sqlOrder = "DELETE FROM orders WHERE orderId = ?";
        try {
            c.setAutoCommit(false);
            PreparedStatement psDetails = c.prepareStatement(sqlDetails);
            psDetails.setInt(1, orderId);
            psDetails.executeUpdate();

            PreparedStatement psOrder = c.prepareStatement(sqlOrder);
            psOrder.setInt(1, orderId);
            int n = psOrder.executeUpdate();
            
            c.commit();
            c.setAutoCommit(true);
            return n > 0;
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalRevenue() {
        String sql = "SELECT SUM(totalValue) FROM orders WHERE ordState = 3"; // Assuming 3 = Completed
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Order> getRecentOrders(int limit) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " o.*, a.firstName, a.lastName " +
                     "FROM orders o JOIN accounts a ON o.account = a.account " +
                     "ORDER BY o.createdDate DESC, o.orderId DESC";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("orderId"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                acc.setFirstName(rs.getString("firstName"));
                acc.setLastName(rs.getString("lastName"));
                o.setAccount(acc);
                o.setCustName(rs.getString("custName"));
                o.setCustAddr(rs.getString("custAddr"));
                o.setCustPhone(rs.getString("custPhone"));
                o.setCreatedDate(rs.getDate("createdDate"));
                o.setTotalValue(rs.getInt("totalValue"));
                o.setOrdState(rs.getInt("ordState"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
