package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.text.*;

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
public class SalesExportPanel extends JPanel {
    public SalesExportPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SalesExportPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jPanel1.setLayout(borderLayout1);
        this.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jPanel2.setLayout(gridLayout1);
        jPanel3.setLayout(gridLayout2);
        jLabel1.setText("From: ");
        jLabel2.setText("File: ");
        jLabel3.setText("Format: ");
        jLabel4.setText("To: ");
        gridLayout1.setColumns(1);
        gridLayout1.setHgap(5);
        gridLayout1.setRows(5);
        gridLayout1.setVgap(5);
        gridLayout2.setColumns(1);
        gridLayout2.setHgap(5);
        gridLayout2.setRows(5);
        gridLayout2.setVgap(5);
        jFormattedTextField1.setValue(new Date());
        jFormattedTextField1.setInputVerifier(new FormattedTextFieldVerifier());
        jFormattedTextField2.setValue(new Date());
        jFormattedTextField2.setInputVerifier(new FormattedTextFieldVerifier());
        String[] formats = {"QuickBooks"};
        for (int i = 0; i < formats.length; i++) {
            jComboBox1.addItem(formats[i]);
        }
        jButton1.setText("Browse");
        jTextField1.setColumns(30);
        jButton1.addActionListener(new SalesExportPanel_jButton1_actionAdapter(this));
        jButton2.setText("Export");
        jButton2.addActionListener(new SalesExportPanel_jButton2_actionAdapter(this));
        jPanel5.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel6.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel7.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jPanel8.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel4.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        this.add(jPanel1);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jPanel2.add(jLabel1);
        jPanel2.add(jLabel4);
        jPanel2.add(jLabel3);
        jPanel2.add(jLabel2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);
        jPanel3.add(jPanel4);
        jPanel4.add(jFormattedTextField1);
        jPanel3.add(jPanel8);
        jPanel8.add(jFormattedTextField2);
        jPanel3.add(jPanel7);
        jPanel7.add(jComboBox1);
        jPanel3.add(jPanel6);
        jPanel6.add(jTextField1);
        jPanel6.add(jButton1);
        jPanel3.add(jPanel5);
        jPanel5.add(jButton2);
    }

    JFrame parent;
    JPanel jPanel1 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JFormattedTextField jFormattedTextField1 = new JFormattedTextField();
    JFormattedTextField jFormattedTextField2 = new JFormattedTextField();
    JComboBox jComboBox1 = new JComboBox();
    JTextField jTextField1 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout3 = new FlowLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    FlowLayout flowLayout5 = new FlowLayout();
    FlowLayout flowLayout6 = new FlowLayout();
    public void jButton1_actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            jTextField1.setText(file.toString());
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {

    }

    class FormattedTextFieldVerifier extends InputVerifier {
        public boolean verify(JComponent input) {
            if (input instanceof JFormattedTextField) {
                JFormattedTextField ftf = (JFormattedTextField) input;
                JFormattedTextField.AbstractFormatter formatter = ftf.
                        getFormatter();
                if (formatter != null) {
                    String text = ftf.getText();
                    try {
                        formatter.stringToValue(text);
                        return true;
                    } catch (ParseException pe) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean shouldYieldFocus(JComponent input) {
            return verify(input);
        }
    }

}


class SalesExportPanel_jButton2_actionAdapter implements ActionListener {
    private SalesExportPanel adaptee;
    SalesExportPanel_jButton2_actionAdapter(SalesExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class SalesExportPanel_jButton1_actionAdapter implements ActionListener {
    private SalesExportPanel adaptee;
    SalesExportPanel_jButton1_actionAdapter(SalesExportPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
