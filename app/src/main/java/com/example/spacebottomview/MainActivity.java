package com.example.spacebottomview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.spacebottomview.fragments.HomeFragment;
import com.example.spacebottomview.fragments.SearchFragment;
import com.example.spacebottomview.interfaces.OnBackPressed;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigation;
    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;
    private FragmentManager fragmentManager;
    Fragment fragment;
    String phone_brands;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation =findViewById(R.id.bottom_navigation);
        floatingActionButton=findViewById(R.id.fab);
        bottomAppBar=findViewById(R.id.bar);
        bottomNavigation.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        View view = bottomNavigation.findViewById(R.id.navigation_home);
        view.performClick();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        homeclickMethod();

        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        bottomNavigation.performClick();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                homeclickMethod();
                break;
            case R.id.navigation_search:
                fragment = new SearchFragment();
                break;
        }


        return loadFragment(fragment);
    }

    private void homeclickMethod() {
        fragment = null;
        fragment = new HomeFragment();
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}



