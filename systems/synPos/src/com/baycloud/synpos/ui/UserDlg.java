package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

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
public class UserDlg extends JDialog {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    private JLabel jLabel2 = new JLabel();
    private JTextField jTextField2 = new JTextField();
    private JLabel jLabel3 = new JLabel();
    private JTextField jTextField3 = new JTextField();
    private JLabel jLabel4 = new JLabel();
    private JTextField jTextField4 = new JTextField();
    private JLabel jLabel5 = new JLabel();
    private JComboBox jComboBox1 = new JComboBox();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    User user = null;
    String[] data = null;

    public UserDlg(User user) {
        this.user = user;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(gridLayout2);
        jLabel1.setText(I18N.getLabelString("First Name")+": ");
        jLabel2.setText(I18N.getLabelString("Last Name")+": ");
        jTextField2.setColumns(10);
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jLabel3.setText(I18N.getLabelString("Username")+": ");
        jTextField3.setColumns(10);
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jLabel4.setText(I18N.getLabelString("Password")+": ");
        jTextField4.setColumns(10);
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField1.setColumns(10);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jLabel5.setText(I18N.getLabelString("Role")+": ");
        String[] roles = {I18N.getMessageString("Administrator"), I18N.getMessageString("Manager"), I18N.getMessageString("Employee")};

        for (int i = 0; i < roles.length; i++) {
            jComboBox1.addItem(roles[i]);
        }
        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(new UserDlg_jButton2_actionAdapter(this));
        jButton3.setMnemonic('O');
        jButton3.setText(I18N.getButtonString("OK"));
        jButton3.addActionListener(new UserDlg_jButton3_actionAdapter(this));
        jPanel2.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(5);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(5);
        jPanel8.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout2);
        jPanel6.setLayout(flowLayout3);
        jPanel5.setLayout(flowLayout4);
        jPanel4.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel3.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.RIGHT);
        this.setModal(true);
        jPanel1.add(jPanel8);
        jPanel8.add(jTextField1);
        jPanel1.add(jPanel7);
        jPanel7.add(jTextField2);
        jPanel1.add(jPanel6);
        jPanel6.add(jTextField3);
        jPanel1.add(jPanel5);
        jPanel5.add(jTextField4);
        jPanel1.add(jPanel4);
        jPanel4.add(jComboBox1);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton3);
        jPanel3.add(jButton2);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel2.add(jLabel3);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel5);

        if (user != null) {
            jTextField1.setText(user.getFirstName());
            jTextField2.setText(user.getLastName());
            jTextField3.setText(user.getUsername());
            jTextField3.setEditable(false);
            jTextField4.setText(user.getPassword());
            jComboBox1.setSelectedIndex(user.getType() - 1);
            setTitle(I18N.getLabelString("Edit User"));
        } else {
            setTitle(I18N.getLabelString("New User"));
        }
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        if (user == null && User.exists(jTextField3.getText().trim())) {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Username already exists:") +
                                          jTextField3.getText().trim(),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        data = new String[5];
        data[0] = jTextField1.getText().trim();
        data[1] = jTextField2.getText().trim();
        data[2] = jTextField3.getText().trim();
        data[3] = jTextField4.getText().trim();
        data[4] = jComboBox1.getSelectedItem().toString();

        setVisible(false);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        data = null;
        setVisible(false);
    }

    public String[] getUser() {
        return data;
    }
}


class UserDlg_jButton2_actionAdapter implements ActionListener {
    private UserDlg adaptee;
    UserDlg_jButton2_actionAdapter(UserDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class UserDlg_jButton3_actionAdapter implements ActionListener {
    private UserDlg adaptee;
    UserDlg_jButton3_actionAdapter(UserDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
