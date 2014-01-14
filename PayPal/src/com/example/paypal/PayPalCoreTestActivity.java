
package com.example.paypal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.commons.network.paypal.PayPalHandler;
import com.commons.network.paypal.process.InitLibrary.OnInitLibraryListener;
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPalActivity;

public class PayPalCoreTestActivity extends Activity implements OnInitLibraryListener,
        OnClickListener {
    private LinearLayout mLinearLayout;
    private CheckoutButton mCheckoutBtn;
    private PayPalHandler mPayPalHandler;
    private Button mInitLibraryBtn;
    private EditText mAmountEditTxt;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypalscreen);
        this.mLinearLayout = (LinearLayout) findViewById(R.id.PayPalLayout);
        this.mInitLibraryBtn =(Button)findViewById(R.id.InitLib_Btn);
        this.mAmountEditTxt =(EditText)findViewById(R.id.Amount_EditText);
        mInitLibraryBtn.setOnClickListener(this);
        mPayPalHandler = new PayPalHandler(this, "APP-80W284485P519543T", "MY App", 0, true, 1,
                this);
    }

    @Override
    public void onInitFailed() {
        Toast.makeText(this, "Init failed", Toast.LENGTH_SHORT).show();

    }

    public void onInitSucceeded(com.paypal.android.MEP.CheckoutButton mCheckoutBtn) {
        this.mCheckoutBtn = mCheckoutBtn;
        mInitLibraryBtn.setVisibility(View.GONE);
        mLinearLayout.addView(mCheckoutBtn);
        mAmountEditTxt.setVisibility(View.VISIBLE);
        mCheckoutBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == mCheckoutBtn) {
            String amount = mAmountEditTxt.getText().toString();
            if(amount.equals("")) {
                Toast.makeText(this, "enter some amount for transaction", Toast.LENGTH_SHORT).show();
                return;
            }
            
                
            mPayPalHandler.makePayment("interr_1330671638_biz@gmail.com", amount, 0);
        }
        
        if(v.getId() == R.id.InitLib_Btn) {
            Toast.makeText(this, "Initialization started... wait..!!", Toast.LENGTH_SHORT).show();
        }

    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch(resultCode) {
        case Activity.RESULT_OK:
            String resultTitle = "SUCCESS";
            Toast.makeText(this, "transaction successfull", Toast.LENGTH_SHORT).show();
            break;
        case Activity.RESULT_CANCELED:
            resultTitle = "CANCELED";
            String resultInfo = "The transaction has been cancelled.";
            Toast.makeText(this, "transaction Canceled", Toast.LENGTH_SHORT).show();
            break;
        case PayPalActivity.RESULT_FAILURE:
            resultTitle = "FAILURE";
            resultInfo = data.getStringExtra(PayPalActivity.EXTRA_ERROR_MESSAGE);
            Toast.makeText(this, "transaction failed", Toast.LENGTH_SHORT).show();
        }
    }
}
