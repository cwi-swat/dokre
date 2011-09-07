package com.baycloud.synpos.ui;

import com.baycloud.synpos.util.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.lang.Math;

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
class PieChart extends JPanel {
    Map.Entry[] wedges; // The data for the pie
    double total = 0.0; // Total of all wedges

    Color wedgeColor[] = new Color[7];
    Color textColor = Color.black;
    Color otherColor = Color.gray;
    Color shadowColor = Color.lightGray;
    //Color bgColor = Color.white;

    int pieViewSize; // size of square to incise pie into
    static final int pieBorderWidth = 20; // pixels from circle edge to side
    int pieDiameter; // derived from the view size
    int pieRadius; // ..
    int pieCenterPosX; // ..
    int pieCenterPosY; // ..

    int minLabeledWedge = 4; // Min size of a wedge for labeling, degrees.
    int shadowOffset = 4; // offset of shadow in pixels

    public PieChart() {
        total = 0;
        setOpaque(true);
    }

    public PieChart(Hashtable wedges) {
        total = 0;
        setOpaque(true);
        setWedges(wedges);
    }

    public void changeSize() {
        this.pieViewSize = Math.min(getSize().width,
                                    getSize().height);

        //this.bgColor = bc;

        this.pieDiameter = pieViewSize - 2 * pieBorderWidth;
        this.pieRadius = pieDiameter / 2;
        //this.pieCenterPos = pieBorderWidth + pieRadius;
        this.pieCenterPosX = getSize().width / 2;
        this.pieCenterPosY = getSize().height / 2;
        //this.setFont(new Font("Helvetica", Font.BOLD, 12));
        //this.setBackground(bgColor);
        //this.setBackground(new Color(255, 255, 183));
    }

    public void setWedges(Hashtable wedges) {
        this.wedges = Sorter.getSortedHashtableEntries(wedges, false);
        this.total = 0;

        Iterator values = wedges.values().iterator();
        while (values.hasNext()) {
            Double value = (Double) values.next();
            this.total += value.doubleValue();
        }

        this.wedgeColor[0] = Color.green; // colors that black looks good on
        this.wedgeColor[1] = Color.yellow;
        this.wedgeColor[2] = Color.orange;
        this.wedgeColor[3] = Color.cyan;
        this.wedgeColor[4] = Color.pink;
        this.wedgeColor[5] = Color.magenta;
        this.wedgeColor[6] = Color.red;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (total == 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        // for smooth drawing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        changeSize();

        int startDeg = 0;
        int arcDeg;
        int arcDegCount = 0;
        int x, y;
        double angleRad;

        g2.setPaint(shadowColor); // shadow, down and to the right 3 pixels
        g2.fillOval(pieBorderWidth + shadowOffset,
                    pieBorderWidth + shadowOffset,
                    pieDiameter, pieDiameter);

        g2.setPaint(otherColor); // "other" is gray
        g2.fillOval(pieBorderWidth, pieBorderWidth, pieDiameter, pieDiameter);

        int wci = 0;
        int i;
        for (i = 0; i < wedges.length; i++) { // draw wedges
            double frac = ((Double) wedges[i].getValue()).doubleValue();
            arcDeg = (int) Math.round(((frac / total) * 360));
            arcDegCount += arcDeg;
            if ((i + 1) == this.wedges.length & arcDegCount != 360) {
                arcDeg += (360 - arcDegCount); // Avoid a gray wedge due to roundoff.
            }

            g2.setPaint(wedgeColor[wci++]);

            g2.fillArc(pieBorderWidth, pieBorderWidth, pieDiameter, pieDiameter,
                       startDeg, arcDeg);
            if (wci >= wedgeColor.length) {
                wci = 1; // Rotate colors.  Don't reuse 0 in case first and last are same.
            }
            startDeg += arcDeg;
        } // draw wedges

        startDeg = 0; // Do labels so they go on top of the wedges.
        for (i = 0; i < this.wedges.length; i++) {
            double frac = ((Double) wedges[i].getValue()).doubleValue();
            arcDeg = (int) Math.round(((frac / total) * 360));

            if (arcDeg > minLabeledWedge) { // Don't label small wedges.
                g2.setPaint(textColor);
                angleRad = (float) (startDeg + (arcDeg / 2)) *
                           java.lang.Math.PI /
                           180.0;
                x = pieCenterPosX +
                    (int) ((pieRadius / 1.3) * java.lang.Math.cos(angleRad));
                y = pieCenterPosY -
                    (int) ((pieRadius / 1.3) * java.lang.Math.sin(angleRad))
                    + 5; // 5 is about half the height of the text
                String name = (String) wedges[i].getKey();
                String perc = ": " + (int) Math.round(((frac / total) * 100)) +
                              "%";
                Font f = getFont();
                FontMetrics fm = g2.getFontMetrics(f);
                while ((fm.stringWidth(name + perc) + x) > getSize().width) {
                    if (name.length() < 4) {
                        name = null;
                        break;
                    } else {
                        name = name.substring(0, name.length() - 4) + "...";
                    }
                }

                if (name != null) {
                    String label = name + perc;
                    g2.drawString(label, x, y);
                }
            }

            startDeg += arcDeg;
        }
    }
}
