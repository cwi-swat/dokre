package com.baycloud.synpos.ui;

import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.od.*;
import jpay.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import com.baycloud.synpos.util.I18N;

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
public class CreditCardDialog extends JDialog {
    public CreditCardDialog() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setDefaultCloseOperation(javax.swing.WindowConstants.
                                      DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setTitle(I18N.getLabelString("Swipe Card"));
        this.getContentPane().setLayout(borderLayout1);
        jButton1.setMnemonic('C');
        jButton1.setText(I18N.getButtonString("Cancel"));
        jButton1.addActionListener(new CreditCardDialog_jButton1_actionAdapter(this));
        jPanel2.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.RIGHT);
        jPanel1.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jTextArea1.setToolTipText("");
        jTextArea1.addMouseListener(new PopupMenuMouseListener());
        jTextArea1.setEditable(false);
        jTextArea1.setColumns(30);
        jTextArea1.setRows(2);
        jTextArea1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextArea1_keyTyped(e);
            }
        });
        jButton2.setMnemonic('O');

        jButton2.setText(I18N.getButtonString("OK"));
        jButton2.addActionListener(new CreditCardDialog_jButton2_actionAdapter(this));
        jPanel1.add(jLabel1);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
        jPanel2.add(jButton2);
        jPanel2.add(jButton1);
        this.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText(I18N.getMessageString("Please swipe card:"));
        jScrollPane1.getViewport().add(jTextArea1);
        jTextArea1.requestFocus();
        isCancelled = true;
        creditCard = null;
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JPanel jPanel2 = new JPanel();
    JTextArea jTextArea1 = new JTextArea();
    JLabel jLabel1 = new JLabel();
    JButton jButton1 = new JButton();
    FlowLayout flowLayout1 = new FlowLayout();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton jButton2 = new JButton();
    CreditPayment creditCard;
    boolean isCancelled;
    StringBuffer buffer = new StringBuffer();

    public void jTextArea1_keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if (c != '\n') {
            buffer.append(c);
            jTextArea1.append("*");
        } else {
            creditCard = new CreditPayment(KeyboardCCReader.parse(buffer.toString()));
            if (creditCard != null) {
                isCancelled = false;
                dispose();
            }
        }
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        isCancelled = true;
        creditCard = null;
        dispose();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        CreditCard cc = KeyboardCCReader.parse(buffer.toString());

        if (cc == null) {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Error reading credit card. Please try again."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);

            buffer = new StringBuffer();
            jTextArea1.setText("");
            creditCard = null;
        } else {
            creditCard = new CreditPayment(cc);
            isCancelled = false;
            dispose();
        }
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public CreditPayment getCreditCard() {
        return creditCard;
    }
}


class CreditCardDialog_jButton2_actionAdapter implements ActionListener {
    private CreditCardDialog adaptee;
    CreditCardDialog_jButton2_actionAdapter(CreditCardDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class CreditCardDialog_jButton1_actionAdapter implements ActionListener {
    private CreditCardDialog adaptee;
    CreditCardDialog_jButton1_actionAdapter(CreditCardDialog adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
