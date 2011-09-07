package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.table.*;

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
public class UsersPanel extends JPanel {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable(new UsersTableModel());
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private FlowLayout flowLayout1 = new FlowLayout();
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();
    private JComboBox jComboBox2 = new JComboBox();
    JPanel jPanel2 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    FlowLayout flowLayout2 = new FlowLayout();
    public UsersPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public UsersPanel(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setBorder(null);
        jPanel1.setLayout(flowLayout1);
        jLabel1.setText(I18N.getLabelString("Username")+": ");
        jButton1.setMnemonic('N');
        jButton1.setText(I18N.getButtonString("New"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        jTextField1.setColumns(15);
        jTextField1.addMouseListener(new PopupMenuMouseListener());

        jButton2.setMnemonic('E');
        jButton2.setText(I18N.getButtonString("Edit"));
        jButton2.addActionListener(new UsersPanel_jButton2_actionAdapter(this));
        jButton3.setMnemonic('F');
        jButton3.setText(I18N.getButtonString("Find"));
        jButton3.addActionListener(new UsersPanel_jButton3_actionAdapter(this));
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jLabel1, null);
        jPanel1.add(jTextField1, null);
        jPanel1.add(jButton3);
        jScrollPane1.getViewport().add(jTable1);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jPanel2.add(jButton1);
        TableColumn roleColumn = jTable1.getColumnModel().getColumn(5);
        String[] roles = {I18N.getMessageString("Administrator"), I18N.getMessageString("Manager"), I18N.getMessageString("Employee")};

        for (int i = 0; i < roles.length; i++) {
            jComboBox2.addItem(roles[i]);
        }
        roleColumn.setCellEditor(new DefaultCellEditor(jComboBox2));
        //jTable1.addMouseListener(new TablePopupMenuMouseListener());
        jTable1.setCellSelectionEnabled(true);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable1);
        jTable1.addMouseListener(new TablePopupMenuMouseListener());
    }

    void jButton1_actionPerformed(ActionEvent e) {
        UserDlg dlg = new UserDlg(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        String[] user = dlg.getUser();

        if (user != null) {
            UsersTableModel model = (UsersTableModel) jTable1.getModel();
            String msg = model.addUser(user[0], user[1], user[2], user[3],
                                       user[4]);

            if (msg != null) {
                JOptionPane.showMessageDialog(parent, msg,
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                dlg.setVisible(true);
            } else {
                jTable1.revalidate();
                jTable1.repaint();
            }
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            UsersTableModel model1 = (UsersTableModel) jTable1.getModel();
            int userId = ((Integer) model1.getValueAt(selectedRow, 0)).
                         intValue();
            UserDlg dlg = new UserDlg(new User(userId));
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dlgSize = dlg.getPreferredSize();
            dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                            (screenSize.height - dlgSize.height) / 2);
            dlg.pack();
            dlg.setVisible(true);

            String[] user = dlg.getUser();

            if (user != null) {
                model1.setValueAt(user[0], selectedRow, 3);
                model1.setValueAt(user[1], selectedRow, 4);
                model1.setValueAt(user[2], selectedRow, 1);
                model1.setValueAt(user[3], selectedRow, 2);
                model1.setValueAt(user[4], selectedRow, 5);
                jTable1.revalidate();
                jTable1.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select an user to edit."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        String username = jTextField1.getText().trim();
        UsersTableModel model1 = (UsersTableModel) jTable1.getModel();

        int row = model1.findUser(username);
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("User not found:") + username,
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

        } else {
            jTable1.changeSelection(row, 1, false, false);
            //int orderId = ( (Integer) model1.getValueAt(row, 1)).intValue();
            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    public void jButton4_actionPerformed(ActionEvent e) {
        UsersTableModel model1 = (UsersTableModel) jTable1.getModel();
        model1.showAll();
        jTable1.revalidate();
        jTable1.repaint();
    }
}


class UsersPanel_jButton3_actionAdapter implements ActionListener {
    private UsersPanel adaptee;
    UsersPanel_jButton3_actionAdapter(UsersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


class UsersPanel_jButton2_actionAdapter implements ActionListener {
    private UsersPanel adaptee;
    UsersPanel_jButton2_actionAdapter(UsersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
