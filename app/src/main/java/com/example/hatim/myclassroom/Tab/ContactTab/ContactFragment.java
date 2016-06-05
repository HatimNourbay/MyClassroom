package com.example.hatim.myclassroom.Tab.ContactTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hatim.myclassroom.DatabaseParams.DataBaseHelper;
import com.example.hatim.myclassroom.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Richard on 28/05/2016.
 */
public class ContactFragment extends Fragment {

    //public ArrayList<ContactItem> contactItem = new ArrayList<ContactItem>();
    public FloatingActionButton addFABtn;
    public DataBaseHelper dataBaseHelper = null;
    private Dao<ContactTable, Integer> contactDao;
    ContactAdapter contactAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_cont_recycler, container, false);
        //Recycler view
        RecyclerView contactListV = (RecyclerView) view.findViewById(R.id.cont_list);
        contactListV.setHasFixedSize(true);
        contactListV.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {

            contactDao =  getHelper().getContactDao();


            List<ContactTable> contactTable = contactDao.queryForAll();


            final LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            final View rowView = layoutInflater.inflate(R.layout.contacts_layout, contactListV, false);
            ((TextView)rowView.findViewById(R.id.contPren)).setText("Prenom");
            ((TextView)rowView.findViewById(R.id.contNom)).setText("Nom");


            contactAdapter = new ContactAdapter(contactTable);
            contactListV.setAdapter( contactAdapter);

            // If, no record found in the database, appropriate message needs to be displayed.
            /*if(contactTable.size() == 0)
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setMessage("Pas de contact");
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }*/


        } catch (SQLException e) {
            e.printStackTrace();
        }

        contactAdapter.notifyDataSetChanged();


        //Add contact Button
        addFABtn = (FloatingActionButton) view.findViewById(R.id.addFABtn);
        addFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getContext(), ContactAddActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(getContext(), DataBaseHelper.class);
        }
        return dataBaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        if (dataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            dataBaseHelper = null;
        }
    }


}
