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
 * @version 0.9.2
 */
public class Station {
    private String name;
    private int id;

    public Station(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Station(int id) {
        this.id = id;

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from stations where stations_id = " +
                    id);
            if (rs.next()) {
                name = rs.getString("stations_name");
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Station getStation(int id) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from stations where stations_id = " +
                    id);
            if (rs.next()) {
                String name = rs.getString("stations_name");
                return new Station(name, id);
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[] getAllStations() {
        Vector temp = new Vector();

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from stations");
            while (rs.next()) {
                temp.add(new Station(rs.getString("stations_name"),
                                     rs.getInt("stations_id")));
            }
            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp.toArray();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        try {
            StoreDB db = new StoreDB();
            int r = db.update("update stations set stations_name = '" +
                              StoreDB.escape(newName) +
                              "' where stations_id = " + id);
            this.name = newName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
