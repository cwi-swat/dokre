package com.baycloud.synpos.od;

import com.baycloud.synpos.xt.*;

import java.util.*;
import javax.swing.tree.*;
import java.sql.ResultSet;

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
public class CategoryTreeModel extends DefaultTreeModel {
    /**
     * Creates a new Products object, with the entries coming from
     * <code>path</code>.
     */
    public CategoryTreeModel() {
        super(new Category(1));
        addCategory((Category) getRoot());
    }

    public Category cutPaste(Category node1, Category node2) {
        if (node1.getId() == 1) {
            return null; // 0: root
        }

        Category newNode = copyPaste(node1, node2);

        if (newNode != null) {
            deleteCategory(node1);
        }

        return newNode;
    }

    public Category copyPaste(Category node1, Category node2) {
        if (node1 == node2 || node1.getId() == 1) {
            return null;
        }

        try {
            StoreDB db = new StoreDB();
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update("insert into categories values(NULL, '', " +
                      node2.getId() + ", '" + node1.getName() + "', '"+NOW+"', '"+NOW+"')");
            int id = db.insertID();
            Category newDir = new Category(id);
            //newDir.setCreated(new Date());
            insertNodeInto(newDir, node2, node2.getChildCount());

            ResultSet rs = db.query("select products_code, products_barcode, products_description, products_quantity, products_price, products_tax,  last_modified, date_created from products where products_category = " +
                                    node1.getId());
            while (rs.next()) {
                db.update("insert into products values(NULL, '" + rs.getString(1) +"', " +
                          id + ", '" + rs.getString(2) + "', '" +
                          rs.getString(3) +
                          "', '" + rs.getInt(4) + "', '" + rs.getDouble(5) +
                          "', '" +
                          rs.getDouble(6) + "', '"+NOW+"', '"+NOW+"')");
            }

            for (Enumeration e = node1.children(); e.hasMoreElements(); ) {
                copyPaste((Category) e.nextElement(), newDir);
            }

            return newDir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Category createCategory(Category pd) {
        try {
            StoreDB db = new StoreDB();
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update("insert into categories values(NULL, '', " +
                      pd.getId() + ",  'New Category', '"+NOW+"', '"+NOW+"')");
            int id = db.insertID();
/*
            if (id == 1) {
                db.update("insert into categories values(NULL, " +
                          pd.getId() + ", '', 'New Category', '"+NOW+"', '"+NOW+"')");
                id = db.insertID();
                db.update("delete from categories where categories_id = 1");
            }
*/
            Category newDir = new Category(id);
            //newDir.setCreated(new Date());
            insertNodeInto(newDir, pd, pd.getChildCount());
            return newDir;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteAllCategory() {
        Category pd = getCategory(1);

        while (pd.getChildCount() > 0) {
            Category o = (Category) pd.getChildAt(0);
            deleteCategory(o);
        }

        try {
            StoreDB db = new StoreDB();
            db.update("delete from products where products_category = " +
                      pd.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(Category pd) {
        while (pd.getChildCount() > 0) {
            Category o = (Category) pd.getChildAt(0);
            deleteCategory(o);
        }

        try {
            StoreDB db = new StoreDB();
            db.update("delete from products where products_category = " +
                      pd.getId());

            db.update("delete from categories where categories_id = " +
                      pd.getId());
            removeNodeFromParent(pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category getCategory(int id) {
        if (id == 1) {
            return (Category) getRoot();
        }

        for (Enumeration e = root.children(); e.hasMoreElements(); ) {
            Category o = getCategory((Category) e.nextElement(), id);

            if (o != null) {
                return o;
            }
        }

        return null;
    }

    public Category getCategory(Category cat, int id) {
        if (cat.getId() == id) {
            return cat;
        }

        for (Enumeration e = cat.children(); e.hasMoreElements(); ) {
            Category o = getCategory((Category) e.nextElement(), id);

            if (o != null) {
                return o;
            }
        }

        return null;
    }

    protected void addCategory(Category dir) {
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from categories where parent_id = " +
                    dir.getId());
            while (rs.next()) {
                Category childDir = new Category(rs.getInt(
                        "categories_id"));
                dir.add(childDir);
                addCategory(childDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
