package com.baycloud.synpos.ui;

import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.util.Calendar;
import java.text.NumberFormat;

import com.baycloud.synpos.od.Order;
import com.baycloud.synpos.util.CCValidator;
import com.baycloud.synpos.xt.GatewayManager;
import com.baycloud.synpos.od.CreditPayment;
import com.baycloud.synpos.od.Configuration;
import com.baycloud.synpos.xt.CreditCardReader;
import com.baycloud.synpos.util.I18N;

import jpay.CreditCard;

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
public class CreditPaymentDlg extends JDialog {
    public CreditPaymentDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CreditPaymentDlg(JFrame parent, double total) {
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
    CreditPayment creditCard = null;

    private void jbInit() throws Exception {
        flowLayout7.setAlignment(FlowLayout.RIGHT);
        flowLayout7.setVgap(5);
        gridLayout10.setColumns(1);
        gridLayout10.setHgap(5);
        gridLayout10.setRows(3);
        gridLayout10.setVgap(5);
        gridLayout11.setColumns(1);
        gridLayout11.setHgap(5);
        gridLayout11.setRows(3);
        gridLayout11.setVgap(5);
        jLabel11.setDisplayedMnemonic('H');
        jLabel11.setLabelFor(jTextField17);
        jLabel11.setText(I18N.getLabelString("Holder Name")+": ");
        jLabel18.setDisplayedMnemonic('E');
        jLabel18.setText(I18N.getLabelString("Expire")+": ");
        jLabel19.setDisplayedMnemonic('N');
        jLabel19.setText(I18N.getLabelString("Card Number")+": ");
        jPanel33.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout8.setHgap(0);
        flowLayout8.setVgap(0);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jPanel16.setLayout(flowLayout5);
        jPanel16.setBorder(border1);
        this.setModal(true);
        this.setTitle(I18N.getLabelString("Credit Card"));
        jRadioButton3.setMnemonic('L');
        jRadioButton3.setText("Standalone");
        jRadioButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButton3_actionPerformed(e);
            }
        });
        jPanel16.add(jPanel27);
        jPanel27.setLayout(borderLayout9);
        jPanel27.add(jPanel31, java.awt.BorderLayout.CENTER);
        jPanel27.add(jPanel30, java.awt.BorderLayout.WEST);
        jPanel27.add(jPanel32, java.awt.BorderLayout.SOUTH);
        jPanel30.setLayout(gridLayout10);
        jPanel31.setLayout(gridLayout11);
        jPanel32.setLayout(flowLayout7);
        jPanel34.setLayout(flowLayout9);
        jPanel30.add(jLabel11);
        jPanel30.add(jLabel19);
        jPanel30.add(jLabel18);
        jPanel31.add(jTextField17);
        jPanel31.add(jTextField18);
        jPanel31.add(jPanel33);
        jPanel32.add(jButton12);
        jPanel32.add(jButton13);
        jPanel34.add(jRadioButton2);
        jPanel34.add(jRadioButton1);
        //jPanel34.add(jRadioButton3);
        jTextField18.setEditable(true);
        jTextField18.addMouseListener(new PopupMenuMouseListener());
        jTextField18.setColumns(16);
        jButton12.setMnemonic('O');
        jButton12.setText(I18N.getButtonString("OK"));
        jButton12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton12_actionPerformed(e);
            }
        });
        jButton13.setMnemonic('C');
        jButton13.setText(I18N.getButtonString("Cancel"));
        jButton13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton13_actionPerformed(e);
            }
        });
        jTextField17.setEditable(true);
        jTextField17.addMouseListener(new PopupMenuMouseListener());
        jRadioButton1.setMnemonic('W');
        jRadioButton1.setText(I18N.getLabelString("Swipe"));
        jRadioButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButton1_actionPerformed(e);
            }
        });
        jRadioButton2.setMnemonic('Y');
        jRadioButton2.setSelected(true);
        jRadioButton2.setText(I18N.getLabelString("Key in"));
        jRadioButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jRadioButton2_actionPerformed(e);
            }
        });
        flowLayout9.setAlignment(FlowLayout.CENTER); //jTabbedPane1.add(jPanel4, "jPanel4");
        jPanel33.add(jTextField20);
        jPanel33.add(jLabel20);
        jPanel33.add(jTextField19);
        buttonGroup2.add(jRadioButton1);
        buttonGroup2.add(jRadioButton2);
        buttonGroup2.add(jRadioButton3);
        jTextField19.setEditable(true);
        jTextField19.addMouseListener(new PopupMenuMouseListener());
        jTextField19.setText(I18N.getMessageString("yyyy"));
        jTextField19.setColumns(4);
        jLabel20.setText(" / ");
        jTextField20.setEditable(true);
        jTextField20.addMouseListener(new PopupMenuMouseListener());
        jTextField20.setText(I18N.getMessageString("mm"));
        jTextField20.setColumns(2);
        add(jPanel16, BorderLayout.CENTER);
        jPanel27.add(jPanel34, java.awt.BorderLayout.NORTH);
    }

    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    GridLayout gridLayout10 = new GridLayout();
    GridLayout gridLayout11 = new GridLayout();
    FlowLayout flowLayout7 = new FlowLayout();
    FlowLayout flowLayout8 = new FlowLayout();

    FlowLayout flowLayout9 = new FlowLayout();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel18 = new JLabel();
    JLabel jLabel19 = new JLabel();
    JTextField jTextField17 = new JTextField();
    JTextField jTextField18 = new JTextField();

    JPanel jPanel33 = new JPanel();
    JButton jButton12 = new JButton();
    JButton jButton13 = new JButton();

    JPanel jPanel30 = new JPanel();
    JPanel jPanel31 = new JPanel();
    JPanel jPanel32 = new JPanel();
    JPanel jPanel34 = new JPanel();
    JTextField jTextField19 = new JTextField();
    JLabel jLabel20 = new JLabel();
    JTextField jTextField20 = new JTextField();

    BorderLayout borderLayout9 = new BorderLayout();

    FlowLayout flowLayout5 = new FlowLayout();
    Border border15 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    JPanel jPanel27 = new JPanel();
    ButtonGroup buttonGroup2 = new ButtonGroup();

    Border border16 = new TitledBorder(border15, "Credit/Debit");
    JPanel jPanel16 = new JPanel();
    Border border1 = BorderFactory.createEmptyBorder();
    JRadioButton jRadioButton3 = new JRadioButton();

    public void jRadioButton1_actionPerformed(ActionEvent e) {
        String model = Configuration.get("msr.model");

        if(model == null) {
            JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("No MSR found."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                return;
        }

        jTextField17.setEditable(false);
        jTextField18.setEditable(false);
        jTextField20.setEditable(false);
        jTextField19.setEditable(false);
        jTextField17.setText("");
        jTextField18.setText("");
        jTextField20.setText(I18N.getMessageString("mm"));
        jTextField19.setText(I18N.getMessageString("yyyy"));
        //creditCard = null;

        if(model.equals("msr-keyboard-wedge")) {
            CreditCardDialog ccDlg = new CreditCardDialog();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension ccSize = ccDlg.getPreferredSize();
            ccDlg.setLocation((screenSize.width - ccSize.width) / 2,
                              (screenSize.height - ccSize.height) / 2);

            ccDlg.pack();
            ccDlg.setVisible(true);

            if (ccDlg.isCancelled()) {
                jRadioButton2.setSelected(true);
                jTextField17.setEditable(true);
                jTextField18.setEditable(true);
                jTextField20.setEditable(true);
                jTextField19.setEditable(true);
                jTextField17.setText("");
                jTextField18.setText("");
                jTextField20.setText(I18N.getMessageString("mm"));
                jTextField19.setText(I18N.getMessageString("yyyy"));
            } else {
                creditCard = ccDlg.getCreditCard();
                if (creditCard != null) {
                    jTextField17.setText(creditCard.getName());
                    jTextField18.setText(creditCard.getNumber());
                    jTextField20.setText(creditCard.getExpireMonth());
                    jTextField19.setText(creditCard.getExpireYear());
                } else {
                    JOptionPane.showMessageDialog(parent,
                                                  I18N.getMessageString("Error reading credit card. Please try again."),
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    jRadioButton2.setSelected(true);
                    jTextField17.setEditable(true);
                    jTextField18.setEditable(true);
                    jTextField20.setEditable(true);
                    jTextField19.setEditable(true);
                    jTextField17.setText("");
                    jTextField18.setText("");
                    jTextField20.setText(I18N.getMessageString("mm"));
                    jTextField19.setText(I18N.getMessageString("yyyy"));
                }
            }
        } else {
            try {
            CreditCard cc = CreditCardReader.read();

                if (cc != null) {
                    jTextField17.setText(creditCard.getName());
                    jTextField18.setText(creditCard.getNumber());
                    jTextField20.setText(creditCard.getExpireMonth());
                    jTextField19.setText(creditCard.getExpireYear());
                    creditCard = new CreditPayment(cc);
                } else {
                    JOptionPane.showMessageDialog(parent,
                                                  I18N.getMessageString("Error reading credit card. Please try again."),
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    jRadioButton2.setSelected(true);
                    jTextField17.setEditable(true);
                    jTextField18.setEditable(true);
                    jTextField20.setEditable(true);
                    jTextField19.setEditable(true);
                    jTextField17.setText("");
                    jTextField18.setText("");
                    jTextField20.setText(I18N.getMessageString("mm"));
                    jTextField19.setText(I18N.getMessageString("yyyy"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void jRadioButton2_actionPerformed(ActionEvent e) {
        jTextField17.setEditable(true);
        jTextField18.setEditable(true);
        jTextField20.setEditable(true);
        jTextField19.setEditable(true);
        jTextField17.setText("");
        jTextField18.setText("");
        jTextField20.setText(I18N.getMessageString("mm"));
        jTextField19.setText(I18N.getMessageString("yyyy"));
    }

    public void jButton12_actionPerformed(ActionEvent e) {
//  if (jTable2.getRowCount() == 0) {
        //   return;
        // }
        if (jRadioButton2.isSelected()) {
            String name = jTextField17.getText().trim();
            String number = jTextField18.getText().trim();
            String month = jTextField20.getText().trim();
            String year = jTextField19.getText().trim();

            if (name.length() == 0) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Please enter holder name."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

                return;
            }

            if (number.length() == 0) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Please enter credit card number."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

                return;
            }

            try {
                if (!CCValidator.validCC(number)) {
                    JOptionPane.showMessageDialog(parent,
                                                  I18N.getMessageString("Invalid credit card number."),
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Invalid credit card number."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                return;

            }

            int expMonth = 0;

            try {
                expMonth = Integer.parseInt(month);
            } catch (Exception ex) {
            }

            if (expMonth < 1 || expMonth > 12) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Invalid expire month."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

                return;
            }
            int expYear = 0;

            try {
                expYear = Integer.parseInt(year);
            } catch (Exception ex) {
            }

            if (expYear < Calendar.getInstance().get(Calendar.YEAR)) {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString("Invalid expire year."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

                return;
            }

            creditCard = new CreditPayment(name, number, month, year);

        } else if (creditCard == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please swipe card."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            NumberFormat nf = NumberFormat.getInstance();
            //final double total = nf.parse(jTextField5.getText()).doubleValue();
            final MessageDialog msgDlg = new MessageDialog(parent, I18N.getLabelString("Message"),
                    I18N.getMessageString("Processing ..."));

            new Thread() {
                public void run() {
                    try {
                        /*CreditPayment payment = new CreditPayment(creditCard.getTrack1(), creditCard.getTrack2(),
                         creditCard.getName(), creditCard.getNumber(), creditCard.getAuthCode(),
                         creditCard.getTransId());*/
                        GatewayManager.chargeCreditCard(total, Order.getNextId(),
                                creditCard);
                        //completeSale(creditCard);
                        msgDlg.setVisible(false);
                        setVisible(false);
                    } catch (Exception ex) {
                        creditCard = null;
                        msgDlg.setVisible(false);
                        JOptionPane.showMessageDialog(parent,
                                ex.getMessage(),
                                I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.start();
            msgDlg.pack();
            msgDlg.setVisible(true);
        } catch (Exception ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(parent,
                                          ex.getMessage(),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton13_actionPerformed(ActionEvent e) {
        creditCard = null;
        setVisible(false);
    }

    public CreditPayment getPayment() {
        return creditCard;
    }

    public void jRadioButton3_actionPerformed(ActionEvent e) {

    }
}
