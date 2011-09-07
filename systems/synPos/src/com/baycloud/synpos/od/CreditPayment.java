package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;
import java.sql.ResultSet;
import jpay.*;

/**
 * <p>Title: synPOS</p>
 *
 * <p>Description: synPOS is a desktop POS (Point Of Sale) client for online
 * ERP, eCommerce, and CRM systems. Released under the GNU General Public
 * License. Absolutely no warranty. Use at your own risk.</p>
 *
 * <p>Copyright: Copyright (c) 2006 synPOS.com</p>
 *
 * <p>Website: www.synpos.com</p>
 *
 * @author H.Q.
 * @version 0.9.1
 */
public class CreditPayment implements Payment {
    private String name;
    private String number;
    private String expireMonth;
    private String expireYear;
    private String auth;
    private String transid;
    private String track1;
    private String track2;

    public CreditPayment(String track1, String track2, String name,
                         String number,
                         String expireMonth, String expireYear) {
        this.name = name;
        this.number = number;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.track2 = track2;
        this.track1 = track1;
    }

    public CreditPayment(CreditCard card) {
        this.name = card.getNameOnCard();
        this.number = card.getAccountNumber();
        this.expireMonth = card.getExpireMonth();
        this.expireYear = card.getExpireYear();
        this.track2 = card.getTrack2();
        this.track1 = card.getTrack1();
    }

    public CreditPayment(String name, String number, String expireMonth,
                         String expireYear) {
        this.name = name;
        this.number = number;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
    }

    public CreditPayment(int payId) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from payments_credit where payments_credit_id = " +
                    payId);
            if (rs.next()) {
                name = rs.getString("payments_credit_name");
                number = rs.getString("payments_credit_number");
                expireMonth = rs.getString("payments_credit_expire_month") + "";
                expireYear = rs.getString("payments_credit_expire_year") + "";
                auth = rs.getString("payments_credit_auth");
                transid = rs.getString("payments_credit_transid");
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getCardType() {
        return CCValidator.getCardName(CCValidator.getCardID(number));
    }

    public String getTrack2() {
        return track2;
    }

    public String getTrack1() {
        return track1;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setAuthCode(String auth) {
        this.auth = auth;
    }

    public String getAuthCode() {
        return auth;
    }

    public String getTransId() {
        return transid;
    }

    public void setTransId(String transid) {
        this.transid = transid;
    }

    public String getPaymentType() {
        return "Credit/Debit";
    }

    public int insertDB(int orderId) {
        int payId = -1;

        try {
            StoreDB db = new StoreDB();
            db.update("insert into payments_credit values(NULL, " + orderId +
                      ", '" +
                      name + "', '" + number + "', " + expireMonth + ", " +
                      expireYear + ", '" + auth + "', '" + transid + "', true)");
            payId = db.insertID();
            //db.shutdown();
        } catch (Exception e) {
        }

        return payId;
    }

    public CreditCard getCreditCard() {
        return new CreditCard(track1, track2, number, expireMonth, expireYear,
                              name);
    }
}
