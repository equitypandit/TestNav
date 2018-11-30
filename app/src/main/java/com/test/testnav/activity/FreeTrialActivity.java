package com.test.testnav.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.test.testnav.R;

import java.io.IOException;
import java.net.URLEncoder;

public class FreeTrialActivity extends AppCompatActivity {

    Toolbar toolbar;
    WebView htmlWebView;
    SharedPreferences pref;
    String SP_name, SP_email, SP_mobile;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynow);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Take A Free Trial");

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        pref = getApplicationContext().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SP_name = pref.getString("Name", "");
        SP_email = pref.getString("Email", "");
        SP_mobile = pref.getString("Mobile", "");
//        if(SP_mobile.equals("-")){
//            SP_mobile = " ";
//        }

        Toast.makeText(FreeTrialActivity.this, SP_email + " " + SP_mobile + " " + SP_name, Toast.LENGTH_LONG).show();

        htmlWebView = (WebView) findViewById(R.id.paynow);
        htmlWebView.setWebViewClient(new WebViewClient());
        WebSettings webSetting = htmlWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDisplayZoomControls(true);


//        if (SP_email.equals("")) {
//            htmlWebView.loadUrl("http://www.equitypandit.com/portal/app_freetrial.php");
//        } else {
        url = "http://www.equitypandit.com/portal/app_freetrial.php";
        try {
            String postData = "tname=" + URLEncoder.encode(SP_name + "_", "UTF-8") + "&temail=" + URLEncoder.encode(SP_email, "UTF-8");
            htmlWebView.postUrl(url, postData.getBytes());
        } catch (IOException e) {
            Toast.makeText(FreeTrialActivity.this, "IO e : " + e, Toast.LENGTH_LONG).show();
        }
//        }


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
