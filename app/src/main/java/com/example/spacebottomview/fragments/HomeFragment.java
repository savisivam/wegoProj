package com.example.spacebottomview.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.spacebottomview.MainActivity;
import com.example.spacebottomview.R;
import com.example.spacebottomview.adapter.ImageSlideAdapter;
import com.example.spacebottomview.adapter.BrandNameAdapter;
import com.example.spacebottomview.adapter.ProductAdapter;
import com.example.spacebottomview.interfaces.Api_Client;
import com.example.spacebottomview.pojo_classes.Brand;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    RecyclerView recyclerView, brandlist_view;
    BrandNameAdapter brandNameAdapter;
    ArrayList<Brand> mobiles_list;
    ViewPager mPager;
    CircleIndicator indicator;
    ArrayList<Brand> branded_list;
    ProductAdapter productAdapter;
    ProgressBar loadProgress;
    private static int NUM_PAGES = 0;
    private static int currentPage = 0;
    private int[] XMEN = {R.drawable.imageslider1, R.drawable.imageslider2, R.drawable.imageslider3, R.drawable.imageslider4, R.drawable.imageslider5};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    boolean fromHome = false;
    RelativeLayout loader;
    ImageView giff;

    @Override
    public void onResume() {
        ((MainActivity) getActivity())
                .setActionBarTitle("Shop Mobile");
        Log.e("onResume", "onResume");
        fetchJSON();
        fetchMobile();

        super.onResume();

    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        brandlist_view = view.findViewById(R.id.brandlist_view);
        brandlist_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        loadProgress = view.findViewById(R.id.progressBar);
        init(view);

        return view;
    }

    private void init(View v) {
        mPager = v.findViewById(R.id.pager);
        indicator = v.findViewById(R.id.indicator);
        mPager.setAdapter(new ImageSlideAdapter(getActivity()));
        indicator.setViewPager(mPager);
        NUM_PAGES = XMEN.length;
        // Auto start of viewpager

        final Handler handler = new Handler();
        final Runnable Update =
                new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 4000, 4000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
                if (response.isSuccessful() && response.body() != null) {
                    String jsonresponse = null;
                    try {
                        loadProgress.setVisibility(View.GONE);
                        jsonresponse = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("resposeee" + jsonresponse);
                    brand_details(jsonresponse);
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

    public void brand_details(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray filters_ary = obj.getJSONObject("meta").getJSONArray("filters");
            System.out.println("arrayyy" + filters_ary);
            if (filters_ary != null) {
                for (int i = 0; i < filters_ary.length(); i++) {
                    JSONArray obj_brand = filters_ary.getJSONObject(1).getJSONArray("brand");
                    System.out.println("objjjj" + obj_brand);
                    branded_list = new ArrayList<>();
                    for (int j = 0; j < obj_brand.length(); j++) {
                        Brand brand = new Brand();
                        brand.brand_name = obj_brand.getJSONObject(j).getString("name");
                        branded_list.add(brand);
                        System.out.println("branddd" + branded_list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        fromHome = true;
        productAdapter = new ProductAdapter(getActivity(), branded_list, fromHome);
        brandlist_view.setAdapter(productAdapter);
    }


    public void fetchMobile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api_Client.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api_Client request = retrofit.create(Api_Client.class);
        Call<ResponseBody> call = request.getMobile();
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
                    System.out.println("resposeee" + jsonresponse);
                    productDetails(jsonresponse);
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

    public void productDetails(String response) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray prod_array = obj.getJSONArray("data");
            System.out.println("productttt" + prod_array);
            if (prod_array != null) {
                mobiles_list = new ArrayList<>();
                for (int i = 0; i < prod_array.length(); i++) {
                    Brand brand = new Brand();
                    brand.product_id = prod_array.getJSONObject(i).getString("product_id");
                    brand.product_name = prod_array.getJSONObject(i).getString("product_title");
                    brand.product_price = prod_array.getJSONObject(i).getString("product_lowest_price");
                    brand.product_image = prod_array.getJSONObject(i).getString("product_image");
                    mobiles_list.add(brand);
                    System.out.println("mobileeee" + mobiles_list);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        brandNameAdapter = new BrandNameAdapter(getActivity(), mobiles_list);
        recyclerView.setAdapter(brandNameAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}


