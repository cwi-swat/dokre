package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.util.I18N;

import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import jpos.util.JposPropertiesConst;
import com.borland.jbcl.layout.BoxLayout2;

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
public class ConfigMSRPanel extends JPanel implements ListSelectionListener {
    public ConfigMSRPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigMSRPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        String jposXML = System.getProperty(JposPropertiesConst.
                                            JPOS_POPULATOR_FILE_PROP_NAME);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream in = new FileInputStream(jposXML);
        Document document = builder.parse(in);
        Element root = document.getDocumentElement();
        if (root.getNodeName().equals("JposEntries")) {
            NodeList nodes = root.getElementsByTagName("JposEntry");

            for (int i = 0; i < nodes.getLength(); i++) {
                NodeList jpos = ((Element) nodes.item(i)).getElementsByTagName(
                        "jpos");
                if (jpos.getLength() > 0) {
                    NamedNodeMap attrs = jpos.item(0).getAttributes();
                    Node category = attrs.getNamedItem("category");

                    if (category != null &&
                        category.getNodeValue().equals("MSR")) {
                        NamedNodeMap printerAttrs = nodes.item(i).getAttributes();
                        Node printer = printerAttrs.getNamedItem("logicalName");

                        if (printer != null) {
                            String logicalName = printer.getNodeValue();
                            //vendors.add(logicalName.getNodeValue());
                            NodeList vendorNodes = ((Element) nodes.item(i)).
                                    getElementsByTagName("vendor");
                            if (vendorNodes.getLength() > 0) {
                                NamedNodeMap vendorAttrs = vendorNodes.item(0).
                                        getAttributes();
                                Node vendorNode = vendorAttrs.getNamedItem(
                                        "name");
                                if (vendorNode != null) {
                                    String vendorName = vendorNode.getNodeValue();
                                    Vector vendorModels = (Vector) models.get(
                                            vendorName);
                                    if (vendorModels == null) {
                                        vendorModels = new Vector();
                                        vendorModels.add(logicalName);
                                        models.put(vendorName, vendorModels);
                                    } else {
                                        vendorModels.add(logicalName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        in.close();
        jList1.setListData(models.keySet().toArray());
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(this);
        String vendor = Configuration.get("msr.vendor");

        if (vendor != null) {
            jList1.setSelectedValue(vendor, true);
            jList2.setListData((Vector) models.get(vendor));
        } else {
            Collection allModels = models.values();

            if (allModels.size() > 0) {
                jList1.setSelectedIndex(0);
                jList2.setListData((Vector) allModels.toArray()[0]);
            }
        }

        String model = Configuration.get("msr.model");
        if (model != null) {
            jList2.setSelectedValue(model, true);
        } else {
            jList2.setSelectedIndex(0);
        }

        jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setLayout(flowLayout1);
        flowLayout1.setAlignment(FlowLayout.LEFT);
        jPanel1.setLayout(borderLayout1);
        jPanel2.setLayout(flowLayout2);
        jLabel1.setText(I18N.getLabelString("Vendor")+": ");
        jPanel4.setLayout(boxLayout21);
        jPanel5.setLayout(boxLayout22);
        jLabel2.setText(I18N.getLabelString("Model")+": ");
        jPanel3.setLayout(flowLayout3);
        flowLayout3.setAlignment(FlowLayout.RIGHT);
        jButton1.setMnemonic('S');
        jButton1.setText(I18N.getButtonString("Submit"));
        jButton1.addActionListener(new
                                   ConfigMSRPanel_jButton1_actionAdapter(this));
        jCheckBox1.setText(I18N.getLabelString("No MSR"));
        if (model == null) {
            jCheckBox1.setSelected(true);
        }
        boxLayout21.setAxis(BoxLayout.Y_AXIS);
        boxLayout22.setAxis(BoxLayout.Y_AXIS);
        this.add(jPanel1);
        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jPanel4);
        jPanel2.add(jScrollPane1);
        jScrollPane1.getViewport().setView(jList1);
        jPanel2.add(jPanel5);
        jScrollPane2.getViewport().setView(jList2);
        jPanel1.add(jPanel3, java.awt.BorderLayout.SOUTH);
        jPanel3.add(jCheckBox1);
        jPanel3.add(jButton1);
        jPanel4.add(jLabel1, null);
        jPanel2.add(jScrollPane2);
        jPanel5.add(jLabel2, null);
    }

    JFrame parent;
    HashMap models = new HashMap();
    FlowLayout flowLayout1 = new FlowLayout();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JList jList1 = new JList();
    JPanel jPanel5 = new JPanel();
    JLabel jLabel2 = new JLabel();
    JList jList2 = new JList();
    FlowLayout flowLayout3 = new FlowLayout();
    JButton jButton1 = new JButton();
    JScrollPane jScrollPane1 = new JScrollPane();
    JScrollPane jScrollPane2 = new JScrollPane();
    JCheckBox jCheckBox1 = new JCheckBox();
    BoxLayout2 boxLayout21 = new BoxLayout2();
    BoxLayout2 boxLayout22 = new BoxLayout2();
    FlowLayout flowLayout2 = new FlowLayout();

    public void jButton1_actionPerformed(ActionEvent e) {
        if (jCheckBox1.isSelected()) {
            Configuration.delete("msr.vendor");
            Configuration.delete("msr.model");
            JOptionPane.showMessageDialog(parent,
                    I18N.getMessageString("Your MSR settings have been changed."),
                                          I18N.getLabelString("Message"),
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String vendor = (String) jList1.getSelectedValue();
        String model = (String) jList2.getSelectedValue();

        if (vendor == null || model == null) {
            JOptionPane.showMessageDialog(parent,
                                          I18N.getMessageString("Please select a MSR model."),
                                          I18N.getLabelString("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        Configuration.set("msr.vendor", vendor);
        Configuration.set("msr.model", model);
        JOptionPane.showMessageDialog(parent,
                I18N.getMessageString("Your MSR settings have been changed."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);
    }

    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();
        String vendor = (String) list.getSelectedValue();
        jList2.setListData((Vector) models.get(vendor));
        jList2.revalidate();
        jList2.repaint();
    }
}


class ConfigMSRPanel_jButton1_actionAdapter implements ActionListener {
    private ConfigMSRPanel adaptee;
    ConfigMSRPanel_jButton1_actionAdapter(ConfigMSRPanel adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
