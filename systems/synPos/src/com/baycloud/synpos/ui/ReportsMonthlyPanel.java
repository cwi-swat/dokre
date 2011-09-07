package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.DateFormat;
import java.io.*;
import java.awt.Component;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.Border;
import java.text.SimpleDateFormat;
import com.baycloud.synpos.util.Sorter;

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
public class ReportsMonthlyPanel extends JPanel {
    public ReportsMonthlyPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jTextArea1.setText("");
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextArea1.addMouseListener(new PopupMenuMouseListener());
        jTextArea1.setColumns(40);
        jPanel1.setLayout(borderLayout2);
        jLabel1.setText(I18N.getLabelString("Month") + ": ");
        jPanel3.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        flowLayout2.setHgap(0);
        jButton1.setMnemonic('S');
        jButton1.setText(I18N.getButtonString("Save"));
        jButton1.addActionListener(new
                                   ReportsMonthlyPanel_jButton1_actionAdapter(this));
        jButton2.setMnemonic('P');
        jButton2.setText(I18N.getButtonString("Print"));
        jButton2.addActionListener(new
                                   ReportsMonthlyPanel_jButton2_actionAdapter(this));
        jButton3.setMnemonic('G');
        jButton3.setText(I18N.getButtonString("Go"));
        jButton3.addActionListener(new
                                   ReportsMonthlyPanel_jButton3_actionAdapter(this));
        this.add(jPanel1, java.awt.BorderLayout.WEST);
        jScrollPane1.getViewport().add(jTextArea1);
        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);
        jPanel3.add(jLabel1);
        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        jTextField1.setText(df.format(new Date()));
        //jFormattedTextField1.setInputVerifier(new FormattedTextFieldVerifier());
        jPanel3.add(jTextField1);
        jPanel3.add(jButton3);
        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jPanel2.add(component1);
        jPanel2.add(jButton1);

        Border border6 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        Border border7 = new TitledBorder(border6,
                                          I18N.getLabelString("Sales by Station"));
        Border border8 = BorderFactory.createCompoundBorder(border7,
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Border border3 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        Border border4 = new TitledBorder(border3,
                                          I18N.getLabelString("Sales by User"));
        Border border5 = BorderFactory.createCompoundBorder(border4,
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Border border9 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        Border border10 = new TitledBorder(border9,
                                           I18N.getLabelString(
                "Top 5 Bestsellers by Quantity"));
        Border border11 = BorderFactory.createCompoundBorder(border10,
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Border border12 = BorderFactory.createEtchedBorder(Color.white,
                new Color(148, 145, 140));
        Border border13 = new TitledBorder(border12,
                                           I18N.getLabelString(
                "Top 5 Bestsellers by Dollar"));
        Border border14 = BorderFactory.createCompoundBorder(border13,
                BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jPanel5.setBorder(border5);
        jPanel5.setLayout(borderLayout3);
        jPanel5.add(piePanel1, BorderLayout.CENTER);
        jPanel6.setLayout(borderLayout4);
        jPanel6.setBorder(border8);
        jPanel6.add(piePanel2, BorderLayout.CENTER);
        jPanel7.setBorder(border11);
        jPanel7.setLayout(borderLayout5);
        jPanel7.add(piePanel3, BorderLayout.CENTER);
        jPanel8.setLayout(borderLayout6);
        jPanel8.setBorder(border14);
        jPanel8.add(piePanel4, BorderLayout.CENTER);
        jPanel4.setLayout(gridLayout1);
        jPanel4.add(jPanel5);
        jPanel4.add(jPanel6);
        jPanel4.add(jPanel7);
        jPanel4.add(jPanel8);
        this.add(jPanel4, java.awt.BorderLayout.CENTER);
    }

    JFrame parent;
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTextArea jTextArea1 = new JTextArea();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JLabel jLabel1 = new JLabel();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JTextField jTextField1 = new JTextField();
    JPanel jPanel4 = new JPanel();
    GridLayout gridLayout1 = new GridLayout(2, 2);
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    BorderLayout borderLayout4 = new BorderLayout();
    BorderLayout borderLayout5 = new BorderLayout();
    BorderLayout borderLayout6 = new BorderLayout();
    PieChart piePanel1 = new PieChart();
    PieChart piePanel2 = new PieChart();
    PieChart piePanel3 = new PieChart();
    PieChart piePanel4 = new PieChart();
    Component component1 = Box.createHorizontalStrut(5);

    public void jButton3_actionPerformed(ActionEvent e) {
        try {
            String str = jTextField1.getText();
            SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
            Date date = df.parse(str);
            setDate(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parent, ex.getMessage(), I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }

    }

    public void jButton2_actionPerformed(ActionEvent e) {
        if (report != null) {
            try {
                Printer.print(report.split("\n"));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        if (report != null) {
            try {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(parent);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    PrintStream out = new PrintStream(file);
                    out.print(report);
                    out.close();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, ex.getMessage(), I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String report;

    private void setDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        MonthlyReport dp = new MonthlyReport(c.get(Calendar.MONTH),
                                             c.get(Calendar.YEAR));
        Hashtable totals = dp.getTotals();
        Hashtable stationTotals = dp.getStationTotals();
        Hashtable userTotals = dp.getUserTotals();
        Hashtable quantityTotals = dp.getBestSellersByQuantity();
        Hashtable dollarTotals = dp.getBestSellersByDollar();
        Map.Entry[] stationEntries = Sorter.getSortedHashtableEntries(
                stationTotals, false);
        Map.Entry[] userEntries = Sorter.getSortedHashtableEntries(userTotals, false);
        Map.Entry[] quantityEntries = Sorter.getSortedHashtableEntries(
                quantityTotals, false);
        Map.Entry[] dollarEntries = Sorter.getSortedHashtableEntries(
                dollarTotals, false);

        SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
        report = I18N.getLabelString("Monthly Report")+" " + df.format(date) + "\n\n";

        Iterator keys = totals.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            report += key + ": ";
            if (!key.equals(I18N.getLabelString("Number of Items"))) {
                report += "$";
            }
            report += totals.get(key) + "\n";
        }

        report += "\n"+I18N.getLabelString("Sales by User")+"\n";

        for (int i = 0; i < userEntries.length; i++) {
            String key = (String) userEntries[i].getKey();
            report += (i + 1) + ". " + key + ": $" + userEntries[i].getValue() +
                    "\n";
        }

        report += "\n"+I18N.getLabelString("Sales by Station")+"\n";

        for (int i = 0; i < stationEntries.length; i++) {
            String key = (String) stationEntries[i].getKey();
            report += (i + 1) + ". " + key + ": $" + stationEntries[i].getValue() +
                    "\n";
        }

        report += "\n"+I18N.getLabelString("Top 5 Bestsellers by Quantity")+"\n";

        Hashtable qb = new Hashtable();
        int items = ((Integer) totals.get(I18N.getLabelString("Number of Items"))).intValue();
        int n = 0;

        for (int i = 0; i < quantityEntries.length; i++) {
            String key = (String) quantityEntries[i].getKey();
            int pos = key.indexOf(" ");
            report += (i + 1) + ". " + key.substring(0, pos) + ": " +
                    quantityEntries[i].getValue() +
                    "\n";
            report += key.substring(pos + 1) + "\n";
            int val = ((Integer) quantityEntries[i].getValue()).intValue();
            qb.put(key.substring(pos + 1), new Double(val));
            n += val;
        }

        if (n < items) {
            qb.put(I18N.getLabelString("Other"), new Double(items - n));
        }

        report += "\n"+I18N.getLabelString("Top 5 Bestsellers by Dollar")+"\n";

        Hashtable db = new Hashtable();
        double total = ((Double) totals.get(I18N.getLabelString("Sales Total"))).doubleValue();
        double m = 0;

        for (int i = 0; i < dollarEntries.length; i++) {
            String key = (String) dollarEntries[i].getKey();
            int pos = key.indexOf(" ");
            double val = ((Double) dollarEntries[i].getValue()).doubleValue();
            val = Math.round(val * 100.0) / 100.0;
            report += (i + 1) + ". " + key.substring(0, pos) + ": $" + val +
                    "\n";
            report += key.substring(pos + 1) + "\n";
            db.put(key.substring(pos + 1), new Double(val));
            m += val;
        }

        if (m < total) {
            db.put(I18N.getLabelString("Other"), new Double(total - m));
        }

        jTextArea1.setText(report);
        piePanel1.setWedges(userTotals);
        piePanel2.setWedges(stationTotals);
        piePanel3.setWedges(qb);
        piePanel4.setWedges(db);
        revalidate();
        repaint();
    }
}


class ReportsMonthlyPanel_jButton3_actionAdapter implements ActionListener {
    private ReportsMonthlyPanel adaptee;
    ReportsMonthlyPanel_jButton3_actionAdapter(ReportsMonthlyPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}


class ReportsMonthlyPanel_jButton2_actionAdapter implements ActionListener {
    private ReportsMonthlyPanel adaptee;
    ReportsMonthlyPanel_jButton2_actionAdapter(ReportsMonthlyPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class ReportsMonthlyPanel_jButton1_actionAdapter implements ActionListener {
    private ReportsMonthlyPanel adaptee;
    ReportsMonthlyPanel_jButton1_actionAdapter(ReportsMonthlyPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
