package com.example.ralapchi.myproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralapchi on 2017/5/11.
 */

public class Fragment_shouye extends Fragment{
    // 工具栏
    private Toolbar toolbar;
    // 导航布局
    private TabLayout tabLayout;
    // 视图对象
    private ViewPager viewPager;
    // 自定义类，导航布局的适配器
    private TabAdaper tabAdaper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shouye,container,false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout)view.findViewById(R.id.tabLayout);
        initview();

        // fragment的适配器类

        return view;
    }
    public void initview(){
        tabAdaper = new TabAdaper(getFragmentManager());
        // 设置适配器
        viewPager.setAdapter(tabAdaper);
        // 直接绑定viewpager，消除了以前的需要设置监听器的繁杂工作
        tabLayout.setupWithViewPager(viewPager);
    }
    class TabAdaper extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<>();
        // 标题数组
        String[] titles = {"首页", "频道"};

        public TabAdaper(FragmentManager fm) {
            super(fm);
            fragmentList.add(new Fragment_tuijian());
            fragmentList.add(new Fragment_pindao());


        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
