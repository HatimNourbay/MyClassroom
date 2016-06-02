package com.example.hatim.myclassroom.Tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hatim.myclassroom.R;

import org.w3c.dom.Text;


/**
 * Created by Richard on 01/06/2016.
 */
public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    TextView addTitleTV, addPrenomTV, addNomTV;
    EditText addPrenomET, addNomET;
    FloatingActionButton addPhotoFABtn;
    Button addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addTitleTV = (TextView) findViewById(R.id.addTitleTV);
        addPrenomTV = (TextView) findViewById(R.id.addPrenomTV);
        addNomTV = (TextView) findViewById(R.id.addNomTV);

        addPrenomET = (EditText) findViewById(R.id.addPrenomET);
        addNomET = (EditText) findViewById(R.id.addNomET);

        addPhotoFABtn = (FloatingActionButton) findViewById(R.id.addFABtn);
        addBtn = (Button) findViewById(R.id.addBtn);

        setContentView(R.layout.activity_add_contact);
    }

    @Override
    public void onClick(View v) {

        // add text -> 2 btn

    }
}
