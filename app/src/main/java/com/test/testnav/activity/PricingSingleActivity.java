package com.test.testnav.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.testnav.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PricingSingleActivity extends AppCompatActivity {

    ListView listView;
    String pac_name, pac_id, pac_details, more_de, text, url;
    StringRequest request;
    Bundle bundle;
    Toolbar toolbar;
    WebView webview;
    TextView set_title;
    ToggleButton toggle;
    List<HashMap<String, String>> pricing_list = new ArrayList<HashMap<String, String>>();
    SimpleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricing_single);

        getDataBundle();

        initCompo();

        setData();

        initListener();


    }

    private void getDataBundle() {

        // getting data
        bundle = getIntent().getExtras();
        pac_name = bundle.getString("pac_name");
        pac_id = bundle.getString("pac_id");
        pac_details = bundle.getString("pac_details");
        more_de = bundle.getString("more_de");
    }

    private void initCompo() {
        listView = (ListView) findViewById(android.R.id.list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        set_title = (TextView) findViewById(R.id.pac_name);
        webview = (WebView) findViewById(R.id.pac_details);
        toggle = (ToggleButton) findViewById(R.id.shhi);

    }

    private void initListener() {

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    webview.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    webview.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setData() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(pac_name);

        set_title.setText(pac_name);

        if (pac_details.equals("")) {
            text = "<html><body style=\"text-align:justify; color: #848484; font-size: 14px;\">Details not available...</body></Html>";
        } else {
            text = "<html><body style=\"text-align:justify; color: #848484; font-size: 14px;\"> " + pac_details + more_de + "</body></Html>";
        }
        webview.loadData(text.replace("<img ", "<img style='width:100% !important; height: auto !important;'"), "text/html; charset=utf-8", "utf-8");
        webview.setVisibility(View.GONE);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        getPricingById(pac_id);

    }

    private void getPricingById(final String pac_id) {
        url = "http://equitypandit.com/get_pricing_app.php?pac_id=" + pac_id;

        request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String string) {
//                Toast.makeText(PricingSingleActivity.this, "Response | "+ string, Toast.LENGTH_SHORT).show();

                /////
                String[] from = {"pac_name", "price", "dis_p", "dis_amount", "duration", "show_hide"};

                // Ids of views in listview_layout
                int[] to = {R.id.pac_name, R.id.price, R.id.dis_p, R.id.dis_price, R.id.duration, R.id.offer_txt};
                try {
                    JSONArray array = new JSONArray(string);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonLineItem = (JSONObject) array.getJSONObject(i);

                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("pac_name", jsonLineItem.getString("plan"));

                        hm.put("dis_amount", jsonLineItem.getString("dis_price"));
                        hm.put("duration", "( " + jsonLineItem.getString("duration") + " Months )");
                        if (jsonLineItem.getString("dis_p").equals("0")) {
                            hm.put("dis_p", "");
                            hm.put("price", "");
                            hm.put("show_hide", "");
                        } else {
                            hm.put("dis_p", jsonLineItem.getString("dis_p") + "% OFF");
                            hm.put("price", jsonLineItem.getString("amt"));
                            hm.put("show_hide", "On Offer");
                        }
//                        hm.put("img", "http:\\/\\/www.equitypandit.com\\/wp-content\\/uploads\\/"+jsonLineItem.getString("meta_value"));
                        pricing_list.add(hm);

                    }
                } catch (JSONException e) {
//             JSON error
                    e.printStackTrace();
//             Toast.makeText(context, "Json this error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                adapter = new SimpleAdapter(PricingSingleActivity.this, pricing_list, R.layout.single_pricing_plan_layout, from, to);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, String> details = pricing_list.get(i);
                        String s_duration = details.get("duration");
                        String s_amt = details.get("dis_amount");

//                        Toast.makeText(PricingSingleActivity.this, "Package Title: " + title, Toast.LENGTH_LONG).show();
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.equitypandit.com/payonline.php"));
//                        startActivity(browserIntent);

                        //req//
//                        WebView webview = new WebView(PricingSingleActivity.this);
//                        webview.getSettings().setJavaScriptEnabled(true);
//                        webview.setWebViewClient(new WebViewClient());
//                        setContentView(webview);
//                        url = "http://www.equitypandit.com/payonline.php";
//                        try {
//                            String postData = "productinfo=" + URLEncoder.encode(title, "UTF-8");
//                            webview.postUrl(url, postData.getBytes());
//                        } catch (IOException e) {
//                            Toast.makeText(PricingSingleActivity.this, "IO e : " + e, Toast.LENGTH_LONG).show();
//                        }
                        Intent intent1 = new Intent(PricingSingleActivity.this, PaynowActivity.class);
                        Bundle args = new Bundle();
                        args.putString("pac_name", pac_name);
                        args.putString("pac_duration", s_duration);
                        args.putString("pac_amount", s_amt);
                        intent1.putExtras(args);
                        startActivity(intent1);
                        //req//

                    }
                });
                /////
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PricingSingleActivity.this, "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue rQueue = Volley.newRequestQueue(PricingSingleActivity.this);
        rQueue.add(request);
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
