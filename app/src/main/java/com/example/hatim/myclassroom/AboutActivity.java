package com.example.hatim.myclassroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Richard on 05/06/2016.
 */
public class AboutActivity extends AppCompatActivity {

    public TextView aboutTitleTextView, aboutContentTextView;
    public Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutContentTextView = (TextView) findViewById(R.id.aboutContentTV);
        aboutTitleTextView = (TextView) findViewById(R.id.aboutTitleTV);
        toolbar = (Toolbar) findViewById(R.id.tool_about);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setLogo(R.drawable.myclassroomlogo_small);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
