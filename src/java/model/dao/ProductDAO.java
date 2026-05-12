/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Category;
import model.Product;

public class ProductDAO extends DBContext implements Accessible<Product> {

    private final String GET_ALL_PRODUCT
            = "SELECT p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId";

    private final String GET_PRODUCT_FROM_ID
            = "SELECT p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId "
            + "WHERE p.productId=?";

    private final String DELETE_PRODUCT_BY_ID
            = "DELETE FROM products WHERE productId=?";

    private final String ADD_PRODUCT
            = "INSERT INTO products "
            + "(productId, productName, productImage, brief, postedDate, typeId, account, unit, price, discount, quantity, sold, status) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final String UPDATE_PRODUCT_BY_ID
            = "UPDATE products SET "
            + "productName=?, productImage=?, brief=?, postedDate=?, typeId=?, account=?, unit=?, price=?, discount=?, quantity=?, sold=?, status=? "
            + "WHERE productId=?";

    private final String GET_PRODUCT_BY_CATEGORY_ID
            = "SELECT p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId "
            + "WHERE p.typeId=?";

    private final String SEARCH_PRODUCT_BY_NAME
            = "SELECT p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId "
            + "WHERE p.productName LIKE ?";

    private final String GET_TOP_DISCOUNT_PRODUCTS
            = "SELECT TOP 12 p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId "
            + "ORDER BY p.discount DESC";

    private final String GET_AVAILABLE_PRODUCTS
            = "SELECT p.*, c.categoryName "
            + "FROM products p "
            + "JOIN categories c ON p.typeId = c.typeId "
            + "WHERE p.quantity > 0 AND p.status = 1";
    
    private final String GET_NEW_ARRIVALS_PRODUCTS
        = "SELECT TOP 12 p.*, c.categoryName "
        + "FROM products p "
        + "JOIN categories c ON p.typeId = c.typeId "
        + "WHERE p.status = 1 "
        + "ORDER BY p.postedDate DESC";
    
    @Override
    public int insertRec(Product product) {

        try {

            PreparedStatement stm = c.prepareStatement(ADD_PRODUCT);

            stm.setString(1, product.getProductId());
            stm.setString(2, product.getProductName());
            stm.setString(3, product.getProductImage());
            stm.setString(4, product.getBrief());
            stm.setDate(5, product.getPostedDate());
            stm.setInt(6, product.getType().getTypeId());
            stm.setString(7, product.getAccount().getAccount());
            stm.setString(8, product.getUnit());
            stm.setInt(9, product.getPrice());
            stm.setInt(10, product.getDiscount());
            stm.setInt(11, product.getQuantity());
            stm.setInt(12, product.getSold());
            stm.setInt(13, product.getStatus());

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
    public int updateRec(Product product) {

        try {

            PreparedStatement stm = c.prepareStatement(UPDATE_PRODUCT_BY_ID);

            stm.setString(1, product.getProductName());
            stm.setString(2, product.getProductImage());
            stm.setString(3, product.getBrief());
            stm.setDate(4, product.getPostedDate());
            stm.setInt(5, product.getType().getTypeId());
            stm.setString(6, product.getAccount().getAccount());
            stm.setString(7, product.getUnit());
            stm.setInt(8, product.getPrice());
            stm.setInt(9, product.getDiscount());
            stm.setInt(10, product.getQuantity());
            stm.setInt(11, product.getSold());
            stm.setInt(12, product.getStatus());
            stm.setString(13, product.getProductId());

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
    public int deleteRec(Product product) {

        try {

            PreparedStatement stm = c.prepareStatement(DELETE_PRODUCT_BY_ID);

            stm.setString(1, product.getProductId());

            int n = stm.executeUpdate();

            if (n != 0) {
                return 1;
            }

        } catch (Exception e) {
        }

        return 0;
    }

    @Override
    public Product getObjectById(String id) {

        try {

            PreparedStatement stm = c.prepareStatement(GET_PRODUCT_FROM_ID);

            stm.setString(1, id);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                Product pro = new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                );

                return pro;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Product> listAll() {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(GET_ALL_PRODUCT);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get data fail");
        }

        return listRes;
    }

    public List<Product> getProductsByCategoryId(int typeId) {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(GET_PRODUCT_BY_CATEGORY_ID);

            stm.setInt(1, typeId);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get product by category fail");
        }

        return listRes;
    }

    public List<Product> searchProductByName(String keyword) {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(SEARCH_PRODUCT_BY_NAME);

            stm.setString(1, "%" + keyword + "%");

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Search fail");
        }

        return listRes;
    }

    public List<Product> getTopDiscountProducts() {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(GET_TOP_DISCOUNT_PRODUCTS);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get top discount fail");
        }

        return listRes;
    }

     public List<Product> getNewProducts() {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(GET_NEW_ARRIVALS_PRODUCTS);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get top discount fail");
        }

        return listRes;
    }
    
    public List<Product> getRecentlyViewedProducts(String account) {
        List<Product> listRes = new ArrayList<>();
        String sql = "SELECT TOP 12 p.*, c.categoryName, uv.viewTime "
                   + "FROM products p "
                   + "JOIN categories c ON p.typeId = c.typeId "
                   + "JOIN userViews uv ON p.productId = uv.productId "
                   + "WHERE uv.account = ? AND p.status = 1 "
                   + "ORDER BY uv.viewTime DESC";
        try {
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setString(1, account);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }
        } catch (Exception e) {
            System.out.println("Get recently viewed products fail: " + e.getMessage());
        }
        return listRes;
    }
    
    
    public boolean isExist(String productId) {

        String sql = "SELECT productId FROM products WHERE productId=?";

        try {

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setString(1, productId);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void updateProductQuantityAfterOrder(String productId, int quantityBought) {

        String sql
                = "UPDATE products "
                + "SET quantity = quantity - ?, sold = sold + ? "
                + "WHERE productId=?";

        try {

            PreparedStatement stm = c.prepareStatement(sql);

            stm.setInt(1, quantityBought);
            stm.setInt(2, quantityBought);
            stm.setString(3, productId);

            stm.executeUpdate();

            // kiểm tra nếu hết hàng
            String checkSql
                    = "UPDATE products SET status = 0 WHERE quantity <= 0 AND productId=?";

            PreparedStatement check = c.prepareStatement(checkSql);

            check.setString(1, productId);

            check.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getProductQuantity(String productId) {
        String sql = "SELECT quantity FROM products WHERE productId=?";
        try {
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Product> getAvailableProducts() {

        List<Product> listRes = new ArrayList<>();

        try {

            PreparedStatement stm = c.prepareStatement(GET_AVAILABLE_PRODUCTS);

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {

                Category cate = new Category(
                        rs.getInt("typeId"),
                        rs.getString("categoryName")
                );

                Account acc = new Account();
                acc.setAccount(rs.getString("account"));

                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.out.println("Get available products fail");
        }

        return listRes;
    }

    public List<Product> filterProducts(String cid, String keyword, String sort,
                                    String minPrice, String maxPrice) {

    List<Product> list = new ArrayList<>();

    String sql = "SELECT * FROM products WHERE status = 1 ";

    if (keyword != null && !keyword.trim().isEmpty()) {
        sql += "AND productName LIKE ? ";
    }

    if (cid != null && !cid.isEmpty()) {
        sql += "AND typeId = ? ";
    }

    if (minPrice != null && !minPrice.isEmpty()) {
        sql += "AND (price - price * discount / 100.0) >= ? ";
    }

    if (maxPrice != null && !maxPrice.isEmpty()) {
        sql += "AND (price - price * discount / 100.0) <= ? ";
    }

    switch (sort) {

        case "priceAsc":
            sql += "ORDER BY (price - price * discount / 100.0) ASC";
            break;

        case "priceDesc":
            sql += "ORDER BY (price - price * discount / 100.0) DESC";
            break;

        case "oldest":
            sql += "ORDER BY postedDate ASC";
            break;

        case "sale":
            sql += "ORDER BY discount DESC";
            break;

        case "featured":
            sql += "ORDER BY sold DESC, postedDate DESC";
            break;

        default:
            sql += "ORDER BY postedDate DESC";
    }

    try {

        PreparedStatement ps = c.prepareStatement(sql);

        int index = 1;

        if (keyword != null && !keyword.trim().isEmpty()) {
            ps.setString(index++, "%" + keyword + "%");
        }

        if (cid != null && !cid.isEmpty()) {
            ps.setInt(index++, Integer.parseInt(cid));
        }

        if (minPrice != null && !minPrice.isEmpty()) {
            ps.setInt(index++, Integer.parseInt(minPrice));
        }

        if (maxPrice != null && !maxPrice.isEmpty()) {
            ps.setInt(index++, Integer.parseInt(maxPrice));
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Product p = new Product();

            p.setProductId(rs.getString("productId"));
            p.setProductName(rs.getString("productName"));
            p.setProductImage(rs.getString("productImage"));
            p.setBrief(rs.getString("brief"));
            p.setPostedDate(rs.getDate("postedDate"));

            p.setPrice(rs.getInt("price"));
            p.setDiscount(rs.getInt("discount"));
            p.setUnit(rs.getString("unit"));

            p.setQuantity(rs.getInt("quantity"));
            p.setSold(rs.getInt("sold"));
            p.setStatus(rs.getInt("status"));

            Category cate = new Category();
            cate.setTypeId(rs.getInt("typeId"));
            p.setType(cate);

            Account acc = new Account();
            acc.setAccount(rs.getString("account"));
            p.setAccount(acc);

            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
    
    public int countAvailableProducts() {
        String sql = "SELECT COUNT(*) FROM products WHERE status = 1";
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

    public List<Product> getTopSellingProducts(int limit) {
        List<Product> listRes = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " p.*, c.categoryName " +
                     "FROM products p JOIN categories c ON p.typeId = c.typeId " +
                     "ORDER BY p.sold DESC";
        try {
            PreparedStatement stm = c.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category cate = new Category(rs.getInt("typeId"), rs.getString("categoryName"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRes;
    }

    public List<Product> getLowStockProducts(int threshold) {
        List<Product> listRes = new ArrayList<>();
        String sql = "SELECT p.*, c.categoryName " +
                     "FROM products p JOIN categories c ON p.typeId = c.typeId " +
                     "WHERE p.quantity < ? AND p.status = 1";
        try {
            PreparedStatement stm = c.prepareStatement(sql);
            stm.setInt(1, threshold);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Category cate = new Category(rs.getInt("typeId"), rs.getString("categoryName"));
                Account acc = new Account();
                acc.setAccount(rs.getString("account"));
                listRes.add(new Product(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getString("brief"),
                        rs.getDate("postedDate"),
                        cate,
                        acc,
                        rs.getString("unit"),
                        rs.getInt("price"),
                        rs.getInt("discount"),
                        rs.getInt("quantity"),
                        rs.getInt("sold"),
                        rs.getInt("status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listRes;
    }
}
