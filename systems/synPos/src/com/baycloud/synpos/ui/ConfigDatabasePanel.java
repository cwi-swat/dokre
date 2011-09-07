package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

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
public class ConfigDatabasePanel extends JPanel {
    public ConfigDatabasePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigDatabasePanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        String dbDriver = Configuration.get("database.driver");
        String dbUrl = Configuration.get("database.url");
        String dbUser = Configuration.get("database.username");
        String dbPass = Configuration.get("database.password");

        this.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout1);
        jPanel3.setLayout(gridLayout2);
        jLabel1.setText(I18N.getLabelString("Driver Name")+": ");
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(5);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(5);
        gridLayout2.setVgap(5);

        jPanel5.setLayout(flowLayout6);
        jButton2.setMnemonic('S');
        jButton2.setText(I18N.getButtonString("Submit"));
        jButton2.addActionListener(new
                                   ConfigDatabasePanel_jButton2_actionAdapter(this));
        jLabel2.setText(I18N.getLabelString("Database URL")+": ");
        jPanel6.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jTextField3.setColumns(20);
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField5.addMouseListener(new PopupMenuMouseListener());
        jPanel8.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        jTextField1.setColumns(20);
        jLabel4.setText(I18N.getLabelString("Username")+": ");
        jLabel5.setText(I18N.getLabelString("Password")+": ");
        jTextField4.setColumns(10);
        jPanel4.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jTextField5.setColumns(10);
        this.add(jPanel1);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        Enumeration keys = sysLooks.keys();
        while (keys.hasMoreElements()) {
        }

        jPanel3.add(jPanel6);
        jPanel6.add(jTextField1);
        jPanel3.add(jPanel4);
        jPanel4.add(jTextField3);
        jPanel3.add(jPanel8);
        jPanel8.add(jTextField4);
        jPanel3.add(jPanel7);
        jPanel7.add(jTextField5);
        jPanel3.add(jPanel5);
        jPanel5.add(jButton2);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel5);
        jTextField1.setText(dbDriver);
        jTextField3.setText(dbUrl);
        jTextField4.setText(dbUser);
        jTextField5.setText(dbPass);
    }

    JFrame parent;
    Hashtable sysLooks = new Hashtable();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JPanel jPanel5 = new JPanel();
    JButton jButton2 = new JButton();
    JLabel jLabel2 = new JLabel();
    JTextField jTextField3 = new JTextField();
    JPanel jPanel6 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JPanel jPanel7 = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    JPanel jPanel8 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    JTextField jTextField1 = new JTextField();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();
    FlowLayout flowLayout5 = new FlowLayout();

    public void jButton2_actionPerformed(ActionEvent event) {

        String dbDriver = jTextField1.getText().trim();
        String dbUrl = jTextField3.getText().trim();
        String dbUser = jTextField4.getText().trim();
        String dbPass = jTextField5.getText().trim();

        if (dbDriver.equals("")) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please enter a driver name."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dbUrl.equals("")) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please enter a database url."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // make a test connection
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbUrl, // filenames
                    dbUser, // username
                    dbPass); // password

            //Configuration.set("system.look", selectedLook);
            //UIManager.setLookAndFeel( (String) sysLooks.get(selectedLook));
        } catch (Exception e) {
            //e.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                                          e.getMessage(),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        Configuration.set("database.driver", dbDriver);
        Configuration.set("database.url", dbUrl);
        Configuration.set("database.username", dbUser);
        Configuration.set("database.password", dbPass);

        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Your database settings have been updated."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);

    }
}


class ConfigDatabasePanel_jButton2_actionAdapter implements ActionListener {
    private ConfigDatabasePanel adaptee;
    ConfigDatabasePanel_jButton2_actionAdapter(ConfigDatabasePanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
