package com.baycloud.synpos.payment;

import com.baycloud.synpos.xt.*;
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
public class EFSNet implements CreditCardGateway {
    private String url;
    private String storeId;
    private String storeKey;

    public EFSNet(String url, String storeId, String storeKey) {
        this.url = url;
        this.storeId = storeId;
        this.storeKey = storeKey;
    }

    public PaymentTransaction chargeCreditCard(double amount,
                                               String orderId,
                                               CreditCard card) throws
            PaymentException {

        try {
            String appId = "synPOS";

            String request = "<Request StoreID=\"" + storeId + "\" StoreKey=\"" +
                             storeKey + "\" ApplicationID=\"" + appId + "\">";
            request += "<CreditCardCharge>";
            request += "<ReferenceNumber>" + orderId + "</ReferenceNumber>";
            request += "<TransactionAmount>" + amount + "</TransactionAmount>";
            if (card.getTrack2() != null) {
                request += "<Track2>" + card.getTrack2() + "</Track2>";
            } else if (card.getTrack1() != null) {
                request += "<Track1>" + card.getTrack1() + "</Track1>";
            } else {
                request += "<AccountNumber>" + card.getAccountNumber() +
                        "</AccountNumber>";
                request += "<ExpirationMonth>" + card.getExpireMonth() +
                        "</ExpirationMonth>";
                request += "<ExpirationYear>" +
                        card.getExpireYear().substring(2) +
                        "</ExpirationYear>";
            }
            request += "</CreditCardCharge>";
            request += "</Request>";
            //System.out.print(request);
            String response = Synchronizer.post(request);
            return null; //TODO
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
        throw new PaymentException("Unknown");
    }
}
