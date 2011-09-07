package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
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
public class StationsTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Station ID"), I18N.getLabelString("Station Name")};

    private String[] fieldNames = {"stations_id", "stations_name"};

    private Object[][] data;

    public StationsTableModel() {
        showAll();
    }

    public void showAll() {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select * from stations");
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] station = new Object[2];
                station[0] = new Integer(rs.getInt(1));

                for (int j = 1; j < 2; j++) {
                    station[j] = rs.getString(j + 1);
                }

                temp.add(station);
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

    public int findStation(String name) {
        int row = -1;

        for (int i = 0; i < getRowCount(); i++) {
            if (name.equals(data[i][1])) {
                row = i;
                break;
            }
        }

        return row;
    }

    public String addStation(String name) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                if (data[i][1].equals(name)) {
                    return I18N.getMessageString("Station name already exists:") + name;
                }
            }
        }

        try {
            StoreDB db = new StoreDB();
            db.update("insert into stations values(NULL, '" + StoreDB.escape(name) + "')");
            int stationId = db.insertID();
            int row = getRowCount();
            Object[][] newData = new Object[row + 1][];

            for (int i = 0; i < row; i++) {
                newData[i] = data[i];
            }

            Object[] temp = new Object[2];
            temp[0] = new Integer(stationId);
            temp[1] = name;

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
            int stationId = ((Integer) data[row][0]).intValue();

            StoreDB db = new StoreDB();

            String newValue = "'" + StoreDB.escape(value.toString()) + "'";

            int r = db.update("update stations set " + fieldNames[col] + " = " +
                              newValue +
                              " where stations_id = " + stationId);
            //db.shutdown();

            data[row][col] = value;
            fireTableCellUpdated(row, col);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
