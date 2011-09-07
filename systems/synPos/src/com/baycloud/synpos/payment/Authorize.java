package com.baycloud.synpos.payment;

import com.baycloud.synpos.util.HTTP;

import jpay.*;

import org.w3c.dom.Document;
import java.net.URLEncoder;
import org.w3c.dom.Node;
import java.io.StringReader;
import org.w3c.dom.Element;
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
public class Authorize implements CreditCardGateway {
    public static final String MARKET_TYPE = "2";
    private String url;
    private String deviceType;
    private String login;
    private String transactionKey;
    private String testMode;
    private String transactionType;

    public Authorize(String url, String login, String transactionKey,
                     String deviceType, String transactionType, String testMode) {
        this.url = url;
        this.login = login;
        this.transactionKey = transactionKey;
        this.deviceType = deviceType;
        this.transactionType = transactionType;
        this.testMode = testMode;
    }

    public PaymentTransaction chargeCreditCard(double amount,
                                               String orderId,
                                               CreditCard card) throws
            PaymentException {

        try {
            String content = "x_cpversion=" + URLEncoder.encode("1.0", "UTF-8") +
                             "&x_login=" + URLEncoder.encode(login, "UTF-8") +
                             "&x_market_type=" +
                             URLEncoder.encode(MARKET_TYPE, "UTF-8") +
                             "&x_device_type=" +
                             URLEncoder.encode(deviceType, "UTF-8") +
                             "&x_tran_key=" +
                             URLEncoder.encode(transactionKey, "UTF-8") +
                             "&x_invoice_num=" + orderId;

            if (card.getTrack2() != null) {
                content += "&x_track2=" +
                        URLEncoder.encode(card.getTrack2(), "UTF-8");
            } else if (card.getTrack1() != null) {
                content += "&x_track1=" +
                        URLEncoder.encode(card.getTrack1(), "UTF-8");
            } else {
                content += "&x_card_num=" +
                        URLEncoder.encode(card.getAccountNumber(), "UTF-8");
                content += "&x_exp_date=" +
                        URLEncoder.encode(card.getExpireMonth() + "" +
                                          card.getExpireYear(), "UTF-8");
            }

            content += "&x_amount=" + URLEncoder.encode("" + amount, "UTF-8") +
                    "&x_type=" + URLEncoder.encode(transactionType, "UTF-8") +
                    "&x_test_request=" + URLEncoder.encode(testMode, "UTF-8") +
                    "&x_response_format=" + URLEncoder.encode("0", "UTF-8");

            String response = HTTP.post(url, content, "form", null, null);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.
                                      newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(
                    response)));
            Element root = document.getDocumentElement();
            if (root.getNodeName().equals("response")) {
                NodeList nodes = root.getElementsByTagName("ResponseCode");

                if (nodes.getLength() > 0) {
                    Node respNode = nodes.item(0).getFirstChild();
                    if (respNode != null) {
                        String respCode = respNode.getNodeValue();
                        if (respCode != null) {
                            if (respCode.equals("1")) {
                                String authCode = null;
                                String transactionId = null;
                                String referenceNumber = null;
                                nodes = root.getElementsByTagName("AuthCode");

                                if (nodes.getLength() > 0) {
                                    Node authNode = nodes.item(0).getFirstChild();
                                    if (authNode != null) {
                                        authCode = authNode.getNodeValue();
                                        //System.out.println(authCode);
                                    }
                                }

                                nodes = root.getElementsByTagName("TransID");

                                if (nodes.getLength() > 0) {
                                    Node transidNode = nodes.item(0).
                                            getFirstChild();
                                    if (transidNode != null) {
                                        transactionId = transidNode.
                                                getNodeValue();
                                        //System.out.println(transId);
                                    }
                                }

                                return new PaymentTransaction(respCode,
                                        authCode,
                                        transactionId, orderId);
                            } else {
                                nodes = root.getElementsByTagName("ErrorText");
                                String errorText = null;

                                if (nodes.getLength() > 0) {
                                    Node errorNode = nodes.item(0).
                                            getFirstChild();
                                    if (errorNode != null) {
                                        errorText = errorNode.
                                                getNodeValue();
                                    }
                                }

                                throw new PaymentException(errorText);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new PaymentException("Unknown");
    }

    public PaymentTransaction refundCreditCard(double amount,
                                               String orderId,
                                               String accountNumber,
                                               String transactionId) throws
            PaymentException {

        try {
            return refund(amount, orderId, accountNumber,
                          transactionId, "VOID"); //type = "CREDIT";
        } catch (PaymentException e) {

        }
        return refund(amount, orderId, accountNumber, transactionId,
                      "CREDIT");
    }

    private PaymentTransaction refund(double amount,
                                      String orderId,
                                      String accountNumber,
                                      String transactionId,
                                      String type) throws PaymentException {
        try {
            String content = "x_cpversion=" + URLEncoder.encode("1.0", "UTF-8") +
                             "&x_login=" + URLEncoder.encode(login, "UTF-8") +
                             "&x_market_type=" +
                             URLEncoder.encode(MARKET_TYPE, "UTF-8") +
                             "&x_device_type=" +
                             URLEncoder.encode(deviceType, "UTF-8") +
                             "&x_tran_key=" +
                             URLEncoder.encode(transactionKey, "UTF-8") +
                             "&x_ref_trans_id=" +
                             URLEncoder.encode(transactionId, "UTF-8") +
                             "&x_card_num=" +
                             URLEncoder.encode(accountNumber, "UTF-8") +
                             "&x_amount=" +
                             URLEncoder.encode(amount + "", "UTF-8") +
                             "&x_type=" + URLEncoder.encode(type, "UTF-8") +
                             "&x_test_request=" +
                             URLEncoder.encode(testMode, "UTF-8") +
                             "&x_response_format=" +
                             URLEncoder.encode("0", "UTF-8");
            String response = HTTP.post(url, content, "form", null, null);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.
                                      newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(
                    response)));
            Element root = document.getDocumentElement();
            if (root.getNodeName().equals("response")) {
                NodeList nodes = root.getElementsByTagName("ResponseCode");

                if (nodes.getLength() > 0) {
                    Node respNode = nodes.item(0).getFirstChild();
                    if (respNode != null) {
                        String respCode = respNode.getNodeValue();
                        if (respCode != null) {
                            if (respCode.equals("1")) {
                                String authCode = null;
                                String transId = null;

                                nodes = root.getElementsByTagName("AuthCode");

                                if (nodes.getLength() > 0) {
                                    Node authNode = nodes.item(0).getFirstChild();
                                    if (authNode != null) {
                                        authCode = authNode.getNodeValue();
                                    }
                                }
                                nodes = root.getElementsByTagName("TransID");

                                if (nodes.getLength() > 0) {
                                    Node transidNode = nodes.item(0).
                                            getFirstChild();
                                    if (transidNode != null) {
                                        transId = transidNode.
                                                  getNodeValue();
                                    }
                                }

                                return new PaymentTransaction(respCode,
                                        authCode,
                                        transId, orderId);
                            } else {
                                nodes = root.getElementsByTagName("ErrorText");

                                if (nodes.getLength() > 0) {
                                    Node errorNode = nodes.item(0).
                                            getFirstChild();
                                    if (errorNode != null) {
                                        String errorText = errorNode.
                                                getNodeValue();
                                        throw new PaymentException(errorText);
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         throw new PaymentException("Unknown");
    }
}
