package com.test.testnav.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.testnav.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NewsSingle extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        // getting data
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("TITLE");
        String date = bundle.getString("DATE");
        String content = bundle.getString("CONTENT");
        String image = bundle.getString("IMAGE");
//        Toast.makeText(this, image, Toast.LENGTH_SHORT).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);

        // setting the data on views
        TextView set_title = (TextView) findViewById(R.id.d_title);
        TextView set_date = (TextView) findViewById(R.id.d_date_time);
//        TextView set_content = (TextView) findViewById(R.id.d_content);
        ImageView set_image = (ImageView) findViewById(R.id.d_image);
        Integer width = ((Resources.getSystem().getDisplayMetrics().widthPixels)/2);
        if(image != null) {
            Picasso.with(this)
                    .load("http://www.equitypandit.com/wp-content/uploads/"+image)
                    .resize(0, width)
                    .into(set_image);
        } else {
            set_image.getLayoutParams().height = 0;
        }
        WebView webView = (WebView) findViewById(R.id.web_content);
        String text = "<html><body style=\"text-align:justify\">" + content.replace("\n","<p style='text-align: justify;'>") + "</body></Html>";
        webView.loadData(text.replace("<img ","<img style='width:100% !important; height: auto !important;'"), "text/html; charset=utf-8", "utf-8");

        set_title.setText(title);
        set_date.setText(date);
//        set_content.setText(content);

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
