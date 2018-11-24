package com.test.testnav.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.test.testnav.R;
import com.test.testnav.activity.NewsSingle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MediaSectionFragment extends ListFragment {
    View view;
    String word = "media_news";

    List<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Media Section");

        view = super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
//        Toast.makeText(getActivity(), "Toast Message", Toast.LENGTH_SHORT).show();
//        news_and_events
        String url = "http://equitypandit.com/ss_old.php?word=" + word;
//        String url = "http://equitypandit.com/ss.php";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String string) {
//                Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();

                String[] from = {"img", "txt", "cur", "con"};

                // Ids of views in listview_layout
                int[] to = {R.id.flag, R.id.txt, R.id.cur};
                try {
                    JSONArray array = new JSONArray(string);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonLineItem = (JSONObject) array.getJSONObject(i);

                        HashMap<String, String> hm = new HashMap<String, String>();
                        hm.put("txt", jsonLineItem.getString("post_title"));
                        hm.put("cur", jsonLineItem.getString("post_date"));
                        hm.put("con", jsonLineItem.getString("post_content"));
                        hm.put("img", jsonLineItem.getString("meta_value"));

//                        hm.put("img", "http:\\/\\/www.equitypandit.com\\/wp-content\\/uploads\\/"+jsonLineItem.getString("meta_value"));
                        oslist.add(hm);

                    }
                } catch (JSONException e) {
//             JSON error
                    e.printStackTrace();
//             Toast.makeText(context, "Json this error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), oslist, R.layout.single_list_layout, from, to);
                setListAdapter(adapter);

                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        HashMap<String, String> details = oslist.get(i);
                        String title = details.get("txt");
                        String date_time = details.get("cur");
                        String content = details.get("con");
                        String image = details.get("img");
//                        Toast.makeText(getActivity(), "News Title: " + title, Toast.LENGTH_LONG).show();

                        Intent intent1 = new Intent(getActivity(), NewsSingle.class);
                        Bundle args = new Bundle();
                        args.putString("TITLE", title);
                        args.putString("DATE", date_time);
                        args.putString("CONTENT", content);
                        args.putString("IMAGE", image);
                        intent1.putExtras(args);
                        startActivity(intent1);

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "Some error occurred!!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);
        return view;
    }
}