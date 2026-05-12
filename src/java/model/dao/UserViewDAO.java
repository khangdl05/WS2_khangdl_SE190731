/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.UserView;

/**
 * 
 *
 * @author ADMIN
 */
public class UserViewDAO extends DBContext implements Accessible<UserView> {

    private final String GET_ALL_VIEWS = "SELECT * FROM userViews";

    private final String GET_VIEW_BY_ID = "SELECT * FROM userViews WHERE viewId = ?";

    private final String GET_VIEWS_BY_ACCOUNT = "SELECT * FROM userViews WHERE account = ? ORDER BY viewTime DESC";

    private final String GET_VIEWS_BY_PRODUCT = "SELECT * FROM userViews WHERE productId = ? ORDER BY viewTime DESC";

    private final String GET_VIEW_BY_ACCOUNT_AND_PRODUCT = "SELECT * FROM userViews WHERE account = ? AND productId = ?";

    private final String INSERT_VIEW = "INSERT INTO userViews (account, productId, viewTime) VALUES (?, ?, ?)";

    private final String UPDATE_VIEW = "UPDATE userViews SET account = ?, productId = ?, viewTime = ? WHERE viewId = ?";

    private final String DELETE_VIEW = "DELETE FROM userViews WHERE viewId = ?";

    private final String DELETE_VIEWS_BY_ACCOUNT = "DELETE FROM userViews WHERE account = ?";

    @Override
    public int insertRec(UserView view) {
        try {
            PreparedStatement stm = c.prepareStatement(INSERT_VIEW, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, view.getAccount());
            stm.setString(2, view.getProductId());
            stm.setDate(3, view.getViewTime());
            int n = stm.executeUpdate();
            if (n != 0) {
                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    view.setViewId(generatedKeys.getInt(1));
                }
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateRec(UserView view) {
        try {
            PreparedStatement stm = c.prepareStatement(UPDATE_VIEW);
            stm.setString(1, view.getAccount());
            stm.setString(2, view.getProductId());
            stm.setDate(3, view.getViewTime());
            stm.setInt(4, view.getViewId());
            int n = stm.executeUpdate();
            if (n != 0) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteRec(UserView view) {
        try {
            PreparedStatement stm = c.prepareStatement(DELETE_VIEW);
            stm.setInt(1, view.getViewId());
            int n = stm.executeUpdate();
            if (n != 0) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public UserView getObjectById(String id) {
        try {
            PreparedStatement stm = c.prepareStatement(GET_VIEW_BY_ID);
            stm.setInt(1, Integer.parseInt(id));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserView> listAll() {
        List<UserView> list = new ArrayList<>();
        try {
            PreparedStatement stm = c.prepareStatement(GET_ALL_VIEWS);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.out.println("Get all user_views fail: " + e.getMessage());
        }
        return list;
    }

    
    public List<UserView> getViewsByAccount(String account) {
        List<UserView> list = new ArrayList<>();
        try {
            PreparedStatement stm = c.prepareStatement(GET_VIEWS_BY_ACCOUNT);
            stm.setString(1, account);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.out.println("Get views by account fail: " + e.getMessage());
        }
        return list;
    }

   
    public List<UserView> getViewsByProduct(String productId) {
        List<UserView> list = new ArrayList<>();
        try {
            PreparedStatement stm = c.prepareStatement(GET_VIEWS_BY_PRODUCT);
            stm.setString(1, productId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (Exception e) {
            System.out.println("Get views by product fail: " + e.getMessage());
        }
        return list;
    }

  
    public void recordView(String account, String productId) {
        try {
            PreparedStatement stm = c.prepareStatement(GET_VIEW_BY_ACCOUNT_AND_PRODUCT);
            stm.setString(1, account);
            stm.setString(2, productId);
            ResultSet rs = stm.executeQuery();

            Date now = new Date(System.currentTimeMillis());

            if (rs.next()) {
                // Cập nhật viewTime
                UserView existing = mapRow(rs);
                existing.setViewTime(now);
                updateRec(existing);
            } else {
                // Thêm mới
                insertRec(new UserView(0, account, productId, now));
            }
        } catch (Exception e) {
            System.out.println("Record view fail: " + e.getMessage());
        }
    }

    
    public int deleteViewsByAccount(String account) {
        try {
            PreparedStatement stm = c.prepareStatement(DELETE_VIEWS_BY_ACCOUNT);
            stm.setString(1, account);
            return stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

 
    public double getAverageViewedPrice(String account) {
        String sql = "SELECT AVG(p.price - p.price * p.discount / 100.0) AS avgPrice "
                + "FROM userViews uv "
                + "JOIN products p ON uv.productId = p.productId "
                + "WHERE uv.account = ?";
        try {
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, account);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                double avg = rs.getDouble("avgPrice");
                if (!rs.wasNull()) {
                    return avg;
                }
            }
        } catch (Exception e) {
            System.out.println("getAverageViewedPrice fail: " + e.getMessage());
        }
        return -1;
    }

  
    public String classifySegment(String account) {
        double avg = getAverageViewedPrice(account);
        if (avg < 0) {
            return "Chưa xác định";
        } else if (avg < 5_000_000) {
            return "Thu nhập thấp";
        } else if (avg <= 15_000_000) {
            return "Thu nhập trung bình";
        } else {
            return "Thu nhập cao";
        }
    }

    
    public List<Object[]> getAllUserSegments() {
        List<Object[]> result = new ArrayList<>();
        String sql = "SELECT uv.account, "
                + "AVG(p.price - p.price * p.discount / 100.0) AS avgPrice "
                + "FROM userViews uv "
                + "JOIN products p ON uv.productId = p.productId "
                + "GROUP BY uv.account "
                + "ORDER BY uv.account";
        try {
            PreparedStatement stm = c.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String acc = rs.getString("account");
                double avg = rs.getDouble("avgPrice");
                String segment;
                if (avg < 1000000) {
                    segment = "Thu nhập thấp";
                } else if (avg <= 2000000) {
                    segment = "Thu nhập trung bình";
                } else {
                    segment = "Thu nhập cao";
                }
                result.add(new Object[] { acc, avg, segment });
            }
        } catch (Exception e) {
            System.out.println("getAllUserSegments fail: " + e.getMessage());
        }
        return result;
    }

    private UserView mapRow(ResultSet rs) throws Exception {
        return new UserView(
                rs.getInt("viewId"),
                rs.getString("account"),
                rs.getString("productId"),
                rs.getDate("viewTime"));
    }

    public static void main(String[] args) {
        UserViewDAO dao = new UserViewDAO();
        List<UserView> list = dao.listAll();
        for (UserView view : list) {
            System.out.println(view.toString());
        }
    }
}
