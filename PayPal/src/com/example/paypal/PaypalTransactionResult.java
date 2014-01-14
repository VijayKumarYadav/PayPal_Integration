/**
 * 
 */

package com.example.paypal;

import java.io.Serializable;


import com.paypal.android.MEP.PayPalResultDelegate;

/**
 * @author VijayK
 */
public class PaypalTransactionResult implements PayPalResultDelegate, Serializable {

    private static final long serialVersionUID = 10001L;

    /**
     * @param transactionListener
     */
    public PaypalTransactionResult() {
    }

    /**
     * Notification that the payment was canceled.
     * 
     * @param paymentStatus the status of the transaction
     */
    @Override
    public void onPaymentCanceled(String paymentStatus) {
    }

    /**
     * Notification that the payment has failed.
     * 
     * @param paymentStatus the status of the transaction
     * @param correlationID the correlationID for the transaction failure
     * @param payKey the pay key for the payment
     * @param errorID the ID of the error that occurred
     * @param errorMessage the error message for the error that occurred
     */
    @Override
    public void onPaymentFailed(String paymentStatus, String correlationID, String payKey,
            String errorID, String errorMessage) {

    }

    /**
     * Notification that the payment has been completed successfully.
     * 
     * @param payKey the pay key for the payment
     * @param paymentStatus the status of the transaction
     */
    @Override
    public void onPaymentSucceeded(String payKey, String paymentStatus) {
    }

}
