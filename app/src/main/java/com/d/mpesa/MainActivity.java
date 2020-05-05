package com.d.mpesa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;

import java.io.IOException;
import java.util.Base64;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private Button Btn;
    private String CONSUMER_KEY = "lwEhbCwgg9AmZVG8Sh2DjGxkhohIvXYm";
    private String CONSUMER_SECRET = "j0TxBOu9GFkUeGtr";

    Daraja daraja;

    String phoneNumber = "254716060198";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);

//        EncodeData();

        daraja = Daraja.with(CONSUMER_KEY, CONSUMER_SECRET, new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {

            }

            @Override
            public void onError(String error) {
                Log.e("AccessError", error);
            }
        });


        Btn = findViewById(R.id.Btn);
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText etAmount = findViewById(R.id.etAmount);
                String txtAmount = etAmount.getText().toString();
                if (!TextUtils.isEmpty(txtAmount)) {

                    LNMExpress lnmExpress = new LNMExpress(
                            "174379",
                            "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                            TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                            txtAmount,
                            "254716060198",
                            "174379",
                            phoneNumber,
//                        "http://mycallbackurl.com/checkout.php",
                            "http://mpesa-requestbin.herokuapp.com/1ccgqp61",
                            "001ABC",
                            "Goods Payment"
                    );

                    daraja.requestMPESAExpress(lnmExpress,
                            new DarajaListener<LNMResult>() {
                                @Override
                                public void onResult(@NonNull LNMResult lnmResult) {
//                                Log.i("Result", lnmResult.ResponseCode);

                                    if (lnmResult.ResponseDescription == "Success. Request accepted for processing")
                                        Toast.makeText(MainActivity.this, "Proceed to payment", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onError(String error) {

                                    Toast.makeText(MainActivity.this, "Error! Please Try Again " + error, Toast.LENGTH_SHORT).show();

                                }
                            });

                }
            }
        });


    }



    private void EncodeData() {

        String sample = "BusinessShortCode"+"passkey"+"YYYYMMDDHHMMSS";

        // Encode into Base64 format
        String BasicBase64format = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            BasicBase64format = Base64.getEncoder()
                    .encodeToString(sample.getBytes());
        }

        Log.i("Encoded:", BasicBase64format);


    }




}
