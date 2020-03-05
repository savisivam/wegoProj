package com.example.spacebottomview.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacebottomview.MainActivity;
import com.example.spacebottomview.R;
import com.example.spacebottomview.adapter.MobileNameAdapter;
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

public class MobileBrandFrag extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Brand> brand_datas;
    String phone_brands;
    MobileNameAdapter mobileNameAdapter;
    ProgressBar loadProgress;
    public MobileBrandFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_brand_frag, container, false);
        recyclerView=view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        Bundle b=getArguments();
        phone_brands = b.getString("phone_brands");

        System.out.println("phone_brands"+phone_brands);
        loadProgress =view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity())
                .setActionBarTitle(phone_brands);
        mobileBrandlist();
        super.onResume();

    }

    public void mobileBrandlist(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_Client.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_Client request = retrofit.create(Api_Client.class);
        Call<ResponseBody> call = request.getUsers("https://price-api.datayuge.com/api/v1/compare/search?api_key=66g87FrMlv0eOx1z2G5FsGnkl1s29NEGUNm&product="+phone_brands);
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
                    System.out.println("responseee"+jsonresponse);
                     brandedMobile(jsonresponse);
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
    public void brandedMobile(String response){
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray mobile_list=obj.getJSONArray("data");
            System.out.println("productttt"+mobile_list);
            if(mobile_list!=null){
                brand_datas=new ArrayList<>();
                for(int i=0;i<mobile_list.length();i++){
                    Brand brand=new Brand();
                    brand.product_id=mobile_list.getJSONObject(i).getString("product_id");
                    brand.product_name=mobile_list.getJSONObject(i).getString("product_title");
                    brand.product_price=mobile_list.getJSONObject(i).getString("product_lowest_price");
                    brand.product_image=mobile_list.getJSONObject(i).getString("product_image");
                    brand_datas.add(brand);
                    System.out.println("mobileeee"+brand_datas);
                    }
                }
        }catch (Exception e){
            e.printStackTrace();
        }
        mobileNameAdapter=new MobileNameAdapter(getActivity(),brand_datas);
        recyclerView.setAdapter(mobileNameAdapter);
    }

}