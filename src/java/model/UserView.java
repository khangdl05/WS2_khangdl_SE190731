/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 * Maps to the 'user_views' table in the database.
 * Fields: viewId, account, productId, viewTime
 *
 * @author ADMIN
 */
public class UserView {

    private int viewId;
    private String account;
    private String productId;
    private Date viewTime;

    public UserView() {
    }

    public UserView(int viewId, String account, String productId, Date viewTime) {
        this.viewId = viewId;
        this.account = account;
        this.productId = productId;
        this.viewTime = viewTime;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getViewTime() {
        return viewTime;
    }

    public void setViewTime(Date viewTime) {
        this.viewTime = viewTime;
    }

    @Override
    public String toString() {
        return "UserView{" + "viewId=" + viewId + ", account=" + account
                + ", productId=" + productId + ", viewTime=" + viewTime + '}';
    }
}
