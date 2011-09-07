package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
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
 * @version 0.9.2
 */
public class CustomersPanel extends JPanel {
    private JFrame parent;
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable(new CustomersTableModel());
    JLabel jLabel8 = new JLabel();
    JTextField jTextField6 = new JTextField();
    JPanel jPanel2 = new JPanel();
    FlowLayout flowLayout2 = new FlowLayout();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JPanel jPanel3 = new JPanel();
    Border border1 = BorderFactory.createEtchedBorder(Color.white,
            new Color(148, 145, 140));
    Border border2 = new TitledBorder(border1, "Query");
    FlowLayout flowLayout23 = new FlowLayout();
    JButton jButton4 = new JButton();
    JButton jButton1 = new JButton();
    JButton jButton5 = new JButton();
    JButton jButton6 = new JButton();
    JButton jButton7 = new JButton();
    JButton jButton8 = new JButton();
    JButton jButton9 = new JButton();

    public CustomersPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CustomersPanel(JFrame parent) {
        this.parent = parent;
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        jLabel8.setText(I18N.getLabelString("Email") + ": ");
        jTextField6.setColumns(15);
        jPanel2.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.RIGHT);
        jButton3.setMnemonic('E');
        jButton3.setText(I18N.getButtonString("Edit"));
        jButton3.addActionListener(new CustomersPanel_jButton3_actionAdapter(this));
        jButton2.setMnemonic('N');
        jButton2.setText(I18N.getButtonString("New"));
        jButton2.addActionListener(new CustomersPanel_jButton2_actionAdapter(this));
        jPanel3.setLayout(flowLayout23);
        jButton4.setActionCommand("F");
        jButton4.setMnemonic('F');
        jButton4.setText(I18N.getButtonString("Find"));
        jButton4.addActionListener(new CustomersPanel_jButton4_actionAdapter(this));
        flowLayout23.setAlignment(FlowLayout.LEFT);
        jButton1.setMnemonic('Q');
        jButton1.setText(I18N.getButtonString("Query"));
        jButton1.addActionListener(new CustomersPanel_jButton1_actionAdapter(this));
        jButton5.setMnemonic('K');
        jButton5.setText(I18N.getButtonString("Mark All As Synced"));
        jButton5.addActionListener(new CustomersPanel_jButton5_actionAdapter(this));
        jButton6.setMnemonic('M');
        jButton6.setText(I18N.getButtonString("Mark As Synced"));
        jButton6.addActionListener(new CustomersPanel_jButton6_actionAdapter(this));
        jButton7.setMnemonic('A');
        jButton7.setText(I18N.getButtonString("Sync All"));
        jButton7.addActionListener(new CustomersPanel_jButton7_actionAdapter(this));
        jButton8.setMnemonic('S');
        jButton8.setText(I18N.getButtonString("Sync"));
        jButton8.addActionListener(new CustomersPanel_jButton8_actionAdapter(this));
        jButton9.setMnemonic('D');
        jButton9.setText(I18N.getButtonString("Display All"));
        jButton9.addActionListener(new CustomersPanel_jButton9_actionAdapter(this));
        this.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        jScrollPane1.getViewport().add(jTable1);
        this.add(jPanel2, java.awt.BorderLayout.SOUTH);
        //jPanel2.add(jButton8);
        //jPanel2.add(jButton6);
        //jPanel2.add(jButton7);
        //jPanel2.add(jButton5);
        jPanel2.add(jButton3);
        jPanel2.add(jButton2);
        this.add(jPanel3, java.awt.BorderLayout.NORTH);
        jPanel3.add(jLabel8);
        jPanel3.add(jTextField6);
        jPanel3.add(jButton4);
        jPanel3.add(jButton1);
        jPanel3.add(jButton9);
        TableExcelAdapter myAd1 = new TableExcelAdapter(jTable1);
        jTable1.addMouseListener(new TablePopupMenuMouseListener());
        jTextField6.addMouseListener(new PopupMenuMouseListener());
    }

    void jButton1_actionPerformed(ActionEvent e) {
        CustomersTableModel model = (CustomersTableModel) jTable1.getModel();
        CustomersQueryDlg dlg = new CustomersQueryDlg();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);
        String[] queryStr = dlg.getQuery();

        if (queryStr != null) {
            model.query(queryStr[0], queryStr[1], queryStr[2], queryStr[3],
                        queryStr[4],
                        queryStr[5], queryStr[6],
                        queryStr[7], queryStr[8], queryStr[9]);
            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    public void jButton3_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();

            int custId = ((Integer) model1.getValueAt(selectedRow, 0)).intValue();
            Customer cust = new Customer(custId);
            CustomerDlg dlg = new CustomerDlg(cust);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension dlgSize = dlg.getPreferredSize();
            dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                            (screenSize.height - dlgSize.height) / 2);
            dlg.pack();
            dlg.setVisible(true);

            if (dlg.getCustomer() != null) {
                Customer customer = dlg.getCustomer();

                model1.setValueAt(customer.getFirstName(), selectedRow, 1);
                model1.setValueAt(customer.getLastName(), selectedRow, 2);
                model1.setValueAt(customer.getTitle(), selectedRow, 3);
                model1.setValueAt(customer.getBirthDate(), selectedRow, 4);
                model1.setValueAt(customer.getAddress1(), selectedRow, 5);
                model1.setValueAt(customer.getAddress2(), selectedRow, 6);
                model1.setValueAt(customer.getCity(), selectedRow, 7);
                model1.setValueAt(customer.getState(), selectedRow, 8);
                model1.setValueAt(customer.getZip(), selectedRow, 9);
                model1.setValueAt(customer.getPhone(), selectedRow, 10);
                model1.setValueAt(customer.getFax(), selectedRow, 11);
                model1.setValueAt(customer.getEmail(), selectedRow, 12);
                model1.setValueAt(customer.getDateCreated(), selectedRow, 13);
                model1.setValueAt(customer.getLastModified(), selectedRow, 14);
                /*
                                String status = "";

                                if (!customer.isSynchronized()) {
                                    status += "S";
                                }

                                model1.setValueAt(status, selectedRow, 15);
                 */
                jTable1.revalidate();
                jTable1.repaint();
            }

            //dlg.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString(
                    "Please select a customer to edit."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        CustomerDlg dlg = new CustomerDlg();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dlgSize = dlg.getPreferredSize();
        dlg.setLocation((screenSize.width - dlgSize.width) / 2,
                        (screenSize.height - dlgSize.height) / 2);
        dlg.pack();
        dlg.setVisible(true);
        Customer cust = dlg.getCustomer();

        if (cust != null) {
            CustomersTableModel model = (CustomersTableModel) jTable1.getModel();
            model.find(cust.getEmail());
            jTable1.revalidate();
            jTable1.repaint();
        }
    }

    public void jButton4_actionPerformed(ActionEvent e) {
        String email = jTextField6.getText().trim();

        if (email.equals("")) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Please enter an email address."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomersTableModel model = (CustomersTableModel) jTable1.getModel();
        model.find(email);
        jTable1.revalidate();
        jTable1.repaint();
    }

    public void jButton8_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() == Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();
            //String status = (String) model1.getValueAt(selectedRow, 6);
            boolean synced = model1.isSynchronized(selectedRow); //!status.endsWith("S");

            if (!synced) {
                String syncUrl = StoreConfiguration.get("sync_url");

                if (syncUrl == null) {
                    JOptionPane.showMessageDialog(parent,
                                                  I18N.getMessageString(
                            "Synchronization not configured."),
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
                                              I18N.getMessageString(
                        "The selected customer has already been synchronized."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString(
                    "Please select a customer to synchronize."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton6_actionPerformed(ActionEvent e) {
        int selectedRow = jTable1.getSelectionModel().getMinSelectionIndex();

        if (selectedRow >= 0) {
            CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();
            //String status = (String) model1.getValueAt(selectedRow, 6);
            //boolean synced = !status.endsWith("S");

            if (!model1.isSynchronized(selectedRow)) {
                int option = JOptionPane.showConfirmDialog(this,
                        I18N.getMessageString("This will only change the customers status from un-synchronized to synchronized without updating the remote database."),
                        I18N.getLabelString("Warning"),
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    model1.setSynchronized(selectedRow);
                    jTable1.revalidate();
                    jTable1.repaint();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                                              I18N.getMessageString(
                        "The selected customer has already been synchronized."),
                                              I18N.getLabelString("Error"),
                                              JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                                          I18N.getMessageString(
                    "Please select a customer."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButton7_actionPerformed(ActionEvent e) {
        if (Synchronizer.getMode() == Synchronizer.NEVER_SYNC) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

            return;
        }

        String syncUrl = StoreConfiguration.get("sync_url");

        if (syncUrl == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString(
                    "Synchronization not configured."),
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);
            return;
        }

        CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();

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

    public void jButton5_actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(this,
                I18N.getMessageString("This will only change the customers status from un-synchronized to synchronized without updating the remote database."),
                I18N.getLabelString("Warning"),
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {

            CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();

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

    public void jButton9_actionPerformed(ActionEvent e) {
        CustomersTableModel model1 = (CustomersTableModel) jTable1.getModel();
        model1.showAll();
        jTable1.revalidate();
        jTable1.repaint();
    }
}


class CustomersPanel_jButton9_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton9_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton9_actionPerformed(e);
    }
}


class CustomersPanel_jButton5_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton5_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton5_actionPerformed(e);
    }
}


class CustomersPanel_jButton6_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton6_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton6_actionPerformed(e);
    }
}


class CustomersPanel_jButton7_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton7_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton7_actionPerformed(e);
    }
}


class CustomersPanel_jButton8_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton8_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton8_actionPerformed(e);
    }
}


class CustomersPanel_jButton1_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton1_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


class CustomersPanel_jButton4_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton4_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton4_actionPerformed(e);
    }
}


class CustomersPanel_jButton2_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton2_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class CustomersPanel_jButton3_actionAdapter implements ActionListener {
    private CustomersPanel adaptee;
    CustomersPanel_jButton3_actionAdapter(CustomersPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton3_actionPerformed(e);
    }
}
