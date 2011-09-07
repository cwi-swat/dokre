package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
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
public class StationDlg extends JDialog {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JLabel jLabel1 = new JLabel();
    private JTextField jTextField1 = new JTextField();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    GridLayout gridLayout1 = new GridLayout();
    JPanel jPanel8 = new JPanel();
    GridLayout gridLayout2 = new GridLayout();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    Station station = null;
    String[] data = null;

    public StationDlg(Station station) {
        this.station = station;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(gridLayout2);
        jLabel1.setText(I18N.getLabelString("Station Name")+":");
        jTextField1.setColumns(20);
        jTextField1.addMouseListener(new PopupMenuMouseListener());

        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(new StationDlg_jButton2_actionAdapter(this));
        jButton3.setMnemonic('O');
        jButton3.setText(I18N.getButtonString("OK"));
        jButton3.addActionListener(new StationDlg_jButton3_actionAdapter(this));
        jPanel2.setLayout(gridLayout1);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(1);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(1);
        jPanel8.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel3.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.RIGHT);
        this.setModal(true);
        jPanel1.add(jPanel8);
        jPanel8.add(jTextField1);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton3);
        jPanel3.add(jButton2);
        jPanel2.add(jLabel1);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        this.getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        if (station != null) {
            jTextField1.setText(station.getName());
            setTitle(I18N.getLabelString("Edit Station"));
        } else {
            setTitle(I18N.getLabelString("New Station"));
        }
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        data = new String[1];
        data[0] = jTextField1.getText().trim();
        setVisible(false);
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        data = null;
        setVisible(false);
    }

    public String[] getStation() {
        return data;
    }
}


class StationDlg_jButton2_actionAdapter implements ActionListener {
    private StationDlg adaptee;
    StationDlg_jButton2_actionAdapter(StationDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class StationDlg_jButton3_actionAdapter implements ActionListener {
    private StationDlg adaptee;
    StationDlg_jButton3_actionAdapter(StationDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
