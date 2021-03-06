package com.hxh.permission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class PermissionActivity1 extends AppCompatActivity
{
    private static final String TAG = "permission_test";
    private static final int PERMISSION_REQUEST_CODE = 0x1;

    private Context mContext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission1);
    }

    public void get(View v)
    {
        Log.i(TAG, "checkSelfPermission-WRITE_EXTERNAL_STORAGE=" +
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED));
        Log.i(TAG, "checkSelfPermission-CAMERA=" +
                (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED));

        //Android M及以上处理权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //检查是否有写入SD卡的授权
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(mContext, "All permission is ok", Toast.LENGTH_SHORT).show();

                //执行操作
                //...
            }
            else
            {
                Toast.makeText(mContext, "All permission is not ok", Toast.LENGTH_SHORT).show();

                //弹出获取权限对话框
                //如果第二次弹出后，点击下次不再提醒后将不弹出获取权限对话框
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE);
            }
        }
        else
        {
            Toast.makeText(mContext, "No need permission", Toast.LENGTH_SHORT).show();

            //执行操作
            //...
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            //第二次弹出获取权限对话框，点击下次不再提醒后将返回false
            Log.i(TAG, "shouldShowRequestPermissionRationale-WRITE_EXTERNAL_STORAGE=" +
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE));
            Log.i(TAG, "shouldShowRequestPermissionRationale-CAMERA=" +
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA));

            boolean isPermissionOk = true;

            for (int index = 0; index < permissions.length; index++)
            {
                String permission = permissions[index];

                Log.i(TAG, "permission=" + permission);

                if (grantResults[index] != PackageManager.PERMISSION_GRANTED)
                {
                    isPermissionOk = false;

                    Log.i(TAG, "isPermissionOk=false---permission=" + permission);
                }
            }

            if (isPermissionOk)
            {
                Toast.makeText(mContext, "All permission is ok", Toast.LENGTH_SHORT).show();

                //执行操作
                //...
            }
            else if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1 &&
                    !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                    !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
            {
                new AlertDialog.Builder(this)
                        .setMessage("前往应用权限设置处设置权限")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Toast.makeText(mContext, "在该页面中点击“权限”进入，开启“相机”和“存储空间”权限\n(部分机型只有“相机”权限)", Toast.LENGTH_LONG).show();

                                //启动系统权限设置界面
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);

                                finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        })
                        .create()
                        .show();
            }
            else
            {
                Toast.makeText(mContext, "All permission is not ok", Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    }
}
