package com.example.hatim.myclassroom.Log;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hatim.myclassroom.R;
import com.example.hatim.myclassroom.Tab.WelcomeTab.InformationLoading;
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


    public void handleSignInResult(GoogleSignInResult result, SharedPreferences myClassPrefs){
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();

            SharedPreferences.Editor editor = myClassPrefs.edit();
            if (acct != null) {
                editor.putString(mContext.getString(R.string.acc_name),acct.getDisplayName());
                if (!(acct.getPhotoUrl() == null)){
                    editor.putString(mContext.getString(R.string.acc_photo),acct.getPhotoUrl().toString());

                    InformationLoading informationLoading = new InformationLoading();
                    if (!myClassPrefs.getString(mContext.getString(R.string.acc_photo),"empty").equalsIgnoreCase("empty")){
                        informationLoading.retrieveProfilePicture(myClassPrefs.getString(mContext.getString(R.string.acc_photo),"empty"),mContext);
                    }

                }
                else{
                    editor.putString(mContext.getString(R.string.acc_photo),"empty");
                }
                editor.putString(mContext.getString(R.string.acc_mail),acct.getEmail());
                editor.putString(mContext.getString(R.string.acc_token),acct.getIdToken());
            }
            editor.commit();

        } else {
            // Signed out, show unauthenticated UI.
        }

    }
}
