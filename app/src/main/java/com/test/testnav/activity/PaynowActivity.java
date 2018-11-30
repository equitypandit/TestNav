package com.test.testnav.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.test.testnav.R;

import java.io.IOException;
import java.net.URLEncoder;

public class PaynowActivity extends AppCompatActivity {

    Toolbar toolbar;
    WebView htmlWebView;
    String pac_title, pac_amt, pac_duration;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynow);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pay Now");

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

//        get data
        Bundle bundle = getIntent().getExtras();
        pac_title = bundle.getString("pac_name");
        pac_amt = bundle.getString("pac_amount");
        pac_duration = bundle.getString("pac_duration");

        htmlWebView = (WebView) findViewById(R.id.paynow);
        htmlWebView.setWebViewClient(new WebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);

        if (pac_title.equals("")) {
            htmlWebView.loadUrl("http://www.equitypandit.com/payonline.php");
        } else {
            url = "http://www.equitypandit.com/payonline.php";
            try {
                String postData = "productinfo=" + URLEncoder.encode(pac_title + "_" + pac_duration, "UTF-8") + "&amount=" + URLEncoder.encode(pac_amt, "UTF-8");
                htmlWebView.postUrl(url, postData.getBytes());
            } catch (IOException e) {
                Toast.makeText(PaynowActivity.this, "IO e : " + e, Toast.LENGTH_LONG).show();
            }
        }


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
