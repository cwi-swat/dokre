package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.*;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
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
 * @version 0.9.2
 */
public class CustomerDlg extends JDialog {
    public CustomerDlg() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public CustomerDlg(Customer cust) {
        this.cust = cust;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        jLabel4.setText(I18N.getLabelString("Date of Birth")+":");
        jLabel5.setText(I18N.getLabelString("Address1")+" (*):");
        jLabel6.setText(I18N.getLabelString("Address2")+":");
        jLabel7.setText(I18N.getLabelString("City")+" (*):");
        jLabel8.setText(I18N.getLabelString("State")+" (*):");
        jLabel9.setText(I18N.getLabelString("Zip")+" (*):");
        jLabel10.setText(I18N.getLabelString("Phone")+" (*):");
        jLabel11.setText(I18N.getLabelString("Fax")+":");
        jLabel12.setText(I18N.getLabelString("Email")+" (*):");
        jPanel4.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jTextField1.setColumns(10);
        jPanel5.setLayout(flowLayout2);
        flowLayout2.setAlignment(FlowLayout.LEFT);
        jTextField2.setColumns(10);
        jPanel6.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.LEFT);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(I18N.getLabelString("Male"));
        jRadioButton2.setText(I18N.getLabelString("Female"));
        jPanel7.setLayout(flowLayout4);
        flowLayout4.setAlignment(FlowLayout.LEFT);
        jTextField3.setText(I18N.getMessageString("mm/dd/yyyy"));
        jTextField3.setColumns(10);
        jPanel8.setLayout(flowLayout5);
        flowLayout5.setAlignment(FlowLayout.LEFT);
        jTextField4.setColumns(20);
        jTextField5.setColumns(20);
        jPanel9.setLayout(flowLayout6);
        flowLayout6.setAlignment(FlowLayout.LEFT);
        jLabel13.setText(I18N.getMessageString("Fields marked with (*) are required."));
        jPanel16.setLayout(flowLayout7);
        flowLayout7.setAlignment(FlowLayout.LEFT);
        jPanel10.setLayout(flowLayout8);
        jPanel11.setLayout(flowLayout9);
        jPanel12.setLayout(flowLayout10);
        jPanel13.setLayout(flowLayout11);
        jPanel14.setLayout(flowLayout12);
        jPanel15.setLayout(flowLayout13);
        flowLayout8.setAlignment(FlowLayout.LEFT);
        flowLayout9.setAlignment(FlowLayout.LEFT);
        flowLayout10.setAlignment(FlowLayout.LEFT);
        flowLayout11.setAlignment(FlowLayout.LEFT);
        flowLayout12.setAlignment(FlowLayout.LEFT);
        flowLayout13.setAlignment(FlowLayout.LEFT);
        jTextField6.setColumns(10);
        jTextField7.setColumns(10);
        jTextField8.setColumns(10);
        jTextField9.setColumns(10);
        jTextField10.setColumns(10);
        jTextField11.setColumns(20);
        jPanel3.setLayout(flowLayout14);
        flowLayout14.setAlignment(FlowLayout.RIGHT);
        jButton1.setMnemonic('O');
        jButton1.setText(I18N.getButtonString("OK"));
        jButton1.addActionListener(new CustomerDlg_jButton1_actionAdapter(this));
        jButton2.setMnemonic('C');
        jButton2.setText(I18N.getButtonString("Cancel"));
        jButton2.addActionListener(new CustomerDlg_jButton2_actionAdapter(this));
        this.setModal(true);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);
        jLabel2.setText(I18N.getLabelString("Last Name")+" (*):");
        jLabel3.setText(I18N.getLabelString("Sex")+":");
        jPanel1.add(jLabel1);
        jPanel1.add(jLabel2);
        jPanel1.add(jLabel3);
        jLabel1.setText(I18N.getLabelString("First Name")+" (*):");
        gridLayout2.setColumns(1);
        gridLayout2.setRows(12);
        gridLayout1.setColumns(1);
        gridLayout1.setRows(12);
        jPanel2.setLayout(gridLayout2);
        jPanel1.setLayout(gridLayout1);
        this.getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel4);
        jPanel4.add(jTextField1);
        jPanel2.add(jPanel5);
        jPanel5.add(jTextField2);
        this.getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jButton1);
        this.getContentPane().add(jPanel16, java.awt.BorderLayout.NORTH);
        jPanel16.add(jLabel13);
        jPanel1.add(jLabel4);
        jPanel1.add(jLabel5);
        jPanel1.add(jLabel6);
        jPanel1.add(jLabel7);
        jPanel1.add(jLabel8);
        jPanel1.add(jLabel9);
        jPanel1.add(jLabel10);
        jPanel2.add(jPanel6);
        jPanel6.add(jRadioButton1);
        jPanel6.add(jRadioButton2);
        jPanel2.add(jPanel7);
        jPanel7.add(jTextField3);
        jPanel2.add(jPanel8);
        jPanel8.add(jTextField4);
        jPanel2.add(jPanel9);
        jPanel9.add(jTextField5);
        jPanel2.add(jPanel10);
        jPanel10.add(jTextField6);
        jPanel2.add(jPanel11);
        jPanel11.add(jTextField7);
        jPanel2.add(jPanel12);
        jPanel12.add(jTextField8);
        jPanel2.add(jPanel13);
        jPanel13.add(jTextField9);
        jPanel1.add(jLabel11);
        jPanel1.add(jLabel12);
        jPanel2.add(jPanel14);
        jPanel14.add(jTextField10);
        jPanel2.add(jPanel15);
        jPanel15.add(jTextField11);
        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jPanel3.add(jButton2);

        if (cust != null) {
            setTitle(I18N.getLabelString("Edit Customer"));
            jTextField1.setText(cust.getFirstName());
            jTextField2.setText(cust.getLastName());
            if (cust.getTitle().equals("F")) {
                jRadioButton2.setSelected(true);
            }
            //SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            jTextField3.setText(cust.getBirthDate());
            jTextField4.setText(cust.getAddress1());
            jTextField5.setText(cust.getAddress2());
            jTextField6.setText(cust.getCity());
            jTextField7.setText(cust.getState());
            jTextField8.setText(cust.getZip());
            jTextField9.setText(cust.getPhone());
            jTextField10.setText(cust.getFax());
            jTextField11.setText(cust.getEmail());
        } else {
            setTitle(I18N.getLabelString("New Customer"));
        }

        jTextField1.addMouseListener(new PopupMenuMouseListener());
        jTextField2.addMouseListener(new PopupMenuMouseListener());
        jTextField3.addMouseListener(new PopupMenuMouseListener());
        jTextField4.addMouseListener(new PopupMenuMouseListener());
        jTextField5.addMouseListener(new PopupMenuMouseListener());
        jTextField6.addMouseListener(new PopupMenuMouseListener());
        jTextField7.addMouseListener(new PopupMenuMouseListener());
        jTextField8.addMouseListener(new PopupMenuMouseListener());
        jTextField9.addMouseListener(new PopupMenuMouseListener());
        jTextField10.addMouseListener(new PopupMenuMouseListener());
        jTextField11.addMouseListener(new PopupMenuMouseListener());
    }

    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JLabel jLabel6 = new JLabel();
    JLabel jLabel7 = new JLabel();
    JLabel jLabel8 = new JLabel();
    JLabel jLabel9 = new JLabel();
    JLabel jLabel10 = new JLabel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    JPanel jPanel11 = new JPanel();
    JPanel jPanel12 = new JPanel();
    JPanel jPanel13 = new JPanel();
    JLabel jLabel11 = new JLabel();
    JLabel jLabel12 = new JLabel();
    JPanel jPanel14 = new JPanel();
    JPanel jPanel15 = new JPanel();
    FlowLayout flowLayout1 = new FlowLayout();
    JTextField jTextField1 = new JTextField();
    FlowLayout flowLayout2 = new FlowLayout();
    JTextField jTextField2 = new JTextField();
    FlowLayout flowLayout3 = new FlowLayout();
    JRadioButton jRadioButton1 = new JRadioButton();
    JRadioButton jRadioButton2 = new JRadioButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    FlowLayout flowLayout4 = new FlowLayout();
    JTextField jTextField3 = new JTextField();
    FlowLayout flowLayout5 = new FlowLayout();
    JTextField jTextField4 = new JTextField();
    JTextField jTextField5 = new JTextField();
    FlowLayout flowLayout6 = new FlowLayout();
    JPanel jPanel16 = new JPanel();
    JLabel jLabel13 = new JLabel();
    FlowLayout flowLayout7 = new FlowLayout();
    FlowLayout flowLayout8 = new FlowLayout();
    FlowLayout flowLayout9 = new FlowLayout();
    FlowLayout flowLayout10 = new FlowLayout();
    FlowLayout flowLayout11 = new FlowLayout();
    FlowLayout flowLayout12 = new FlowLayout();
    FlowLayout flowLayout13 = new FlowLayout();
    JTextField jTextField6 = new JTextField();
    JTextField jTextField7 = new JTextField();
    JTextField jTextField8 = new JTextField();
    JTextField jTextField9 = new JTextField();
    JTextField jTextField10 = new JTextField();
    JTextField jTextField11 = new JTextField();
    FlowLayout flowLayout14 = new FlowLayout();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    Customer cust = null;

    public void jButton1_actionPerformed(ActionEvent e) {
        String firstName = jTextField1.getText().trim();
        String lastName = jTextField2.getText().trim();
        boolean title = jRadioButton1.isSelected();
        String birthDate = jTextField3.getText().trim();
        String address1 = jTextField4.getText().trim();
        String address2 = jTextField5.getText().trim();
        String city = jTextField6.getText().trim();
        String state = jTextField7.getText().trim();
        String zip = jTextField8.getText().trim();
        String phone = jTextField9.getText().trim();
        String fax = jTextField10.getText().trim();
        String email = jTextField11.getText().trim();
        String error = "";

        if (firstName.equals("")) {
            error += I18N.getMessageString("First Name is empty.")+"\n\r";
        }

        if (lastName.equals("")) {
            error += I18N.getMessageString("Last Name is empty.")+"\n\r";
        }

        Date date = null;

        if (!birthDate.equals("")) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                date = df.parse(birthDate);
            } catch (Exception ex) {
                error += I18N.getMessageString("Date of Birth is invalid.")+"\n\r";
            }
        }

        if (address1.equals("")) {
            error += I18N.getMessageString("Address1 is empty.")+"\n\r";
        }

        if (city.equals("")) {
            error += I18N.getMessageString("City is empty.")+"\n\r";
        }

        if (state.equals("")) {
            error += I18N.getMessageString("State is empty.")+"\n\r";
        }

        if (zip.equals("")) {
            error += I18N.getMessageString("Zip is empty.")+"\n\r";
        }

        if (phone.equals("")) {
            error += I18N.getMessageString("Phone is empty.")+"\n\r";
        }

        if (email.equals("")) {
            error += I18N.getMessageString("Email is empty.")+"\n\r";
        }

        if (error.length() > 0) {
            JOptionPane.showMessageDialog(this, error,
                                          I18N.getLabelString("Error"),
                                          JOptionPane.ERROR_MESSAGE);

        } else {
            if (cust != null) {
                cust.update(firstName, lastName, title ? "M" : "F",
                            birthDate,
                            address1, address2, city, state, zip, phone,
                            fax, email);

            } else {
                if (Customer.getCustomer(email, "email") != null) {
                    JOptionPane.showMessageDialog(this,
                                                  I18N.getMessageString("Email address already exists")+": " +
                                                  email,
                                                  I18N.getLabelString("Error"),
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }

                cust = new Customer("", firstName, lastName, title ? "M" : "F",
                                    birthDate,
                                    address1, address2, city, state, zip, phone,
                                    fax, email, new Date(), new Date());

            }

            cust.save();
/*
            if (Synchronizer.getMode() == Synchronizer.REAL_SYNC) {
                cust.sync();
            }
*/
            setVisible(false);

        }
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        cust = null;
        setVisible(false);
    }

    public Customer getCustomer() {
        return cust;
    }
}


class CustomerDlg_jButton2_actionAdapter implements ActionListener {
    private CustomerDlg adaptee;
    CustomerDlg_jButton2_actionAdapter(CustomerDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class CustomerDlg_jButton1_actionAdapter implements ActionListener {
    private CustomerDlg adaptee;
    CustomerDlg_jButton1_actionAdapter(CustomerDlg adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
