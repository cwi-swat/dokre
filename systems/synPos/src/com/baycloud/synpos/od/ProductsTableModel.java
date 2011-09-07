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
public class ProductsTableModel extends AbstractTableModel {
    private String[] columnNames = {I18N.getLabelString("Item#"), I18N.getLabelString("Description"),
                                   I18N.getLabelString("Price"), I18N.getLabelString("Tax (%)"), I18N.getLabelString("Quantity"),
                                   I18N.getLabelString("Last Modified"), I18N.getLabelString("Date Created")};
    private String[] fieldNames = {"products_barcode", "products_description",
                                  "products_price", "products_tax",
                                  "products_quantity"};
    private Object[][] data;
    private int catId;

    public ProductsTableModel(int catId) {
        setCategory(catId);
    }

    public void setCategory(int catId) {
        this.catId = catId;

        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select products_barcode, products_description, products_price, products_tax, products_quantity, last_modified, date_created, products_id from products where products_category = " +
                    catId);
            Vector temp = new Vector();

            while (rs.next()) {
                Object[] product = new Object[8];
                for (int j = 1; j < 3; j++) {
                    product[j - 1] = rs.getString(j);
                }

                for (int j = 3; j < 5; j++) {
                    product[j - 1] = new Double(rs.getDouble(j));
                }

                product[4] = new Integer(rs.getInt(5));

                for (int j = 6; j < 8; j++) {
                    product[j - 1] = rs.getDate(j);
                }
                product[7] = new Integer(rs.getInt(8));
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

    public void deleteProducts(int[] rows) {
        try {
            StoreDB db = new StoreDB();
            for (int i = 0; i < rows.length; i++) {
                int prodId = ((Integer) data[rows[i]][7]).intValue();
                db.update("delete from products where products_id = " + prodId);
            }
            Object[][] newData = new Object[data.length - rows.length][];
            int j = 0;

            for (int i = 0; i < data.length; i++) {
                boolean deleted = false;

                for (int k = 0; k < rows.length; k++) {
                    if (i == rows[k]) {
                        deleted = true;
                        break;
                    }
                }

                if (!deleted) {
                    newData[j++] = data[i];
                }
            }

            data = newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createProduct() {
        try {
            StoreDB db = new StoreDB();
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update("insert into products values(NULL, '', " +
                      catId + ", '', 'New Product', 0, 0, 0, '"+NOW+"', '"+NOW+"')");
            int prodId = db.insertID();
            Object[] prod = {"", "New Product", new Double(0.00),
                            new Double(0.00),
                            new Integer(0), new java.util.Date(),
                            new java.util.Date(),
                            new Integer(prodId)};
            Object[][] newData = new Object[data.length + 1][];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            newData[data.length] = prod;

            data = newData;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyProduct(int row) {
        try {
            StoreDB db = new StoreDB();
            int prodId = ((Integer) data[row][7]).intValue();
            ResultSet rs = db.query("select products_barcode, products_description, products_price, products_tax, products_quantity, last_modified, date_created, products_id from products where products_id = " +
                                    prodId);
            if (rs.next()) {
                String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();

                db.update("insert into products values(NULL, ''," +
                          catId + ", '" + rs.getString(1) + "', '" +
                          rs.getString(2) +
                          "', '" + rs.getInt(5) + "', '" + rs.getDouble(3) +
                          "', '" + rs.getDouble(4) + "', '"+NOW+"', '"+NOW+"')");
                int id = db.insertID();
                Object[] prod = {rs.getString(1), rs.getString(2),
                                new Double(rs.getDouble(3)),
                                new Double(rs.getDouble(4)),
                                new Integer(rs.getInt(5)), new java.util.Date(),
                                new java.util.Date(),
                                new Integer(id)};
                Object[][] newData = new Object[data.length + 1][];

                for (int i = 0; i < data.length; i++) {
                    newData[i] = data[i];
                }

                newData[data.length] = prod;
                data = newData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int findProduct(String barcode) {
        for (int i = 0; i < getRowCount(); i++) {
            if (data[i][0].equals("barcode")) {
                return i;
            }
        }

        return -1;
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
        if (col < 5) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        try {
            if (col > 4) {
                return;
            }

            int prodId = ((Integer) data[row][7]).intValue();
            StoreDB db = new StoreDB();
            db.update("update products set " + fieldNames[col] + " = '" +
                      value +
                      "' where products_id = " + prodId);
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        } catch (Exception e) {
        }
    }
}
