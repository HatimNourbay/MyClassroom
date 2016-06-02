package com.example.hatim.myclassroom.DocRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.example.hatim.myclassroom.R;

import java.util.ArrayList;

/**
 * Created by Hatim on 31/05/2016.
 */
public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocHolder> {

    ArrayList<Document> docList;

    private MultiSelector mMultiSelector;


    public DocAdapter(ArrayList<Document> oncomingDocs, MultiSelector multiselectFrag){
        mMultiSelector = multiselectFrag;
        docList = oncomingDocs;
    }
    @Override
    public DocHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.document_layout, parent, false);

        return new DocHolder(itemView,mMultiSelector);
    }

    @Override
    public void onBindViewHolder(DocHolder holder, int position) {
        Document docItem = docList.get(position);
        holder.docName.setText(docItem.nameDoc);
        holder.docTypeImage.setImageResource(docItem.imageDoc);
        holder.Goodname = docItem.nameDoc;
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    public class DocHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener{


        public Document document;
        public ImageView docTypeImage;
        public TextView docName;
        public String Goodname;

        public DocHolder(View itemView, MultiSelector multiSelector) {
            super(itemView, multiSelector);
            docTypeImage = (ImageView)itemView.findViewById(R.id.docDrawable);
            docName = (TextView)itemView.findViewById(R.id.docDescription);
            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);

            mMultiSelector.setSelectable(true);

            setSelectionModeBackgroundDrawable();
        }

        @Override
        public void onClick(View v) {
            /*if (document == null) {
                return;
            }
            if (!multiSelector.tapSelection(this)) {
                // start an instance of CrimePagerActivity
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                i.putExtra(DocFragment.EXTRA_CRIME_ID, c.getId());
                startActivity(i);
            }*/
            Log.wtf("NameSelect",Goodname) ;

            if (mMultiSelector.isSelected(getAdapterPosition(),getItemId())){
                mMultiSelector.setSelected(this,false);
            }
            else{
                mMultiSelector.setSelected(this,true);
            }

        }

        @Override
        public boolean onLongClick(View v) {
            Context context = v.getContext();
            return true;


        }

    }
}
