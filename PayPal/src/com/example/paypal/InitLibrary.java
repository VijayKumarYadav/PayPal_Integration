/**
 * 
 */

package com.example.paypal;

import android.content.Context;
import android.os.AsyncTask;

import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;

/**
 * The Class InitLibrary initializes the PayPal payment library.
 * 
 * @author VijayK 
 */
public class InitLibrary extends AsyncTask<Void, Void, PayPal> {

    private Context context;
    private int serverMode;
    private String APP_ID;
    private boolean isShippingEnabled;
    private int feePayer;
    private boolean isDynamicAmountCalculation;
    private OnInitLibraryListener onInitLibraryListener;

    public InitLibrary(Context context, String APP_ID, int serverMode, int feePayer,
            boolean isShippingEnabled, boolean isDynamicAmountCalculation,
            OnInitLibraryListener listener) {
        this.context = context;
        this.APP_ID = APP_ID;
        this.serverMode = serverMode;
        this.feePayer = feePayer;
        this.isShippingEnabled = isShippingEnabled;
        this.isDynamicAmountCalculation = isDynamicAmountCalculation;
        this.onInitLibraryListener = listener;
    }

    @Override
    protected PayPal doInBackground(Void... arg0) {

        // if library is already initialized then no need to initialize it again.
        PayPal paypalAlreadyInit = PayPal.getInstance();
        if (paypalAlreadyInit != null) {
            return paypalAlreadyInit;
        }

        PayPal mPaypal = PayPal.initWithAppID(context, APP_ID, serverMode);
        mPaypal.setLanguage("en_US");
        mPaypal.setFeesPayer(feePayer);
        mPaypal.setShippingEnabled(isShippingEnabled);
        mPaypal.setDynamicAmountCalculationEnabled(isDynamicAmountCalculation);
        return mPaypal;
    }

    @Override
    protected void onPostExecute(PayPal result) {
        super.onPostExecute(result);

        boolean isSuccessfull = result != null ? true : false;

        if (isSuccessfull) {

            CheckoutButton mCheckoutBtn = result.getCheckoutButton(context, PayPal.BUTTON_152x33,
                    CheckoutButton.TEXT_PAY);
            onInitLibraryListener.onInitSucceeded(mCheckoutBtn);
        }

        else
            onInitLibraryListener.onInitFailed();

    }

    /**
     * The listener interface for receiving onInitLibrary events. The class that is interested in
     * processing a onInitLibrary event implements this interface When the onInitLibrary event
     * occurs, that object's appropriate method is invoked. On successful initialization paypal
     * checkout button will be passed as parameter. This button is sub class of linear layout.
     * 
     * @see OnInitLibraryEvent
     */
    public interface OnInitLibraryListener {
        public void onInitSucceeded(CheckoutButton mCheckoutBtn);

        public void onInitFailed();
    }

}
