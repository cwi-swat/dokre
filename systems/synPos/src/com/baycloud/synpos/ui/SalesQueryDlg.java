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
public class SalesQueryDlg extends JDialog {
    public static final String[] QUERY_PERIODS = {"Today", "Yesterday", "This Week", "Last Week",
                           "This Month", "Last Month"};
    public static final String[] QUERY_PAYMENTS = {"All", "Cash", "Check", "Credit/Debit"};
    public static final String[] QUERY_CANCELLED = {"All", "Yes", "No"};
    public static final String[] QUERY_SYNCED = {"All", "Yes", "No"};

    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JComboBox jComboBox1 = new JComboBox();
    private JLabel jLabel2 = new JLabel();
    private JComboBox jComboBox2 = new JComboBox();
    private JLabel jLabel3 = new JLabel();
    private JComboBox jComboBox3 = new JComboBox();
    Border border4 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border5 = new TitledBorder(border4, "Sales");
    Border border6 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border7 = new TitledBorder(border6, "Sales");
    JPanel jPanel4 = new JPanel();
    JLabel jLabel5 = new JLabel();
    JComboBox jComboBox4 = new JComboBox();
    JLabel jLabel6 = new JLabel();
    JComboBox jComboBox5 = new JComboBox();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    GridLayout gridLayout2 = new GridLayout();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();

    public SalesQueryDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SalesQueryDlg(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jLabel1.setText(I18N.getLabelString("Period")+": ");
        jPanel1.setLayout(gridLayout2);
        jLabel2.setText(I18N.getLabelString("User")+": ");
        jLabel3.setText(I18N.getLabelString("Payment")+": ");
        String[] i18n_periods = {I18N.getMessageString("Today"), I18N.getMessageString("Yesterday"), I18N.getMessageString("This Week"), I18N.getMessageString("Last Week"),
                           I18N.getMessageString("This Month"), I18N.getMessageString("Last Month")};

        for (int i = 0; i < i18n_periods.length; i++) {
            jComboBox1.addItem(i18n_periods[i]);
        }

        Object[] cashiers = User.getAllUsers();
        jComboBox2.addItem(I18N.getMessageString("All"));

        for (int i = 0; i < cashiers.length; i++) {
            jComboBox2.addItem(cashiers[i]);
        }

        String[] i18n_payments = {I18N.getMessageString("All"), I18N.getMessageString("Cash"), I18N.getMessageString("Check"), I18N.getMessageString("Credit Card")};

        for (int i = 0; i < i18n_payments.length; i++) {
            jComboBox3.addItem(i18n_payments[i]);
        }

        String[] i18n_cancelled = {I18N.getMessageString("All"), I18N.getMessageString("Yes"), I18N.getMessageString("No")};

        for (int i = 0; i < i18n_cancelled.length; i++) {
            jComboBox4.addItem(i18n_cancelled[i]);
        }

        String[] i18n_synced = {I18N.getMessageString("All"), I18N.getMessageString("Yes"), I18N.getMessageString("No")};

        for (int i = 0; i < i18n_synced.length; i++) {
            jComboBox5.addItem(i18n_synced[i]);
        }

        Object[] stations = Station.getAllStations();
        jComboBox6.addItem(I18N.getMessageString("All"));

        for (int i = 0; i < stations.length; i++) {
            jComboBox6.addItem(((Station) stations[i]).getName());
        }

        //jComboBox6.addItem(I18N.getMessageString("All"));

        jPanel1.setBorder(null);
        jPanel4.setLayout(gridLayout1);
        jLabel5.setText(I18N.getLabelString("Cancelled")+": ");
        jLabel6.setText(I18N.getLabelString("Synced")+": ");
        jPanel2.setLayout(flowLayout1);
        jButton1.setMnemonic('C');
        jButton1.setText(I18N.getButtonString("Cancel"));
        jButton1.addActionListener(new SalesQueryDlg_jButton1_actionAdapter(this));
        jButton2.setMnemonic('O');
        jButton2.setText(I18N.getButtonString("OK"));
        jButton2.addActionListener(new SalesQueryDlg_jButton2_actionAdapter(this));
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(6);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(6);
        jPanel8.setLayout(flowLayout2);
        jPanel7.setLayout(flowLayout3);
        jPanel6.setLayout(flowLayout4);
        jPanel5.setLayout(flowLayout5);
        jPanel3.setLayout(flowLayout6);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        this.setModal(true);
        this.setTitle(I18N.getLabelString("Sales Query"));
        jLabel4.setText(I18N.getLabelString("Station")+": ");
        jPanel9.setLayout(flowLayout7);
        flowLayout7.setAlignment(FlowLayout.LEFT);
        jPanel1.add(jPanel8);
        jPanel8.add(jComboBox1);
        jPanel1.add(jPanel9);
        jPanel9.add(jComboBox6);
        jPanel1.add(jPanel7);
        jPanel7.add(jComboBox2);
        jPanel1.add(jPanel6);
        jPanel6.add(jComboBox3);
        jPanel1.add(jPanel5);
        jPanel5.add(jComboBox4);
        jPanel1.add(jPanel3);
        jPanel3.add(jComboBox5);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jPanel2.add(jButton1);
        this.getContentPane().add(jPanel4, java.awt.BorderLayout.WEST);
        jPanel4.add(jLabel1);
        jPanel4.add(jLabel4);
        jPanel4.add(jLabel2);
        jPanel4.add(jLabel3);
        jPanel4.add(jLabel5);
        jPanel4.add(jLabel6);

        this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    }

    String[] queryStr = null;
    JLabel jLabel4 = new JLabel();
    JPanel jPanel9 = new JPanel();
    JComboBox jComboBox6 = new JComboBox();
    FlowLayout flowLayout7 = new FlowLayout();

    public void jButton2_actionPerformed(ActionEvent e) {
        queryStr = new String[6];
        queryStr[0] = QUERY_PERIODS[jComboBox1.getSelectedIndex()];
        queryStr[1] = jComboBox6.getSelectedIndex() == 0 ? "All" : jComboBox6.getSelectedItem().toString();
        queryStr[2] = jComboBox2.getSelectedIndex() == 0 ? "All" : jComboBox2.getSelectedItem().toString();
        queryStr[3] = QUERY_PAYMENTS[jComboBox3.getSelectedIndex()];
        queryStr[4] = QUERY_CANCELLED[jComboBox4.getSelectedIndex()];
        queryStr[5] = QUERY_SYNCED[jComboBox5.getSelectedIndex()];
        setVisible(false);
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        queryStr = null;
        setVisible(false);
    }

    public String[] getQuery() {
        return queryStr;
    }
}


class SalesQueryDlg_jButton1_actionAdapter implements ActionListener {
    private SalesQueryDlg adaptee;
    SalesQueryDlg_jButton1_actionAdapter(SalesQueryDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class SalesQueryDlg_jButton2_actionAdapter implements ActionListener {
    private SalesQueryDlg adaptee;
    SalesQueryDlg_jButton2_actionAdapter(SalesQueryDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
