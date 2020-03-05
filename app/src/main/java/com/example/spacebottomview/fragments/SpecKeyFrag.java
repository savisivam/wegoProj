package com.example.spacebottomview.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.MainActivity;
import com.example.spacebottomview.R;
import com.example.spacebottomview.adapter.MobileNameAdapter;
import com.example.spacebottomview.adapter.OnlineDetailAdapter;
import com.example.spacebottomview.adapter.SpeckeyAdapter;
import com.example.spacebottomview.interfaces.Api_Client;
import com.example.spacebottomview.pojo_classes.Brand;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecKeyFrag extends Fragment {
    ArrayList<Brand> product_list;
    ArrayList<Brand> onlinebrand_details;
    String product_id, product_img,prod_names;
    RecyclerView specview, online_detail_view;
    ImageView imageView,sold_img;
    ProgressBar loadProgress;
    TextView phone_names;
    public SpecKeyFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speckey_frag, container, false);
        Bundle b = getArguments();
        product_id = b.getString("product_id");
        product_img = b.getString("product_img");
        prod_names = b.getString("product_title");
        System.out.println("phone_brands" + product_id);
        System.out.println("aaaaaaaa" + product_img);
    //    specview = view.findViewById(R.id.spec_list);

        online_detail_view=view.findViewById(R.id.online_detail_view);
        online_detail_view.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        online_detail_view.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        imageView = view.findViewById(R.id.spec_img);
        loadProgress=view.findViewById(R.id.progressBar);
        sold_img=view.findViewById(R.id.sold_img);
        phone_names=view.findViewById(R.id.phone_names);
        phone_names.setText(prod_names);
//        specview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Picasso.with(getActivity())
                .load(product_img)
                .into(imageView);

        return view;
    }

    public void fetchSpecVal() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_Client.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_Client request = retrofit.create(Api_Client.class);
        Call<ResponseBody> call = request.getUsers("https://price-api.datayuge.com/api/v1/compare/specs?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm&id=" + product_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonresponse = null;
                    try {
                        loadProgress.setVisibility(View.GONE);
                        jsonresponse = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("responseee" + jsonresponse);
                    popularBrands(jsonresponse);
                } else {
                    Log.i("onEmptyResponse", "Returned empty response");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity())
                .setActionBarTitle(prod_names);
        super.onResume();
    //    fetchSpecVal();
        fetchOnlineDetails();
    }

    public void popularBrands(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray popular_brand = obj.getJSONObject("data").getJSONArray("main_specs");
            product_list = new ArrayList<>();
            for (int i = 0; i < popular_brand.length(); i++) {
                Brand brand = new Brand();
                brand.spec_items = popular_brand.getString(i);
                product_list.add(brand);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeckeyAdapter speckeyAdapter = new SpeckeyAdapter(getActivity(), product_list);
        specview.setAdapter(speckeyAdapter);
    }



    public void fetchOnlineDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_Client.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_Client request = retrofit.create(Api_Client.class);
        Call<ResponseBody> call = request.getUsers("https://price-api.datayuge.com/api/v1/compare/detail?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm&id=" + product_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String jsonresponse = null;
                    try {
                        loadProgress.setVisibility(View.GONE);
                        online_detail_view.setVisibility(View.VISIBLE);
                        jsonresponse = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("responseee" + jsonresponse);
                    onlineDetails(jsonresponse);
                } else {
                    sold_img.setVisibility(View.VISIBLE);
                    Log.i("onEmptyResponse", "Returned empty response");

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public void onlineDetails(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            System.out.println("response12"+response);
            JSONObject data_obj = obj.getJSONObject("data");
            JSONArray online_brand = data_obj.has("stores") ? data_obj.getJSONArray("stores") : new JSONArray();
            onlinebrand_details = new ArrayList<>();
            boolean issold=true;
            for (int i1 = 0; i1 < online_brand.length(); i1++) {
                JSONObject store = online_brand.getJSONObject(i1);
                System.out.println("oidfsdgh"+store);
                String itr = store.keys().next();
                if (!TextUtils.isEmpty(itr)) {
                    System.out.println("itr" + itr);
                    if (store.get(itr) instanceof JSONArray) {
                        JSONArray jsonArray = new JSONArray(String.valueOf(store.get(itr)));
                        if(issold)  {
                            sold_img.setVisibility(View.VISIBLE);
                        }
                         System.out.println("jsonnnn" + jsonArray);
                         } else{
//                        JSONObject condObject = new JSONObject(/*String.valueOf(store.get(itr))*/);
//                        condObject = store.getJSONObject("");
                          issold=false;
                          for(int j=0;j<store.length();j++){

                            Brand brand=new Brand();
                            JSONObject condObject = new JSONObject(String.valueOf(store.get(itr)));
                            System.out.println("amazonnn"+condObject);
                            brand.logo=condObject.getString("product_store_logo");
                            brand.online_price_rate=condObject.getString("product_price");
                            brand.product_rate=condObject.getString("product_mrp");
                            brand.delivery_date=condObject.getString("product_delivery");
                            brand.delivery_cost=condObject.getString("product_delivery_cost");
                            brand.prod_url=condObject.getString("product_store_url");
                            System.out.println("conddd"+condObject);
                            onlinebrand_details.add(brand);
                            online_detail_view.setVisibility(View.VISIBLE);

                        }

                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
         OnlineDetailAdapter onlineDetailAdapter=new OnlineDetailAdapter(getActivity(),onlinebrand_details);
         online_detail_view.setAdapter(onlineDetailAdapter);

    }
}
