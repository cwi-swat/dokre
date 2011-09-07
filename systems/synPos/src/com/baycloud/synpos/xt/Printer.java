package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;

import java.text.*;
import javax.swing.ImageIcon;

import jpos.JposConst;
import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.util.JposPropertiesConst;

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
public class Printer {
    public static final String ESC = ((char) 0x1b) + "";
    //public static final String LF = ( (char) 0x0a) + "";
    public static final String LF = ESC + "|lF";
    public static final String SPACES =
            "                                                                                                                                                                                                                                                        ";
    public static final String STARS =
            "**************************************************";
    public static final int TOTAL_WIDTH = 440;
    public static final int DEFAULT_WIDTH = 300;

    public static void print(Order order, String copy) throws Exception {
        if (!order.getPayment().getPaymentType().equals("Credit/Debit")) {
            throw new Exception("Not a credit card transcation.");
        }

        CreditPayment cp = (CreditPayment) order.getPayment();
        // instantiate a new jpos.POSPrinter object
        POSPrinter printer = new POSPrinter();

        try {
            String model = Configuration.get("printer.model");

            if (model != null) {
                // open the printer object according to the entry names defined in jpos.xml
                printer.open(model);
                // claim exclsive usage of the printer object
                printer.claim(1);

                // enable the device for input and output
                printer.setDeviceEnabled(true);

                // set map mode to metric - all dimensions specified in 1/100mm units
                //printer.setMapMode(POSPrinterConst.PTR_MM_METRIC); // unit = 1/100 mm - i.e. 1 cm = 10 mm = 10 * 100 units

                do {
                    // poll for printer status
                    //   a javax.swing based application would be best to both poll for status
                    //   AND register for asynchronous StatusUpdateEvent notification
                    //   see the JavaPOS specification for details on this

                    // check if the cover is open
                    if (printer.getCoverOpen() == true) {
                        throw new Exception("Printer cover is open");
                    }

                    // check if the printer is out of paper
                    if (printer.getRecEmpty() == true) {
                        throw new Exception("Printer is out of paper");
                    }

                    // being a transaction
                    //   transaction mode causes all output to be buffered
                    //   once transaction mode is terminated, the buffered data is
                    //   outputted to the printer in one shot - increased reliability
                    printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                             POSPrinterConst.PTR_TP_TRANSACTION);

                    String logo = StoreConfiguration.get("cc." + copy + ".logo");

                    if (logo != null && printer.getCapRecBitmap() == true) {
                        // print an image file
                        try {
                            printer.printBitmap(POSPrinterConst.PTR_S_RECEIPT,
                                                logo,
                                                POSPrinterConst.PTR_BM_ASIS,
                                                POSPrinterConst.PTR_BM_CENTER);
                        } catch (JposException ex) {
                            if (ex.getErrorCode() != JposConst.JPOS_E_NOEXIST) {
                                // error other than file not exist - propogate it
                                //throw ex;
                            }

                            // image file not found - ignore this error & proceed
                        }
                    }

                    // call printNormal repeatedly to generate out receipt
                    //   the following JavaPOS-POSPrinter control code sequences are used here
                    //   ESC + "|cA"          -> center alignment
                    //   ESC + "|4C"          -> double high double wide character printing
                    //   ESC + "|bC"          -> bold character printing
                    //   ESC + "|uC"          -> underline character printing
                    //   ESC + "|rA"          -> right alignment

                    String header = StoreConfiguration.get("cc." + copy +
                            ".header");

                    if (header != null) {
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            replaceFormat(header) + LF);
                        //String[] lines = header.split("\n");
                        //for (int i = 0; i < lines.length; i++) {
                        //String line = replaceFormat(lines[i]);
                        //printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, line + LF);
                        //}
                    }

                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(2);
                    nf.setMinimumFractionDigits(2);

                    String invLine = "";
                    String invoice = StoreConfiguration.get("cc." + copy +
                            ".body.order");
                    String formatStr = StoreConfiguration.get("cc." + copy +
                            ".body.time");
                    String time = "";
                    if (formatStr != null) {
                        DateFormat timeFormat = new SimpleDateFormat(formatStr);
                        time = timeFormat.format(order.getTime());
                    }

                    if (invoice != null) {
                        invLine += invoice + " " + order.getOrderId();
                    }

                    int emptyChars = printer.getRecLineChars() - invLine.length() -
                                     time.length();
                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                        invLine +
                                        SPACES.substring(0, emptyChars) + time +
                                        LF);

                    String cardLine = cp.getCardType();
                    String cardNumber = cp.getNumber();
                    cardNumber = STARS.substring(0, cardNumber.length() - 4) +
                                 cardNumber.substring(cardNumber.length() - 4);
                    emptyChars = printer.getRecLineChars() - cardLine.length() -
                                 cardNumber.length();
                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                        cardLine +
                                        SPACES.substring(0, emptyChars) +
                                        cardNumber + LF);

                    String authStr = StoreConfiguration.get("cc." + copy +
                            ".body.approval");

                    String authLine = (cp.getTrack1() == null && cp.getTrack2() == null) ?
                                      "Key-in" : "Swiped";
                    if (authStr != null) {
                        authStr += cp.getAuthCode();
                        emptyChars = printer.getRecLineChars() -
                                     authLine.length() -
                                     authStr.length();
                        authLine = authStr + SPACES.substring(0, emptyChars) +
                                   authLine;
                    }

                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                        authLine + LF);

                    String totalStr = StoreConfiguration.get("cc." + copy +
                            ".body.total");

                    if (totalStr != null) {
                        String totalLine = totalStr + nf.format(order.getTotal());
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            totalLine + LF);
                    }

                    if (copy.equals("store")) {
                        String sigStr = StoreConfiguration.get(
                                "cc.store.body.signature");
                        if (sigStr != null) {
                            if (sigStr.length() > printer.getRecLineChars()) {
                                sigStr = sigStr.substring(0,
                                        printer.getRecLineChars());
                            }
                            printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                                sigStr + LF);
                        }
                    }

                    /*
                              if (printer.getCapRecBarCode() == true) {
                                // print a Code 3 of 9 barcode with the data "123456789012" encoded
                                //   the 10 * 100, 60 * 100 parameters below specify the barcode's height and width
                                //   in the metric map mode (1cm tall, 6cm wide)
                     printer.printBarCode(POSPrinterConst.PTR_S_RECEIPT, order.getOrderId(),
                     POSPrinterConst.PTR_BCS_Code39, 10 * 100,
                     60 * 100, POSPrinterConst.PTR_BC_CENTER,
                     POSPrinterConst.PTR_BC_TEXT_BELOW);
                              }
                     */
                    String footer = StoreConfiguration.get("cc." + copy +
                            ".footer");

                    if (footer != null) {
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            replaceFormat(footer) + LF);
                    }

                    // exit our printing loop
                } while (false);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            // close the printer object
            try {
                // the ESC + "|100fP" control code causes the printer to execute a paper cut
                //   after feeding to the cutter position
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                    ESC + "|100fP");

                // terminate the transaction causing all of the above buffered data to be sent to the printer
                printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                         POSPrinterConst.PTR_TP_NORMAL);

                printer.close();
            } catch (Exception ex) {}
        }
    }

    public static void print(String[] report) throws Exception {
        // instantiate a new jpos.POSPrinter object
        POSPrinter printer = new POSPrinter();

        try {
            String model = Configuration.get("printer.model");

            if (model != null) {
                // open the printer object according to the entry names defined in jpos.xml
                printer.open(model);
                // claim exclsive usage of the printer object
                printer.claim(1);

                // enable the device for input and output
                printer.setDeviceEnabled(true);

                // set map mode to metric - all dimensions specified in 1/100mm units
                //printer.setMapMode(POSPrinterConst.PTR_MM_METRIC); // unit = 1/100 mm - i.e. 1 cm = 10 mm = 10 * 100 units

                do {
                    // poll for printer status
                    //   a javax.swing based application would be best to both poll for status
                    //   AND register for asynchronous StatusUpdateEvent notification
                    //   see the JavaPOS specification for details on this

                    // check if the cover is open
                    if (printer.getCoverOpen() == true) {
                        throw new Exception("Printer cover is open");
                    }

                    // check if the printer is out of paper
                    if (printer.getRecEmpty() == true) {
                        throw new Exception("Printer is out of paper");
                    }

                    // being a transaction
                    //   transaction mode causes all output to be buffered
                    //   once transaction mode is terminated, the buffered data is
                    //   outputted to the printer in one shot - increased reliability
                    printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                             POSPrinterConst.PTR_TP_TRANSACTION);

                    for (int i = 0; i < report.length; i++) {
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            report[i] + LF);
                    }

                    // exit our printing loop
                } while (false);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            // close the printer object
            try {
                // the ESC + "|100fP" control code causes the printer to execute a paper cut
                //   after feeding to the cutter position
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                    ESC + "|100fP");

                // terminate the transaction causing all of the above buffered data to be sent to the printer
                printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                         POSPrinterConst.PTR_TP_NORMAL);

                printer.close();
            } catch (Exception ex) {}
        }

    }

    public static void print(Order order) throws Exception {
        // instantiate a new jpos.POSPrinter object
        POSPrinter printer = new POSPrinter();

        try {
            String model = Configuration.get("printer.model");

            if (model != null) {
                // open the printer object according to the entry names defined in jpos.xml
                printer.open(model);
                // claim exclsive usage of the printer object
                printer.claim(1);

                // enable the device for input and output
                printer.setDeviceEnabled(true);

                // set map mode to metric - all dimensions specified in 1/100mm units
                //printer.setMapMode(POSPrinterConst.PTR_MM_METRIC); // unit = 1/100 mm - i.e. 1 cm = 10 mm = 10 * 100 units

                do {
                    // poll for printer status
                    //   a javax.swing based application would be best to both poll for status
                    //   AND register for asynchronous StatusUpdateEvent notification
                    //   see the JavaPOS specification for details on this

                    // check if the cover is open
                    if (printer.getCoverOpen() == true) {
                        throw new Exception("Printer cover is open");
                    }

                    // check if the printer is out of paper
                    if (printer.getRecEmpty() == true) {
                        throw new Exception("Printer is out of paper");
                    }

                    // being a transaction
                    //   transaction mode causes all output to be buffered
                    //   once transaction mode is terminated, the buffered data is
                    //   outputted to the printer in one shot - increased reliability
                    printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                             POSPrinterConst.PTR_TP_TRANSACTION);

                    String logo = StoreConfiguration.get("receipt.logo");

                    if (logo != null && printer.getCapRecBitmap() == true) {
                        // print an image file
                        try {
                            //ImageIcon icon = new ImageIcon(logo);
                            //int width = (int) 1.0 * icon.getIconWidth() / TOTAL_WIDTH * printer.getRecLineWidth();
                            printer.printBitmap(POSPrinterConst.PTR_S_RECEIPT,
                                                logo,
                                                POSPrinterConst.PTR_BM_ASIS,
                                                POSPrinterConst.PTR_BM_CENTER);
                        } catch (JposException ex) {
                            if (ex.getErrorCode() != JposConst.JPOS_E_NOEXIST) {
                                // error other than file not exist - propogate it
                                //throw ex;
                            }
                            // image file not found - ignore this error & proceed
                        }
                    }

                    // call printNormal repeatedly to generate out receipt
                    //   the following JavaPOS-POSPrinter control code sequences are used here
                    //   ESC + "|cA"          -> center alignment
                    //   ESC + "|4C"          -> double high double wide character printing
                    //   ESC + "|bC"          -> bold character printing
                    //   ESC + "|uC"          -> underline character printing
                    //   ESC + "|rA"          -> right alignment

                    String header = StoreConfiguration.get("receipt.header");

                    if (header != null) {
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            replaceFormat(header) + LF);
                        //String[] lines = header.split("\n");
                        //for (int i = 0; i < lines.length; i++) {
                        //String line = replaceFormat(lines[i]);
                        //printer.printNormal(POSPrinterConst.PTR_S_RECEIPT, line + LF);
                        //}
                    }
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(2);
                    nf.setMinimumFractionDigits(2);

                    String invLine = "";
                    String invoice = StoreConfiguration.get(
                            "receipt.body.order");
                    String formatStr = StoreConfiguration.get(
                            "receipt.body.time");
                    String time = "";
                    if (formatStr != null) {
                        DateFormat timeFormat = new SimpleDateFormat(formatStr);
                        time = timeFormat.format(order.getTime());
                    }

                    if (invoice != null) {
                        invLine += invoice + " " + order.getOrderId();
                    }

                    int emptyChars = printer.getRecLineChars() - invLine.length() -
                                     time.length();
                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                        invLine +
                                        SPACES.substring(0, emptyChars) + time +
                                        LF);

                    OrderProduct[] products = order.getProducts();
                    String divider = StoreConfiguration.get(
                            "receipt.body.divider");
                    int width = DEFAULT_WIDTH;

                    if (divider != null) {
                        try {
                            width = Integer.parseInt(divider);
                        } catch (Exception ex) {
                        }
                    }

                    width = (int) (1.0 * width / TOTAL_WIDTH *
                                   printer.getRecLineChars());

                    for (int i = 0; i < products.length; i++) {
                        String quantity = products[i].getQuantity() + "@";
                        String prodLine = products[i].getBarcode();
                        prodLine +=
                                SPACES.substring(0,
                                                 width - prodLine.length() -
                                                 quantity.length());
                        prodLine += quantity;
                        String price = nf.format(products[i].getPrice());
                        prodLine +=
                                SPACES.substring(0,
                                                 printer.getRecLineChars() -
                                                 width - price.length());
                        prodLine += price;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            prodLine + LF);
                        prodLine = products[i].getDescription();
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            prodLine + LF);
                    }

                    double sub = order.getTotal() - order.getTax();

                    String subStr = StoreConfiguration.get(
                            "receipt.body.subtotal");
                    String totalStr = StoreConfiguration.get(
                            "receipt.body.total");
                    String taxStr = StoreConfiguration.get("receipt.body.tax");
                    String paidStr = StoreConfiguration.get("receipt.body.paid");
                    String changeStr = StoreConfiguration.get(
                            "receipt.body.change");

                    if (subStr != null) {
                        String value = nf.format((order.getTotal() -
                                                  order.getTax()));
                        String subLine = SPACES.substring(0,
                                width - subStr.length()) +
                                         subStr + SPACES.substring(0,
                                printer.getRecLineChars() - width -
                                value.length()) + value;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            subLine + LF);
                    }
                    if (taxStr != null) {
                        String value = nf.format(order.getTax());
                        String taxLine = SPACES.substring(0,
                                width - taxStr.length()) +
                                         taxStr + SPACES.substring(0,
                                printer.getRecLineChars() - width -
                                value.length()) + value;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            taxLine + LF);
                    }
                    if (totalStr != null) {
                        String value = nf.format(order.getTotal());
                        String totalLine = SPACES.substring(0,
                                width - totalStr.length()) +
                                           totalStr + SPACES.substring(0,
                                printer.getRecLineChars() - width -
                                value.length()) + value;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            totalLine + LF);
                    }

                    Payment pay = order.getPayment();
                    String payLine = pay.getPaymentType();

                    if (pay.getPaymentType().equals("Check")) {
                        payLine += " #" + ((CheckPayment) pay).getNumber();
                    } else if (pay.getPaymentType().equals("Credit/Debit")) {
                        CreditPayment cp = (CreditPayment) pay;
                        String cardNumber = cp.getNumber();
                        cardNumber = STARS.substring(0, cardNumber.length() - 4) +
                                     cardNumber.substring(cardNumber.length() -
                                4);
                        payLine = cp.getCardType() + " " + cardNumber;
                    }
                    printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                        payLine + LF);

                    if (paidStr != null) {
                        String value = nf.format(order.getTotal());

                        if (pay.getPaymentType().equals("Cash")) {
                            value = ((CashPayment) pay).getPaid() + "";
                        }

                        String paidLine = SPACES.substring(0,
                                width - paidStr.length()) +
                                          paidStr + SPACES.substring(0,
                                printer.getRecLineChars() - width -
                                value.length()) + value;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            paidLine + LF);
                    }

                    if (changeStr != null) {
                        String value = "0.00";
                        //Payment pay = order.getPayment();
                        if (pay.getPaymentType().equals("Cash")) {
                            value = nf.format(((CashPayment) pay).getChange());
                        }

                        String changeLine = SPACES.substring(0,
                                width - changeStr.length()) +
                                            changeStr + SPACES.substring(0,
                                printer.getRecLineChars() - width -
                                value.length()) + value;
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            changeLine + LF);
                    }
                    /*
                              if (printer.getCapRecBarCode() == true) {
                                // print a Code 3 of 9 barcode with the data "123456789012" encoded
                                //   the 10 * 100, 60 * 100 parameters below specify the barcode's height and width
                                //   in the metric map mode (1cm tall, 6cm wide)
                     printer.printBarCode(POSPrinterConst.PTR_S_RECEIPT, order.getOrderId(),
                     POSPrinterConst.PTR_BCS_Code39, 10 * 100,
                     60 * 100, POSPrinterConst.PTR_BC_CENTER,
                     POSPrinterConst.PTR_BC_TEXT_BELOW);
                              }
                     */
                    String footer = StoreConfiguration.get("receipt.footer");

                    if (footer != null) {
                        printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                            replaceFormat(footer) + LF);
                    }

                    // exit our printing loop
                } while (false);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            // close the printer object
            try {
                // the ESC + "|100fP" control code causes the printer to execute a paper cut
                //   after feeding to the cutter position
                printer.printNormal(POSPrinterConst.PTR_S_RECEIPT,
                                    ESC + "|100fP");

                // terminate the transaction causing all of the above buffered data to be sent to the printer
                printer.transactionPrint(POSPrinterConst.PTR_S_RECEIPT,
                                         POSPrinterConst.PTR_TP_NORMAL);
                printer.close();
            } catch (Exception ex) {}
        }
    }

    public static String replaceFormat(String str) {
        if (str == null) {
            return null;
        }

        str = str.replaceAll("\\|", ESC + "|");
        str = str.replaceAll(ESC + "\\|" + ESC + "\\|", "|");
        str = str.replaceAll("\n", LF);
        return str;
    }
}
