package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.text.NumberFormat;
import java.awt.event.ActionListener;

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
public class CashPaymentDlg extends JDialog {
    public CashPaymentDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CashPaymentDlg(JFrame parent, double total) {
        super(parent);
        this.parent = parent;
        this.total = total;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    JFrame parent;
    double total;
    CashPayment cash = null;

    private void jbInit() throws Exception {
        jButton3.setMnemonic('O');
        jButton3.setText(I18N.getButtonString("OK"));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3_actionPerformed(e);
            }
        });
        jTextField6.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                jTextField6_textChanged(e);
            }

            public void removeUpdate(DocumentEvent e) {
                jTextField6_textChanged(e);
            }

            public void insertUpdate(DocumentEvent e) {
                jTextField6_textChanged(e);
            }
        });

        jPanel15.setLayout(flowLayout4);
        jButton2.setMnemonic('C');
        //jScrollPane1.setPreferredSize(new Dimension(0, 0));
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(e);
            }
        });
        flowLayout4.setAlignment(FlowLayout.RIGHT);
        flowLayout4.setHgap(0); //jTable2.setSelectionForeground(Color.white);
        component1 = Box.createHorizontalStrut(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(2);
        gridLayout2.setVgap(5);
        gridLayout5.setColumns(1);
        gridLayout5.setHgap(5);
        gridLayout5.setRows(2);
        gridLayout5.setVgap(5);
        jLabel6.setDisplayedMnemonic('P');
        jLabel6.setLabelFor(jTextField6);
        jLabel6.setText(I18N.getLabelString("Amount Tendered")+": ");
        jLabel7.setText(I18N.getLabelString("Change")+": ");
        jTextField6.setColumns(20);
        jTextField6.addMouseListener(new PopupMenuMouseListener());
        jTextField7.setEditable(false);
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jPanel10.setBorder(null);
        jPanel10.setLayout(flowLayout3);
        this.setModal(true);
        this.setTitle(I18N.getLabelString("Cash"));
        jPanel10.add(jPanel12);
        border8 = BorderFactory.createCompoundBorder(border7,
                BorderFactory.
                createEmptyBorder(5, 5, 5, 5));
        jPanel12.setLayout(borderLayout1);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel12.add(jPanel13, BorderLayout.WEST);
        jPanel13.add(jLabel6, null);
        jPanel13.add(jLabel7, null);
        jPanel12.add(jPanel14, BorderLayout.CENTER);
        jPanel14.add(jTextField6, null);
        jPanel14.add(jTextField7, null);
        jPanel12.add(jPanel15, java.awt.BorderLayout.SOUTH);
        jPanel15.add(jButton3, null);
        jPanel15.add(jButton2);
        jPanel15.add(component1);
        jPanel13.setLayout(gridLayout2);
        jPanel14.setLayout(gridLayout5);
        add(jPanel10, BorderLayout.CENTER);
    }

    Component component1 = Box.createHorizontalStrut(8);
    GridLayout gridLayout2 = new GridLayout();
    GridLayout gridLayout5 = new GridLayout();
    JPanel jPanel15 = new JPanel();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    FlowLayout flowLayout4 = new FlowLayout();

    JTextField jTextField6 = new JTextField();
    JTextField jTextField7 = new JTextField();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel13 = new JPanel();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel12 = new JPanel();
    JPanel jPanel10 = new JPanel();
    FlowLayout flowLayout3 = new FlowLayout();
    Border border6 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border7 = new TitledBorder(border6, I18N.getLabelString("Cash"));
    Border border8 = BorderFactory.createCompoundBorder(border7,
            BorderFactory.createEmptyBorder(5, 5, 5, 5));

    void jTextField6_textChanged(DocumentEvent e) {
        Document doc = e.getDocument();
        String txt = doc.toString();

        try {
            NumberFormat nf = NumberFormat.getInstance();
            //double total = nf.parse(jTextField5.getText()).doubleValue();
            double tendered = nf.parse(jTextField6.getText()).doubleValue();
            double change = tendered - total;
            nf.setMaximumFractionDigits(2);
            nf.setMinimumFractionDigits(2);
            jTextField7.setText(nf.format(change));
        } catch (Exception ex) {
            //ex.printStackTrace();
            // tendered
        }
    }

    void jButton3_actionPerformed(ActionEvent e) {
        try {
            NumberFormat nf = NumberFormat.getInstance();
            double change = nf.parse(jTextField7.getText()).doubleValue();

            if (change < 0) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Change amount is incorrect."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                return;
            }

            double tendered = nf.parse(jTextField6.getText()).doubleValue();
            cash = new CashPayment(tendered, change);
            setVisible(false);
            /*
             final MessageDialog msgDlg = new MessageDialog(parent, "Message",
              "Processing ...");

                   new Thread() {
              public void run() {
                try {
                  //completeSale(payment);
                  msgDlg.setVisible(false);
                } catch (Exception ex) {
                  msgDlg.setVisible(false);
                  JOptionPane.showMessageDialog(parent,
                                                ex.getMessage(),
             "Error", JOptionPane.ERROR_MESSAGE);

                }
              }
                   }.start();
                   msgDlg.pack();
                   msgDlg.setVisible(true);
             */
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                                          ex.getMessage(),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public CashPayment getPayment() {
        return cash;
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        cash = null;
        setVisible(false);
    }
}
