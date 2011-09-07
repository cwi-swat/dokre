package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.KeyEvent;

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
public class ConfigReceiptCCStorePanel extends JPanel {
    public ConfigReceiptCCStorePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigReceiptCCStorePanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        String logo = StoreConfiguration.get("cc.store.logo");
        String header = StoreConfiguration.get("cc.store.header");
        if (header == null) {
            header =
                    "|cA"+I18N.getMessageString("Company Name")+"\n|cA"+I18N.getMessageString("Street Address1")+"\n|cA"+I18N.getMessageString("Street Address2")+"\n|cA"+I18N.getMessageString("City, State ZIP")+"\n|cA"+I18N.getMessageString("Phone")+"|lF";
        }

        String footer = StoreConfiguration.get("cc.store.footer");
        if (footer == null) {
            footer = "|lF|cA"+I18N.getMessageString("Store Copy");
        }

        String appr = StoreConfiguration.get("cc.store.body.approval");
        if (appr == null) {
            appr = I18N.getMessageString("Auth#");
        }

        String sign = StoreConfiguration.get("cc.store.body.signature");
        if (sign == null) {
            sign = I18N.getMessageString("Signature")+":";
        }

        String total = StoreConfiguration.get("cc.store.body.total");
        if (total == null) {
            total = I18N.getMessageString("Total")+":";
        }

        String order = StoreConfiguration.get("cc.store.body.order");
        if (order == null) {
            order = I18N.getMessageString("Sale#");
        }

        String time = StoreConfiguration.get("cc.store.body.time");
        if (time == null) {
            time = "MM/dd/yyyy hh:mm:ss";
        }

        this.setLayout(borderLayout10);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout1);
        jPanel3.setLayout(gridLayout2);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(4);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(4);
        gridLayout2.setVgap(5);
        jLabel1.setText(I18N.getLabelString("Logo")+": ");
        jLabel2.setText(I18N.getLabelString("Header")+": ");
        jLabel3.setText(I18N.getLabelString("Body")+": ");
        jLabel4.setText(I18N.getLabelString("Footer")+": ");
        jPanel4.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel6.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jPanel5.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel8.setLayout(borderLayout2);
        jPanel10.setLayout(flowLayout7);
        jLabel5.setText(I18N.getLabelString("Image File")+": ");

        if (logo != null) {
            jTextField1.setText(logo);
        }
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jButton1.setMnemonic('B');
        jButton1.setText(I18N.getButtonString("Browse"));
        jButton1.addActionListener(new
                ConfigReceiptCCStorePanel_jButton1_actionAdapter(this));
        if (logo != null) {
            jLabel6.setIcon(new ImageIcon(logo));
        }

        jTextArea1.setText(header);
        jTextArea1.addMouseListener(new PopupMenuMouseListener());
        jTextArea1.setColumns(40);
        jTextArea2.setText(footer);
        jTextArea2.addMouseListener(new PopupMenuMouseListener());
        jPanel9.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.RIGHT);
        flowLayout6.setHgap(0);
        jButton2.setMnemonic('S');
        jButton2.setText(I18N.getButtonString("Submit"));
        jButton2.addActionListener(new
                ConfigReceiptCCStorePanel_jButton2_actionAdapter(this));
        jTextField1.setColumns(20);
        jLabel16.setText(" 000");
        flowLayout7.setAlignment(FlowLayout.LEFT);
        flowLayout7.setHgap(0);
        component1.setPreferredSize(new Dimension(5, 0));
        component1.setMinimumSize(new Dimension(5, 0));
        jPanel16.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout8.setHgap(0);
        flowLayout8.setVgap(0);
        jPanel17.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.RIGHT);
        flowLayout9.setHgap(0);
        flowLayout9.setVgap(0);
        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        gridLayout3.setColumns(1);
        gridLayout3.setRows(6);
        gridLayout3.setVgap(5);
        jPanel11.setLayout(borderLayout3);
        jLabel7.setText(I18N.getLabelString("Card Type"));
        jLabel8.setText(I18N.getLabelString("Card Number"));
        jPanel12.setLayout(borderLayout4);
        jLabel10.setToolTipText("");
        jLabel10.setText(I18N.getLabelString("Entry Method"));
        jPanel13.setLayout(borderLayout5);
        jTextField2.setText(appr);
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jTextField2.setColumns(10);
        jLabel11.setText(" 000");
        jPanel18.setLayout(flowLayout10);
        flowLayout10.setHgap(0);
        flowLayout10.setVgap(0);
        jPanel19.setLayout(borderLayout7);
        jTextField3.setText(total);
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jTextField3.setColumns(10);
        jLabel12.setText(" 0.00");
        jPanel20.setLayout(flowLayout11);
        flowLayout11.setHgap(0);
        flowLayout11.setVgap(0);
        jPanel21.setLayout(borderLayout8);
        jTextField4.setText(
                I18N.getMessageString("Signature")+": X________________________________________");
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField4.setColumns(10);
        jTextField7.setColumns(10);
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jPanel23.setLayout(borderLayout9);
        jPanel24.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel2.add(jPanel4);
        jPanel4.add(jLabel1);
        jPanel2.add(jPanel7);
        jPanel7.add(jLabel2);
        jPanel2.add(jPanel6);
        jPanel6.add(jLabel3);
        jPanel2.add(jPanel5);
        jPanel5.add(jLabel4);
        jPanel14.setLayout(gridLayout3);
        jPanel15.setLayout(borderLayout6);
        jTextField7.setText(order);
        jTextField8.setText(time);
        jTextField8.addMouseListener(new PopupMenuMouseListener());
        jTextField8.setHorizontalAlignment(JTextField.RIGHT);
        jPanel1.add(jPanel9, java.awt.BorderLayout.SOUTH);
        jPanel9.add(jButton2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel10.add(jLabel5, null);
        jPanel10.add(component1);
        jPanel10.add(jTextField1, null);
        jPanel10.add(component2);
        jPanel10.add(jButton1, null); //jSplitPane1.setDividerLocation(5);
        jPanel3.add(jPanel8);
        jPanel3.add(jScrollPane1);
        jPanel3.add(jPanel14);
        jScrollPane1.getViewport().add(jTextArea1);
        jPanel15.add(jLabel16, java.awt.BorderLayout.CENTER);
        jPanel15.add(jPanel16, java.awt.BorderLayout.WEST);
        jPanel16.add(jTextField7);
        jPanel15.add(jPanel17, java.awt.BorderLayout.EAST);
        jPanel17.add(jTextField8);
        jPanel11.add(jLabel7, java.awt.BorderLayout.WEST);
        jPanel11.add(jLabel8, java.awt.BorderLayout.EAST);
        jPanel3.add(jPanel23);
        jPanel23.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jScrollPane2.getViewport().add(jTextArea2);
        jPanel12.add(jLabel10, java.awt.BorderLayout.EAST);
        jPanel13.add(jLabel11, java.awt.BorderLayout.CENTER);
        jPanel13.add(jPanel18, java.awt.BorderLayout.WEST);
        jPanel18.add(jTextField2);
        jPanel21.add(jTextField4, java.awt.BorderLayout.NORTH);
        jPanel19.add(jPanel20, java.awt.BorderLayout.WEST);
        jPanel20.add(jTextField3);
        jPanel19.add(jLabel12, java.awt.BorderLayout.CENTER);
        jPanel14.add(jPanel15);
        jPanel14.add(jPanel11);
        jPanel14.add(jPanel12);
        jPanel14.add(jPanel19);
        jPanel14.add(jPanel21);
        jPanel8.add(jScrollPane3, java.awt.BorderLayout.CENTER);
        jPanel8.add(jPanel10, java.awt.BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(jLabel6);
        jScrollPane4.getViewport().add(jPanel24);
        jPanel24.add(jPanel1);
        this.add(jScrollPane4, java.awt.BorderLayout.CENTER);
        jPanel12.add(jPanel13, java.awt.BorderLayout.WEST);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
    }

    JFrame parent;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    JPanel jPanel8 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JPanel jPanel9 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel10 = new JPanel();
    JScrollPane jScrollPane3 = new JScrollPane();
    JLabel jLabel5 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JButton jButton1 = new JButton();
    JTextArea jTextArea1 = new JTextArea();
    JTextArea jTextArea2 = new JTextArea();
    FlowLayout flowLayout6 = new FlowLayout();
    JButton jButton2 = new JButton();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    BorderLayout borderLayout6 = new BorderLayout();
    JTextField jTextField7 = new JTextField();
    JTextField jTextField8 = new JTextField();
    JLabel jLabel16 = new JLabel();
    FlowLayout flowLayout7 = new FlowLayout();
    JPanel jPanel16 = new JPanel();
    JPanel jPanel17 = new JPanel();
    Component component1 = Box.createHorizontalStrut(5);
    Component component2 = Box.createHorizontalStrut(5);
    FlowLayout flowLayout8 = new FlowLayout();
    FlowLayout flowLayout9 = new FlowLayout();
    JLabel jLabel6 = new JLabel();
    GridLayout gridLayout3 = new GridLayout();
    JPanel jPanel11 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JPanel jPanel12 = new JPanel();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel10 = new JLabel();
    JPanel jPanel13 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
    JTextField jTextField2 = new JTextField();
    JLabel jLabel11 = new JLabel();
    JPanel jPanel18 = new JPanel();
    FlowLayout flowLayout10 = new FlowLayout();
    JPanel jPanel19 = new JPanel();
    BorderLayout borderLayout7 = new BorderLayout();
    JPanel jPanel20 = new JPanel();
    JTextField jTextField3 = new JTextField();
    JLabel jLabel12 = new JLabel();
    FlowLayout flowLayout11 = new FlowLayout();
    JPanel jPanel21 = new JPanel();
    BorderLayout borderLayout8 = new BorderLayout();
    JTextField jTextField4 = new JTextField();
    JPanel jPanel23 = new JPanel();
    BorderLayout borderLayout9 = new BorderLayout();
    JScrollPane jScrollPane4 = new JScrollPane();
    JPanel jPanel24 = new JPanel();
    BorderLayout borderLayout10 = new BorderLayout();
    FlowLayout flowLayout1 = new FlowLayout();

    void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            String file = jTextField1.getText().trim();
            jLabel6.setIcon(new ImageIcon(file));
            jScrollPane3.revalidate();
            jScrollPane3.repaint();
        }
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            jLabel6.setIcon(new ImageIcon(file.toString()));
            jScrollPane3.revalidate();
            jScrollPane3.repaint();
            jTextField1.setText(file.toString());
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        String logo = jTextField1.getText().trim();
        String header = jTextArea1.getText().trim();
        String footer = jTextArea2.getText().trim();

        String order = jTextField7.getText().trim();
        String time = jTextField8.getText().trim();
        String sign = jTextField4.getText().trim();
        String appr = jTextField2.getText().trim();

        StoreConfiguration.set("cc.store.logo", logo);
        StoreConfiguration.set("cc.store.header", header);
        StoreConfiguration.set("cc.store.footer", footer);

        StoreConfiguration.set("cc.store.body.order", order);
        StoreConfiguration.set("cc.store.body.time", time);
        StoreConfiguration.set("cc.store.body.signature", sign);
        StoreConfiguration.set("cc.store.body.approval", appr);

        JOptionPane.showMessageDialog(parent,
                I18N.getMessageString("Your design for credit card slip (store copy) has been saved."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);
    }
}


class ConfigReceiptCCStorePanel_jButton2_actionAdapter implements
        ActionListener {
    private ConfigReceiptCCStorePanel adaptee;
    ConfigReceiptCCStorePanel_jButton2_actionAdapter(ConfigReceiptCCStorePanel
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class ConfigReceiptCCStorePanel_jButton1_actionAdapter implements
        ActionListener {
    private ConfigReceiptCCStorePanel adaptee;
    ConfigReceiptCCStorePanel_jButton1_actionAdapter(ConfigReceiptCCStorePanel
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
