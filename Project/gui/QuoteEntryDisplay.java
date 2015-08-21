/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JOptionPane;
import price.PriceFactory;

/**
 *
 * @author hieldc
 */
public class QuoteEntryDisplay extends javax.swing.JFrame {

    private MarketDisplay marketDisplay;
    private String product;

    /**
     * Creates new form QuoteEntryDisplay
     */
    public QuoteEntryDisplay(MarketDisplay md) {
        initComponents();
        marketDisplay = md;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        buyVolumeText = new javax.swing.JTextField();
        buyPriceText = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        sellVolumeText = new javax.swing.JTextField();
        sellPriceText = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Quote Entry"));

        jLabel1.setText("Buy Price:");

        jLabel2.setText("Buy Volume:");

        buyVolumeText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyVolumeTextActionPerformed(evt);
            }
        });
        buyVolumeText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                buyVolumeTextFocusLost(evt);
            }
        });

        buyPriceText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buyPriceTextActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setText("Sell Price:");

        jLabel4.setText("Sell Volume:");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setText("Submit");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buyVolumeText, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buyPriceText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sellVolumeText, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sellPriceText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buyPriceText, buyVolumeText});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sellPriceText, sellVolumeText});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(buyPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(sellPriceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton2)
                    .addComponent(sellVolumeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(buyVolumeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jSeparator2)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setVisible(String p) {
        product = p;
        buyPriceText.setText("");
        buyVolumeText.setText("");
        sellVolumeText.setText("");
        sellPriceText.setText("");
        setTitle("Quote Entry for " + product);
        super.setVisible(true);

    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            if (buyPriceText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Buy price cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (sellPriceText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sell price cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (buyVolumeText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Buy Volume cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }
            if (sellVolumeText.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sell Volume cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            }

            int bv = 0, spw = 0, spf = 0, sv = 0;
            String field = "";
            String value = "";
            String buyPrice = "", sellPrice = "";
            try {
                field = "Buy Price";
                buyPrice = fixPrice(buyPriceText.getText());

                field = "Sell Price";
                sellPrice = fixPrice(sellPriceText.getText());

                field = "Buy Volume";
                value = buyVolumeText.getText();
                bv = Integer.parseInt(buyVolumeText.getText());

                field = "Sell Volume";
                value = sellVolumeText.getText();
                sv = Integer.parseInt(sellVolumeText.getText());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Numeric value for " + field + " field: " + value, "Invalid Numeric Data", JOptionPane.ERROR_MESSAGE);
                return;
            }
            marketDisplay.getUser().submitQuote(product, PriceFactory.makeLimitPrice(buyPrice), bv, PriceFactory.makeLimitPrice(sellPrice), sv);
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private String fixPrice(String p) {
        String result = p;
        if (!result.contains(".")) {
            result += ".00";
        }
        String x = result.substring(result.indexOf("."));

        if (result.substring(result.indexOf(".")).length() == 2) {
            result += "0";
        }

        return result;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buyVolumeTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyVolumeTextActionPerformed
        sellVolumeText.setText(buyVolumeText.getText());
    }//GEN-LAST:event_buyVolumeTextActionPerformed

    private void buyVolumeTextFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_buyVolumeTextFocusLost
        sellVolumeText.setText(buyVolumeText.getText());
    }//GEN-LAST:event_buyVolumeTextFocusLost

    private void buyPriceTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyPriceTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buyPriceTextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField buyPriceText;
    private javax.swing.JTextField buyVolumeText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField sellPriceText;
    private javax.swing.JTextField sellVolumeText;
    // End of variables declaration//GEN-END:variables
}