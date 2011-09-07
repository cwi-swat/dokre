package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import com.borland.jbcl.layout.*;
import java.awt.*;

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
public class PasswordPanel extends JPanel {
    private User user;
    private JFrame parent;

    public PasswordPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public PasswordPanel(JFrame parent, User user) {
        this.user = user;
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        //border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
        //titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)), "Password");
        //border2 = BorderFactory.createCompoundBorder(titledBorder1, BorderFactory. createEmptyBorder(5, 5, 5, 5));
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
                white, new Color(148, 145, 140)), "Password");
        border3 = BorderFactory.createCompoundBorder(titledBorder2,
                BorderFactory.
                createEmptyBorder(5, 5, 5, 5));
        border4 = BorderFactory.createEmptyBorder();
        this.setLayout(verticalFlowLayout1);
        jPanel1.setLayout(borderLayout1);
        jPasswordField2.setText("jPasswordField2");
        jPasswordField2.setColumns(40);
        jPasswordField1.setText("jPasswordField1");
        jPasswordField1.setColumns(40);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(3);
        gridLayout2.setVgap(5);
        flowLayout2.setHgap(0);
        flowLayout2.setVgap(0);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(3);
        gridLayout1.setVgap(5);
        jPanel4.setLayout(flowLayout2);
        jLabel2.setText("Confirm Password: ");
        jButton1.setText("Submit");
        jButton1.addActionListener(new PasswordPanel_jButton1_actionAdapter(this));
        jLabel1.setText("New Password: ");
        jPanel3.setLayout(gridLayout2);
        jPanel2.setLayout(gridLayout1);
        jPanel5.setBorder(border3);
        jPanel5.setLayout(flowLayout1);
        jPanel1.setBorder(border4);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        flowLayout1.setHgap(0);
        flowLayout1.setVgap(0);
        this.add(jPanel5, null);
        jPanel5.add(jPanel1, null);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel3.add(jPasswordField1);
        jPanel3.add(jPasswordField2);
        jPanel3.add(jPanel4);
        jPanel4.add(jButton1);
    }

    //private Border border1;
    //private TitledBorder titledBorder1;
    //private Border border2;
    private VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
    private JPanel jPanel5 = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JPasswordField jPasswordField2 = new JPasswordField();
    private JPasswordField jPasswordField1 = new JPasswordField();
    private GridLayout gridLayout2 = new GridLayout();
    private FlowLayout flowLayout2 = new FlowLayout();
    private BorderLayout borderLayout1 = new BorderLayout();
    private GridLayout gridLayout1 = new GridLayout();
    private JPanel jPanel4 = new JPanel();
    private JLabel jLabel2 = new JLabel();
    private JButton jButton1 = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JPanel jPanel3 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private TitledBorder titledBorder2;
    private Border border3;
    private Border border4;
    private FlowLayout flowLayout1 = new FlowLayout();

    public void jButton1_actionPerformed(ActionEvent e) {
        if (jPasswordField1.getDocument() == null) {
            JOptionPane.showMessageDialog(parent,
                                          "Please enter a new password.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;

        }

        if (jPasswordField2.getDocument() == null) {
            JOptionPane.showMessageDialog(parent,
                                          "Please enter confirm your new password.",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;

        }

        String newPass = new String(jPasswordField1.getPassword()).trim();
        String confirmPass = new String(jPasswordField2.getPassword()).trim();

        if (newPass.equals(confirmPass)) {
            if (user.changePassword(newPass)) {
                JOptionPane.showMessageDialog(parent,
                                              "Your password has been changed.",
                                              "Message",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent,
                                              "There has been an error processing your request. Please try again.",
                                              "Error",
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(parent,
                                          "Your new password and confirm password don't match.",
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


class PasswordPanel_jButton1_actionAdapter implements ActionListener {
    private PasswordPanel adaptee;
    PasswordPanel_jButton1_actionAdapter(PasswordPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
