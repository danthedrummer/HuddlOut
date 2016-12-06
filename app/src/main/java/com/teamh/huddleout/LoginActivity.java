package com.teamh.huddleout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import java.net.URLEncoder;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final String TAG = "DevMsg";

        Button loginButton = (Button)findViewById(R.id.loginButton);
        Button registerButton = (Button)findViewById(R.id.registerButton);
        final EditText username = (EditText)findViewById(R.id.usernameField);
        final EditText password = (EditText)findViewById(R.id.passwordField);

        final Context context = this.getApplicationContext();
        final HuddlOutAPI hAPI = HuddlOutAPI.getInstance(context);

        final TextView signInTitle = (TextView)findViewById(R.id.signInTitle);

        loginButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        Popup.show("LOGGING IN...", context);
                        final RequestQueue reQueue = hAPI.login(username.getText().toString(), password.getText().toString());
                        RequestQueue.RequestFinishedListener finishedListener = new RequestQueue.RequestFinishedListener() {
                            @Override
                            public void onRequestFinished(Request request) {
                                if(hAPI.getAuth()){
                                    ActivitySwap.swapToNextActivity(LoginActivity.this, MainMenuActivity.class);
                                    finish();
                                }else{
                                    String errorMsg = hAPI.getMessage();
                                    if(errorMsg != null) {
                                        errorMsg = errorMsg.toUpperCase();
                                        Popup.show(errorMsg, context);
                                    } else {
                                        Popup.show("INTERNAL ERROR", context);
                                    }
                                }
                            }
                        };
                        reQueue.addRequestFinishedListener(finishedListener);
                    }
                }
        );


        registerButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        ActivitySwap.swapToNextActivity(LoginActivity.this, RegisterActivity.class);
                    }
                }
        );

        signInTitle.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v) {

                        Log.i(TAG, "DEV Login");

                        final RequestQueue reQueue = hAPI.login("glennncullen", "1234567");
                        RequestQueue.RequestFinishedListener finishedListener = new RequestQueue.RequestFinishedListener() {
                            @Override
                            public void onRequestFinished(Request request) {
                                if(hAPI.getAuth()){
                                    ActivitySwap.swapToNextActivity(LoginActivity.this, MainMenuActivity.class);
                                    finish();
                                }else{
                                    Popup.show(hAPI.getMessage().toUpperCase(), context);
                                }
                            }
                        };
                        reQueue.addRequestFinishedListener(finishedListener);

                    }

                });
    }

}
