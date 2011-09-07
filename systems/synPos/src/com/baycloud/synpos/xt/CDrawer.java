package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;

import jpos.JposException;
import jpos.CashDrawer;

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
public class CDrawer {
    public static boolean close() throws Exception {
        String model = Configuration.get("cashdrawer.model");

        if (model == null) {
            return true;
        }

        // instantiate a new jpos.CashDrawer object
        CashDrawer cashDrawer = new CashDrawer();

        try {
            // open the cashDrawer object according to the entry names defined in jpos.xml
            cashDrawer.open(model); // TSP700 cashDrawer - mac - usb

            // claim exclsive usage of the cashDrawer object
            cashDrawer.claim(1);

            // enable the device for input and output
            cashDrawer.setDeviceEnabled(true);

            //cashDrawer.close();
            // check if the drawer is currently opened
            // if this check returns true when the drawer is ACTUALLY CLOSED,
            //    set the drawerClosedOnSignalLow property of this device's entry
            //    in the jpos.xml file to true
            if (cashDrawer.getDrawerOpened() == true) {
                return false;
                //System.out.println("cashDrawer.getDrawerOpened() == true");
            } else {
                return true;
                //System.out.println("cashDrawer.getDrawerOpened() == false");
            }

            // open the cash drawer
            //   if your cash drawer is wired to the printer's control circuit
            //   number 2, set the cashDrawer1 proprty of this device's entry in
            //   jpos.xml file to true
            //cashDrawer.openDrawer();
        } catch (JposException e) {
            // display any errors that come up
            e.printStackTrace();
        }

        return false;
    }

    public static void open() throws Exception {
        String model = Configuration.get("cashdrawer.model");

        if (model == null) {
            return;
        }

        // instantiate a new jpos.CashDrawer object
        CashDrawer cashDrawer = new CashDrawer();

        try {
            // open the cashDrawer object according to the entry names defined in jpos.xml
            cashDrawer.open(model); // TSP700 cashDrawer - mac - usb

            // claim exclsive usage of the cashDrawer object
            cashDrawer.claim(1);

            // enable the device for input and output
            cashDrawer.setDeviceEnabled(true);

            // check if the drawer is currently opened
            // if this check returns true when the drawer is ACTUALLY CLOSED,
            //    set the drawerClosedOnSignalLow property of this device's entry
            //    in the jpos.xml file to true
            if (cashDrawer.getDrawerOpened() == true) {
                return;
                //System.out.println("cashDrawer.getDrawerOpened() == true");
            }

            // open the cash drawer
            //   if your cash drawer is wired to the printer's control circuit
            //   number 2, set the cashDrawer1 proprty of this device's entry in
            //   jpos.xml file to true
            cashDrawer.openDrawer();
        } catch (JposException e) {
            // display any errors that come up
            e.printStackTrace();
        }
    }
}
