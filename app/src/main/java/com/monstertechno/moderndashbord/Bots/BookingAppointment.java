package com.monstertechno.moderndashbord.Bots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.monstertechno.moderndashbord.R;

public class BookingAppointment extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_appointment);

        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://app.wotnot.io/bot-preview/oL2qHK5HtKug164118518990iJsiricf");
    }
}