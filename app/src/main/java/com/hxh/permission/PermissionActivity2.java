package com.hxh.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionActivity2 extends AppCompatActivity
{
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission2);
    }

    public void get1(View v)
    {
        getPermission1();
    }

    public void get2(View v)
    {
        getPermission2();
    }

    @SuppressLint("CheckResult")
    private void getPermission1()
    {
        //监听具体的某一个权限是否进行了授权
        new RxPermissions(this)
                .requestEach(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new io.reactivex.functions.Consumer<Permission>()
                {
                    @Override
                    public void accept(Permission permission)
                    {
                        if (permission.granted)
                        {
                            Toast.makeText(mContext, "All permission is ok", Toast.LENGTH_SHORT).show();

                            //用户已经同意权限
                            //执行操作
                            //...
                        }
                        else if (permission.shouldShowRequestPermissionRationale)
                        {
                            //用户拒绝了该权限，没有选中『不再询问』,再次启动时，还会提示请求权限的对话框
//                            Toast.makeText(mContext, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "All permission is not ok", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                        else
                        {
                            //用户拒绝了该权限，并且选中『不再询问』
                            //启动系统权限设置界面
                            Toast.makeText(mContext, "在该页面中点击“权限”进入，开启“电话”权限", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);

                            finish();
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getPermission2()
    {
        //监听所有权限是否进行了授权
        new RxPermissions(this)
                .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>()
                {
                    @Override
                    public void accept(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            Toast.makeText(mContext, "All permission is ok", Toast.LENGTH_SHORT).show();

                            //用户已经同意权限
                            //执行操作
                            //...
                        }
                        else
                        {
                            //只要有一个权限被拒绝，就会执行
//                            Toast.makeText(mContext, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "All permission is not ok", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });
    }
}
