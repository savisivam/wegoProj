package com.example.spacebottomview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.R;
import com.example.spacebottomview.fragments.MobileBrandFrag;
import com.example.spacebottomview.pojo_classes.Brand;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {
    Context context;
    ArrayList<Brand> branded_list;
    private ArrayList<Brand> filtered_arraylist;
    boolean fromHome = false;
    BottomNavigationView bottomNavigation;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    public ProductAdapter(Context context, ArrayList<Brand> branded_list,boolean fromHome) {
        this.context = context;
        this.branded_list = branded_list;
        this.filtered_arraylist = branded_list;
        this.fromHome = fromHome;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (fromHome){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_list, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.brand_newlist1, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int i) {

        holder.brand_name.setText(filtered_arraylist.get(i).brand_name);
        holder.brandlist_layout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

               Bundle bundle=new Bundle();
                FragmentTransaction fragmentManager =((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction();
               bundle.putString("phone_brands",filtered_arraylist.get(i).brand_name);
                MobileBrandFrag brandFrag=new MobileBrandFrag();
                brandFrag.setArguments(bundle);
                fragmentManager.replace(R.id.container, brandFrag).addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return filtered_arraylist.size();
        //branded_list== null ? 0 : branded_list.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filtered_arraylist = branded_list;
                } else {
                    ArrayList<Brand> filteredList = new ArrayList<>();
                    for (Brand brand : branded_list) {
                        if (brand.getBrand_name().toLowerCase().trim().contains(charString.toLowerCase())) {
                            filteredList.add(brand);
                        }
                        filtered_arraylist = filteredList;
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered_arraylist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filtered_arraylist = (ArrayList<Brand>) results.values;
                notifyDataSetChanged();
            }
        };
    }

   public class ViewHolder extends RecyclerView.ViewHolder {
        TextView brand_name;
        RelativeLayout brandlist_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            brand_name=itemView.findViewById(R.id.branded_name);
            brandlist_layout=itemView.findViewById(R.id.brandlist_layout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

