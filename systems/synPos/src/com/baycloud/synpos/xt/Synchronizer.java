package com.baycloud.synpos.xt;

import com.baycloud.synpos.od.*;

import com.baycloud.synpos.od.StoreConfiguration;
import com.baycloud.synpos.util.HTTP;
import com.baycloud.synpos.util.SwingWorker;

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
public class Synchronizer {
    public static final int MANUAL_SYNC = 1;
    public static final int REAL_SYNC = 0;
    public static final int NEVER_SYNC = -1;

    private static String username;
    private static String password;
    private static String syncUrl;
    private static boolean debug;

    private static int lengthOfTask = 1000;
    private int current = 0;
    private boolean done = false;
    private boolean cancelled = false;
    private String statMessage;

    public static String post(String request) throws Exception {
        if (username == null) {
            username = StoreConfiguration.get("sync_username");
        }
        if (password == null) {
            password = StoreConfiguration.get("sync_password");
        }
        if (syncUrl == null) {
            syncUrl = StoreConfiguration.get("sync_url");
        }

        if (syncUrl == null || username == null || password == null) {
            return null;
        }

        if(debug) {
            System.out.println(request);
        }

        String response = HTTP.post(syncUrl, request, "xml", username, password);

        if(debug) {
            System.out.println(response);
        }

        return response;
    }

    public static int getMode() {
        StoreConfiguration config = new StoreConfiguration();
        int mode = NEVER_SYNC;

        try {
            mode = Integer.parseInt(config.get("sync_frequency"));
        } catch (Exception e) {
        }

        return mode;
    }

    public static void refreshConfig() {
        username = StoreConfiguration.get("sync_username");
        password = StoreConfiguration.get("sync_password");
        syncUrl = StoreConfiguration.get("sync_url");
        String debugStr = StoreConfiguration.get("sync_debug");
        debug = (debugStr != null && debugStr.equals("true"));
    }

    /**
     * Called from ProgressBarDemo to start the task.
     */
    public void go() {
        final SwingWorker worker = new SwingWorker() {
            public Object construct() {
                current = 0;
                done = false;
                cancelled = false;
                statMessage = "Enter frequency in seconds then click 'Start'.";
                return new ActualTask();
            }
        };
        worker.start();
    }

    /**
     * Called from ProgressBarDemo to find out how much work needs
     * to be done.
     */
    public int getLengthOfTask() {
        return lengthOfTask;
    }

    /**
     * Called from ProgressBarDemo to find out how much has been done.
     */
    public int getCurrent() {
        return current;
    }

    public void stop() {
        cancelled = true;
        statMessage = null;
    }

    /**
     * Called from ProgressBarDemo to find out if the task has completed.
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Returns the most recent status message, or null
     * if there is no current status message.
     */
    public String getMessage() {
        return statMessage;
    }

    /**
     * The actual long running task.  This runs in a SwingWorker thread.
     */
    class ActualTask {
        ActualTask() {
            while (!cancelled) {
                current = 0;
                statMessage = "Searching ...";
                Order[] orders = Order.getSyncOrders();

                if (orders != null) {
                    current = 0;
                    statMessage = "Synchronizing sales ...";

                    int step = (orders.length == 0 ? lengthOfTask :
                                lengthOfTask / orders.length);

                    for (int i = 0; i < orders.length; i++) {
                        current += step;
                        //current += Math.random() * step; //make some progress
                        if (current >= lengthOfTask) {
                            current = 0;
                        }

                        orders[i].sync();
                    }
                }

                Customer[] customers = Customer.getSyncCustomers();

                if (customers != null) {
                    statMessage = "Synchronizing customers ...";
                    current = 0;

                    int step = (customers.length == 0 ? lengthOfTask :
                                lengthOfTask / customers.length);

                    for (int i = 0; i < customers.length; i++) {
                        current += step;
                        //current += Math.random() * step; //make some progress
                        if (current >= lengthOfTask) {
                            current = 0;
                        }

                        customers[i].sync();
                    }
                }

                statMessage = "Waiting ...";
                current = 0;

                try {
                    String freqStr = Configuration.get("auto.sync");

                    if (freqStr != null) {
                        int freq = Integer.parseInt(freqStr);

                        Thread.sleep(freq * 1000);
                    } else {
                        break;
                    }
                } catch (InterruptedException e) {

                }
            }
        }
    }
}
