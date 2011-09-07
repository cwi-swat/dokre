package com.baycloud.synpos.ui;

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
 * @version 0.9.2
 */
public class CustomersQueryDlg extends JDialog {
    public static final String[] QUERY_PERIODS = {"", "Today", "This Week", "Last Week", "This Month",
                           "Last Month", "This Year"};
    public static final String[] QUERY_SYNCED = {"", "Yes", "No"};

    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    private TitledBorder titledBorder1;
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
    JLabel jLabel6 = new JLabel();
    JComboBox jComboBox3 = new JComboBox();
    JLabel jLabel7 = new JLabel();
    JTextField jTextField5 = new JTextField();
    JLabel jLabel9 = new JLabel();
    JTextField jTextField7 = new JTextField();
    JLabel jLabel10 = new JLabel();
    JComboBox jComboBox4 = new JComboBox();
    JTextField jTextField8 = new JTextField();
    JLabel jLabel11 = new JLabel();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    JPanel jPanel11 = new JPanel();
    JPanel jPanel12 = new JPanel();
    JPanel jPanel13 = new JPanel();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    JPanel jPanel16 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel17 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel18 = new JPanel();
    JPanel jPanel20 = new JPanel();
    JPanel jPanel21 = new JPanel();
    JPanel jPanel22 = new JPanel();
    JPanel jPanel23 = new JPanel();
    JPanel jPanel24 = new JPanel();
    JPanel jPanel25 = new JPanel();
    JPanel jPanel26 = new JPanel();
    JPanel jPanel27 = new JPanel();
    JPanel jPanel28 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, "Query");
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    FlowLayout flowLayout7 = new FlowLayout();
    FlowLayout flowLayout8 = new FlowLayout();
    FlowLayout flowLayout9 = new FlowLayout();
    FlowLayout flowLayout10 = new FlowLayout();
    FlowLayout flowLayout11 = new FlowLayout();
    FlowLayout flowLayout12 = new FlowLayout();
    FlowLayout flowLayout13 = new FlowLayout();
    FlowLayout flowLayout14 = new FlowLayout();
    FlowLayout flowLayout15 = new FlowLayout();
    FlowLayout flowLayout16 = new FlowLayout();
    FlowLayout flowLayout17 = new FlowLayout();
    FlowLayout flowLayout18 = new FlowLayout();
    FlowLayout flowLayout19 = new FlowLayout();
    FlowLayout flowLayout20 = new FlowLayout();
    FlowLayout flowLayout21 = new FlowLayout();
    String[] queryStr = null;

    public CustomersQueryDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CustomersQueryDlg(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.setModal(true);
        this.setTitle(I18N.getLabelString("Customer Query"));
        jLabel1.setText(I18N.getLabelString("First Name")+": ");
        jLabel2.setText(I18N.getLabelString("Last Name")+": ");
        jTextField2.setColumns(10);
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jLabel3.setText(I18N.getLabelString("City")+": ");
        jTextField3.setColumns(10);
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jLabel11.setText(I18N.getLabelString("Address")+": ");
        jTextField8.setColumns(10);
        jTextField8.addMouseListener(new PopupMenuMouseListener());
        jTextField5.addMouseListener(new PopupMenuMouseListener());
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jLabel4.setText(I18N.getLabelString("State")+": ");
        jTextField4.setColumns(10);
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField1.setColumns(10);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jLabel5.setText(I18N.getLabelString("Date Created")+": ");
        String[] i18n_periods = {"", I18N.getMessageString("Today"), I18N.getMessageString("This Week"), I18N.getMessageString("Last Week"), I18N.getMessageString("This Month"),
                           I18N.getMessageString("Last Month"), I18N.getMessageString("This Year")};

        for (int i = 0; i < i18n_periods.length; i++) {
            jComboBox1.addItem(i18n_periods[i]);
        }
        for (int i = 0; i < i18n_periods.length; i++) {
            jComboBox3.addItem(i18n_periods[i]);
        }
        String[] i18n_synced = {"", I18N.getMessageString("Yes"), I18N.getMessageString("No")};

        for (int i = 0; i < i18n_synced.length; i++) {
            jComboBox4.addItem(i18n_synced[i]);
        }
        jLabel6.setText(I18N.getLabelString("Last Modified")+": ");
        jLabel7.setText(I18N.getLabelString("Zip")+": ");
        jTextField5.setColumns(10);
        jLabel9.setText(I18N.getLabelString("Phone")+": ");
        jTextField7.setColumns(10);
        jLabel10.setText(I18N.getLabelString("Synced")+": ");
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        jButton3.setMnemonic('O');
        jButton3.setText(I18N.getButtonString("OK"));
        jButton3.addActionListener(new CustomersQueryDlg_jButton3_actionAdapter(this));
        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(new CustomersQueryDlg_jButton2_actionAdapter(this));
        jPanel4.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(9);
        jPanel17.setLayout(gridLayout2);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(9);
        jPanel18.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel28.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel27.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jPanel26.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel25.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        jPanel24.setLayout(flowLayout7);
        flowLayout7.setAlignment(FlowLayout.LEFT);
        jPanel23.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        jPanel22.setLayout(flowLayout9);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        jPanel21.setLayout(flowLayout10);
        flowLayout10.setAlignment(FlowLayout.LEFT);
        jPanel20.setLayout(flowLayout11);
        flowLayout11.setAlignment(FlowLayout.LEFT);
        jPanel16.setLayout(flowLayout12);
        flowLayout12.setAlignment(FlowLayout.LEFT);
        jPanel15.setLayout(flowLayout13);
        flowLayout13.setAlignment(FlowLayout.LEFT);
        jPanel14.setLayout(flowLayout14);
        flowLayout14.setAlignment(FlowLayout.LEFT);
        jPanel13.setLayout(flowLayout15);
        flowLayout15.setAlignment(FlowLayout.LEFT);
        jPanel12.setLayout(flowLayout16);
        flowLayout16.setAlignment(FlowLayout.LEFT);
        jPanel11.setLayout(flowLayout17);
        flowLayout17.setAlignment(FlowLayout.LEFT);
        jPanel10.setLayout(flowLayout18);
        flowLayout18.setAlignment(FlowLayout.LEFT);
        jPanel9.setLayout(flowLayout19);
        flowLayout19.setAlignment(FlowLayout.LEFT);
        jPanel8.setLayout(flowLayout20);
        flowLayout20.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout21);
        flowLayout21.setAlignment(FlowLayout.LEFT);
        jPanel17.add(jPanel18);
        jPanel18.add(jLabel9);
        jPanel17.add(jPanel28);
        jPanel28.add(jLabel1);
        jPanel17.add(jPanel27);
        jPanel27.add(jLabel2);
        jPanel17.add(jPanel26);
        jPanel26.add(jLabel11);
        jPanel17.add(jPanel25);
        jPanel25.add(jLabel3);
        jPanel17.add(jPanel24);
        jPanel24.add(jLabel4);
        jPanel17.add(jPanel23);
        jPanel23.add(jLabel7);
        jPanel17.add(jPanel22);
        jPanel22.add(jLabel5);
        jPanel17.add(jPanel21);
        jPanel21.add(jLabel6);
        //jPanel17.add(jPanel20);
        jPanel20.add(jLabel10);
        jPanel4.add(jPanel16);
        jPanel16.add(jTextField7);
        jPanel4.add(jPanel15);
        jPanel15.add(jTextField1);
        jPanel4.add(jPanel14);
        jPanel14.add(jTextField2);
        jPanel4.add(jPanel13);
        jPanel13.add(jTextField8);
        jPanel4.add(jPanel12);
        jPanel12.add(jTextField3);
        jPanel4.add(jPanel11);
        jPanel11.add(jTextField4);
        jPanel4.add(jPanel10);
        jPanel10.add(jTextField5);
        jPanel4.add(jPanel9);
        jPanel9.add(jComboBox1);
        jPanel4.add(jPanel8);
        jPanel8.add(jComboBox3);
        //jPanel4.add(jPanel7);
        jPanel7.add(jComboBox4);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton3);
        jPanel2.add(jButton2);
        this.getContentPane().add(jPanel4, java.awt.BorderLayout.EAST);
        this.getContentPane().add(jPanel17, java.awt.BorderLayout.WEST);
    }

    void jButton3_actionPerformed(ActionEvent e) {
        queryStr = new String[10];
        queryStr[0] = jTextField1.getText().trim();
        queryStr[1] = jTextField2.getText().trim();
        queryStr[2] = jTextField8.getText().trim();
        queryStr[3] = jTextField3.getText().trim();
        queryStr[4] = jTextField4.getText().trim();
        queryStr[5] = jTextField5.getText().trim();
        queryStr[6] = jTextField7.getText().trim();
        //String email = jTextField6.getText().trim();
        queryStr[7] = QUERY_PERIODS[jComboBox1.getSelectedIndex()];
        queryStr[8] = QUERY_PERIODS[jComboBox3.getSelectedIndex()];
        queryStr[9] = "";//jComboBox4.getSelectedItem().toString();
        setVisible(false);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        queryStr = null;
        setVisible(false);
    }

    public String[] getQuery() {
        return queryStr;
    }
}


class CustomersQueryDlg_jButton2_actionAdapter implements ActionListener {
    private CustomersQueryDlg adaptee;
    CustomersQueryDlg_jButton2_actionAdapter(CustomersQueryDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class CustomersQueryDlg_jButton3_actionAdapter implements ActionListener {
    private CustomersQueryDlg adaptee;
    CustomersQueryDlg_jButton3_actionAdapter(CustomersQueryDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
