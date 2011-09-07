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
public class OrdersProductsTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Item#"), I18N.getLabelString("Description"),
                                   I18N.getLabelString("Price"), I18N.getLabelString("Sales Tax(%)"), I18N.getLabelString("Quantity")};

    private Object[][] data;

    public OrdersProductsTableModel() {
        //setOrder(0);
    }

    public void setOrder(int orderId) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query("select products_barcode, products_description, orders_price, orders_tax, orders_quantity from orders_products where orders_id = " +
                                    orderId);
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] product = new Object[5];
                for (int j = 1; j < 3; j++) {
                    product[j - 1] = rs.getString(j);
                }
                for (int j = 3; j < 5; j++) {
                    product[j - 1] = new Double(rs.getDouble(j));
                }

                product[4] = new Integer(rs.getInt(5));
                temp.add(product);
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
