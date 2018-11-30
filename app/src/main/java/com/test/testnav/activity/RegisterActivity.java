package com.test.testnav.activity;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.testnav.R;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText uname, uemail, umobile, pass, repass;
    String S_uname, S_uemail, S_umobile, S_pass, S_repass, S_login_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        // init compo

        uname = (EditText) findViewById(R.id.username);
        umobile = (EditText) findViewById(R.id.mobile);
        uemail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        repass = (EditText) findViewById(R.id.re_pass);


        // init listeners

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

    public void register(View view) {

        S_uname = uname.getText().toString();
        S_uemail = uemail.getText().toString();
        S_umobile = umobile.getText().toString();
        S_pass = pass.getText().toString();
        S_repass = repass.getText().toString();
        S_login_type = "other";

        if (S_uname.equals("")) {
            uname.setError("Name Required !");
        } else if (S_uemail.equals("")) {
            uemail.setError("Email Required !");
        } else if (S_umobile.equals("")) {
            umobile.setError("Mobile No. Required !");
        } else if (S_pass.equals("")) {
            pass.setError("Password Required !");
        } else if (S_repass.equals("")) {
            repass.setError("Please re-enter Password !");
        } else if (!S_pass.equals(S_repass)) {
            repass.setError("Password & Re-typed Password are not equal !");
        } else {
// send all to server for inserting into DB
            new RegisterBackTask(this, 1).execute(S_uname, S_uemail, S_umobile, S_pass, S_login_type);
        }


//        Toast.makeText(getApplicationContext(), "Create An Account Clicked !", Toast.LENGTH_LONG).show();
    }

    public void login(View view) {
        Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent1);
        finish();
    }

}