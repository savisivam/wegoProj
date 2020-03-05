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
import com.example.spacebottomview.fragments.MobileBrandFrag;
import com.example.spacebottomview.fragments.SpecKeyFrag;
import com.example.spacebottomview.pojo_classes.Brand;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobileNameAdapter extends RecyclerView.Adapter<MobileNameAdapter.ViewHolder> {
    Context context;
    ArrayList<Brand> brand_datas;

    public MobileNameAdapter(Context context, ArrayList<Brand> brand_datas){
        this.context=context;
        this.brand_datas=brand_datas;

    }
    @NonNull
    @Override
    public MobileNameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mobilename_list, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MobileNameAdapter.ViewHolder holder,final int i) {
        holder.mobile_name.setText(brand_datas.get(i).product_name);
        holder.mobile_price.setText("Rs:"+String.valueOf(brand_datas.get(i).product_price));
        Picasso.with(context)
                .load(String.valueOf(brand_datas.get(i).product_image))
                .into(holder.phone_image);
        holder.mobile_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                FragmentTransaction fragmentManager =((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction();
                SpecKeyFrag specKeyFrag=new SpecKeyFrag();
                bundle.putString("product_id",brand_datas.get(i).product_id);
                bundle.putString("product_img",brand_datas.get(i).product_image);
                bundle.putString("product_title",brand_datas.get(i).product_name);
                specKeyFrag.setArguments(bundle);
                fragmentManager.replace(R.id.container, specKeyFrag);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();
            }
        });    }

    @Override
    public int getItemCount() {
        return brand_datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView phone_image;
        TextView mobile_name,mobile_price;
        RelativeLayout mobile_views;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone_image=itemView.findViewById(R.id.mob_img);
            mobile_name=itemView.findViewById(R.id.brandata_name);
            mobile_price=itemView.findViewById(R.id.brandata_prices);
            mobile_views=itemView.findViewById(R.id.mobile_views);
        }
    }
}
