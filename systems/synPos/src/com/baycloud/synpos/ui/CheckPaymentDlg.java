package com.baycloud.synpos.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.I18N;

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
public class CheckPaymentDlg extends JDialog {
    public CheckPaymentDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CheckPaymentDlg(JFrame parent, double total, Customer cust) {
        super(parent);
        this.parent = parent;
        this.total = total;
        this.cust = cust;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    JFrame parent;
    double total;
    Customer cust;
    CheckPayment check = null;

    JPanel jPanel17 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel19 = new JPanel();
    JPanel jPanel20 = new JPanel();
    FlowLayout flowLayout6 = new FlowLayout();
    BorderLayout borderLayout8 = new BorderLayout();
    JButton jButton10 = new JButton();
    JButton jButton11 = new JButton();
    Component component3 = Box.createHorizontalStrut(8);
    Border border2 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border3 = new TitledBorder(border2, "Personal Information");
    Border border1 = BorderFactory.createEmptyBorder();
    Border border4 = new TitledBorder(border1, "Personal Information");
    TitledBorder titledBorder1 = new TitledBorder("");
    Border border5 = BorderFactory.createEmptyBorder();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JLabel jLabel1 = new JLabel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JTextField jTextField1 = new JTextField();
    JPanel jPanel3 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JPanel jPanel9 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout7 = new FlowLayout();
    FlowLayout flowLayout8 = new FlowLayout();
    JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();
    JTextField jTextField6 = new JTextField();
    JTextField jTextField7 = new JTextField();
    TitledBorder titledBorder2 = new TitledBorder("");
    Border border6 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    Border border7 = new TitledBorder(border6, "Contact");
    TitledBorder titledBorder3 = new TitledBorder("");
    Border border8 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border9 = new TitledBorder(border8, "Check");
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JPanel jPanel13 = new JPanel();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    FlowLayout flowLayout9 = new FlowLayout();
    FlowLayout flowLayout10 = new FlowLayout();
    FlowLayout flowLayout11 = new FlowLayout();
    JTextField jTextField8 = new JTextField();
    JTextField jTextField9 = new JTextField();
    JTextField jTextField10 = new JTextField();
    Border border10 = BorderFactory.createEmptyBorder();
    Border border11 = BorderFactory.createEmptyBorder(5, 5, 5, 5);

    private void jbInit() throws Exception {
        setModal(true);
        setTitle(I18N.getLabelString("Check"));
        setResizable(false);
        jPanel19.setBorder(border10);
        component3 = Box.createHorizontalStrut(5);
        jButton10.setMnemonic('O');
        jButton10.setText(I18N.getButtonString("OK"));
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton10_actionPerformed(e);
            }
        });
        jButton11.setMnemonic('C');
        jButton11.setText(I18N.getButtonString("Cancel"));
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton11_actionPerformed(e);
            }
        });

        jPanel17.setLayout(borderLayout2);
        flowLayout6.setAlignment(FlowLayout.RIGHT);
        flowLayout6.setHgap(0);
        flowLayout6.setVgap(5);
        jPanel20.setLayout(flowLayout6);
        jPanel19.setLayout(borderLayout8);
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(10);
        gridLayout1.setVgap(5);
        jPanel2.setLayout(gridLayout2);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(10);
        gridLayout2.setVgap(5);
        jTextField1.setColumns(20);
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jLabel1.setText(I18N.getLabelString("Name")+":");
        jLabel2.setText(I18N.getLabelString("Address1")+":");
        jLabel3.setText(I18N.getLabelString("Address2")+":");
        jLabel4.setText(I18N.getLabelString("City")+":");
        jLabel5.setText(I18N.getLabelString("State")+":");
        jLabel6.setText(I18N.getLabelString("Zip")+":");
        jLabel7.setText(I18N.getLabelString("Phone")+":");
        jPanel9.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout2.setHgap(0);
        flowLayout2.setVgap(0);
        jPanel8.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout3.setHgap(0);
        flowLayout3.setVgap(0);
        jPanel7.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout4.setHgap(0);
        flowLayout4.setVgap(0);
        jPanel6.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        flowLayout5.setHgap(0);
        flowLayout5.setVgap(0);
        jPanel5.setLayout(flowLayout7);
        flowLayout7.setAlignment(FlowLayout.LEFT);
        flowLayout7.setHgap(0);
        flowLayout7.setVgap(0);
        jPanel4.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout8.setHgap(0);
        flowLayout8.setVgap(0);
        jTextField2.setColumns(20);
        jTextField3.setColumns(20);
        jTextField4.setColumns(10);
        jTextField5.setColumns(10);
        jTextField6.setColumns(10);
        jTextField7.setColumns(20);
        jPanel1.setBorder(null);
        jLabel8.setText(I18N.getLabelString("Routing#")+":");
        jLabel9.setText(I18N.getLabelString("Account#")+":");
        jLabel10.setText(I18N.getLabelString("Check#")+":");
        jPanel13.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        flowLayout9.setHgap(0);
        flowLayout9.setVgap(0);
        jPanel15.setLayout(flowLayout10);
        flowLayout10.setAlignment(FlowLayout.LEFT);
        flowLayout10.setHgap(0);
        flowLayout10.setVgap(0);
        jPanel14.setLayout(flowLayout11);
        flowLayout11.setAlignment(FlowLayout.LEFT);
        flowLayout11.setHgap(0);
        flowLayout11.setVgap(0);
        jTextField8.setColumns(20);
        jTextField9.setColumns(20);
        jTextField10.setColumns(20);
        jPanel17.setBorder(border11);
        jPanel20.add(jButton10);
        jPanel20.add(jButton11);
        jPanel20.add(component3);
        jPanel17.add(jPanel20, java.awt.BorderLayout.SOUTH);
        getContentPane().add(jPanel17);
        jPanel17.add(jPanel19, java.awt.BorderLayout.CENTER);
        jPanel19.add(jPanel1, java.awt.BorderLayout.WEST);
        jPanel1.add(jLabel8);
        jPanel1.add(jLabel9);
        jPanel1.add(jLabel10);
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(jLabel3);
        jPanel1.add(jLabel4);
        jPanel1.add(jLabel5);
        jPanel1.add(jLabel6);
        jPanel1.add(jLabel7);
        jPanel19.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel13);
        jPanel13.add(jTextField8);
        jPanel2.add(jPanel15);
        jPanel15.add(jTextField9);
        jPanel2.add(jPanel14);
        jPanel14.add(jTextField10);
        jPanel2.add(jPanel3);
        jPanel3.add(jTextField1);
        jPanel2.add(jPanel9);
        jPanel9.add(jTextField2);
        jPanel2.add(jPanel8);
        jPanel8.add(jTextField3);
        jPanel2.add(jPanel7);
        jPanel7.add(jTextField4);
        jPanel2.add(jPanel6);
        jPanel6.add(jTextField5);
        jPanel2.add(jPanel5);
        jPanel5.add(jTextField6);
        jPanel2.add(jPanel4);
        jPanel4.add(jTextField7);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField5.addMouseListener(new PopupMenuMouseListener());
        jTextField6.addMouseListener(new PopupMenuMouseListener());
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jTextField8.addMouseListener(new PopupMenuMouseListener());
        jTextField9.addMouseListener(new PopupMenuMouseListener());
        jTextField10.addMouseListener(new PopupMenuMouseListener());

        if (cust != null) {
            jTextField1.setText(cust.getFirstName() + " " + cust.getLastName());
            jTextField2.setText(cust.getAddress1());
            jTextField3.setText(cust.getAddress2());
            jTextField4.setText(cust.getCity());
            jTextField5.setText(cust.getState());
            jTextField6.setText(cust.getZip());
            jTextField7.setText(cust.getPhone());
        }
    }

    public void jButton10_actionPerformed(ActionEvent e) {
        try {
            String name = jTextField1.getText().trim();
            String street1 = jTextField2.getText().trim();
            String street2 = jTextField3.getText().trim();
            String city = jTextField4.getText().trim();
            String state = jTextField5.getText().trim();
            String zip = jTextField6.getText().trim();
            String country = "US";
            String phone = jTextField7.getText().trim();
            String routing = jTextField8.getText().trim();
            String account = jTextField9.getText().trim();
            String number = jTextField10.getText().trim();

            check = new CheckPayment(name, street1, street2, city,
                                     state,
                                     zip, country, phone, routing,
                                     account,
                                     number);
            setVisible(false);
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                                          ex.getMessage(),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);

        }
    }

    public void jButton11_actionPerformed(ActionEvent e) {
        check = null;
        setVisible(false);
    }

    public CheckPayment getPayment() {
        return check;
    }
}
