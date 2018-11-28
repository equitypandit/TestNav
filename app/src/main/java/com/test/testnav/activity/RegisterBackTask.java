package com.test.testnav.activity;

/**
 * Created by Administrator on 09-05-2016.
 */

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

import static android.content.Context.MODE_PRIVATE;

public class RegisterBackTask extends AsyncTask<String, Void, String> {
    private Context context;
    private int byGetOrPost = 0;
    String username, useremail, usermobile, password;

    //    flag 0 means get and 1 means post.(By default it is get.
    public RegisterBackTask(Context context, int flag) {
        this.context = context;
        byGetOrPost = flag;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            // first arguments is arg[0] is useremail and second argument is arg[1] contains password
            username = (String) arg0[0];
            useremail = (String) arg0[1];
            usermobile = (String) arg0[2];
            password = (String) arg0[3];

//            String link = "http://192.168.0.106/epsystem/app_api/login.php";
            String link = "http://www.equitypandit.com/portal/app2_api/reg_app.php";
            String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("useremail", "UTF-8") + "=" + URLEncoder.encode(useremail, "UTF-8");
            data += "&" + URLEncoder.encode("mobile", "UTF-8") + "=" + URLEncoder.encode(usermobile, "UTF-8");
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
                add_login_details_to_SP("YES", "-", username, useremail, password, "Other", usermobile);

                // redirect to dashboard
                Intent i = new Intent(context, MainActivity.class);
                i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
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

    public void add_login_details_to_SP(String... args) {

        SharedPreferences pref;
        SharedPreferences.Editor SP_editor;

        pref = context.getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SP_editor = pref.edit();
        // Saving long
        SP_editor.putString("IsLogin", args[0]);      // YES
        SP_editor.putString("Id", args[1]);           // 123
        SP_editor.putString("Name", args[2]);       // Priyank Kahar
        SP_editor.putString("Email", args[3]);      // test@gmail.com
        SP_editor.putString("Password", args[4]);   // Abcd@123
        SP_editor.putString("LoginType", args[5]);  // Google
        SP_editor.putString("Mobile", args[6]);       // 9876543210
        SP_editor.commit();
    }
}