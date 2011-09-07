package com.baycloud.synpos.ui;

import javax.swing.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.FileReader;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
public class ConfigPaymentPanel extends JTabbedPane {
    public ConfigPaymentPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ConfigPaymentPanel(JFrame parent) {
        this.parent = parent;

        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.
                                      newDocumentBuilder();
            Document document = builder.parse(new InputSource(new FileReader(
                    "config/jpay.xml")));
            Element root = document.getDocumentElement();
            if (root.getNodeName().equals("PaymentGateways")) {
                NodeList nodes = root.getElementsByTagName("PaymentGateway");

                for(int i = 0; i < nodes.getLength(); i++) {
                    Element elem = (Element) nodes.item(i);
                    ConfigPaymentGatewayPanel panel = new ConfigPaymentGatewayPanel(parent, elem);
                    if(panel != null && panel.getParamCount() > 0) {
                        this.add(panel, panel.getGatewayName());
                    }
                }
            }
    }

    JFrame parent;
}
