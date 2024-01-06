package com.project.maku_mobile_based.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.maku_mobile_based.R;
import com.project.maku_mobile_based.TenantRecyclerViewInterface;
import com.project.maku_mobile_based.model.Tenants;

import java.util.ArrayList;

public class TenantRecycleAdapter extends RecyclerView.Adapter<TenantRecycleAdapter.ViewHolder> implements Filterable {
    private final TenantRecyclerViewInterface tenantRecyclerViewInterface;

    private ArrayList<Tenants> tenantlistFull;
    private static final String Tag = "RecyclerView";
    private Context tContext ;
    private ArrayList<Tenants> tenantlist;

    public TenantRecycleAdapter(Context tContext, ArrayList<Tenants> tenantlist, TenantRecyclerViewInterface tenantRecyclerViewInterface) {
        this.tContext = tContext;
        this.tenantlist = tenantlist;
        this.tenantRecyclerViewInterface = tenantRecyclerViewInterface;
        tenantlistFull = new ArrayList<>(tenantlist);
    }

    @NonNull
    @Override
    public TenantRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return  new ViewHolder(view, tenantRecyclerViewInterface);
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

    @Override
    public Filter getFilter() {
        return tenantFilter;
    }


    private Filter tenantFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Tenants> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(tenantlistFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Tenants item : tenantlistFull) {
                    if (item.getTenantsName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            tenantlist.clear();
            tenantlist.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder{

        // Widgets

        ImageView imageView;
        TextView textName, textCategory, textDescription;

        public ViewHolder(@NonNull View itemView, TenantRecyclerViewInterface tenantRecyclerViewInterface) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.txtcategory);
            textName = itemView.findViewById(R.id.txtname);
            imageView = itemView.findViewById(R.id.imageView);
            textDescription = itemView.findViewById(R.id.txtDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tenantRecyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            tenantRecyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

