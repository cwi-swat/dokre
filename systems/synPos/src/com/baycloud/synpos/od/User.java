package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;

import java.sql.*;
import java.util.*;

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
public class User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int usertype;
    private int userid;

    public static final int EMPLOYEE = 3;
    public static final int MANAGER = 2;
    public static final int ADMINISTRATOR = 1;

    public User(String username, String password, String firstname,
                String lastname, int type, int id) {
        this.usertype = type;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.userid = id;
    }

    public User(int userId) {
        this.userid = userId;

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from users where users_id = " +
                                    userId);
            if (rs.next()) {
                usertype = rs.getInt("users_authorization");
                firstname = rs.getString("users_firstname");
                lastname = rs.getString("users_lastname");
                username = rs.getString("users_username");
                password = rs.getString("users_password");
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String name, String pass) {
        User user = null;

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from users where users_username = '" +
                    StoreDB.escape(name) + "' and users_password = '" +
                    StoreDB.escape(pass) + "'");
            if (rs.next()) {
                int userId = rs.getInt("users_id");
                int userType = rs.getInt("users_authorization");
                String firstName = rs.getString("users_firstname");
                String lastName = rs.getString("users_lastname");
                user = new User(name, pass, firstName, lastName, userType,
                                userId);
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean exists(String name) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from users where users_username = '" +
                    StoreDB.escape(name) + "'");
            if (rs.next()) {
                return true;
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Object[] getAllUsers() {
        Vector temp = new Vector();

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from users");
            while (rs.next()) {
                String username = rs.getString("users_username");
                temp.add(username);
            }
            //db.shutdown();
        } catch (Exception e) {
        }
        return temp.toArray();
    }

    public int getId() {
        return userid;
    }

    public int getType() {
        return usertype;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean changePassword(String newPass) {
        try {
            StoreDB db = new StoreDB();
            int r = db.update("update users set users_password = '" +
                              StoreDB.escape(newPass) +
                              "' where users_username = '" +
                              StoreDB.escape(username) +
                              "' and users_password = '" +
                              StoreDB.escape(password) + "'");
            this.password = newPass;
            //db.shutdown();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
