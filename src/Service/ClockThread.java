/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author BUI QUANG HIEU
 */
public class ClockThread extends Thread {
//    private JButton button;

    private JLabel jLabel;
//    private JLabel jLabel1;

    public ClockThread(JLabel jLabel) {
        this.jLabel = jLabel;
//        this.jLabel1 = jLabel1;
    }

    @Override
    public void run() {
        int temp = 0, m = 0, h = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd/MM/yyy hh:mm:ss aa");
        SimpleDateFormat sdf1 = new SimpleDateFormat("ss");
        while (true) {

            Date now = new Date();

            String st = sdf.format(now);
            var st1 = h + " hours  " + m + " minute  " + temp++ + " seconds";
//            jLabel1.setText(String.valueOf(st1));
            jLabel.setText(st);
            try {
                Thread.sleep(1000);
                if (temp == 59) {
                    temp = 0;
                    m++;
                }

                if (m == 59) {
                    m = 0;
                    h++;
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(ClockThread.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
