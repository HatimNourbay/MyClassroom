package com.example.hatim.myclassroom.Drawer;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hatim.myclassroom.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hatim on 23/05/2016.
 */


public class SidePanelAdapter extends RecyclerView.Adapter<SidePanelAdapter.MyViewHolder> {
    List<DrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    AssetManager assetMan;

    public SidePanelAdapter(Context context, List<DrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        assetMan = context.getAssets();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drawer_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DrawerItem current = data.get(position);
        holder.title.setText(current.ItemName);
        holder.imageView.setImageResource(current.imgResID);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.itemName);
            title.setPadding(0,25,0,0);
            imageView = (ImageView) itemView.findViewById(R.id.iconItem);
        }

    }
}
