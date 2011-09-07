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
public class LoginDialog extends JDialog implements ActionListener {
    Station station;

    public LoginDialog(Station station) {
        this.station = station;

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
        jLabel1.setDisplayedMnemonic('N');
        jLabel1.setLabelFor(jTextField1);
        jLabel1.setText(I18N.getLabelString("Username") +":");
        jLabel4.setText(station.getName());
        jTextField1.setText("");
        jTextField1.setColumns(20);
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jPasswordField1_keyTyped(e);
            }
        });
        jPasswordField1.addMouseListener(new PopupMenuMouseListener());
        jLabel2.setDisplayedMnemonic('W');
        jLabel2.setLabelFor(jPasswordField1);
        jLabel2.setText(I18N.getLabelString("Password") + ":");
        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(this);
        jButton1.addActionListener(this);
        jButton1.setMnemonic('O');
        jButton1.setText(I18N.getButtonString("OK"));
        jPasswordField1.setText("");
        jPasswordField1.setColumns(20);
        //String[] userTypes = {"Employee", "Manager", "Administrator"};
        //jComboBox1.setModel(new DefaultComboBoxModel(userTypes));
        jPanel1.setLayout(gridLayout1);
        jPanel4.setLayout(borderLayout1);
        jPanel2.setLayout(gridLayout2);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(3);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(3);
        gridLayout2.setVgap(5);
        this.setResizable(false);
        this.setTitle("synPOS "+I18N.getLabelString("Login"));
        jPanel1.setBorder(border1);
        jPanel3.setBorder(border2);
        jPanel3.setLayout(flowLayout1);
        jPanel2.setBorder(border3);
        flowLayout1.setAlignment(FlowLayout.CENTER);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        jLabel3.setText(I18N.getLabelString("Station") +":");
        jPanel1.add(jLabel3);
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel2.add(jLabel4);
        jPanel2.add(jTextField1);
        jPanel2.add(jPasswordField1);
        //jPanel2.add(jComboBox1);
        this.getContentPane().add(jPanel4);
        jPanel4.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton1);
        jPanel3.add(component1, null);
        jPanel3.add(jButton2);
        jPanel4.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel4.add(jPanel1, java.awt.BorderLayout.WEST);
    }

    private void login() {
        //String type = (String) jComboBox1.getSelectedItem();
        String name = jTextField1.getText().trim();

        if (jPasswordField1.getDocument() == null) {
            //setVisible(false);
            JOptionPane.showMessageDialog(this, I18N.getMessageString("Please enter a password."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            //setVisible(true);
            return;
        }

        String pass = new String(jPasswordField1.getPassword()).trim();
        user = User.getUser(name, pass);

        if (user == null) {
            //setVisible(false);
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Invalid username and/or password!"),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            //setVisible(true);
            return;
        }

        setVisible(false);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == jButton1) {
            //String name = jTextField1.getText().trim();
            //String pass = jPasswordField1.getText().trim();
            login();
        }

        if (actionEvent.getSource() == jButton2) {
            System.exit(0);
        }
    }

    void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            //String name = jTextField1.getText().trim();
            //String pass = jPasswordField1.getText().trim();
            login();
        }
    }

    void jPasswordField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            login();
        }
    }

    public User getUser() {
        return user;
    }

    JLabel jLabel1 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JLabel jLabel2 = new JLabel();
    JPasswordField jPasswordField1 = new JPasswordField();
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
    User user;
    FlowLayout flowLayout1 = new FlowLayout();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
}
