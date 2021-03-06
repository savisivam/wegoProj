package com.example.spacebottomview.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.R;
import com.example.spacebottomview.fragments.SpecKeyFrag;
import com.example.spacebottomview.pojo_classes.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BrandNameAdapter extends RecyclerView.Adapter<BrandNameAdapter.ViewHolder>{
    Context context;
    ArrayList<Brand> mobiles_list;

    public BrandNameAdapter(Context context, ArrayList<Brand> mobiles_list){
        this.context=context;
        this.mobiles_list=mobiles_list;

    }
    @NonNull
    @Override
    public BrandNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mobiles_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BrandNameAdapter.ViewHolder holder, final int i) {
       holder.mobile_name.setText(mobiles_list.get(i).product_name);
       holder.mobile_price.setText("Rs:"+String.valueOf(mobiles_list.get(i).product_price));
       Picasso.with(context)
                .load(String.valueOf(mobiles_list.get(i).product_image))
                .into(holder.phone_image);
        holder.mobilelist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                FragmentTransaction fragmentManager =((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction();
                bundle.putString("product_id",mobiles_list.get(i).product_id);
                bundle.putString("product_img",mobiles_list.get(i).product_image);
                bundle.putString("product_title",mobiles_list.get(i).product_name);
                SpecKeyFrag specKeyFrag=new SpecKeyFrag();
                specKeyFrag.setArguments(bundle);
                fragmentManager.replace(R.id.container, specKeyFrag);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mobiles_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         ImageView phone_image;
         TextView mobile_name,mobile_price;
         RelativeLayout mobilelist_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone_image=itemView.findViewById(R.id.phone_image);
            mobile_name=itemView.findViewById(R.id.mobile_name);
            mobile_price=itemView.findViewById(R.id.mobile_price);
            mobilelist_layout=itemView.findViewById(R.id.mobilelist_layout);

        }
    }
}
