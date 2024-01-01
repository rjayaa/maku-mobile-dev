package com.project.maku_mobile_based;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.maku_mobile_based.model.Tenants;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String Tag = "RecyclerView";
    private Context tContext ;
    private ArrayList<Tenants> tenantlist;

    public RecyclerAdapter(Context tContext, ArrayList<Tenants> tenantlist) {
        this.tContext = tContext;
        this.tenantlist = tenantlist;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenants_item,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textCategory.setText(tenantlist.get(position).getTenantsCategory());
        holder.textName.setText(tenantlist.get(position).getTenantsName());
        Glide.with(tContext).load(tenantlist.get(position).getTenantsUrlImage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return tenantlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        // Widgets

        ImageView imageView;
        TextView textName, textCategory;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.txtcategory);
            textName = itemView.findViewById(R.id.txtname);
            imageView = itemView.findViewById(R.id.imageView);


        }
    }
}
