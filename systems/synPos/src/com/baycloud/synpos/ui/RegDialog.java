package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

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
public class RegDialog extends JDialog implements ActionListener {
    private boolean registered = false;

    public RegDialog() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public RegDialog(JFrame parent) {
        super(parent);

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        //setModal(true);
        border1 = BorderFactory.createEmptyBorder(5, 5, 0, 5);
        border2 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border3 = BorderFactory.createEmptyBorder(5, 0, 0, 5);
        component1 = Box.createHorizontalStrut(5);
        jLabel1.setDisplayedMnemonic('M');
        jLabel1.setLabelFor(jTextField1);
        jLabel1.setText("Registration Email:");
        jTextField1.setText("");
        jTextField1.setColumns(20);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jLabel2.setDisplayedMnemonic('K');
        jLabel2.setText("Registration Key:");
        jButton2.setMnemonic('C');
        jButton2.setText("Cancel");
        jButton2.addActionListener(this);
        jButton1.addActionListener(this);
        jButton1.setMnemonic('O');
        jButton1.setText("OK");
        //String[] userTypes = {"Employee", "Manager", "Administrator"};
        //jComboBox1.setModel(new DefaultComboBoxModel(userTypes));
        jPanel1.setLayout(gridLayout1);
        jPanel4.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout2);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(2);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(2);
        gridLayout2.setVgap(5);
        this.setResizable(false);
        this.setTitle("SynPOS Registration");
        jPanel1.setBorder(border1);
        jPanel3.setBorder(border2);
        jPanel3.setLayout(flowLayout1);
        jPanel2.setBorder(border3);
        flowLayout1.setAlignment(FlowLayout.CENTER);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jPanel5.setLayout(borderLayout2);
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 10));
        jLabel3.setForeground(Color.blue);
        jLabel3.setBorder(border7);
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText(
                "To obtain a free registration key, please go to http://www.postosc.com.");
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel2.add(jTextField1); //jPanel2.add(jComboBox1);
        jPanel2.add(jTextField2);
        this.getContentPane().add(jPanel4);
        jPanel3.add(jButton1);
        jPanel3.add(component1, null);
        jPanel3.add(jButton2);
        jPanel5.add(jLabel3, java.awt.BorderLayout.NORTH);
        jPanel4.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel4.add(jPanel1, java.awt.BorderLayout.WEST);
        jPanel5.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel4.add(jPanel5, java.awt.BorderLayout.SOUTH);
    }

    private void check() {
        //String type = (String) jComboBox1.getSelectedItem();
        String email = jTextField1.getText().trim();
        String code = jTextField2.getText().trim();

        if (email.length() == 0) {
            //setVisible(false);
            JOptionPane.showMessageDialog(this,
                                          "Please enter your registration email.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            //setVisible(true);
            return;
        }

        if (code.length() == 0) {
            //setVisible(false);
            JOptionPane.showMessageDialog(this,
                                          "Please enter your registration key.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            //setVisible(true);
            return;
        }

        if (Registration.isValid(email, code)) {
            Configuration.set("registration.key", code);
            Configuration.set("registration.email", email);
            registered = true;
            setVisible(false);
        } else {
            registered = true;
            setVisible(false); /*
                  JOptionPane.showMessageDialog(this,
                       "Invalid registration email and/or key.",
                       "Error", JOptionPane.ERROR_MESSAGE);*/
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jButton1) {
            //String name = jTextField1.getText().trim();
            //String pass = jPasswordField1.getText().trim();
            check();
        }

        if (actionEvent.getSource() == jButton2) {
            System.exit(0);
        }
    }

    void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            //String name = jTextField1.getText().trim();
            //String pass = jPasswordField1.getText().trim();
            check();
        }
    }

    void jTextField2_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            check();
        }
    }

    public boolean isRegistered() {
        return registered;
    }

    JLabel jLabel1 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JLabel jLabel2 = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JPanel jPanel1 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    //JComboBox jComboBox1 = new JComboBox();
    private Border border1;
    private Border border2;
    private Border border3;
    private Component component1;
    //private boolean init = true;
    FlowLayout flowLayout1 = new FlowLayout();
    JTextField jTextField2 = new JTextField();
    JPanel jPanel5 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel3 = new JLabel();
    Border border4 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    Border border5 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border6 = BorderFactory.createCompoundBorder(BorderFactory.
            createEtchedBorder(Color.white, new Color(148, 145, 140)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
    Border border7 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
}
