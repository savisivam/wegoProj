package com.example.spacebottomview.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.MainActivity;
import com.example.spacebottomview.R;
import com.example.spacebottomview.adapter.ProductAdapter;
import com.example.spacebottomview.interfaces.Api_Client;
import com.example.spacebottomview.pojo_classes.Brand;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment  implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    RecyclerView recyclerView;
    ArrayList<Brand> branded_list;
    SearchView searchView;
    ProductAdapter productAdapter;
    ProgressBar loadProgress;
    boolean fromSearch = false;
    public SearchFragment() {
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity())
                .setActionBarTitle("Shop Mobile");
        fetchJSON();
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        recyclerView =view.findViewById(R.id.recycler_view);
        searchView=view.findViewById(R.id.search_view);
        loadProgress =view.findViewById(R.id.progressBar);
        searchView.setQueryHint("search by Brands");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    //    fetchJSON();
        searchView.setOnQueryTextListener(this);

        return view;
    }
   public void fetchJSON() {
       Retrofit retrofit = new Retrofit.Builder()
               .baseUrl(Api_Client.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
               .build();
       Api_Client request = retrofit.create(Api_Client.class);
       Call<ResponseBody> call = request.getJSON();

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful() && response.body() != null){
                   String jsonresponse = null;
                   try {
                     loadProgress.setVisibility(View.GONE);
                       jsonresponse = response.body().string();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                   System.out.println("resposeee"+jsonresponse);
                   brand_details(jsonresponse);
               }else
               {
                   Log.i("onEmptyResponse", "Returned empty response");
               }

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               Log.d("Error",t.getMessage());
           }
       });
   }
    public void brand_details(String response){
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray filters_ary=obj.getJSONObject("meta").getJSONArray("filters");
            System.out.println("arrayyy"+filters_ary);
            if(filters_ary!=null) {
                for (int i = 0; i < filters_ary.length(); i++) {
                    JSONArray obj_brand = filters_ary.getJSONObject(1).getJSONArray("brand");
                    System.out.println("objjjj"+obj_brand);
                    branded_list = new ArrayList<>();
                    for (int j = 0; j < obj_brand.length(); j++) {
                        Brand brand = new Brand();
                        brand.brand_name = obj_brand.getJSONObject(j).getString("name");
                        branded_list.add(brand);
                        System.out.println("branddd"+branded_list);

                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
      productAdapter=new ProductAdapter(getActivity(),branded_list,false);
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        productAdapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        productAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }




}
