package com.example.hatim.myclassroom;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hatim.myclassroom.DocumentChoosing.DocumentPickActivity;
import com.example.hatim.myclassroom.Log.LoginActivity;
import com.example.hatim.myclassroom.Log.PersistLoginInfo;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;




public class BaseLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 9001;
    public SharedPreferences myClassPrefs;
    public final PersistLoginInfo persistLoginInfo = new PersistLoginInfo(mGoogleApiClient,this);

    public GoogleSignInOptions gso;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestProfile()
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        myClassPrefs = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            persistLoginInfo.handleSignInResult(result, myClassPrefs);

            if (result.isSuccess()){

                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.prefs_name),MODE_PRIVATE).edit();
                editor.putBoolean(getString(R.string.acc_connected),true);
                editor.commit();

                Intent startMain = new Intent(this, DocumentPickActivity.class);
                startActivity(startMain);
                finish();


            }
        }
    }


    public void signInWithGplus() {

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    public void onLogout() {

        Log.i("base class", "logout invoked");
        if (mGoogleApiClient.isConnected()) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    if (status.isSuccess()){
                                        Log.wtf("logout","success");
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
