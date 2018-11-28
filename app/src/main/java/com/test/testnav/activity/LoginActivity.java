package com.test.testnav.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.test.testnav.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ET_email, ET_password;
    String S_email, S_password;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private ConnectivityManager cm;

    SharedPreferences pref;
    SharedPreferences.Editor SP_editor;


    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


//        initCompo();
//
//        initListener();

        // Views
        ET_email = (EditText) findViewById(R.id.emailid);
        ET_password = (EditText) findViewById(R.id.password);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);
        // [END customize_button]

    }

//    private void initCompo() {
//
//
//    }
//
//    private void initListener() {
//
//    }

    // Login button clicked
    public void Login(View view) {

        if (check_internet() == 1) {

            S_email = ET_email.getText().toString();
            S_password = ET_password.getText().toString();

            if (S_email.equals("")) {
                ET_email.setError("Field cannot be empty");
            } else if (S_password.equals("")) {
                ET_password.setError("Field cannot be empty");
            } else {
                // call signin activity for verifying username and password from server and get responce accordingly
                new SigninBackTask(this, 1).execute(S_email, S_password);
            }

        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection!", Toast.LENGTH_LONG).show();

        }
    }

    // Login with facebook button clicked
    public void login_Facebook(View view) {
        Toast.makeText(getApplicationContext(), "facebook Clicked !", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onStart() {
        super.onStart();

        pref = getApplicationContext().getSharedPreferences("LoginDetails", MODE_PRIVATE);
        SP_editor = pref.edit();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);

        if (check_internet() == 1) {
            String status = pref.getString("IsLogin", "");
            if (status.equals("YES")) {
//                Toast.makeText(getApplicationContext(), "Already Logged in " + status, Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "LogIn Details Not Found ! " + status, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection!", Toast.LENGTH_LONG).show();
        }
        // [END on_start_sign_in]
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void Login_Google() {
        if (check_internet() == 1) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection!", Toast.LENGTH_LONG).show();

        }
    }
    // [END signIn]

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            // add details to shared prefrence
            add_login_details_to_SP("YES", "-", account.getDisplayName(), account.getEmail(), "-", "Google", "-");
            Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent1);
            finish();
        } else {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Login_Google();
                break;
        }
    }

    // Signup textview clicked
    public void signup(View view) {

        if (check_internet() == 1) {
            Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent1);
        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection!", Toast.LENGTH_LONG).show();
        }

    }

    // Forgot password textview clicked
    public void forgot_password(View view) {

        if (check_internet() == 1) {
            Toast.makeText(getApplicationContext(), "Forgot Password Clicked !", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "please check internet connection!", Toast.LENGTH_LONG).show();
        }

    }

    public int check_internet() {
        int status;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
            status = 1;
        } else {
            status = 0;
        }
        return status;
    }

    public void add_login_details_to_SP(String... args) {

        // Saving long
        SP_editor.putString("IsLogin", args[0]);      // YES
        SP_editor.putString("Id", args[1]);           // 123
        SP_editor.putString("Name", args[2]);       // Priyank Kahar
        SP_editor.putString("Email", args[3]);      // test@gmail.com
        SP_editor.putString("Password", args[4]);   // Abcd@123
        SP_editor.putString("LoginType", args[5]);  // Google
        SP_editor.putString("Mobile", args[6]);       // 9876543210
        SP_editor.commit();

        // add details to DB if not exist
        // mainly for person who loggerd in from google

    }
}
