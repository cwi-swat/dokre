package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.table.*;
import java.sql.*;
import java.util.*;
import java.text.DateFormat;

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
public class CustomersTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Id"), I18N.getLabelString("First Name"), I18N.getLabelString("Last Name"), I18N.getLabelString("Sex"),
                                   I18N.getLabelString("Date of Birth"), I18N.getLabelString("Address1"), I18N.getLabelString("Address2"),
                                   I18N.getLabelString("City"), I18N.getLabelString("State"), I18N.getLabelString("Zip"), I18N.getLabelString("Phone"), I18N.getLabelString("Fax"),
                                   I18N.getLabelString("Email"), I18N.getLabelString("Date Created"), I18N.getLabelString("Last Modified")};
                                   //"Status"};

    private String[] fieldNames = {"customers_id", "firstname", "lastname",
                                  "title", "birthdate", "city", "state", "zip",
                                  "phone", "fax", "email", "date_created",
                                  "last_modified", "synchronized"};

    private Object[][] data;

    public CustomersTableModel() {
        showAll();
    }

    public void synchronize(int row) {
        if (isSynchronized(row)) {
            return;
        }
        if (row >= 0 && row < getRowCount()) {
            int custId = ((Integer) data[row][0]).intValue();
            Customer cust = new Customer(custId);
            cust.sync();
            if (cust.isSynchronized()) {
                data[row][15] = "";
            } else {
                data[row][15] = "S";
            }
        }
    }

    public boolean isSynchronized(int row) {
        if (row >= 0 && row < getRowCount()) {
            String status = (String) data[row][15];
            return!status.endsWith("S");
        }

        return false;
    }

    public void setSynchronized(int row) {
        if (isSynchronized(row)) {
            return;
        }

        if (row >= 0 && row < getRowCount()) {
            try {
                int custId = ((Integer) data[row][0]).intValue();
                StoreDB db = new StoreDB();
                db.update(
                        "update customers set synchronized = true where customers_id = " +
                        custId);
                data[row][6] = "S";
                //db.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void showAll() {
            try {
                StoreDB db = new StoreDB();
                ResultSet rs = db.query("select * from customers");
                Vector temp = new Vector();

                while (rs.next()) {
                    Object[] cust = new Object[columnNames.length];
                    cust[0] = new Integer(rs.getInt("customers_id"));
                    cust[1] = rs.getString("firstname");
                    cust[2] = rs.getString("lastname");
                    cust[3] = rs.getString("title");
                    cust[4] = rs.getString("birthdate");
                    cust[5] = rs.getString("address1");
                    cust[6] = rs.getString("address2");
                    cust[7] = rs.getString("city");
                    cust[8] = rs.getString("state");
                    cust[9] = rs.getString("zip");
                    cust[10] = rs.getString("phone");
                    cust[11] = rs.getString("fax");
                    cust[12] = rs.getString("email");

                    cust[13] = DateFormat.getDateTimeInstance().format(rs.
                            getDate("date_created"));
                    cust[14] = DateFormat.getDateTimeInstance().format(rs.
                            getDate("last_modified"));
/*
                    String status = "";

                    if (rs.getBoolean("synchronized") == false) {
                        status += "S";
                    }
                    cust[15] = status;
 */
                    temp.add(cust);
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

    public void find(String email) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from customers where email = '" +
                                    email + "'");
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] cust = new Object[columnNames.length];
                cust[0] = new Integer(rs.getInt("customers_id"));
                cust[1] = rs.getString("firstname");
                cust[2] = rs.getString("lastname");
                cust[3] = rs.getString("title");
                cust[4] = rs.getString("birthdate");
                cust[5] = rs.getString("address1");
                cust[6] = rs.getString("address2");
                cust[7] = rs.getString("city");
                cust[8] = rs.getString("state");
                cust[9] = rs.getString("zip");
                cust[10] = rs.getString("phone");
                cust[11] = rs.getString("fax");
                cust[12] = rs.getString("email");

                cust[13] = DateFormat.getDateTimeInstance().format(rs.
                        getDate("date_created"));
                cust[14] = DateFormat.getDateTimeInstance().format(rs.
                        getDate("last_modified"));
/*
                String status = "";

                if (rs.getBoolean("synchronized") == false) {
                    status += "S";
                }
                cust[15] = status;
 */
                temp.add(cust);
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

    public void query(String firstName, String lastName, String address1,
                      String city, String state, String zip, String phone,
                      String dateCreated, String lastModified,
                      String synced) {
        String[] sql = new String[10];

        Calendar rightNow = Calendar.getInstance();

        if (dateCreated.equals("")) {
            // no query
        } else if (dateCreated.equals("Today")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            sql[0] = " customers.date_created > '" +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (dateCreated.equals("Yesterday")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long yesterday = today.getTimeInMillis() - 24 * 60 * 60 * 1000;
            sql[0] = " customers.date_created > '" + (new Timestamp(yesterday)) +
                     "' and customers.date_created < '" +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (dateCreated.equals("This Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long sunday = today.getTimeInMillis() -
                          (weekday - 1) * 24 * 60 * 60 * 1000;

            sql[0] = " customers.date_created > '" + (new Timestamp(sunday)) +
                     "'";
        } else if (dateCreated.equals("Last Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long thisSunday = today.getTimeInMillis() -
                              (weekday - 1) * 24 * 60 * 60 * 1000;
            long lastSunday = thisSunday - 7 * 24 * 60 * 60 * 1000;
            sql[0] = " customers.date_created < '" + (new Timestamp(thisSunday)) +
                     "' and customers.date_created > '" +
                     (new Timestamp(lastSunday)) + "'";
        } else if (dateCreated.equals("This Month")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      1, 0, 0, 0);
            sql[0] = " customers.date_created >' " +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (dateCreated.equals("Last Month")) {
            Calendar thisMonth = Calendar.getInstance();
            thisMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH),
                          1, 0, 0, 0);
            Calendar lastMonth = Calendar.getInstance();
            lastMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH) - 1,
                          1, 0, 0, 0);

            sql[0] = " customers.date_created > '" +
                     (new Timestamp(lastMonth.getTimeInMillis())) +
                     "' and customers.date_created < '" +
                     (new Timestamp(thisMonth.getTimeInMillis())) + "'";
        }

        if (lastModified.equals("")) {
            // no query
        } else if (lastModified.equals("Today")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            sql[1] = " customers.lastModified > '" +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (lastModified.equals("Yesterday")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long yesterday = today.getTimeInMillis() - 24 * 60 * 60 * 1000;
            sql[1] = " customers.lastModified > '" + (new Timestamp(yesterday)) +
                     "' and customers.lastModified < '" +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (lastModified.equals("This Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long sunday = today.getTimeInMillis() -
                          (weekday - 1) * 24 * 60 * 60 * 1000;

            sql[1] = " customers.lastModified > '" + (new Timestamp(sunday)) +
                     "'";
        } else if (lastModified.equals("Last Week")) {
            Calendar today = Calendar.getInstance();
            int weekday = today.get(Calendar.DAY_OF_WEEK);
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      rightNow.get(Calendar.DATE), 0, 0, 0);
            long thisSunday = today.getTimeInMillis() -
                              (weekday - 1) * 24 * 60 * 60 * 1000;
            long lastSunday = thisSunday - 7 * 24 * 60 * 60 * 1000;
            sql[1] = " customers.lastModified < '" + (new Timestamp(thisSunday)) +
                     "' and customers.lastModified > '" +
                     (new Timestamp(lastSunday)) + "'";
        } else if (lastModified.equals("This Month")) {
            Calendar today = Calendar.getInstance();
            today.set(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH),
                      1, 0, 0, 0);
            sql[1] = " customers.lastModified >' " +
                     (new Timestamp(today.getTimeInMillis())) + "'";
        } else if (lastModified.equals("Last Month")) {
            Calendar thisMonth = Calendar.getInstance();
            thisMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH),
                          1, 0, 0, 0);
            Calendar lastMonth = Calendar.getInstance();
            lastMonth.set(rightNow.get(Calendar.YEAR),
                          rightNow.get(Calendar.MONTH) - 1,
                          1, 0, 0, 0);

            sql[1] = " customers.lastModified > '" +
                     (new Timestamp(lastMonth.getTimeInMillis())) +
                     "' and customers.lastModified < '" +
                     (new Timestamp(thisMonth.getTimeInMillis())) + "'";
        }

        if (firstName != null) {
            sql[2] = " customers.firstname like '%" + StoreDB.escape(firstName) +
                     "%'";
        }

        if (!lastName.equals("")) {
            sql[3] = " customers.lastname like '%" + StoreDB.escape(lastName) +
                     "%'";
        }

        if (!city.equals("")) {
            sql[4] = " customers.city like '%" + StoreDB.escape(city) + "%'";
        }

        if (!state.equals("")) {
            sql[5] = " customers.state like '%" + StoreDB.escape(state) + "%'";
        }

        if (!zip.equals("")) {
            sql[6] = " customers.zip like '%" + StoreDB.escape(zip) + "%'";
        }

        if (!phone.equals("")) {
            sql[7] = " customers.phone like '%" + StoreDB.escape(phone) + "%'";
        }

        if (!synced.equals("")) {
            sql[8] = " customers.synchronized = " +
                     (synced.equals("Yes") ? "true" : "false");
        }
        if (!address1.equals("")) {
            sql[9] = " customers.address1 like '%" +
                     address1 + "%'";
        }
        String sqlStr = "";

        for (int i = 0; i < 10; i++) {
            if (sql[i] != null && !sql[i].equals("")) {
                if (sqlStr.length() > 0) {
                    sqlStr += " and ";
                }
                sqlStr += sql[i];
            }
        }

        String query = "select * from customers";

        if (sqlStr.length() > 0) {
            query += " where " +sqlStr;
        }
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(query);
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] cust = new Object[columnNames.length];
                cust[0] = new Integer(rs.getInt("customers_id"));
                cust[1] = rs.getString("firstname");
                cust[2] = rs.getString("lastname");
                cust[3] = rs.getString("title");
                cust[4] = rs.getString("birthdate");
                cust[5] = rs.getString("address1");
                cust[6] = rs.getString("address2");
                cust[7] = rs.getString("city");
                cust[8] = rs.getString("state");
                cust[9] = rs.getString("zip");
                cust[10] = rs.getString("phone");
                cust[11] = rs.getString("fax");
                cust[12] = rs.getString("email");

                cust[13] = DateFormat.getDateTimeInstance().format(rs.
                        getDate("date_created"));
                cust[14] = DateFormat.getDateTimeInstance().format(rs.
                        getDate("last_modified"));
/*
                String status = "";

                if (rs.getBoolean("synchronized") == false) {
                    status += "S";
                }
                cust[15] = status;
*/
                temp.add(cust);
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

    public void UpdateCustomer(Customer cust) {
        if (cust == null) {
            return;
        }

        int custId = cust.getId();
        if (custId < 0) {
            return;
        }

        int row = -1;

        for (int i = 0; i < getRowCount(); i++) {
            if (((Integer) data[i][0]).intValue() == custId) {
                row = i;
                break;
            }
        }

        if (row < 0) {
            return;
        }

        data[row][1] = cust.getFirstName();
        data[row][2] = cust.getLastName();
        data[row][3] = cust.getTitle();
        data[row][4] = cust.getBirthDate();
        data[row][5] = cust.getCity();
        data[row][6] = cust.getState();
        data[row][7] = cust.getZip();
        data[row][8] = cust.getPhone();
        data[row][9] = cust.getFax();
        data[row][10] = cust.getEmail();
        data[row][11] = cust.getDateCreated();
        data[row][12] = cust.getLastModified();
        data[row][13] = cust.isSynchronized() ? "" : "S";
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
