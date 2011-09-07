package com.baycloud.synpos.od;

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
public class SalesTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Item#"), I18N.getLabelString("Description"),
                                   I18N.getLabelString("Price"), I18N.getLabelString("Sales Tax(%)"), I18N.getLabelString("Quantity")};

    private Object[][] data;

    public int getProductRow(String barcode) {
        for (int i = 0; i < getRowCount(); i++) {
            if (data[i][0].equals(barcode)) {
                return i;
            }
        }

        return -1;
    }

    public void removeAll() {
        data = null;
        fireTableDataChanged();
    }

    public void removeRow(int row) {
        int rowCount = getRowCount();

        if (rowCount > 0 && row < rowCount) {
            Object[][] newData = new Object[rowCount - 1][];

            for (int i = 0; i < row; i++) {
                newData[i] = data[i];
            }

            for (int i = row + 1; i < rowCount; i++) {
                newData[i - 1] = data[i];
            }

            data = newData;
            fireTableDataChanged();
        }
    }

    public void addRow(String code, String barcode, String desc, double price,
                       double tax, int quantity) {
        int rowCount = getRowCount();
        Object[][] newData = new Object[rowCount + 1][];

        for (int i = 0; i < rowCount; i++) {
            newData[i] = data[i];
        }

        Object[] newRow = new Object[6];
        newRow[0] = barcode;
        newRow[1] = desc;
        newRow[2] = new Double(price);
        newRow[3] = new Double(tax);
        newRow[4] = new Integer(quantity);
        newRow[5] = code;
        newData[rowCount] = newRow;
        data = newData;
        fireTableDataChanged();
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
        return columnNames[col];
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
        }
        return true;
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (col == 2 || col == 3) {
            double newValue = ((Double) value).doubleValue();
            if (newValue < 0) {
                return;
            }

            newValue = Math.round(newValue * 100) / 100.0;
            value = new Double(newValue);
        }

        if (col == 4 && ((Integer) value).intValue() < 0) {
            return;
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}
