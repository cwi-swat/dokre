package com.baycloud.synpos.ui;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.xt.*;
import com.baycloud.synpos.util.*;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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
public class ConfigPaymentGatewayPanel extends JPanel {
    public ConfigPaymentGatewayPanel(JFrame parent, Element elem) {
        this.parent = parent;
        this.elem = elem;
        this.paramCount = 0;
        this.params = new Hashtable();
        this.options = new Hashtable();

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        jPanel4.setLayout(borderLayout1);
        jPanel5.setLayout(gridLayout1);
        jPanel6.setLayout(gridLayout2);
        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);
        jPanel4.add(jPanel5, java.awt.BorderLayout.WEST);
        this.add(jPanel4);
        this.setLayout(flowLayout8);
        flowLayout8.setAlignment(FlowLayout.LEFT);

        NodeList idNodes = elem.getElementsByTagName("GatewayId");
        gatewayId = idNodes.item(0).getFirstChild().getNodeValue();
        NodeList nameNodes = elem.getElementsByTagName("GatewayName");
        gatewayName = nameNodes.item(0).getFirstChild().getNodeValue();
        NodeList classNodes = elem.getElementsByTagName("ClassName");
        gatewayClass = classNodes.item(0).getFirstChild().getNodeValue();
        NodeList paramsNodes = elem.getElementsByTagName("ClassParameter");

        for (int i = 0; i < paramsNodes.getLength(); i++) {
            paramCount++;
            Element paramElem = (Element) paramsNodes.item(i);
            NodeList paramIdNodes = paramElem.getElementsByTagName(
                    "ParameterId");
            String paramId = paramIdNodes.item(0).getFirstChild().getNodeValue();
            NodeList paramNameNodes = paramElem.getElementsByTagName(
                    "ParameterName");
            String paramName = paramNameNodes.item(0).getFirstChild().
                               getNodeValue();
            NodeList paramOptionsNodes = paramElem.getElementsByTagName(
                    "ParameterOption");
            jPanel5.add(new JLabel(paramName + ":"));

            if (paramOptionsNodes.getLength() == 1) {
                Element optionElem = (Element) paramOptionsNodes.item(0);
                NodeList optionNameNodes = optionElem.getElementsByTagName(
                        "OptionName");

                NodeList optionValueNodes = optionElem.getElementsByTagName(
                        "OptionValue");
                String optionValue = optionValueNodes.item(0).getFirstChild().
                                     getNodeValue();

                if (optionNameNodes.item(0).getFirstChild() == null) { //checkbox
                    JCheckBox check = new JCheckBox("", true);

                    if (StoreConfiguration.get("payment.gateway." + gatewayId +
                                               ".parameter." + paramId) != null) {
                        check.setSelected(true);
                    }

                    JPanel panel = new JPanel();
                    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    panel.add(check);
                    jPanel6.add(panel);
                    params.put(paramId, check);
                    options.put(paramId, optionValue);
                } else {
                    String optionName = optionNameNodes.item(0).getFirstChild().
                                        getNodeValue();
                    JComboBox combo = new JComboBox();
                    JPanel panel = new JPanel();
                    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                    panel.add(combo);
                    jPanel6.add(panel);
                    params.put(paramId, combo);
                    Hashtable optionValues = new Hashtable();
                    optionValues.put(optionName, optionValue);
                    options.put(paramId, optionValues);
                    combo.addItem(optionName);
                    /*
                                         String value = StoreConfiguration.get("payment.gateway."+ gatewayId+".parameter."+paramId);

                     if(value != null && value.equals(optionValue)) {
                        combo.setSelectedItem(optionName);
                                         }
                     */
                }
            } else if (paramOptionsNodes.getLength() > 0) {
                JComboBox combo = new JComboBox();
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                panel.add(combo);
                jPanel6.add(panel);
                params.put(paramId, combo);
                Hashtable optionValues = new Hashtable();

                String value = StoreConfiguration.get("payment.gateway." +
                        gatewayId + ".parameter." + paramId);

                for (int j = 0; j < paramOptionsNodes.getLength(); j++) {
                    Element optionElem = (Element) paramOptionsNodes.item(j);
                    NodeList optionNameNodes = optionElem.getElementsByTagName(
                            "OptionName");
                    String optionName = optionNameNodes.item(0).getFirstChild().
                                        getNodeValue();
                    NodeList optionValueNodes = optionElem.getElementsByTagName(
                            "OptionValue");
                    String optionValue = optionValueNodes.item(0).getFirstChild().
                                         getNodeValue();
                    combo.addItem(optionName);
                    optionValues.put(optionName, optionValue);

                    if (value != null && value.equals(optionValue)) {
                        combo.setSelectedItem(optionName);
                    }
                }

                options.put(paramId, optionValues);
            } else {
                JTextField field = new JTextField(20);
                field.addMouseListener(new PopupMenuMouseListener());
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                panel.add(field);
                jPanel6.add(panel);
                params.put(paramId, field);
                String value = StoreConfiguration.get("payment.gateway." +
                        gatewayId + ".parameter." + paramId);

                if (value != null) {
                    field.setText(value);
                }
            }
        }

        NodeList acceptedCardsNodes = elem.getElementsByTagName("AcceptedCards");
        Element acceptedCardElem = (Element) acceptedCardsNodes.item(0);
        NodeList cardsNodes = acceptedCardElem.getChildNodes();

        if (cardsNodes.getLength() > 0) {
            jPanel5.add(new JLabel(I18N.getLabelString("Accept Cards")+":"));
        }

        for (int i = 0; i < cardsNodes.getLength(); i++) {

            String cardId = cardsNodes.item(i).getNodeName();
            String cardName = GatewayManager.getCardName(cardId);

            if (cardName != null) {
                paramCount++;
                JCheckBox check = new JCheckBox(cardName, true);
                JPanel panel = new JPanel();
                panel.setLayout(new FlowLayout(FlowLayout.LEFT));
                panel.add(check);
                jPanel6.add(panel);
                params.put("acceptedcards." + cardId, check);
                options.put("acceptedcards." + cardId, "true");

                String value = StoreConfiguration.get("payment.gateway." +
                        gatewayId + ".parameter.acceptedcards." + cardId);

                if (value != null) {
                    check.setSelected(true);
                }
            }
        }

        gridLayout1.setColumns(1);
        gridLayout1.setRows(paramCount + 1);
        gridLayout2.setColumns(1);
        gridLayout2.setRows(paramCount + 1);
        jButton1.setMnemonic('S');
        //this.setLayout(borderLayout2);
        jButton1.setText(I18N.getButtonString("Submit"));
        jButton1.addActionListener(new
                                   ConfigPaymentGatewayPanel_jButton1_actionAdapter(this));
        jPanel6.add(jPanel11);
        jPanel11.add(jButton1);
        jPanel11.setLayout(new FlowLayout(FlowLayout.LEFT));
    }


    JFrame parent;
    Element elem;
    int paramCount;
    String gatewayName;
    String gatewayClass;
    String gatewayId;
    Hashtable params;
    Hashtable options;

    JPanel jPanel4 = new JPanel();
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    GridLayout gridLayout2 = new GridLayout();
    FlowLayout flowLayout4 = new FlowLayout();
    JPanel jPanel11 = new JPanel();
    JButton jButton1 = new JButton();
    FlowLayout flowLayout8 = new FlowLayout();

    public void jButton1_actionPerformed(ActionEvent e) {
        for (int i = 0; i < GatewayManager.ACCEPTED_CARDS.length; i++) {
            StoreConfiguration.delete("payment.gateway." + gatewayId +
                                      ".parameter.acceptedcards." +
                                      GatewayManager.ACCEPTED_CARDS[i]);
        }

        Iterator iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();

            if (entry.getValue() instanceof JTextField) {
                String paramValue = ((JTextField) entry.getValue()).getText().
                                    trim();
                StoreConfiguration.set("payment.gateway." + gatewayId +
                                       ".parameter." + entry.getKey().toString(),
                                       paramValue);
            } else if (entry.getValue() instanceof JComboBox) {
                String optionName = ((JComboBox) entry.getValue()).
                                    getSelectedItem().toString();
                Hashtable optionValues = (Hashtable) options.get(entry.getKey().
                        toString());
                String optionValue = (String) optionValues.get(optionName);
                StoreConfiguration.set("payment.gateway." + gatewayId +
                                       ".parameter." + entry.getKey().toString(),
                                       optionValue);
            } else if (entry.getValue() instanceof JCheckBox) {
                if (((JCheckBox) entry.getValue()).isSelected()) {
                    String paramValue = (String) options.get(entry.getKey().
                            toString());
                    StoreConfiguration.set("payment.gateway." + gatewayId +
                                           ".parameter." +
                                           entry.getKey().toString(),
                                           paramValue);
                }
            }
        }

        JOptionPane.showMessageDialog(parent,
                                      I18N.getMessageString("Gateway settings have been updated."),
                                      I18N.getLabelString("Message"),
                                      JOptionPane.INFORMATION_MESSAGE);

    }

    public int getParamCount() {
        return paramCount;
    }

    public String getGatewayName() {
        return gatewayName;
    }
}


class ConfigPaymentGatewayPanel_jButton1_actionAdapter implements
        ActionListener {
    private ConfigPaymentGatewayPanel adaptee;
    ConfigPaymentGatewayPanel_jButton1_actionAdapter(ConfigPaymentGatewayPanel
            adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}
