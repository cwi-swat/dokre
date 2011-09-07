package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;

import org.w3c.dom.Document;
import java.io.StringReader;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NodeList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Date;

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
public class ERPClient {
    private static final String POSXML_VERSION = "1.0.0";
    private static final String XML_HEADER =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

    public static String addOrder(Order order) {
        try {
            if (order.isSynchronized()) {
                return null;
            }

            String request = "";

            OrderProduct[] products = order.getProducts();

            for (int i = 0; i < products.length; i++) {
                request += "<Product>";
                request += "<ProductId>" + products[i].getCode() +
                        "</ProductId>";
                request += "<Quantity>" + products[i].getQuantity() +
                        "</Quantity>";
                request += "</Product>";

            }

            Customer customer = order.getCustomer();

            if (customer != null) {
                String custCode = customer.getCode();

                if (custCode == null || custCode.equals("")) {
                    custCode = addCustomer(customer);
                } else {
                    custCode = updateCustomer(customer);
                }

                if (custCode != null && !custCode.equals("")) {
                    customer.setCode(custCode);
                    customer.save();
                    request += "<CustomerId>" + custCode +
                            "</CustomerId>";
                } else {
                    return null;
                }
            } else {
                return null;
            }

            request += "<SubTotal>" + (order.getTotal() - order.getTax()) +
                    "</SubTotal>";
            request += "<Tax>" + order.getTax() + "</Tax>";
            request += "<Total>" + order.getTotal() + "</Total>";
            request = "<AddOrderRequest Version=\"" + POSXML_VERSION + "\">" +
                      request + "</AddOrderRequest>";
            String docType =
                    "<!DOCTYPE AddOrderRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/AddOrderRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new
                    StringReader(
                            response)));
            Element root = document.getDocumentElement();

            if (root.getNodeName().equals("AddOrderResponse")) {
                NodeList ids = root.getElementsByTagName("OrderId");
                if (ids.getLength() > 0) {
                    Element id = (Element) ids.item(0);
                    if (id.getFirstChild() != null) {
                        return id.getFirstChild().getNodeValue();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean cancelOrder(Order order) {
        try {
            String orderCode = order.getCode();

            if (order.isSynchronized() || orderCode == null ||
                orderCode.equals("")) {
                return false;
            }

            String request = "<CancelOrderRequest Version=\"" + POSXML_VERSION +
                             "\"><OrderId>" + orderCode +
                             "</OrderId></CancelOrderRequest>";

            String docType =
                    "<!DOCTYPE CancelOrderRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/CancelOrderRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);

            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new
                    StringReader(
                            response)));
            Element root = document.getDocumentElement();

            if (root.getNodeName().equals("CancelOrderResponse")) {
                NodeList ids = root.getElementsByTagName("OrderId");
                if (ids.getLength() > 0) {
                    Element id = (Element) ids.item(0);
                    if (id.getFirstChild() != null) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Product getProduct(String prodId) {
        try {
            String request = "<GetProductRequest Version=\"" + POSXML_VERSION +
                             "\"><ProductId>" + prodId +
                             "</ProductId></GetProductRequest>";

            String docType =
                    "<!DOCTYPE GetProductRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/GetProductRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();
                if (root.getNodeName().equals("GetProductResponse")) {
                    NodeList nodes = root.getChildNodes();

                    for (int m = 0; m < nodes.getLength(); m++) {
                        if (nodes.item(m).getNodeName().equals("Product")) {
                            NodeList pdNodes = nodes.item(m).getChildNodes();

                            Hashtable temp = new Hashtable();

                            for (int j = 0; j < pdNodes.getLength(); j++) {
                                if (pdNodes.item(j).getNodeName().equals(
                                        "ProductId") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("code",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Barcode") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("barcode",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Name") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("desc",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Quantity") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("quantity",
                                                 new Integer(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Price") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("price",
                                                 new Double(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Tax") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("tax",
                                                 new Double(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                return new Product((String) temp.get(
                                        "code"), (String) temp.get(
                                                "barcode"),
                                        (String) temp.get("desc"),
                                        ((Double) temp.get("price")).
                                        doubleValue(),
                                        ((Double) temp.get("tax")).
                                        doubleValue(),
                                        ((Integer) temp.get("quantity")).
                                        intValue());
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Product[] searchProduct(String barcode, String name,
                                          String desc) {
        try {
            Vector vec = new Vector();
            String request = "<SearchProductRequest Version=\"" +
                             POSXML_VERSION +
                             "\">";

            if (barcode != null) {
                request += "<Barcode>" + barcode + "</Barcode>";
            }

            if (name != null) {
                request += "<Name>" + name + "</Name>";
            }

            if (desc != null) {
                request += "<Description>" + desc + "</Description>";
            }

            request += "</SearchProductRequest>";

            String docType =
                    "<!DOCTYPE SearchProductRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/SearchProductRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();
                if (root.getNodeName().equals("SearchProductResponse")) {
                    NodeList nodes = root.getChildNodes();

                    for (int m = 0; m < nodes.getLength(); m++) {
                        if (nodes.item(m).getNodeName().equals("Product")) {
                            NodeList pdNodes = nodes.item(m).getChildNodes();

                            Hashtable temp = new Hashtable();

                            for (int j = 0; j < pdNodes.getLength(); j++) {
                                if (pdNodes.item(j).getNodeName().equals(
                                        "ProductId") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("code",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Barcode") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("barcode",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Name") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("desc",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Quantity") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("quantity",
                                                 new Integer(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Price") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("price",
                                                 new Double(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Tax") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("tax",
                                                 new Double(pdNodes.item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }
                            }

                            vec.add(new Product((String) temp.get(
                                    "code"), (String) temp.get(
                                            "barcode"),
                                                (String) temp.get("desc"),
                                                ((Double) temp.get("price")).
                                                doubleValue(),
                                                ((Double) temp.get("tax")).
                                                doubleValue(),
                                                ((Integer) temp.get("quantity")).
                                                intValue()));

                        }
                    }

                    if (vec.size() > 0) {
                        Product[] products = new Product[vec.size()];

                        for (int i = 0; i < products.length; i++) {
                            products[i] = (Product) vec.elementAt(i);
                        }

                        return products;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Vector getCategory(Category cat) {
        Vector vec = new Vector();

        try {
            String request = "<GetCategoryRequest Version=\"" + POSXML_VERSION +
                             "\"><CategoryId>" + cat.getCode() +
                             "</CategoryId></GetCategoryRequest>";

            String docType =
                    "<!DOCTYPE GetCategoryRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/GetCategoryRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();
                if (root.getNodeName().equals("GetCategoryResponse")) {
                    NodeList nodes = root.getChildNodes();
                    for (int n = 0; n < nodes.getLength(); n++) {
                        if (nodes.item(n).getNodeName().
                            equals("Product")) {
                            NodeList pdNodes = nodes.item(n).
                                               getChildNodes();

                            Hashtable temp = new Hashtable();

                            for (int j = 0;
                                         j < pdNodes.getLength(); j++) {
                                if (pdNodes.item(j).getNodeName().
                                    equals("ProductId") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    temp.put("code",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals("Barcode") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    temp.put("barcode",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals(
                                            "Name") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    temp.put("desc",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals("Quantity") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    try {
                                        temp.put("quantity",
                                                 new
                                                 Integer(pdNodes.
                                                item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals("Price") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    try {
                                        temp.put("price",
                                                 new
                                                 Double(pdNodes.
                                                item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals("Tax") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    try {
                                        temp.put("tax",
                                                 new
                                                 Double(pdNodes.
                                                item(j).
                                                getFirstChild().
                                                getNodeValue()));
                                    } catch (Exception ex) {
                                    }
                                }
                            }

                            vec.add(new Product(temp.get("code").
                                                toString(), temp.get("barcode").
                                                toString(),
                                                temp.get("desc").toString(),
                                                Double.
                                                parseDouble(temp.get("price").
                                    toString()),
                                                Double.parseDouble(temp.get(
                                    "tax").
                                    toString()),
                                                Integer.parseInt(temp.
                                    get("quantity").toString())));

                        } else if (nodes.item(n).getNodeName().equals(
                                "Category")) {
                            NodeList pdNodes = nodes.item(n).
                                               getChildNodes();
                            String id = "";
                            String name = "";
                            String desc = "";

                            for (int j = 0;
                                         j < pdNodes.getLength(); j++) {
                                if (pdNodes.item(j).getNodeName().
                                    equals("CategoryId") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    id = pdNodes.item(j).
                                         getFirstChild().
                                         getNodeValue();

                                }

                                if (pdNodes.item(j).getNodeName().
                                    equals("Name") &&
                                    pdNodes.item(j).
                                    getFirstChild() != null) {
                                    name = pdNodes.item(j).
                                           getFirstChild().
                                           getNodeValue();

                                }
                            }

                            vec.add(new Category(cat.getId(), id, name));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vec;
    }

    public static Customer[] searchCustomer(String firstname, String lastname,
                                            String title, String birthdate,
                                            String address1, String address2,
                                            String city, String state,
                                            String zip, String country,
                                            String phone, String fax,
                                            String email) {
        try {
            Vector vec = new Vector();
            String request = "<SearchCustomerRequest Version=\"" +
                             POSXML_VERSION +
                             "\">";

            if (firstname != null) {
                request += "<FirstName>" + firstname + "</FirstName>";
            }
            if (lastname != null) {
                request += "<LastName>" + lastname + "</LastName>";
            }

            if (title != null) {
                request += "<Title>" + title + "</Title>";
            }
            if (birthdate != null) {
                request += "<BithDate>" + birthdate + "</BirthDate>";
            }
            if (address1 != null) {
                request += "<Street1>" + address1 + "</Street1>";
            }
            if (address2 != null) {
                request += "<Street2>" + address2 + "</Street2>";
            }
            if (city != null) {
                request += "<City>" + city + "</City>";
            }
            if (state != null) {
                request += "<State>" + state + "</State>";
            }
            if (zip != null) {
                request += "<Zip>" + zip + "</Zip>";
            }
            if (country != null) {
                request += "<Country>" + country + "</Country>";
            }
            if (phone != null) {
                request += "<Phone>" + phone + "</Phone>";
            }

            if (fax != null) {
                request += "<Fax>" + fax + "</Fax>";
            }
            if (email != null) {
                request += "<Email>" + email + "</Email>";
            }

            request += "</SearchCustomerRequest>";

            String docType =
                    "<!DOCTYPE SearchCustomerRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/SearchCustomerRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();
                if (root.getNodeName().equals("SearchCustomerResponse")) {
                    NodeList nodes = root.getChildNodes();

                    for (int m = 0; m < nodes.getLength(); m++) {
                        if (nodes.item(m).getNodeName().equals("Customer")) {
                            NodeList pdNodes = nodes.item(m).getChildNodes();

                            Hashtable temp = new Hashtable();

                            for (int j = 0; j < pdNodes.getLength(); j++) {

                                if (pdNodes.item(j).getNodeName().equals(
                                        "CustomerId") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("code",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "FirstName") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("first_name",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "LastName") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("last_name",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Title") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("title",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "BirthDate") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("BirthDate",
                                                 pdNodes.item(j).
                                                 getFirstChild().
                                                 getNodeValue());
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Street1") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("address1",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Street2") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("address2",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "City") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("city",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "State") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("state",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Zip") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("zip",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Phone") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("phone",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Fax") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("fax",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Email") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("email",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                vec.add(new Customer((String) temp.get(
                                        "code"), (String) temp.get(
                                                "first_name"),
                                        (String) temp.get("last_name"),
                                        (String) temp.get("title"),
                                        (String) temp.get("birth_date"),
                                        (String) temp.get("address1"),
                                        (String) temp.get("address2"),
                                        (String) temp.get("city"),
                                        (String) temp.get("state"),
                                        (String) temp.get("zip"),
                                        (String) temp.get("phone"),
                                        (String) temp.get("fax"),
                                        (String) temp.get("email"),
                                        new Date(),
                                        new Date()));
                            }
                        }
                    }
                }

            }

            Customer[] customers = new Customer[vec.size()];

            for (int i = 0; i < vec.size(); i++) {
                customers[i] = (Customer) vec.elementAt(i);
            }

            return customers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Customer getCustomer(String custId) {
        try {
            String request = "<GetCustomerRequest Version=\"" + POSXML_VERSION +
                             "\"><CustomerId>" + custId +
                             "</CustomerId></GetCustomerRequest>";

            String docType =
                    "<!DOCTYPE GetCustomerRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/GetCustomerRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();
                if (root.getNodeName().equals("GetCustomerResponse")) {
                    NodeList nodes = root.getChildNodes();

                    for (int m = 0; m < nodes.getLength(); m++) {
                        if (nodes.item(m).getNodeName().equals("Customer")) {
                            NodeList pdNodes = nodes.item(m).getChildNodes();

                            Hashtable temp = new Hashtable();

                            for (int j = 0; j < pdNodes.getLength(); j++) {

                                if (pdNodes.item(j).getNodeName().equals(
                                        "CustomerId") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("code",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "FirstName") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("first_name",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "LastName") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("last_name",
                                             pdNodes.item(j).getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Title") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("title",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "BirthDate") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    try {
                                        temp.put("BirthDate",
                                                 pdNodes.item(j).
                                                 getFirstChild().
                                                 getNodeValue());
                                    } catch (Exception ex) {
                                    }
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Street1") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("address1",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "Street2") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("address2",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                if (pdNodes.item(j).getNodeName().equals(
                                        "City") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("city",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "State") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("state",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Zip") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("zip",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Phone") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("phone",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Fax") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("fax",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }
                                if (pdNodes.item(j).getNodeName().equals(
                                        "Email") &&
                                    pdNodes.item(j).getFirstChild() != null) {
                                    temp.put("email",
                                             pdNodes.item(j).
                                             getFirstChild().
                                             getNodeValue());
                                }

                                return new Customer((String) temp.get(
                                        "code"), (String) temp.get(
                                                "first_name"),
                                        (String) temp.get("last_name"),
                                        (String) temp.get("title"),
                                        (String) temp.get("birth_date"),
                                        (String) temp.get("address1"),
                                        (String) temp.get("address2"),
                                        (String) temp.get("city"),
                                        (String) temp.get("state"),
                                        (String) temp.get("zip"),
                                        (String) temp.get("phone"),
                                        (String) temp.get("fax"),
                                        (String) temp.get("email"),
                                        new Date(),
                                        new Date());
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String addCustomer(Customer cust) {
        try {
            String request = "<AddCustomerRequest>";
            request += "<FirstName>" + cust.getFirstName() +
                    "</FirstName>";
            request += "<LastName>" + cust.getLastName() + "</LastName>";
            request += "<Title>" + cust.getTitle() + "</Title>";
            request += "<BirthDate>" + cust.getBirthDate() +
                    "</BirthDate>";
            request += "<Street1>" + cust.getAddress1() + "</Street1>";
            request += "<Street2>" + cust.getAddress2() + "</Street2>";
            request += "<City>" + cust.getCity() + "</City>";
            request += "<State>" + cust.getState() + "</State>";
            request += "<Zip>" + cust.getZip() + "</Zip>";
            request += "<Country>US</Country>";
            request += "<Phone>" + cust.getPhone() + "</Phone>";
            request += "<Fax>" + cust.getFax() + "</Fax>";
            request += "<Email>" + cust.getEmail() + "</Email>";
            request += "</AddCustomerRequest>";

            String docType =
                    "<!DOCTYPE AddCustomerRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/AddCustomerRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();

                if (root.getNodeName().equals("AddCustomerResponse")) {
                    NodeList ids = root.getElementsByTagName("CustomerId");
                    if (ids.getLength() > 0) {
                        Element id = (Element) ids.item(0);
                        if (id.getFirstChild() != null) {
                            String custCode = id.getFirstChild().getNodeValue();
                            return custCode;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String updateCustomer(Customer cust) {
        try {
            String request = "<UpdateCustomerRequest>";
            request += "<CustomerId>" + cust.getCode() +
                    "</CustomerId>";
            request += "<DateModified>" + cust.getLastModified().getTime() +
                    "</DateModified>";
            request += "<FirstName>" + cust.getFirstName() +
                    "</FirstName>";
            request += "<LastName>" + cust.getLastName() + "</LastName>";
            request += "<Title>" + cust.getTitle() + "</Title>";
            request += "<BirthDate>" + cust.getBirthDate() +
                    "</BirthDate>";
            request += "<Street1>" + cust.getAddress1() + "</Street1>";
            request += "<Street2>" + cust.getAddress2() + "</Street2>";
            request += "<City>" + cust.getCity() + "</City>";
            request += "<State>" + cust.getState() + "</State>";
            request += "<Zip>" + cust.getZip() + "</Zip>";
            request += "<Country>US</Country>";
            request += "<Phone>" + cust.getPhone() + "</Phone>";
            request += "<Fax>" + cust.getFax() + "</Fax>";
            request += "<Email>" + cust.getEmail() + "</Email>";
            request += "</UpdateCustomerRequest>";

            String docType =
                    "<!DOCTYPE UpdateCustomerRequest PUBLIC \"-//BAYCLOUD//DTD//EN\" \"http://www.baycloud.com/posXML/" +
                    POSXML_VERSION + "/UpdateCustomerRequest.dtd\">";
            request = XML_HEADER + docType + request;

            //System.out.print(request);
            String response = Synchronizer.post(request);
            //System.out.print(response);
            if (response != null) {
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.
                                          newDocumentBuilder();
                Document document = builder.parse(new InputSource(new
                        StringReader(
                                response)));
                Element root = document.getDocumentElement();

                if (root.getNodeName().equals("UpdateCustomerResponse")) {
                    NodeList ids = root.getElementsByTagName("CustomerId");
                    if (ids.getLength() > 0) {
                        Element id = (Element) ids.item(0);
                        if (id.getFirstChild() != null) {
                            return id.getFirstChild().getNodeValue();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
