package com.example.hatim.myclassroom.Log;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hatim.myclassroom.BaseLogin;
import com.example.hatim.myclassroom.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

public class LoginActivity extends BaseLogin implements View.OnClickListener {

    private SignInButton googleButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        googleButton = (SignInButton) findViewById(R.id.google_button);

        googleButton.setOnClickListener(this);
        googleButton.setScopes(gso.getScopeArray());

    }

    @Override
    public void onClick(View v)  {
        this.signInWithGplus();
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            persistLoginInfo.handleSignInResult(result, myClassPrefs);
        } else {


            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //hideProgressDialog();
                    persistLoginInfo.handleSignInResult(googleSignInResult, myClassPrefs);

                }
            });
        }
    }

}
