package com.test.testnav.activity;

/**
 * Created by Administrator on 09-05-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SigninBackTask extends AsyncTask<String, Void, String> {
    private Context context;
    private int byGetOrPost = 0;
    String useremail, password;

    //    flag 0 means get and 1 means post.(By default it is get.
    public SigninBackTask(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            // first arguments is arg[0] is useremail and second argument is arg[1] contains password
            useremail = (String) arg0[0];
            password = (String) arg0[1];
//            String link = "http://192.168.0.106/epsystem/app_api/login.php";
            String link = "http://www.equitypandit.com/portal/app2_api/login.php";
            String data = URLEncoder.encode("useremail", "UTF-8") + "=" + URLEncoder.encode(useremail, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
//             Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
//        this.statusField.setText(result);
//        this.roleField.setText("");
        try {
            JSONObject jObj = new JSONObject(result);
            boolean error = jObj.getBoolean("error");
//             Check for error node in json
            if (!error) {
                JSONObject user = jObj.getJSONObject("user");
                String id = user.getString("id");
                String name = user.getString("name");
                String email = user.getString("email");
                String mobile = user.getString("mobile");
                String password = user.getString("password");
                String status = user.getString("status");
                String login_type = user.getString("login_type");
                String reg_date = user.getString("reg_date");

                // redirect to dashboard
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i); // call using Context instance
            } else {
//                 Error in login. Get the error message
                String errorMsg = jObj.getString("error_msg");
                Toast.makeText(context,
                        errorMsg, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
//             JSON error
            e.printStackTrace();
//            Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}