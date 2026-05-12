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
import model.Category;

/**
 *
 * @author ADMIN
 */
public class CategoryDAO extends DBContext implements Accessible<Category>{

    private final String GET_ALL_CATEGORY = "select * from categories";
    private final String ADD_NEW_CATEGORY = "INSERT INTO [dbo].[categories]\n" +
                                            "           ([categoryName]\n" +
                                            "           ,[memo])\n" +
                                            "     VALUES\n(" +
                                            "           ?\n" +
                                            "           ,?)";
    private final String DELETE_CATEGORY_BY_ID = "DELETE FROM [dbo].[categories]\n" +
                                            "      WHERE typeId = ? ";
    private final String UPDATE_CATEGORY_BY_ID = "UPDATE [dbo].[categories]\n" +
                                                "   SET [categoryName] = ?\n" +
                                                "      ,[memo] = ?\n" +
                                                " WHERE typeId= ?";
    
    private final String GET_CATEGORY_BY_ID = "SELECT *\n" +
                                            "  FROM [dbo].[categories] \n" +
                                            "  WHERE typeId =?";
    @Override
    public int insertRec(Category category) {
        try {
            PreparedStatement stm = c.prepareStatement(ADD_NEW_CATEGORY);
            stm.setString(1, category.getCategoryName());
            stm.setString(2, category.getMemo());
            int n= stm.executeUpdate();
             if(n!=0){
                return 1;}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateRec(Category newCategory) {
         try {
            PreparedStatement stm = c.prepareStatement(UPDATE_CATEGORY_BY_ID);
            stm.setString(1, newCategory.getCategoryName());
            stm.setString(2, newCategory.getMemo());
            stm.setInt(3, newCategory.getTypeId());
            int n= stm.executeUpdate();
             if(n!=0){
                return 1;}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;    }

    @Override
    public int deleteRec(Category category) {
        try {
            PreparedStatement stm = c.prepareStatement(DELETE_CATEGORY_BY_ID);
            stm.setInt(1, category.getTypeId());
            int n = stm.executeUpdate();
            if(n!=0){
                return 1;}
        } catch (Exception e) {
        }
        return 0;
            }

    @Override
    public Category getObjectById(String id) {
         try {
            PreparedStatement stm = c.prepareStatement(GET_CATEGORY_BY_ID);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Category cate = new Category(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                return cate;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Category> listAll() {
        List<Category> listRes = new ArrayList<>();
        try {
            PreparedStatement stm = c.prepareStatement(GET_ALL_CATEGORY);
            ResultSet rs = stm.executeQuery();         
            while(rs.next()){
               listRes.add( new Category(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3))
               );
            }
        } catch (Exception e) {
            System.out.println("Get data fail");
        }
        return listRes;        
    }
    
}
