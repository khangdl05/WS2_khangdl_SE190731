/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author ADMIN
 */
public class Order {
    private int orderId;
    private Account account;
    private String custName;
    private String custAddr;
    private String custPhone;
    private Date createdDate;
    private Date deliveredDate;
    private int totalValue;
    private int ordState;

    public Order() {
    }

    public Order(int orderId, Account account, String custName, String custAddr, String custPhone,
                 Date createdDate, Date deliveredDate, int totalValue, int ordState) {
        this.orderId = orderId;
        this.account = account;
        this.custName = custName;
        this.custAddr = custAddr;
        this.custPhone = custPhone;
        this.createdDate = createdDate;
        this.deliveredDate = deliveredDate;
        this.totalValue = totalValue;
        this.ordState = ordState;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddr() {
        return custAddr;
    }

    public void setCustAddr(String custAddr) {
        this.custAddr = custAddr;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public int getOrdState() {
        return ordState;
    }

    public void setOrdState(int ordState) {
        this.ordState = ordState;
    }
}
