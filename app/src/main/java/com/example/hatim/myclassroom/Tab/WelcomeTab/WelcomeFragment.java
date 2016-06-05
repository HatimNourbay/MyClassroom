package com.example.hatim.myclassroom.Tab.WelcomeTab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatim.myclassroom.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class WelcomeFragment extends Fragment {

    ImageView imageProf;
    TextView nameProf;
    TextView mailProf;

    SharedPreferences myClassPrefs;


    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myClassPrefs = getContext().getSharedPreferences(getString(R.string.prefs_name),getContext().MODE_PRIVATE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_welcome, container, false);


        imageProf = (ImageView)v.findViewById(R.id.profile_picture);
        nameProf = (TextView)v.findViewById(R.id.profile_name);
        mailProf = (TextView)v.findViewById(R.id.profile_mail);


        nameProf.setText(myClassPrefs.getString(getString(R.string.acc_name),"name"));
        mailProf.setText(myClassPrefs.getString(getString(R.string.acc_mail),"email"));

        String name = "profile.png";
        File myDir = new File(getContext().getFilesDir(),name);
        if (myDir.exists()){
            Picasso.with(getContext()).load(myDir).transform(new CircleTransform()).into(imageProf);
        }
        else{
            Picasso.with(getContext())
                    .load(R.drawable.ic_account_circle_white_48dp)
                    .into(imageProf);
        }

        // Inflate the layout for this fragment
        return v;
    }


}
