package com.example.spacebottomview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ifPermissionGranted()) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
                    final AlertDialog alert=alertDialog.create();
                    alertDialog.setTitle("Info")
                               .setMessage("No internet Connection.Please check your Internet Connection")
                               .setIcon(R.drawable.alert_symbol)
                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   if (ifPermissionGranted()){
                                       Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                       startActivity(i);
                                       finish();
                                   }
                                }
                            });
                    alert.show();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            //finish();
                        }
                    }
                },5000);
            }


            public boolean ifPermissionGranted() {
                ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                    Toast.makeText(SplashActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                    return false;
                }else {
                    return true;
                }

            }
        }


