/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import dal.DBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class AccountDAO extends DBContext implements Accessible<Account>{
    
    private final String GET_ALL_ACCOUNT = "SELECT * from accounts";
    private final String DELETE_ACCOUNT = "DELETE FROM [dbo].[accounts]\n" +
"      WHERE account=?";
    private final String GET_ACCOUNT_FROM_ACCOUNT = "SELECT * from  accounts where account = ?";
    private final String UPDATE_ACCOUNT_BY_ID = "UPDATE [dbo].[accounts]\n" +
                                "   SET [account] = ?\n" +
                                "      ,[pass] = ?\n" +
                                "      ,[lastName] = ? \n" +
                                "      ,[firstName] = ?\n" +
                                "      ,[birthday] = ?\n" +
                                "      ,[gender] = ?\n" +
                                "      ,[phone] = ?\n" +
                                "      ,[isUse] = ?\n" +
                                "      ,[roleInSystem] = ? \n" +
                                " WHERE account = ? ";
    private final String ADD_ACCOUNT = "INSERT INTO [dbo].[accounts]\n" +
                                "           ([account]\n" +
                                "           ,[pass]\n" +
                                "           ,[lastName]\n" +
                                "           ,[firstName]\n" +
                                "           ,[birthday]\n" +
                                "           ,[gender]\n" +
                                "           ,[phone]\n" +
                                "           ,[isUse]\n" +
                                "           ,[roleInSystem])\n" +
                                "     VALUES\n(" +
                                "           ?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,?\n" +
                                "           ,? )";
    
    @Override
    public int insertRec(Account account) {
        try {
            PreparedStatement stm = c.prepareStatement(ADD_ACCOUNT);
            stm.setString(1, account.getAccount());
            stm.setString(2, account.getPass());
            stm.setString(3, account.getLastName());
            stm.setString(4, account.getFirstName());
            stm.setDate(5, account.getBirthday());
            stm.setBoolean(6, account.isGender());
            stm.setString(7, account.getPhone());
            stm.setBoolean(8, account.isIsUse());
            stm.setInt(9, account.getRoleInSystem());
            int n= stm.executeUpdate();
             if(n!=0){
                return 1;}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int updateRec(Account newaccount) {
         try {
            PreparedStatement stm = c.prepareStatement(UPDATE_ACCOUNT_BY_ID);
            stm.setString(1, newaccount.getAccount());
            stm.setString(2, newaccount.getPass());
            stm.setString(3, newaccount.getLastName());
            stm.setString(4, newaccount.getFirstName());
            stm.setDate(5, newaccount.getBirthday());
            stm.setBoolean(6, newaccount.isGender());
            stm.setString(7, newaccount.getPhone());
            stm.setBoolean(8, newaccount.isIsUse());
            stm.setInt(9, newaccount.getRoleInSystem());
            stm.setString(10, newaccount.getAccount());
            int n= stm.executeUpdate();
             if(n!=0){
                return 1;}
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    @Override
    public int deleteRec(Account account) {
        try {
            PreparedStatement stm = c.prepareStatement(DELETE_ACCOUNT);
            stm.setString(1, account.getAccount());
            int n = stm.executeUpdate();
            if(n!=0){
                return 1;}
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public Account getObjectById(String account) {
        try {
            PreparedStatement stm = c.prepareStatement(GET_ACCOUNT_FROM_ACCOUNT);
            stm.setString(1, account);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                Account acc = new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getBoolean(6),
                        rs.getString(7),
                        rs.getBoolean(8),
                        rs.getInt(9)       
                );
                return acc;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Account> listAll() {
       List<Account> listRes = new ArrayList<>();
        try {
            PreparedStatement stm = c.prepareStatement(GET_ALL_ACCOUNT);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next()){
               listRes.add( new Account(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        rs.getBoolean(6),
                        rs.getString(7),
                        rs.getBoolean(8),
                        rs.getInt(9)       
                )
               );
            }
        } catch (Exception e) {
            System.out.println("Get data fail");
        }
        return listRes;
    }
    
    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        List<Account> listRes = dao.listAll();
        
        for (Account listRe : listRes) {
            System.out.println(listRe.toString());
        }
    }
    
    public Account findByUsername(String username) {
    try {
        PreparedStatement stm = c.prepareStatement(GET_ACCOUNT_FROM_ACCOUNT);
        stm.setString(1, username);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return new Account(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDate(5),
                    rs.getBoolean(6),
                    rs.getString(7),
                    rs.getBoolean(8),
                    rs.getInt(9)
            );
        }
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
    return null;
}
    public Account findByEmail(String email) {
    String sql = "SELECT * FROM accounts WHERE email = ?";
    try {
        PreparedStatement stm = c.prepareStatement(sql);
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();

        if (rs.next()) {
            return new Account(
                rs.getString("account"),
                rs.getString("pass"),
                rs.getString("lastName"),
                rs.getString("firstName"),
                rs.getDate("birthday"),
                rs.getBoolean("gender"),
                rs.getString("email"),
                rs.getBoolean("isUse"),
                rs.getInt("roleInSystem")
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
    
    public int countUsers() {
    String sql = "SELECT COUNT(*) FROM accounts WHERE roleInSystem = 3";
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
    
    public void toggleIsUse(String account) {
    String sql = "UPDATE accounts SET isUse = CASE WHEN isUse = 1 THEN 0 ELSE 1 END WHERE account = ?";
    try {
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, account);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}


