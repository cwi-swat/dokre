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
public class ConfigReceiptOrderPanel extends JPanel {
    public ConfigReceiptOrderPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigReceiptOrderPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        String logo = StoreConfiguration.get("receipt.logo");
        String header = StoreConfiguration.get("receipt.header");
        if (header == null) {
            header =
                    "|cA"+I18N.getMessageString("Company Name")+"\n|cA"+I18N.getMessageString("Street Address1")+"\n|cA"+I18N.getMessageString("Street Address2")+"\n|cA"+I18N.getMessageString("City, State ZIP")+"\n|cA"+I18N.getMessageString("Phone")+"|lF";
        }

        String footer = StoreConfiguration.get("receipt.footer");
        if (footer == null) {
            footer = "|lF|cA"+I18N.getMessageString("Thank you for shopping with us!");
        }

        int divider = -1;

        try {
            String bodyDivider = StoreConfiguration.get("receipt.body.divider");
            if (bodyDivider != null) {
                divider = Integer.parseInt(bodyDivider);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (divider < 0) {
            divider = 300;
        }

        String subtotal = StoreConfiguration.get("receipt.body.subtotal");
        if (subtotal == null) {
            subtotal = I18N.getMessageString("SUBTOTAL");
        }

        String total = StoreConfiguration.get("receipt.body.total");
        if (total == null) {
            total = I18N.getMessageString("TOTAL");
        }

        String tax = StoreConfiguration.get("receipt.body.tax");
        if (tax == null) {
            tax = I18N.getMessageString("TAX");
        }

        String paid = StoreConfiguration.get("receipt.body.paid");
        if (paid == null) {
            paid = I18N.getMessageString("AMOUNT TENDERED");
        }

        String change = StoreConfiguration.get("receipt.body.change");
        if (change == null) {
            change = I18N.getMessageString("CHANGE");
        }

        String order = StoreConfiguration.get("receipt.body.order");
        if (order == null) {
            order = I18N.getMessageString("SALE#");
        }

        String time = StoreConfiguration.get("receipt.body.time");
        if (time == null) {
            time = "MM/dd/yyyy hh:mm:ss";
        }

        this.setLayout(borderLayout7);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setLayout(borderLayout1);
        jLabel1.setText(I18N.getLabelString("Logo")+": ");
        jLabel2.setText(I18N.getLabelString("Header")+": ");
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
                                   ConfigReceiptOrderPanel_jButton1_actionAdapter(this));
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
                                   ConfigReceiptOrderPanel_jButton2_actionAdapter(this));
        jPanel11.setLayout(gridLayout3);
        jPanel12.setLayout(gridLayout4);
        gridLayout3.setColumns(1);
        gridLayout3.setHgap(5);
        gridLayout3.setRows(8);
        gridLayout3.setVgap(5);
        gridLayout4.setColumns(1);
        gridLayout4.setHgap(5);
        gridLayout4.setRows(8);
        gridLayout4.setVgap(5);
        jTextField2.setText(I18N.getMessageString("Change")+":");
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jTextField2.setHorizontalAlignment(JTextField.RIGHT);
        jTextField3.setText(I18N.getMessageString("Amount Tendered")+":");
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jTextField3.setHorizontalAlignment(JTextField.RIGHT);
        jTextField4.setText(I18N.getMessageString("Total")+":");
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField4.setHorizontalAlignment(JTextField.RIGHT);
        jTextField5.setText(I18N.getMessageString("Tax")+":");
        jTextField5.addMouseListener(new PopupMenuMouseListener());
        jTextField5.setHorizontalAlignment(JTextField.RIGHT);
        jTextField6.setText(I18N.getMessageString("Subtotal")+":");
        jTextField6.addMouseListener(new PopupMenuMouseListener());
        jTextField6.setHorizontalAlignment(JTextField.RIGHT);
        jLabel7.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel7.setText("0.00");
        jLabel8.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel8.setText("0.00");
        jLabel9.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel9.setText("0.00");
        jLabel10.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel10.setText("0.00");
        jLabel11.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel11.setText("0.00");
        jLabel12.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel12.setText("0.00");
        jPanel13.setLayout(borderLayout4);
        jLabel13.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel13.setText(I18N.getLabelString("Item#")+"  ");
        jLabel14.setHorizontalAlignment(SwingConstants.LEFT);
        jLabel14.setText(I18N.getLabelString("Description"));
        jLabel15.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel15.setText(I18N.getLabelString("Quantity"));
        jTextField1.setColumns(20);
        jLabel16.setText(" 000");
        flowLayout7.setAlignment(FlowLayout.LEFT);
        flowLayout7.setHgap(0);
        component1.setPreferredSize(new Dimension(5, 0));
        component1.setMinimumSize(new Dimension(5, 0));
        jPanel16.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout8.setHgap(0);
        jPanel17.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.RIGHT);
        flowLayout9.setHgap(0);
        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
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
        jPanel18.setLayout(borderLayout3);
        jLabel3.setText(I18N.getLabelString("Body")+": ");
        jPanel14.setLayout(borderLayout5);
        jPanel15.setLayout(borderLayout6);
        jTextField7.setText(I18N.getMessageString("Sale#"));
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jTextField8.setText(time);
        jTextField8.addMouseListener(new PopupMenuMouseListener());
        jTextField8.setHorizontalAlignment(JTextField.RIGHT);
        if (divider > 0) {
            jSplitPane1.setDividerLocation(divider);
        }
        jPanel19.setLayout(flowLayout10);
        flowLayout10.setAlignment(FlowLayout.LEFT);
        jPanel20.setLayout(flowLayout11);
        flowLayout11.setAlignment(FlowLayout.LEFT);
        flowLayout11.setHgap(0);
        flowLayout11.setVgap(0);
        jPanel21.setLayout(flowLayout12);
        flowLayout12.setAlignment(FlowLayout.LEFT);
        flowLayout12.setHgap(0);
        flowLayout12.setVgap(0);
        jLabel18.setText(I18N.getLabelString("Payment Method"));
        jLabel19.setText("");
        jPanel12.add(jLabel7);
        jPanel12.add(jLabel17);
        jPanel12.add(jLabel12);
        jPanel12.add(jLabel11);
        jPanel12.add(jLabel10);
        jPanel12.add(jLabel19);
        jPanel12.add(jLabel9);
        jPanel12.add(jLabel8);
        jPanel3.add(jPanel8);
        jPanel13.add(jLabel13, java.awt.BorderLayout.WEST);
        jPanel13.add(jLabel15, java.awt.BorderLayout.EAST);
        jPanel20.add(jLabel14);
        jPanel11.add(jPanel13);
        jPanel11.add(jPanel20);
        jPanel11.add(jTextField6);
        jPanel11.add(jTextField5);
        jPanel11.add(jTextField4);
        jPanel11.add(jPanel21);
        jPanel21.add(jLabel18);
        jPanel11.add(jTextField3);
        jPanel11.add(jTextField2);
        jSplitPane1.add(jPanel12, JSplitPane.BOTTOM);
        jSplitPane1.add(jPanel11, JSplitPane.TOP);
        jPanel16.add(jTextField7);
        jPanel16.add(component3);
        jPanel14.add(jSplitPane1, BorderLayout.CENTER);
        jPanel14.add(jPanel15, BorderLayout.NORTH);
        jPanel10.add(jLabel5, null);
        jPanel10.add(component1);
        jPanel10.add(jTextField1, null);
        jPanel10.add(component2);
        jPanel10.add(jButton1, null); //jSplitPane1.setDividerLocation(5);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel15.add(jPanel17, java.awt.BorderLayout.EAST);
        jPanel17.add(jTextField8);
        jPanel15.add(jLabel16, java.awt.BorderLayout.CENTER);
        jPanel15.add(jPanel16, java.awt.BorderLayout.WEST);
        jPanel8.add(jScrollPane3, java.awt.BorderLayout.CENTER);
        jPanel8.add(jPanel10, java.awt.BorderLayout.SOUTH);
        jScrollPane3.getViewport().add(jLabel6);
        jPanel2.add(jPanel4);
        jPanel4.add(jLabel1);
        jPanel2.add(jPanel7);
        jPanel7.add(jLabel2);
        jPanel2.add(jPanel6);
        jPanel6.add(jLabel3);
        jPanel2.add(jPanel5);
        jPanel5.add(jLabel4);
        jPanel18.add(jScrollPane2, java.awt.BorderLayout.CENTER);
        jPanel9.add(jButton2);
        jPanel3.add(jScrollPane1);
        jPanel3.add(jPanel14);
        jScrollPane1.getViewport().add(jTextArea1);
        jScrollPane2.getViewport().add(jTextArea2);
        jPanel3.add(jPanel18);
        jScrollPane4.getViewport().add(jPanel19);
        jPanel19.add(jPanel1);
        this.add(jScrollPane4, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel9, java.awt.BorderLayout.SOUTH);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
    }

    JFrame parent;
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    BorderLayout borderLayout1 = new BorderLayout();
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
    JSplitPane jSplitPane1 = new JSplitPane();
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
    JPanel jPanel11 = new JPanel();
    JPanel jPanel12 = new JPanel();
    GridLayout gridLayout3 = new GridLayout();
    GridLayout gridLayout4 = new GridLayout();
    JPanel jPanel13 = new JPanel();
    JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();
    JTextField jTextField6 = new JTextField();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    BorderLayout borderLayout4 = new BorderLayout();
    JLabel jLabel13 = new JLabel();
    JLabel jLabel14 = new JLabel();
    JLabel jLabel15 = new JLabel();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    BorderLayout borderLayout5 = new BorderLayout();
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
    Component component3 = Box.createHorizontalStrut(5);
    JLabel jLabel6 = new JLabel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel18 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JScrollPane jScrollPane4 = new JScrollPane();
    BorderLayout borderLayout7 = new BorderLayout();
    JPanel jPanel19 = new JPanel();
    FlowLayout flowLayout10 = new FlowLayout();
    JPanel jPanel20 = new JPanel();
    FlowLayout flowLayout11 = new FlowLayout();
    JLabel jLabel17 = new JLabel();
    JPanel jPanel21 = new JPanel();
    FlowLayout flowLayout12 = new FlowLayout();
    JLabel jLabel18 = new JLabel();
    JLabel jLabel19 = new JLabel();

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
        String change = jTextField2.getText().trim();
        String paid = jTextField3.getText().trim();
        String total = jTextField4.getText().trim();
        String tax = jTextField5.getText().trim();
        String subtotal = jTextField6.getText().trim();
        String order = jTextField7.getText().trim();
        String time = jTextField8.getText().trim();
        int divider = jSplitPane1.getDividerLocation();
        //System.out.println("width:"+jTextArea1.getSize().width+" divider:"+divider);
        StoreConfiguration.set("receipt.logo", logo);
        StoreConfiguration.set("receipt.header", header);
        StoreConfiguration.set("receipt.footer", footer);
        StoreConfiguration.set("receipt.body.change", change);
        StoreConfiguration.set("receipt.body.total", total);
        StoreConfiguration.set("receipt.body.subtotal", subtotal);
        StoreConfiguration.set("receipt.body.tax", tax);
        StoreConfiguration.set("receipt.body.paid", paid);
        StoreConfiguration.set("receipt.body.divider", "" + divider);
        StoreConfiguration.set("receipt.body.order", order);
        StoreConfiguration.set("receipt.body.time", "" + time);
        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Your receipt design has been saved."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);
    }
}


class ConfigReceiptOrderPanel_jButton2_actionAdapter implements ActionListener {
    private ConfigReceiptOrderPanel adaptee;
    ConfigReceiptOrderPanel_jButton2_actionAdapter(ConfigReceiptOrderPanel
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class ConfigReceiptOrderPanel_jButton1_actionAdapter implements ActionListener {
    private ConfigReceiptOrderPanel adaptee;
    ConfigReceiptOrderPanel_jButton1_actionAdapter(ConfigReceiptOrderPanel
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
