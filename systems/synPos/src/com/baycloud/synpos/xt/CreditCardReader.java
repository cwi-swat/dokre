package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;
import jpay.*;

import jpos.JposException;
import jpos.MSR;

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
public class CreditCardReader {
    public static CreditCard read() throws Exception {
        String model = Configuration.get("msr.model");

        if (model != null) {

            // instantiate a new jpos.MSR object
            MSR msr = new MSR();

            try {
                // open the msr object according to the entry names defined in jpos.xml
                msr.open(model);

                // claim exclsive usage of the msr object
                msr.claim(1);

                // enable the device for input and output
                msr.setDeviceEnabled(true);

                String name = msr.getFirstName() + msr.getSurname();
                String number = msr.getAccountNumber();
                String track1 = new String(msr.getTrack1Data());
                String track2 = new String(msr.getTrack2Data());
                String expDate = msr.getExpirationDate();
                String[] pieces = expDate.split("/");
                String expMonth = pieces[1];
                String expYear = "20" + pieces[0];
                return new CreditCard(track1, track2, number, expMonth, expYear,
                                      name);
            } catch (JposException e) {
                // display any errors that come up
                e.printStackTrace();
            }
        }

        return null;
    }
}
