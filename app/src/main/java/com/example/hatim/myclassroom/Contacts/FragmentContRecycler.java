package com.example.hatim.myclassroom.Contacts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.hatim.myclassroom.R;

import java.util.ArrayList;


/**
 * Created by Richard on 28/05/2016.
 */
public class FragmentContRecycler extends Fragment {

    RecyclerView contactListV;
    Adapter adapterContRecycler;

    ArrayList<ContactItem> contList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cont_recycler, container, false);

        adapterContRecycler = (Adapter) new AdapterContRecycler(contList);
        contactListV.setAdapter((RecyclerView.Adapter) adapterContRecycler);
        contactListV.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }


}
