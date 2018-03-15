package com.example.ralapchi.myproject;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.String;


public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=top&key=9b66b37a560ac68ca037dfdec41207e0";
    private Fragment_shouye fg1;
    private Fragment_shipin fg2;
    private Fragment_wode fg3;


    //RadioGroup
    private RadioGroup radiogroup;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment1();


    }

    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //初始化fragment并添加到事务中，如果为null就new一个
        if (fg1 == null) {
            fg1 = new Fragment_shouye();
            transaction.add(R.id.main_frame_layout, fg1);
        }
        //隐藏所有fragment

        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(fg1);


        //提交事务
        transaction.commit();
    }

    //显示第二个fragment
    private void initFragment2() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fg2 == null) {
            fg2 = new Fragment_shipin();
            transaction.add(R.id.main_frame_layout, fg2);
        }
        hideFragment(transaction);
        transaction.show(fg2);


        transaction.commit();
    }

    //显示第三个fragment
    private void initFragment3() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fg3 == null) {
            fg3 = new Fragment_wode();
            transaction.add(R.id.main_frame_layout, fg3);
        }
        hideFragment(transaction);
        transaction.show(fg3);


        transaction.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (fg1 != null) {
            transaction.hide(fg1);
        }
        if (fg2 != null) {
            transaction.hide(fg2);
        }
        if (fg3 != null) {
            transaction.hide(fg3);
        }
    }

    private void initView() {

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_shouye:
                        initFragment1();
                        break;
                    case R.id.rb_shipin:
                        initFragment2();
                        break;
                    case R.id.rb_wode:
                        initFragment3();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}