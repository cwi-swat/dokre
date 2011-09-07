package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.text.*;

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
public class OrdersTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Date"), I18N.getLabelString("Sale#"), I18N.getLabelString("Total"), I18N.getLabelString("Sales Tax"), I18N.getLabelString("Payment"),
                                   I18N.getLabelString("User"), I18N.getLabelString("Station"), I18N.getLabelString("Status")};

    private Object[][] data;

    public OrdersTableModel() {
        query("Today", "All", "All", "All", "All", "All");
    }

    public int findOrder(int orderId) {
        int row = -1;

        for (int i = 0; i < getRowCount(); i++) {
            if (((Integer) data[i][1]).intValue() == orderId) {
                row = i;
                break;
            }
        }

        if (row < 0) {
            try {
                StoreDB db = new StoreDB();
                //System.out.print("select orders.date_purchased, orders.orders_id, orders.orders_total, orders.orders_tax, orders.payment_method, users.users_username, orders.orders_status from orders, users where orders.user_id = users.users_id and " + sql);
                ResultSet rs = db.query("select orders.date_purchased, orders.orders_id, orders.orders_total, orders.orders_tax, orders.payment_method, users.users_username, stations.stations_name,orders.orders_status,  orders.synchronized from orders, users, stations where orders.users_id = users.users_id and orders.stations_id = stations.stations_id and orders.orders_id = " +
                                        orderId);

                Vector temp = new Vector();

                while (rs.next()) {
                    Object[] order = new Object[columnNames.length];
                    order[0] = DateFormat.getDateTimeInstance().format(rs.
                            getTimestamp(1));
                    order[1] = new Integer(rs.getInt(2));
                    order[2] = new Double(rs.getDouble(3));
                    order[3] = new Double(rs.getDouble(4));
                    order[4] = rs.getString(5);
                    order[5] = rs.getString(6);
                    order[6] = rs.getString(7);
                    String status = "";

                    if (rs.getBoolean(8) == true) {
                        status += "C";
                    }
                    if (rs.getBoolean(9) == false) {
                        status += "S";
                    }
                    order[7] = status;
                    temp.add(order);
                }

                if (temp.size() > 0) {
                    data = new Object[temp.size()][];

                    for (int i = 0; i < temp.size(); i++) {
                        data[i] = (Object[]) temp.elementAt(i);
                    }

                    row = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return row;
    }

    public void query(String period, String station, String cashier,
                      String payment,
                      String cancelled, String synced) {
        String sql = "";
        Calendar rightNow = Calendar.getInstance();

        if (period.equals("Today")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            sql += " orders.date_purchased > '" +
                    (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (period.equals("Yesterday")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long yesterday = today.getTimeInMillis() - 24 * 60 * 60 * 1000;
            sql += " orders.date_purchased > '" + (new Timestamp(yesterday)) +
                    "' and orders.date_purchased < '" +
                    (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (period.equals("This Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long sunday = today.getTimeInMillis() -
                          (weekday - 1) * 24 * 60 * 60 * 1000;

            sql += " orders.date_purchased > '" + (new Timestamp(sunday)) + "'";
        } else if (period.equals("Last Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long thisSunday = today.getTimeInMillis() -
                              (weekday - 1) * 24 * 60 * 60 * 1000;
            long lastSunday = thisSunday - 7 * 24 * 60 * 60 * 1000;
            sql += " orders.date_purchased < '" + (new Timestamp(thisSunday)) +
                    "' and orders.date_purchased > '" +
                    (new Timestamp(lastSunday)) + "'";
        } else if (period.equals("This Month")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      1, 0, 0, 0);
            sql += " orders.date_purchased >' " +
                    (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (period.equals("Last Month")) {
            Calendar thisMonth = Calendar.getInstance();
            thisMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH),
                          1, 0, 0, 0);
            Calendar lastMonth = Calendar.getInstance();
            lastMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH) - 1,
                          1, 0, 0, 0);

            sql += " orders.date_purchased > '" +
                    (new Timestamp(lastMonth.getTimeInMillis())) +
                    "' and orders.date_purchased < '" +
                    (new Timestamp(thisMonth.getTimeInMillis())) + "'";
        }

        if (!station.equals("All")) {
            sql += " and stations.stations_name = '" + StoreDB.escape(station) +
                    "'";
        }

        if (!cashier.equals("All")) {
            sql += " and users.users_username = '" + StoreDB.escape(cashier) +
                    "'";
        }

        if (!payment.equals("All")) {
            sql += " and orders.payment_method = '" + StoreDB.escape(payment) +
                    "'";
        }

        if (!cancelled.equals("All")) {
            sql += " and orders.status = " +
                    (cancelled.equals("Yes") ? "true" : "false");
        }
        if (!synced.equals("All")) {
            sql += " and orders.synchronized = " +
                    (synced.equals("Yes") ? "true" : "false");
        }

        try {
            StoreDB db = new StoreDB();
            //System.out.print("select orders.date_purchased, orders.orders_id, orders.orders_total, orders.orders_tax, orders.payment_method, users.users_username, orders.orders_status from orders, users where orders.user_id = users.users_id and " + sql);
            ResultSet rs = db.query("select orders.date_purchased, orders.orders_id, orders.orders_total, orders.orders_tax, orders.payment_method, users.users_username, stations.stations_name,orders.orders_status,  orders.synchronized from orders, users, stations where orders.users_id = users.users_id and orders.stations_id = stations.stations_id and " +
                                    sql);
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] order = new Object[columnNames.length];
                order[0] = DateFormat.getDateTimeInstance().format(rs.
                        getTimestamp(1));
                order[1] = new Integer(rs.getInt(2));
                order[2] = new Double(rs.getDouble(3));
                order[3] = new Double(rs.getDouble(4));
                order[4] = rs.getString(5);
                order[5] = rs.getString(6);
                order[6] = rs.getString(7);
                String status = "";

                if (rs.getBoolean(8) == true) {
                    status += "C";
                }
                if (rs.getBoolean(9) == false) {
                    status += "S";
                }
                order[7] = status;
                temp.add(order);
            }

            data = new Object[temp.size()][];

            for (int i = 0; i < temp.size(); i++) {
                data[i] = (Object[]) temp.elementAt(i);
            }

            //db.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancel(int row) {
        if (isCanceled(row)) {
            return;
        }
        if (row >= 0 && row < getRowCount()) {
            int orderId = ((Integer) data[row][1]).intValue();
            Order order = new Order(orderId);
            order.cancel();

            String status = "";
            if (order.isCancelled()) {
                status += "C";
            }
            if (!order.isSynchronized()) {
                status += "S";
            }

            data[row][7] = status;
        }
    }

    public void synchronize(int row) {
        if (isSynchronized(row)) {
            return;
        }

        if (row >= 0 && row < getRowCount()) {
            int orderId = ((Integer) data[row][1]).intValue();
            Order order = new Order(orderId);
            order.sync();
            if (order.isSynchronized()) {
                String status = (String) data[row][7];
                data[row][7] = status.substring(0, status.length() - 1);
            }
        }
    }

    public boolean isSynchronized(int row) {
        if (row >= 0 && row < getRowCount()) {
            String status = (String) data[row][7];
            return!status.endsWith("S");
        }

        return false;
    }

    public boolean isCanceled(int row) {
        if (row >= 0 && row < getRowCount()) {
            String status = (String) data[row][7];
            return status.startsWith("C");
        }

        return false;
    }

    public void setSynchronized(int row) {
        if (isSynchronized(row)) {
            return;
        }

        if (row >= 0 && row < getRowCount()) {
            try {
                int orderId = ((Integer) data[row][1]).intValue();
                StoreDB db = new StoreDB();
                db.update(
                        "update orders set synchronized = true where orders_id = " +
                        orderId);
                String status = (String) data[row][7];
                data[row][7] = status.substring(0, status.length() - 1);
                db.shutdown();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public double getTotal() {
        double total = 0;

        for (int i = 0; i < getRowCount(); i++) {
            if (!((String) data[i][7]).startsWith("C")) {
                total += ((Double) data[i][2]).doubleValue();
            }
        }

        return total;
    }

    /**
     * Returns the number of columns in the model.
     *
     * @return the number of columns in the model
     * @todo Implement this javax.swing.table.TableModel method
     */
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the number of rows in the model.
     *
     * @return the number of rows in the model
     * @todo Implement this javax.swing.table.TableModel method
     */
    public int getRowCount() {
        if (data != null) {
            return data.length;
        } else {
            return 0;
        }
    }

    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     * @todo Implement this javax.swing.table.TableModel method
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
