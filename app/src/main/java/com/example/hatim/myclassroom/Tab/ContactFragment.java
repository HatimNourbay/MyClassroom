package com.example.hatim.myclassroom.Tab;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hatim.myclassroom.R;

import java.util.ArrayList;


/**
 * Created by Richard on 28/05/2016.
 */
public class ContactFragment extends Fragment {

    public ArrayList<ContactItem> contactItem = new ArrayList<ContactItem>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_cont_recycler, container, false);
        RecyclerView contactListV = (RecyclerView) view.findViewById(R.id.cont_list);
        contactListV.setHasFixedSize(true); // pour garder la même taille
        contactListV.setLayoutManager(new LinearLayoutManager(getActivity())); // détecte les changements de données et adapte

        //add contact manually -> temporaire, le temps de pouvoir ajouter un contact
        ContactItem contactA = new ContactItem();
        contactA.id = 1;
        contactA.prenom = "Ateam";
        contactA.nom = "NourRich";
        contactItem.add(contactA);
        ContactItem contactB = new ContactItem();
        contactB.id = 2;
        contactB.prenom = "Bever";
        contactB.nom = "RichNour";
        contactItem.add(contactB);

        ContactAdapter contactAdapter = new ContactAdapter(contactItem);
        contactListV.setAdapter(contactAdapter);
        return view;
    }


}
