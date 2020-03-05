package com.example.spacebottomview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.R;
import com.example.spacebottomview.pojo_classes.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SpeckeyAdapter extends RecyclerView.Adapter<SpeckeyAdapter.ViewHolder> {
    Context context;
    ArrayList<Brand> product_list;
    String product_img;
    public SpeckeyAdapter(Context context, ArrayList<Brand> product_list){
        this.context=context;
        this.product_list=product_list;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.speckey_adapter, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.spec_lists.setText(product_list.get(i).spec_items);

    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView spec_lists;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spec_lists=itemView.findViewById(R.id.spec_lists);
        }
    }
}
