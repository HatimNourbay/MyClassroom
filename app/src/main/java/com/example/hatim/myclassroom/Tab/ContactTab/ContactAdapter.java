package com.example.hatim.myclassroom.Tab.ContactTab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatim.myclassroom.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Richard on 28/05/2016.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolderContact> {

    public List<ContactTable> contactTable;
    public Context context;


    public class ViewHolderContact extends RecyclerView.ViewHolder{

        public ImageView photo;
        public TextView prenom;
        public TextView nom;
        public RecyclerView contactRecyclerView;

        public ViewHolderContact(View itemView) {
            super(itemView);

            photo = (ImageView) itemView.findViewById(R.id.contImg);
            prenom = (TextView) itemView.findViewById(R.id.contPren);
            nom = (TextView) itemView.findViewById(R.id.contNom);
            contactRecyclerView = (RecyclerView) itemView.findViewById(R.id.cont_list);


        }
    }

    @Override
    public void onBindViewHolder(ViewHolderContact holder, int position) {

        ContactTable contact = contactTable.get(position);
        Picasso.with(holder.photo.getContext())
                .load(R.drawable.ic_account_circle_white_48dp)
                .into(holder.photo);
        holder.prenom.setText(contact.prenom);
        holder.nom.setText(contact.nom);
    }

    @Override
    public ViewHolderContact onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_layout, parent, false);
        return new ViewHolderContact(itemView);
    }

    @Override
    public int getItemCount() {
        return contactTable.size();
    }

    public ContactAdapter(List<ContactTable> contactItems) {
        this.contactTable = contactItems;
    }

}
