package com.example.hatim.myclassroom.Tab.ContactTab;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hatim.myclassroom.DataBaseHelper;
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
    private DataBaseHelper dataBaseHelper = null;
    private Dao<ContactTable, Integer> contactDao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_cont_recycler, container, false);
        //Recycler view
        RecyclerView contactListV = (RecyclerView) view.findViewById(R.id.cont_list);
        contactListV.setHasFixedSize(true); // pour garder la même taille
        contactListV.setLayoutManager(new LinearLayoutManager(getActivity())); // détecte les changements de données et adapte

        try {
            // This is how, a reference of DAO object can be done
            contactDao =  getHelper().getContactDao();

            // Query the database. We need all the records so, used queryForAll()
            List<ContactTable> contactTable = contactDao.queryForAll();

            // Set the header of the ListView
            final LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            final View rowView = layoutInflater.inflate(R.layout.contacts_layout, contactListV, false);
            ((TextView)rowView.findViewById(R.id.contPren)).setText("Prenom");
            ((TextView)rowView.findViewById(R.id.contNom)).setText("Nom");
           //contactListV.addHeaderView(rowView);

            //Now, link the Adapter with the RecyclerView
            //Adapter
            ContactAdapter contactAdapter = new ContactAdapter(contactTable);
            contactListV.setAdapter( contactAdapter);

            // Attach OnItemLongClickListener and OnItemClickListener to track user action and perform accordingly
            /*contactListV.setOnItemLongClickListener(this;
            contactListV.setOnItemClickListener(this);*/

            // If, no record found in the database, appropriate message needs to be displayed.
            if(contactTable.size() == 0)
            {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Pas de contact");
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //add contact manually -> temporaire, le temps de pouvoir ajouter un contact
        /*ContactItem contactA = new ContactItem();
        contactA.id = 1;
        contactA.prenom = "Ateam";
        contactA.nom = "NourRich";
        contactItem.add(contactA);
        ContactItem contactB = new ContactItem();
        contactB.id = 2;
        contactB.prenom = "Bever";
        contactB.nom = "RichNour";
        contactItem.add(contactB);*/


        //Add contact Button
        addFABtn = (FloatingActionButton) view.findViewById(R.id.addFABtn);
        addFABtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouverture nouvelle addContactActivity
                Intent intent = new Intent(getActivity(), ContactAddActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    private DataBaseHelper getHelper() {
        if (dataBaseHelper == null) {
            dataBaseHelper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
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
