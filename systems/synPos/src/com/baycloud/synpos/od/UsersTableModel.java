package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.table.*;
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
public class UsersTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("User ID"), I18N.getLabelString("Username"), I18N.getLabelString("Password"),
                                   I18N.getLabelString("First Name"), I18N.getLabelString("Last Name"), I18N.getLabelString("Role")};

    private String[] fieldNames = {"users_id", "users_username",
                                  "users_password",
                                  "users_firstname", "users_lastname",
                                  "users_authorization"};

    private Object[][] data;

    public UsersTableModel() {
        showAll();
    }

    public void showAll() {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select users_id, users_username, users_password, users_firstname, users_lastname, users_authorization from users");
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] user = new Object[6];
                user[0] = new Integer(rs.getInt(1));

                for (int j = 1; j < 5; j++) {
                    user[j] = rs.getString(j + 1);
                }

                String[] roles = {I18N.getMessageString("Administrator"), I18N.getMessageString("Manager"), I18N.getMessageString("Employee")};

                user[5] = roles[rs.getInt(6) - 1];

                temp.add(user);
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

    public int findUser(String username) {
        int row = -1;

        for (int i = 0; i < getRowCount(); i++) {
            if (username.equals(data[i][1])) {
                row = i;
                break;
            }
        }

        return row;
    }

    public String addUser(String first, String last, String name, String pass,
                          String role) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (data[i][1].equals(name)) {
                    return I18N.getMessageString("Username already exists:") + name;
                }
            }
        }

        int auth = 1;

        if (role.equals(I18N.getMessageString("Administrator"))) {
            auth = 1;
        }
        if (role.equals(I18N.getMessageString("Manager"))) {
            auth = 2;
        }
        if (role.equals(I18N.getMessageString("Employee"))) {
            auth = 3;
        }

        try {
            StoreDB db = new StoreDB();
            db.update("insert into users values(NULL, '" + StoreDB.escape(first) +
                      "', '" + StoreDB.escape(last) +
                      "', '" + StoreDB.escape(name) + "', '" + StoreDB.escape(pass) +
                      "', " + auth + ")");
            int userId = db.insertID();
            int row = getRowCount();
            Object[][] newData = new Object[row + 1][];

            for (int i = 0; i < row; i++) {
                newData[i] = data[i];
            }

            Object[] temp = new Object[6];
            temp[0] = new Integer(userId);
            temp[1] = name;
            temp[2] = pass;
            temp[3] = first;
            temp[4] = last;
            temp[5] = role;
            newData[row] = temp;
            data = newData;
            //db.shutdown();
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
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

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        try {
            if (col == 0) {
                return;
            }

            if (data != null && col == 1) {
                for (int i = 0; i < data.length; i++) {
                    if (data[i][1].equals(value) && i != row) {
                        return;
                    }
                }
            }
            int userId = ((Integer) data[row][0]).intValue();

            StoreDB db = new StoreDB();

            String newValue = "";

            if (col == 5) {
                newValue = "3";

                if (value.equals(I18N.getMessageString("Administrator"))) {
                    newValue = "1";
                }
                if (value.equals(I18N.getMessageString("Manager"))) {
                    newValue = "2";
                }
            } else {
                newValue = "'" + StoreDB.escape(value.toString()) + "'";
            }
            int r = db.update("update users set " + fieldNames[col] + " = " +
                              newValue +
                              " where users_id = " + userId);
            //db.shutdown();

            data[row][col] = value;
            fireTableCellUpdated(row, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
