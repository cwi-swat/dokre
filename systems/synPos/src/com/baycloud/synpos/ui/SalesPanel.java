package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.text.*;
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
public class SalesPanel extends JPanel implements ListSelectionListener {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    private JPanel jPanel1 = new JPanel();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JButton jButton1 = new JButton();
    private JButton jButton2 = new JButton();
    private JTable jTable2 = new JTable(new OrdersProductsTableModel());
    private JTable jTable1 = new JTable(new OrdersTableModel());
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JButton jButton4 = new JButton();
    JButton jButton3 = new JButton();
    Border border4 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border5 = new TitledBorder(border4, "Sales");
    JButton jButton5 = new JButton();
    JButton jButton6 = new JButton();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JSplitPane jSplitPane3 = new JSplitPane();
    JScrollPane jScrollPane3 = new JScrollPane();
    JScrollPane jScrollPane4 = new JScrollPane();
    FlowLayout flowLayout3 = new FlowLayout();
    Border border6 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border7 = new TitledBorder(border6, "Sales");
    JPanel jPanel4 = new JPanel();
    FlowLayout flowLayout4 = new FlowLayout();
    JLabel jLabel4 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JButton jButton7 = new JButton();
    JSplitPane jSplitPane2 = new JSplitPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JPanel jPanel3 = new JPanel();
    JButton jButton8 = new JButton();

    public SalesPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public SalesPanel(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jPanel1.setLayout(flowLayout3);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setBorder(null);

        jButton1.setMnemonic('S');
        jButton1.setText(I18N.getButtonString("Sync"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });
        jButton2.setMnemonic('A');
        jButton2.setText(I18N.getButtonString("Sync All"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton2_actionPerformed(e);
            }
        });
        String[] views = {"Table"};

        for (int i = 0; i < views.length; i++) {
        }
        jScrollPane1.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton4.setMnemonic('C');
        jButton4.setText(I18N.getButtonString("Cancel"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton4_actionPerformed(e);
            }
        });
        jButton3.setMnemonic('Q');
        jButton3.setText(I18N.getButtonString("Query"));
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3_actionPerformed(e);
            }
        });
        jButton5.setMnemonic('M');
        jButton5.setText(I18N.getButtonString("Mark As Synced"));
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton5_actionPerformed(e);
            }
        });
        jButton6.setMnemonic('K');
        jButton6.setText(I18N.getButtonString("Mark All As Synced"));
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton6_actionPerformed(e);
            }
        });
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jPanel1.setBorder(null);
        jPanel4.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.RIGHT);
        jLabel4.setText(I18N.getLabelString("Sale#")+": ");
        jTextField1.setColumns(15);
        jButton7.setMnemonic('F');
        jButton7.setText(I18N.getButtonString("Find"));
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton7_actionPerformed(e);
            }
        });
        jButton8.setMnemonic('P');
        jButton8.setText(I18N.getButtonString("Print"));
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton8_actionPerformed(e);
            }
        });
        jPanel1.add(jLabel4);
        jPanel1.add(jTextField1);
        jPanel1.add(jButton7);
        jPanel1.add(jButton3);

        jPanel4.add(jButton1);
        jPanel4.add(jButton5);
        jPanel4.add(jButton2);
        jPanel4.add(jButton6);
        jPanel4.add(jButton4);
        this.add(jSplitPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTable1);
        jScrollPane4.getViewport().add(jTable2);
        jSplitPane1.add(jScrollPane1, JSplitPane.TOP);
        jSplitPane1.add(jSplitPane3, JSplitPane.BOTTOM);
        this.add(jPanel4, java.awt.BorderLayout.SOUTH);
        jSplitPane2.add(jScrollPane2, JSplitPane.TOP);
        jScrollPane2.getViewport().add(jPanel3);
        jSplitPane2.add(jScrollPane3, JSplitPane.BOTTOM);
        jScrollPane3.getViewport().add(jPanel2);
        jSplitPane3.add(jScrollPane4, JSplitPane.BOTTOM);
        jSplitPane3.add(jSplitPane2, JSplitPane.TOP);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel4.add(jButton8);
        jTable1.getSelectionModel().addListSelectionListener(this);
        //jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setCellSelectionEnabled(true);
        //jTable1.setColumnSelectionAllowed(true);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable1);
        jTable2.setCellSelectionEnabled(true);
        jTable2.addMouseListener(new TablePopupMenuMouseListener());
        jTable1.addMouseListener(new TablePopupMenuMouseListener()); //jTable1.addMouseListener(new TablePopupMenuMouseListener());
        TableExcelAdapter myAd2 = new TableExcelAdapter(jTable2);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
        jSplitPane1.setDividerLocation(150);
        jSplitPane3.setDividerLocation(300);
        jSplitPane2.setDividerLocation(150);
    }

    public void valueChanged(ListSelectionEvent e) {
        //Ignore extra messages.
        if (e.getValueIsAdjusting()) {
            return;
        }

        ListSelectionModel lsm =
                (ListSelectionModel) e.getSource();
        if (lsm.isSelectionEmpty()) {
            //no rows are selected
        } else {
            int selectedRow = lsm.getMinSelectionIndex();
            TableModel model1 = jTable1.getModel();
            int orderId = ((Integer) model1.getValueAt(selectedRow, 1)).
                          intValue();
            showOrderDetails(orderId);
        }
    }

    void showOrderDetails(int orderId) {
        jPanel2.removeAll();
        jPanel3.removeAll();
        OrdersProductsTableModel model2 = (OrdersProductsTableModel) jTable2.
                                          getModel();
        model2.setOrder(orderId);

        if (orderId >= 0) {
            Order order = new Order(orderId);
            Payment payment = order.getPayment();
            JPanel payPanel = createPaymentPanel(payment);
            jPanel2.add(payPanel);

            Customer cust = order.getCustomer();
            JPanel custPanel = createCustPanel(cust);
            jPanel3.add(custPanel);
        }

        jPanel2.revalidate();
        jPanel2.repaint();
        jPanel3.revalidate();
        jPanel3.repaint();
        jTable2.revalidate();
        jTable2.repaint();
        //((MainFrame) parent).changeSize();
    }

    JPanel createPaymentPanel(Payment payment) {
        String payType = payment.getPaymentType();
        JPanel payPanel = new JPanel();
        payPanel.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        payPanel.add(leftPanel, BorderLayout.WEST);
        payPanel.add(rightPanel, BorderLayout.CENTER);

        if (payType.equals("Cash")) {
            leftPanel.setLayout(new GridLayout(2, 1));
            rightPanel.setLayout(new GridLayout(2, 1));
            leftPanel.add(new JLabel(I18N.getLabelString("Amount Tendered")+": "));
            leftPanel.add(new JLabel(I18N.getLabelString("Change")+": "));
            rightPanel.add(new JLabel("" + ((CashPayment) payment).getPaid()));
            rightPanel.add(new JLabel("" + ((CashPayment) payment).getChange()));
        } else if (payType.equals("Check")) {
            leftPanel.setLayout(new GridLayout(10, 1));
            rightPanel.setLayout(new GridLayout(10, 1));
            if (((CheckPayment) payment).getName() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Name")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getName()));
            }
            if (((CheckPayment) payment).getStreet1() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Address1")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getStreet1()));
            }
            if (((CheckPayment) payment).getStreet2() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Address2")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getStreet2()));
            }
            if (((CheckPayment) payment).getCity() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("City")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getCity()));
            }
            if (((CheckPayment) payment).getState() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("State")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getState()));
            }
            if (((CheckPayment) payment).getZip() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Zip")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getZip()));
            }
            if (((CheckPayment) payment).getPhone() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Phone")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getPhone()));
            }
            if (((CheckPayment) payment).getRouting() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Routing#")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getRouting()));
            }
            if (((CheckPayment) payment).getAccount() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Account#")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getAccount()));
            }
            if (((CheckPayment) payment).getNumber() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Check#")+": "));
                rightPanel.add(new JLabel(((CheckPayment) payment).getNumber()));
            }
        } else if (payType.equals("Credit/Debit")) {
            leftPanel.setLayout(new GridLayout(5, 1));
            rightPanel.setLayout(new GridLayout(5, 1));
            if (((CreditPayment) payment).getName() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Holder Name")+": "));
                rightPanel.add(new JLabel(((CreditPayment) payment).getName()));
            }

            if (((CreditPayment) payment).getNumber() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Card Number")+": "));
                rightPanel.add(new JLabel(((CreditPayment) payment).getNumber()));
            }

            if (((CreditPayment) payment).getExpireMonth() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Expire")+": "));
                rightPanel.add(new JLabel(((CreditPayment) payment).
                                          getExpireMonth() +
                                          "/" +
                                          ((CreditPayment) payment).
                                          getExpireYear()));
            }

            if (((CreditPayment) payment).getAuthCode() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Auth Code")+": "));
                rightPanel.add(new JLabel(((CreditPayment) payment).getAuthCode()));
            }
            if (((CreditPayment) payment).getTransId() != null) {
                leftPanel.add(new JLabel(I18N.getLabelString("Transaction ID")+": "));
                rightPanel.add(new JLabel(((CreditPayment) payment).getTransId()));
            }
        }

        return payPanel;
    }

    JPanel createCustPanel(Customer cust) {
        JPanel custPanel = new JPanel();
        custPanel.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        custPanel.add(leftPanel, BorderLayout.WEST);
        custPanel.add(rightPanel, BorderLayout.CENTER);

        leftPanel.setLayout(new GridLayout(10, 1));
        rightPanel.setLayout(new GridLayout(10, 1));
        if (cust != null) {
            leftPanel.add(new JLabel(I18N.getLabelString("First Name")+": "));
            rightPanel.add(new JLabel(cust.getFirstName()));
            leftPanel.add(new JLabel(I18N.getLabelString("Last Name")+": "));
            rightPanel.add(new JLabel(cust.getLastName()));
            leftPanel.add(new JLabel(I18N.getLabelString("Address1")+": "));
            rightPanel.add(new JLabel(cust.getAddress1()));

            leftPanel.add(new JLabel(I18N.getLabelString("Address2")+": "));
            rightPanel.add(new JLabel(cust.getAddress2()));

            leftPanel.add(new JLabel(I18N.getLabelString("City")+": "));
            rightPanel.add(new JLabel(cust.getCity()));

            leftPanel.add(new JLabel(I18N.getLabelString("State")+": "));
            rightPanel.add(new JLabel(cust.getState()));

            leftPanel.add(new JLabel(I18N.getLabelString("Zip")+": "));
            rightPanel.add(new JLabel(cust.getZip()));

            leftPanel.add(new JLabel(I18N.getLabelString("Phone")+": "));
            rightPanel.add(new JLabel(cust.getPhone()));

            leftPanel.add(new JLabel(I18N.getLabelString("Fax")+": "));
            rightPanel.add(new JLabel(cust.getFax()));

            leftPanel.add(new JLabel(I18N.getLabelString("Email")+": "));
            rightPanel.add(new JLabel(cust.getEmail()));
        }
        return custPanel;
    }

    void jButton1_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() == Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();
            //String status = (String) model1.getValueAt(selectedRow, 6);
            boolean synced = model1.isSynchronized(selectedRow); //!status.endsWith("S");

            if (!synced) {
                String syncUrl = StoreConfiguration.get("sync_url");

                if (syncUrl == null) {
                    JOptionPane.showMessageDialog(parent,
                                                  I18N.getMessageString("Synchronization not configured."),
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }

                model1.synchronize(selectedRow);
                //int orderId = ( (Integer) model1.getValueAt(selectedRow, 1)).intValue();
                //new Order(orderId).sync();
                jTable1.revalidate();
                jTable1.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString("The selected sale has already been synchronized."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select a sale to synchronize."),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    void jButton2_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() == Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Synchronization not configured."),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        String syncUrl = StoreConfiguration.get("sync_url");

        if (syncUrl == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Synchronization not configured."),
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();

        int row = model1.getRowCount();
        for (int i = 0; i < row; i++) {
            boolean synced = model1.isSynchronized(row);

            if (!synced) {
                model1.synchronize(row);
            }
        }

        jTable1.revalidate();
        jTable1.repaint();
    }

    void jButton3_actionPerformed(ActionEvent e) {
        SalesQueryDlg dlg = new SalesQueryDlg();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);
        String[] queryStr = dlg.getQuery();

        if (queryStr != null) {
            OrdersTableModel model = (OrdersTableModel) jTable1.getModel();
            model.query(queryStr[0], queryStr[1], queryStr[2], queryStr[3],
                        queryStr[4],
                        queryStr[5]);
            jTable1.clearSelection();

            if(model.getRowCount() > 0) {
                jTable1.setRowSelectionInterval(0, 0);
                int orderId = ((Integer) model.getValueAt(0, 1)).intValue();
                showOrderDetails(orderId);
            }

            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    void jButton4_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            int option = JOptionPane.showConfirmDialog(parent,
                    I18N.getMessageString("Are you sure you want to cancel the selected sale?"),
                    "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (option != JOptionPane.YES_OPTION) {
                return;
            }

            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();
            //int orderId = ( (Integer) model1.getValueAt(selectedRow, 1)).intValue();
            //new Order(orderId).cancel();
            if (!model1.isCanceled(selectedRow)) {
                model1.cancel(selectedRow);
                int orderId = ((Integer) model1.getValueAt(selectedRow, 1)).
                              intValue();
                Order order = new Order(orderId);
                Payment payment = order.getPayment();

                if (payment.getPaymentType().equals("Credit/Debit")) {
                    option = JOptionPane.showConfirmDialog(parent,
                            I18N.getMessageString("Do you want to refund the credit card charge?"),
                            "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        try {
                            GatewayManager.refundCreditCard(order.getTotal(),
                                    orderId, (CreditPayment) payment);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this,
                                    ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                jTable1.revalidate();
                jTable1.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString("The selected sale has already been cancelled."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select a sale to cancel."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton6_actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this,
                I18N.getMessageString("This will only change the sales status from un-synchronized to synchronized without updating the remote database."),
                I18N.getLabelString("Warning"),
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {

            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();

            int row = model1.getRowCount();
            for (int i = 0; i < row; i++) {
                boolean synced = model1.isSynchronized(i);

                if (!synced) {
                    model1.setSynchronized(i);
                }
            }

            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    public void jButton5_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();
            //String status = (String) model1.getValueAt(selectedRow, 6);
            //boolean synced = !status.endsWith("S");

            if (!model1.isSynchronized(selectedRow)) {
                int option = JOptionPane.showConfirmDialog(this,
                        I18N.getMessageString("This will only change the sales status from un-synchronized to synchronized without updating the remote database."),
                        I18N.getLabelString("Warning"),
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    model1.setSynchronized(selectedRow);
                    jTable1.revalidate();
                    jTable1.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString("The selected sale has already been synchronized."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select a sale to synchronize."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    private void findOrder() {
        try {
            int orderId = Integer.parseInt(jTextField1.getText().trim());
            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();
            int row = model1.findOrder(orderId);
            if (row < 0) {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString("Sale# not found:") + orderId,
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

            } else {
                jTable1.changeSelection(row, 1, false, false);
                //int orderId = ( (Integer) model1.getValueAt(row, 1)).intValue();
                showOrderDetails(orderId);
                jTable1.revalidate();
                jTable1.repaint();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please enter an valid sale#."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton7_actionPerformed(ActionEvent e) {
        findOrder();
    }

    void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            findOrder();
        }
    }

    public void jButton8_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            OrdersTableModel model1 = (OrdersTableModel) jTable1.getModel();
            //int orderId = ( (Integer) model1.getValueAt(selectedRow, 1)).intValue();
            //new Order(orderId).cancel();
            if (!model1.isCanceled(selectedRow)) {
                int orderId = ((Integer) model1.getValueAt(selectedRow, 1)).
                              intValue();
                Order order = new Order(orderId);
                Payment payment = order.getPayment();
                try {
                    if (payment.getPaymentType().equals("Credit/Debit")) {
                        Object[] possibleValues = {I18N.getMessageString("Receipt"),
                                                  I18N.getMessageString("Credit Card Slip - Customer Copy"),
                                                  I18N.getMessageString("Credit Card Slip - Store Copy")};
                        Object selectedValue = JOptionPane.showInputDialog(null,
                                I18N.getMessageString("Please choose one to print:"), I18N.getLabelString("Print Receipt"),
                                JOptionPane.INFORMATION_MESSAGE, null,
                                possibleValues, possibleValues[0]);
                        if (selectedValue.equals(I18N.getMessageString("Receipt"))) {
                            Printer.print(order);
                        } else if (selectedValue.equals(
                                I18N.getMessageString("Credit Card Slip - Customer Copy"))) {
                            Printer.print(order, "customer");
                        } else if (selectedValue.equals(
                                I18N.getMessageString("Credit Card Slip - Store Copy"))) {
                            Printer.print(order, "store");
                        }
                    } else {
                        Printer.print(order);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString("The selected sale has already been cancelled."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString("Please select a sale to print."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
