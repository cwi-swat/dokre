package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.SystemColor;
import javax.swing.border.Border;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.text.NumberFormat;

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
public class TotalPanel extends JPanel {
    public TotalPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(Color.green);
        this.setBorder(border3);
        this.setLayout(borderLayout1);
        jLabel2.setText(I18N.getLabelString("Total")+":");
        jLabel2.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        jLabel1.setText(I18N.getLabelString("Sub Total")+":");
        jLabel1.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        jPanel1.setLayout(gridLayout1);
        jPanel2.setLayout(gridLayout2);
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(10);
        gridLayout1.setRows(3);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(10);
        gridLayout2.setRows(3);
        jPanel1.setBackground(Color.green);
        jPanel1.setBorder(border5);
        jPanel2.setBackground(Color.green);
        jPanel2.setBorder(border4);
        jLabel4.setText("0.00");
        jLabel5.setToolTipText("");
        jLabel5.setText("0.00");
        jLabel6.setText("0.00");
        jLabel3.setText(I18N.getLabelString("Sales Tax")+":");
        jLabel3.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel3);
        jPanel1.add(jLabel2);
        this.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel5);
        jPanel2.add(jLabel6);
        this.add(jPanel1, java.awt.BorderLayout.WEST);
        jLabel4.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        jLabel5.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        jLabel6.setFont(new java.awt.Font("BankGothic Md BT", Font.BOLD, 18));
        //reset();
    }

    double subTotal = 0;
    double salesTax = 0;
    double total = 0;
    Border border1 = BorderFactory.createLineBorder(SystemColor.controlText, 2);
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    Border border2 = BorderFactory.createBevelBorder(BevelBorder.RAISED,
            Color.green, Color.green, new Color(0, 124, 0), new Color(0, 178, 0));
    Border border3 = BorderFactory.createLineBorder(Color.black, 1);
    Border border4 = BorderFactory.createEmptyBorder(0, 5, 0, 5);
    Border border5 = BorderFactory.createEmptyBorder(0, 5, 0, 5);

    public void reset() {
        this.subTotal = 0;
        this.salesTax = 0;
        this.total = 0;
        jLabel4.setText("0.00");
        jLabel5.setText("0.00");
        jLabel6.setText("0.00");
    }

    public void setSubTotal(double d) {
        this.subTotal = Math.round(d * 100) / 100.0;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        this.jLabel4.setText(nf.format(this.subTotal));
    }

    public void setSalesTax(double d) {
        this.salesTax = Math.round(d * 100) / 100.0;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        this.jLabel5.setText(nf.format(this.salesTax));
    }

    public void setTotal(double d) {
        this.total = Math.round(d * 100) / 100.0;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        this.jLabel6.setText(nf.format(this.total));
    }

    public double getSubTotal() {
        return this.subTotal;
    }

    public double getSalesTax() {
        return this.salesTax;
    }

    public double getTotal() {
        return this.total;
    }
}
