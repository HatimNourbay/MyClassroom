package com.example.hatim.myclassroom.Contacts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatim.myclassroom.R;

import java.util.ArrayList;

/**
 * Created by Richard on 28/05/2016.
 */
public class AdapterContRecycler extends RecyclerView.Adapter<AdapterContRecycler.VewHolderContact> {

    private ArrayList<ContactItem> contactItems;

    public class VewHolderContact extends RecyclerView.ViewHolder{

        public ImageView photo;
        public TextView prenom;
        public TextView nom;

        public VewHolderContact(View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.contImg);
            prenom = (TextView) itemView.findViewById(R.id.contPren);
            nom = (TextView) itemView.findViewById(R.id.contNom);

        }
    }
    public AdapterContRecycler(ArrayList<ContactItem> contactItems) {
        this.contactItems = contactItems;
    }

    @Override
    public VewHolderContact onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_layout, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(VewHolderContact holder, int position) {

        ContactItem contact = contactItems.get(position);
        //holder.photo.setImage -> voir comment on prend les photos dans le tel
        holder.prenom.setText(contact.prenom);
        holder.nom.setText(contact.nom);
    }

    @Override
    public int getItemCount() {
        return contactItems.size();
    }


}
