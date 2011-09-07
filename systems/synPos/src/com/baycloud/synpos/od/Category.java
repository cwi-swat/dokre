package com.baycloud.synpos.od;

import com.baycloud.synpos.util.SwingWorker;
import com.baycloud.synpos.xt.*;

import java.util.Date;
import javax.swing.tree.TreeNode;
import java.util.Vector;
import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.util.Calendar;

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
public class Category extends DefaultMutableTreeNode {
    /** Dates created. */
    private Date created;
    private int id;
    private String code;

    private int lengthOfTask = 1000;
    private int current = 0;
    private boolean done = false;
    private boolean cancelled = false;
    private String statMessage = null;

    public Category(int id) {
        this.id = id;
        // no need to set parent
        try {
            StoreDB db = new StoreDB();
            ResultSet rs = db.query(
                    "select * from categories where categories_id =" + id);

            if (rs.next()) {
                String name = rs.getString("categories_name");
                this.code = rs.getString("categories_code");
                this.created = rs.getDate("date_created");
                setUserObject(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Category(int parentId, String code, String name) {
        super(name);
        this.code = code;

        try {
            StoreDB db = new StoreDB();
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update(
                    "insert into categories values(NULL, '" +
                    StoreDB.escape(code) + "', " +
                    parentId + ", '" +
                    StoreDB.escape(name) +
                    "', '"+NOW+"', '"+NOW+"')");
            this.id = db.insertID();
            this.created = new Date();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return (String) getUserObject();
    }

    public Date getCreated() {
        return created;
    }

    public void setName(String name) {
        try {
            StoreDB db = new StoreDB();
            String NOW = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString();
            db.update("update categories set categories_name ='" +
                      MyDB.escape(name) +
                      "', last_modified='"+NOW+"' where categories_id =" + id);

            setUserObject(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                cancelled = false;
                statMessage = null;
                return new ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        cancelled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }

    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            getCategory((Category) getRoot());
        }

        void getCategory(Category cat) {
            if (cancelled || done) {
                return;
            }
            current += Math.random() * 100; //make some progress
            if (current >= lengthOfTask) {
                current = 0;
            }

            statMessage = "";
            TreeNode[] path = cat.getPath();

            for (int i = 0; i < path.length; i++) {
                Category pathCat = (Category) path[i];

                if (!pathCat.isRoot()) {
                    if (!statMessage.equals("")) {
                        statMessage += "->";
                    }
                    statMessage += pathCat.getName();
                }
            }

            Vector vec = ERPClient.getCategory(cat);

            for (int i = 0; i < vec.size(); i++) {
                Object obj = vec.elementAt(i);

                if (obj instanceof Product) {
                    Product product = (Product) obj;

                    if(product.getId() < 0) {
                        product.save(cat.getId());
                    }
                } else {
                    Category newCat = (Category) obj;
                    cat.add(newCat);
                    getCategory(newCat);
                }
            }

            if (cat.isRoot()) {
                done = true;
            }
        }
    }
}
