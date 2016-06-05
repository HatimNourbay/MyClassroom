package com.example.hatim.myclassroom.DocumentChoosing;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.example.hatim.myclassroom.DocumentHelper.Document;
import com.example.hatim.myclassroom.R;

import java.util.ArrayList;


public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocHolder> {

    ArrayList<Document> docList;
    ArrayList<Document> docSelected = new ArrayList<>();

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
        holder.GoodName = docItem.nameDoc;
        holder.docItemHolded = docItem;
    }

    @Override
    public int getItemCount() {
        return docList.size();
    }

    public class DocHolder extends SwappingHolder implements View.OnClickListener, View.OnLongClickListener{

        public ImageView docTypeImage;
        public TextView docName;
        public String GoodName;
        public Document docItemHolded;

        public DocHolder(View itemView, MultiSelector multiSelector) {
            super(itemView, multiSelector);
            docTypeImage = (ImageView)itemView.findViewById(R.id.docDrawable);
            docName = (TextView)itemView.findViewById(R.id.docDescription);
            setSelectionModeBackgroundDrawable(setColorActivation(itemView.getContext()));

            itemView.setOnClickListener(this);
            itemView.setLongClickable(true);
            itemView.setOnLongClickListener(this);

            mMultiSelector.setSelectable(true);

        }

        @Override
        public void onClick(View v) {
            Log.wtf("NameSelect", GoodName) ;

            if (mMultiSelector.isSelected(getAdapterPosition(),getItemId())){
                mMultiSelector.setSelected(this,false);
                docSelected.remove(docItemHolded);
            }
            else{
                mMultiSelector.setSelected(this,true);
                docSelected.add(docItemHolded);
            }

        }

        @Override
        public boolean onLongClick(View v) {
            Context context = v.getContext();
            return true;

        }
        private Drawable setColorActivation(Context context) {


            Drawable colorDrawable = new ColorDrawable(ContextCompat.getColor(context,R.color.colorActivated));

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_activated}, colorDrawable);
            stateListDrawable.addState(StateSet.WILD_CARD, null);

            return stateListDrawable;
        }

    }
}
