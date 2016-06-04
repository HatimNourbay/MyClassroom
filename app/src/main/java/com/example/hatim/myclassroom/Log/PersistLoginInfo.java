package com.example.hatim.myclassroom.Log;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hatim.myclassroom.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class PersistLoginInfo {

    GoogleApiClient persistClient;
    Context mContext;



    public PersistLoginInfo(GoogleApiClient googleApiClient, Context context){
        persistClient = googleApiClient;
        mContext = context;
    }


    public void handleSignInResult(GoogleSignInResult result){
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.prefs_name), mContext.MODE_PRIVATE).edit();
            if (acct != null) {
                editor.putString(mContext.getString(R.string.acc_name),acct.getDisplayName());
                if (!acct.getPhotoUrl().equals(null)){
                    editor.putString(mContext.getString(R.string.acc_photo),acct.getPhotoUrl().toString());
                }
                else{
                    editor.putString(mContext.getString(R.string.acc_photo),"empty");
                }
                editor.putString(mContext.getString(R.string.acc_mail),acct.getEmail());
                editor.putString(mContext.getString(R.string.acc_token),acct.getIdToken());
                editor.putBoolean(mContext.getString(R.string.acc_connected),true);
            }
            editor.commit();

        } else {
            // Signed out, show unauthenticated UI.
        }

    }
}
