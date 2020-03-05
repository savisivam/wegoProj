package com.example.spacebottomview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.spacebottomview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageSlideAdapter extends PagerAdapter {
    private  int[] XMEN = {R.drawable.imageslider1, R.drawable.imageslider2, R.drawable.imageslider3, R.drawable.imageslider4, R.drawable.imageslider5};
    private LayoutInflater inflater;
    private Context context;

    public ImageSlideAdapter(Context context) {
        this.context = context;
       /* this.images=images;
        inflater = LayoutInflater.from(context);*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return XMEN.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      //  View view = inflater.from(container.getContext()).inflate(R.layout.sliding_images, null);

        View view = inflater.from(container.getContext()).inflate(R.layout.slide_view, null);
        ImageView myImage = (ImageView) view
                .findViewById(R.id.image);
        myImage.setImageResource(XMEN[position]);
        ViewPager viewPager = (ViewPager) container;


        viewPager.addView(view,0);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
