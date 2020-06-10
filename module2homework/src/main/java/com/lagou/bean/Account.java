package com.lagou.bean;

import java.io.Serializable;

public class Account implements Serializable {

    private String username;
    private String cardNo;
    private int money;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + username + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", money=" + money +
                '}';
    }
}
