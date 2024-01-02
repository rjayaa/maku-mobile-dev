package com.project.maku_mobile_based;

import android.content.Context;
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

public class TenantRecycleAdapter extends RecyclerView.Adapter<TenantRecycleAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    private static final String Tag = "RecyclerView";
    private Context tContext ;
    private ArrayList<Tenants> tenantlist;

    public TenantRecycleAdapter(Context tContext, ArrayList<Tenants> tenantlist, RecyclerViewInterface recyclerViewInterface) {
        this.tContext = tContext;
        this.tenantlist = tenantlist;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public TenantRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenants_item,parent,false);
        return  new ViewHolder(view,recyclerViewInterface);
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
        TextView textName, textCategory, textDescription;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.txtcategory);
            textName = itemView.findViewById(R.id.txtname);
            imageView = itemView.findViewById(R.id.imageView);
            textDescription = itemView.findViewById(R.id.txtDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

