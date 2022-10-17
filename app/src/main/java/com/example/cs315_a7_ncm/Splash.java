package com.example.cs315_a7_ncm;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity
{
    Handler mHandler;
    Runnable mRunnable;

    ModelsJunk modelJ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        modelJ = new ModelsJunk();
        modelJ.JSONParse(this);

        mHandler = new Handler(Looper.getMainLooper());
        mRunnable = () ->
        {
            Intent intent = new Intent(Splash.this, ItemDetailHostActivity.class);
            startActivity(intent);
            finish();
        };

        mHandler.postDelayed(mRunnable,5500);
    }
}