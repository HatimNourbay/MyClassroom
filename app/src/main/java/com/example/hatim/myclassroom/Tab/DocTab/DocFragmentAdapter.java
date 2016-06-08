package com.example.hatim.myclassroom.Tab.DocTab;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.example.hatim.myclassroom.DocumentChoosing.DocAdapter;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.DocumentHelper.DocumentItemDB;
import com.example.hatim.myclassroom.MainActivity;
import com.example.hatim.myclassroom.R;

import org.w3c.dom.DocumentFragment;

import java.util.ArrayList;


public class DocFragmentAdapter extends DocAdapter {

    private MultiSelector mMultiSelector;
    SlidingHelper slideHelper;

    public DocFragmentAdapter(ArrayList<Document> oncomingDocs, MultiSelector multiselectFrag) {
        super(oncomingDocs, multiselectFrag);

        mMultiSelector = multiselectFrag;
    }

    @Override
    public DoclHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_layout, parent, false);

        return new DoclHolder(itemView,mMultiSelector);
    }

    public class DoclHolder extends DocAdapter.DocHolder{

        public DoclHolder(View itemView, MultiSelector multiSelector) {
            super(itemView, multiSelector);
        }


        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {

            //((AppCompatActivity) getActivity()).startSupportActionMode(mDeleteMode);

            if (!slideHelper.isPanelShown()){
                slideHelper.slideUpDown(slideHelper.viewSlide);
            }
            mMultiSelector.setSelected(this,true);
            return true;
        }
    }
}

