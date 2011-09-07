package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;
import com.baycloud.synpos.payment.*;

import jpay.*;

import java.util.*;
import java.lang.reflect.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.io.*;
import java.net.*;
import org.w3c.dom.*;

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
public class GatewayManager {
    public static final String[] ACCEPTED_CARDS = new String[] {"CreditCard"};
    public static final String[] ACCEPTED_CARDS_NAMES = new String[] {
            "Credit Card"};

    private static Hashtable gateways = new Hashtable();

    public static String getCardName(String cardId) {
        for (int i = 0; i < ACCEPTED_CARDS.length; i++) {
            if (ACCEPTED_CARDS[i].equals(cardId)) {
                return ACCEPTED_CARDS_NAMES[i];
            }
        }

        return null;
    }

    public static CreditCardGateway getCreditCardGateway() {
        CreditCardGateway gateway = (CreditCardGateway) gateways.get(
                "gateway.creditcard");

        if (gateway == null) {
            try {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        FileReader(
                                "config/jpay.xml")));
                Element root = document.getDocumentElement();

                if (root.getNodeName().equals("PaymentGateways")) {
                    NodeList nodes = root.getElementsByTagName("PaymentGateway");

                    for (int i = 0; i < nodes.getLength(); i++) {
                        Element elem = (Element) nodes.item(i);
                        NodeList idNodes = elem.getElementsByTagName(
                                "GatewayId");
                        String gatewayId = idNodes.item(0).getFirstChild().
                                           getNodeValue();
                        NodeList nameNodes = elem.getElementsByTagName(
                                "GatewayName");
                        String gatewayName = nameNodes.item(0).getFirstChild().
                                             getNodeValue();
                        NodeList classNodes = elem.getElementsByTagName(
                                "ClassName");
                        String className = classNodes.item(0).getFirstChild().
                                           getNodeValue();
                        String value = StoreConfiguration.get(
                                "payment.gateway." +
                                gatewayId +
                                ".parameter.acceptedcards.CreditCard");

                        if (value != null) {
                            try {
                                NodeList paramsNodes = elem.
                                        getElementsByTagName(
                                                "ClassParameter");
                                Class[] strArgsClass = new Class[paramsNodes.
                                        getLength()];
                                Object[] strArgs = new Object[paramsNodes.
                                        getLength()];

                                for (int j = 0; j < paramsNodes.getLength(); j++) {
                                    Element paramElem = (Element) paramsNodes.
                                            item(
                                            j);
                                    NodeList paramIdNodes = paramElem.
                                            getElementsByTagName(
                                            "ParameterId");
                                    String paramId = paramIdNodes.item(0).
                                            getFirstChild().getNodeValue();

                                    strArgsClass[j] = String.class;
                                    strArgs[j] = StoreConfiguration.get(
                                            "payment.gateway." + gatewayId +
                                            ".parameter." + paramId);
                                }

                                Class gatewayClass = Class.forName(className);
                                Constructor gatewayConstructor = gatewayClass.
                                        getConstructor(strArgsClass);
                                gateway = (CreditCardGateway)
                                          gatewayConstructor.
                                          newInstance(strArgs);
                                gateways.put("gateway.creditcard", gateway);
                                break;
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return gateway;
    }

    public static void chargeCreditCard(double amount, int orderId,
                                        CreditPayment payment) throws
            Exception {

        CreditCardGateway gateway = getCreditCardGateway();

        if (gateway == null) {
            throw new Exception("No payment gateway available");
        } else {
            CreditCard card = payment.getCreditCard();
            PaymentTransaction resp = gateway.chargeCreditCard(amount,
                    orderId + "", card);
            payment.setAuthCode(resp.getAuthCode());
            payment.setTransId(resp.getTransactionId());
        }
    }

    public static void refundCreditCard(double amount, int orderId,
                                        CreditPayment payment) throws
            Exception {
        CreditCardGateway gateway = getCreditCardGateway();

        if (gateway == null) {
            throw new Exception("No payment gateway available");
        } else {
            PaymentTransaction resp = gateway.refundCreditCard(amount,
                    orderId + "", payment.getNumber(), payment.getTransId());

            payment.setAuthCode(resp.getAuthCode());
            payment.setTransId(resp.getTransactionId());
        }
    }
}
