package com.hxh.permission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void na(View v)
    {
        startActivity(new Intent(mContext,PermissionActivity1.class));
    }

    public void rx(View v)
    {
        startActivity(new Intent(mContext,PermissionActivity2.class));
    }
}
