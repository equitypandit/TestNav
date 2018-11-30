package com.test.testnav.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.test.testnav.R;

public class LogoutActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button cnf_logout;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    GoogleApiClient mGoogleApiClient;
    private EditText uname, uemail;
    String SP_name, SP_email;

    SharedPreferences pref;
    SharedPreferences.Editor SP_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Logout");

        //
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));


        // init comp
        uname = (EditText) findViewById(R.id.uname);
        uemail = (EditText) findViewById(R.id.uemail);

        pref = getApplicationContext().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SP_editor = pref.edit();




        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        uname.setText(pref.getString("Name", ""));
        uemail.setText(pref.getString("Email", ""));


        // init_component()
        cnf_logout = (Button) findViewById(R.id.conf_logout);

        // init_listener()
        cnf_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G_signOut();
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    // [START signOut]
    public void G_signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        Toast.makeText(getApplicationContext(), "Logged Out !", Toast.LENGTH_LONG).show();
                        // clear shared prefrence
                        SP_editor.clear();
                        SP_editor.commit(); // commit changes
                        // [END_EXCLUDE]
                    }
                });
        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i); // Launch the HomescreenActivity
        finish();
    }
    // [END signOut]

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void remove_login_details_from_shared_pref(){

    }
}