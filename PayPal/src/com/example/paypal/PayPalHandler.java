/**
 * 
 */

package com.example.paypal;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;

import com.commons.network.paypal.process.InitLibrary;
import com.commons.network.paypal.process.InitLibrary.OnInitLibraryListener;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalPayment;

/**
 * The Class PayPalHandler handles the PayPal events like initialization of mobile paypal payment
 * library. Once library is properly initialized, user can make payments.
 * 
 * @author VijayK
 */
public class PayPalHandler {

    private Activity mActivity;
    private String appName;

    /**
     * Instantiates a new pay pal handler. This will also start the PayPal library Initialization on
     * the background thread.On Successful library Initialization OnInitLibraryListener`s method
     * will be called passing paypal checkout button.
     * 
     * @param mActivity the mActivity
     * @param App_Id the app_ id
     * @param appName the app name
     * @param serverMode the server mode: use 0 for sandbox mode, 1 for live mode and 2 for none.
     * @param isShippingEnanbled the is shipping enanbled
     * @param feePayer the fee payer, if there are fees for transaction then this person will pay
     *            the fee. It can be: SENDER - 1, PRIMARYRECEIVER - 2, EACHRECEIVER - 0, and
     *            SECONDARYONLY - 3
     * @param listener the listener
     */
    public PayPalHandler(Activity activity, String App_Id, String appName, int serverMode,
            boolean isShippingEnanbled, int feePayer, OnInitLibraryListener listener) {

        this.mActivity = activity;
        this.appName = appName;

        new InitLibrary(activity, App_Id, serverMode, feePayer, isShippingEnanbled, false, listener)
                .execute();
    }

    /**
     * Make the payment to the specified receiver. This method will return -1 if the paypal library
     * is not initialized otherwise return 1 and start the new paypal activity for making
     * transactions.This payment mode deals in simple payment mode which support payment to a single
     * recipient.
     * 
     * @param receiverMailId the receiver mail id
     * @param subTotal the sub total
     * @param paymentType the payment type can be: PAYMENT_TYPE_GOODS - 0, PAYMENT_TYPE_SERVICE - 1,
     *            PAYMENT_TYPE_PERSONAL - 2, or PAYMENT_TYPE_NONE - 3.
     * @return the int
     */
    public int makePayment(String receiverMailId, String subTotal, int paymentType) {
        if (PayPal.getInstance() == null)
            return -1;

        PayPalPayment mPayPalPayment = getPayPalPayment(receiverMailId, subTotal, paymentType);
        Intent mPaymentIntent = PayPal.getInstance().checkout(mPayPalPayment, mActivity);

        mActivity.startActivityForResult(mPaymentIntent, 1);
        return 1;
    }

    /**
     * @param receiverMailId
     * @param subTotal
     * @param paymentType
     * @return
     */
    @SuppressWarnings("deprecation")
    private PayPalPayment getPayPalPayment(String receiverMailId, String subTotal, int paymentType) {

        PayPalPayment mPayPalPayment = new PayPalPayment();
        mPayPalPayment.setRecipient(receiverMailId);
        mPayPalPayment.setCurrencyType("USD");
        mPayPalPayment.setSubtotal(new BigDecimal(subTotal));
        mPayPalPayment.setPaymentType(paymentType);

        mPayPalPayment.setMerchantName(this.appName);
        mPayPalPayment.setDescription("A simple payment");
        mPayPalPayment.setCustomID("2345235");
        mPayPalPayment.setMemo("Hi! I'm making a memo for a payment.");
        return mPayPalPayment;
    }
}
