package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.border.*;
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
public class NewSalePanel extends JPanel implements TableModelListener {
    JFrame parent;
    BorderLayout borderLayout5 = new BorderLayout();
    JPanel jPanel11 = new JPanel();
    BorderLayout borderLayout6 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JTable jTable2 = new JTable(new SalesTableModel());
    JButton jButton1 = new JButton();
    //FlowLayout flowLayout1 = new FlowLayout();
    JTextField jTextField1 = new JTextField();
    TitledBorder titledBorder1;
    Border border1;
    Border border2;
    TitledBorder titledBorder2;
    JButton jButton4 = new JButton();
    JPanel jPanel5 = new JPanel();
    JButton jButton7 = new JButton();
    JButton jButton8 = new JButton();
    TotalPanel totalPanel = new TotalPanel();
    Border border3 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border4 = new TitledBorder(border3, "Cash");
    Border border5 = BorderFactory.createCompoundBorder(border4,
            BorderFactory.createEmptyBorder(5, 5, 5, 5));

    Border border9;
    JButton jButton9 = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();

    ButtonGroup buttonGroup1 = new ButtonGroup();

    Border border10 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border11 = new TitledBorder(border10, "Check");
    Border border12 = BorderFactory.createCompoundBorder(border11,
            BorderFactory.createEmptyBorder(5, 5, 5, 5));

    //CreditPayment creditCard = null;
    TitledBorder titledBorder3 = new TitledBorder("");
    JButton jButton14 = new JButton();
    TitledBorder titledBorder4 = new TitledBorder("");
    Border border17 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border18 = new TitledBorder(border17, "Calculator");
    JPanel jPanel35 = new JPanel();
    BorderLayout borderLayout10 = new BorderLayout();
    Border border19 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border20 = new TitledBorder(border19, "Item"); ;
    JButton jButton3 = new JButton();
    Border border6 = BorderFactory.createEmptyBorder();
    FlowLayout flowLayout2 = new FlowLayout();
    Border border7 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
            Color.white, new Color(148, 145, 140));
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JButton jButton2 = new JButton();
    JButton jButton5 = new JButton();
    Customer customer = new Customer(0);
    JTextField jTextField2 = new JTextField();
    Border border8 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    Border border13 = BorderFactory.createEmptyBorder();

    public NewSalePanel() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NewSalePanel(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border12 = BorderFactory.createCompoundBorder(border11,
                BorderFactory.
                createEmptyBorder(5, 5, 5, 5));

        border5 = BorderFactory.createCompoundBorder(border4,
                BorderFactory.
                createEmptyBorder(5, 5, 5, 5));
        titledBorder1 = new TitledBorder("");
        border1 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        border2 = BorderFactory.createEmptyBorder();
        titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
                white, new Color(148, 145, 140)), "New Sale");
        border9 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        this.setLayout(borderLayout5);
        jPanel11.setLayout(borderLayout6);
        jPanel1.setLayout(borderLayout1);
        jLabel1.setDisplayedMnemonic('I');
        jLabel1.setLabelFor(jTextField1);
        jLabel1.setText(I18N.getLabelString("Item#") + ":");
        jButton1.setMnemonic('A');
        //jScrollPane3.setNextFocusableComponent(jPanel4);
        jButton1.setText(I18N.getButtonString("Add"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton1_actionPerformed(e);
            }
        });

        jTextField1.setColumns(15);
        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                jTextField1_keyTyped(e);
            }
        });
        titledBorder1.setTitle("Tips");
        this.setBorder(border2);
        //flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setBorder(border13);
        jButton4.setMnemonic('D');
        jButton4.setText(I18N.getButtonString("Delete"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton4_actionPerformed(e);
            }
        });
        jPanel5.setLayout(flowLayout2);
        jButton7.setEnabled(true);
        jButton7.setMnemonic('R');
        jButton7.setText(I18N.getButtonString("Credit Card"));
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton7_actionPerformed(e);
            }
        });
        jButton8.setEnabled(true);
        jButton8.setMnemonic('K');
        jButton8.setText(I18N.getButtonString("Check"));
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton8_actionPerformed(e);
            }
        });
        //jPanel4.add(jPanel3, jButton8.getActionCommand());


        jPanel5.setBorder(border9);
        jButton9.setMnemonic('T');
        jButton9.setText("Tax Exempt");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton9_actionPerformed(e);
            }
        });

        jButton14.setMnemonic('E');
        jButton14.setText(I18N.getButtonString("Edit"));
        jButton14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton14_actionPerformed(e);
            }
        });

        jPanel35.setLayout(borderLayout10);
        jButton3.setMnemonic('H');
        jButton3.setText(I18N.getButtonString("Cash"));
        jButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButton3_actionPerformed(e);
            }
        });
        jLabel2.setText(I18N.getLabelString("Customer") + ":");

        if (customer != null) {
            jLabel3.setText(customer.getFirstName() + " " +
                            customer.getLastName());
        } else {
            jLabel3.setText("Default Customer");
        }

        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Change"));
        jButton2.addActionListener(new NewSalePanel_jButton2_actionAdapter(this));
        jButton5.setMnemonic('N');
        jButton5.setText(I18N.getButtonString("New"));
        jButton5.addActionListener(new NewSalePanel_jButton5_actionAdapter(this));
        jPanel3.add(jLabel2);
        jPanel3.add(jLabel3);
        jPanel3.add(jButton2);
        jPanel3.add(jButton5);
        jPanel2.add(jLabel1);
        jPanel2.add(jTextField1);
        jPanel2.add(jButton1);
        jPanel2.add(jButton14);
        jPanel2.add(jButton4);
        jScrollPane1.getViewport().add(jTable2);
        this.add(jPanel11, BorderLayout.SOUTH);
        jPanel5.add(jButton3);
        jPanel5.add(jButton8);
        jPanel5.add(jButton7);
        jTable2.getModel().addTableModelListener(this);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.setCellSelectionEnabled(true);
        jPanel35.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        this.add(jPanel35, java.awt.BorderLayout.CENTER);
        jPanel11.add(jPanel5, java.awt.BorderLayout.CENTER);
        jPanel11.add(totalPanel, java.awt.BorderLayout.NORTH);
        this.add(jPanel1, java.awt.BorderLayout.NORTH);
        jPanel1.add(jPanel3, java.awt.BorderLayout.EAST);
        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);
        jTextField1.requestFocusInWindow();
        jTable2.setCellSelectionEnabled(true);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable2);
        jTable2.addMouseListener(new TablePopupMenuMouseListener());
    }

    void jButton1_actionPerformed(ActionEvent e) {
        String barcode = jTextField1.getText().trim();
        addProduct(barcode);
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        TableModel model = (TableModel) e.getSource();
        int rowCount = model.getRowCount();
        double subtotal = 0;
        double subdiscount = 0;
        double subtax = 0;

        for (int i = 0; i < rowCount; i++) {
            double price = ((Double) model.getValueAt(i, 2)).doubleValue();
            double tax = ((Double) model.getValueAt(i, 3)).doubleValue();
            int quantity = ((Integer) model.getValueAt(i, 4)).intValue();
            subtotal += price * quantity;
            subtax += tax / 100 * subtotal * quantity;
        }

        subtotal = Math.round(subtotal * 100) / 100.00;
        subtax = Math.round(subtax * 100) / 100.00;
        double total = subtotal + subtax;

        totalPanel.setSubTotal(subtotal);
        totalPanel.setSalesTax(subtax);
        totalPanel.setTotal(total);
        totalPanel.validate();
        totalPanel.repaint();
    }

    void completeSale(Payment payment) {
        try {
            TableModel model = jTable2.getModel();
            int rowCount = model.getRowCount();
            OrderProduct[] products = new OrderProduct[rowCount];

            for (int i = 0; i < rowCount; i++) {
                products[i] = new OrderProduct((String) model.getValueAt(i, 5),
                                               (String) model.getValueAt(i, 0),
                                               (String) model.getValueAt(i, 1),
                                               ((Double) model.getValueAt(i, 2)).
                                               doubleValue(),
                                               ((Double) model.getValueAt(i, 3)).
                                               doubleValue(),
                                               ((Integer) model.getValueAt(i, 4)).
                                               intValue());
            }

            NumberFormat nf = NumberFormat.getInstance();
            User user = ((MainFrame) parent).getUser();

            Order order = Order.createOrder(products,
                                            totalPanel.getTotal(),
                                            totalPanel.getSalesTax(), payment,
                                            user.getId(), customer);

            if (order != null) {
                newSale();

                if (payment.getPaymentType().equals("Cash")) {
                    CDrawer.open();
                }

                Printer.print(order);

                if (payment.getPaymentType().equals("Credit/Debit")) {
                    Printer.print(order, "customer");
                    Printer.print(order, "store");
                }

                if (Synchronizer.getMode() == Synchronizer.REAL_SYNC) {
                    order.sync();
                }

                if (payment.getPaymentType().equals("Cash")) {
                    while (!CDrawer.close()) {
                        try {
                            JOptionPane.showMessageDialog(parent,
                                    I18N.getMessageString(
                                            "Please close cash drawer."),
                                    I18N.getLabelString("Error"),
                                    JOptionPane.ERROR_MESSAGE);
                            Thread.sleep(10);
                        } catch (Exception exp) {
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString(
                        "Order processing error."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parent,
                                          ex.getMessage(),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

        }
    }

    void newSale() {
        SalesTableModel model = (SalesTableModel) jTable2.getModel();
        model.removeAll();
        jTable2.invalidate();
        customer = new Customer(0);
        jLabel3.setText(customer.getFirstName() + " " +
                        customer.getLastName());
        jTextField1.setText("");
        validate();
        repaint();
        jTextField1.requestFocusInWindow();
    }

    void jButton2_actionPerformed(ActionEvent e) {
        SelectCustomerDlg dlg = new SelectCustomerDlg(customer);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        if (dlg.getCustomer() != null) {
            customer = dlg.getCustomer();

            if (customer != null) {
                jLabel3.setText(customer.getFirstName() + " " +
                                customer.getLastName());
            } else {
                jLabel3.setText("Default Customer");
            }
            validate();
            repaint();
        }
    }

    void jTextField1_keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            String barcode = jTextField1.getText().trim();
            addProduct(barcode);
        }
    }

    void addProduct(String barcode) {
        barcode = barcode.trim();

        if (barcode.equals("")) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "Please enter item#."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        SalesTableModel model = (SalesTableModel) jTable2.getModel();
        int row = model.getProductRow(barcode);

        if (row >= 0) {
            int quantity = ((Integer) model.getValueAt(row, 4)).intValue();
            model.setValueAt(new Integer(quantity + 1), row, 4);
        } else {
            Product product = Product.getProduct(barcode);

            if (product != null) {
                model.addRow(product.getCode(), barcode, product.getDescription(),
                             product.getPrice(),
                             product.getTax(), 1);
            } else {
                JOptionPane.showMessageDialog(parent,
                                              I18N.getMessageString(
                        "Product not found."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        jTable2.revalidate();
        jTable2.repaint();
    }

    void jButton4_actionPerformed(ActionEvent e) {
        ListSelectionModel lsm = jTable2.getSelectionModel();
        if (lsm.isSelectionEmpty()) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "Please select an item to delete."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            int option = JOptionPane.showConfirmDialog(parent,
                    I18N.getMessageString(
                            "Are you sure you want to delete this item?"),
                    I18N.getLabelString("Confirm"),
                    JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.NO_OPTION) {
                return;
            }

            int selectedRow = lsm.getMinSelectionIndex();
            SalesTableModel model = (SalesTableModel) jTable2.getModel();
            model.removeRow(selectedRow);
            jTable2.revalidate();
            jTable2.repaint();
        }
    }

    void jButton9_actionPerformed(ActionEvent e) {
        ListSelectionModel lsm = jTable2.getSelectionModel();
        if (lsm.isSelectionEmpty()) {
        } else {
            int selectedRow = lsm.getMinSelectionIndex();
            if (selectedRow >= 0) {
                SalesTableModel model = (SalesTableModel) jTable2.getModel();
                model.setValueAt(new Double(0), selectedRow, 3);
                jTable2.revalidate();
                jTable2.repaint();
            }
        }
    }

    public void jButton8_actionPerformed(ActionEvent e) {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "No items purchased."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        CheckPaymentDlg dlg = new CheckPaymentDlg(parent, totalPanel.getTotal(),
                                                  customer);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        if (dlg.getPayment() != null) {
            completeSale(dlg.getPayment());
        }
    }

    public void jButton7_actionPerformed(ActionEvent e) {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "No items purchased."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        CreditPaymentDlg dlg = new CreditPaymentDlg(parent, totalPanel.getTotal());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        if (dlg.getPayment() != null) {
            completeSale(dlg.getPayment());
        }
    }

    public void jButton14_actionPerformed(ActionEvent e) {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "Please select an item to edit."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        int currentRow = jTable2.getSelectedRow();
        int currentCol = jTable2.getSelectedColumn();

        if (currentRow < 0) {
            currentRow = 0;
        }
        if (currentCol < 0) {
            currentCol = 0;
        }
        jTable2.changeSelection(currentRow, currentCol, false, false);
        jTable2.requestFocus();
        jTable2.revalidate();
        jTable2.repaint();
    }

    public void jButton15_actionPerformed(ActionEvent e) {
        if (jTable2.getRowCount() == 0) {
            return;
        }

        int currentRow = jTable2.getSelectedRow();
        int currentCol = jTable2.getSelectedColumn();

        currentRow++;
        if (currentRow == jTable2.getRowCount()) {
            currentRow--;
        }
        if (currentCol < 0) {
            currentCol = 0;
        }
        jTable2.changeSelection(currentRow, currentCol, false, false);
        jTable2.revalidate();
        jTable2.repaint();
    }

    public void jButton16_actionPerformed(ActionEvent e) {
        int currentRow = jTable2.getSelectedRow();
        int currentCol = jTable2.getSelectedColumn();

        currentCol--;
        if (currentRow < 0) {
            currentRow = 0;
        }
        if (currentCol < 0) {
            currentCol = 0;
        }
        jTable2.changeSelection(currentRow, currentCol, false, false);
        jTable2.revalidate();
        jTable2.repaint();
    }

    public void jButton17_actionPerformed(ActionEvent e) {
        int currentRow = jTable2.getSelectedRow();
        int currentCol = jTable2.getSelectedColumn();

        currentCol++;
        if (currentRow < 0) {
            currentRow = 0;
        }
        if (currentCol == jTable2.getColumnCount()) {
            currentCol--;
        }
        jTable2.changeSelection(currentRow, currentCol, false, false);
        jTable2.revalidate();
        jTable2.repaint();
    }

    public void jButton18_actionPerformed(ActionEvent e) {
        newSale();
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        if (jTable2.getRowCount() == 0) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                                                  "No items purchased."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        CashPaymentDlg dlg = new CashPaymentDlg(parent, totalPanel.getTotal());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        if (dlg.getPayment() != null) {
            completeSale(dlg.getPayment());
        }
    }

    public void jButton5_actionPerformed(ActionEvent e) {
        CustomerDlg dlg = new CustomerDlg();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);

        if (dlg.getCustomer() != null) {
            customer = dlg.getCustomer();
            if (customer != null) {
                jLabel3.setText(customer.getFirstName() + " " +
                                customer.getLastName());
            } else {
                jLabel3.setText("Default Customer");
            }
            dlg.setVisible(false);
            validate();
            repaint();
        } else {
            dlg.setVisible(false);
        }
    }
}


class NewSalePanel_jButton2_actionAdapter implements ActionListener {
    private NewSalePanel adaptee;
    NewSalePanel_jButton2_actionAdapter(NewSalePanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class NewSalePanel_jButton5_actionAdapter implements ActionListener {
    private NewSalePanel adaptee;
    NewSalePanel_jButton5_actionAdapter(NewSalePanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {

        adaptee.jButton5_actionPerformed(e);
    }
}
