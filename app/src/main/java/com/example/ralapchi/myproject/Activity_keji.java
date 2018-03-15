package com.example.ralapchi.myproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.news_entity.News;


import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ralapchi on 2017/4/26.
 */

public class Activity_keji extends SwipeBackActivity {
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=keji&key=9b66b37a560ac68ca037dfdec41207e0";
    /**
     * ListView对象
     */
    private ListView listview;
    /**
     * 新闻集合对象
     */
    private List<News> datas;
    /**
     * 自定义的Adapter对象
     */
    private MyAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keji);
        initview();

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swiperefresh_keji);
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        swipeRefreshLayout.setProgressViewOffset(true, 50, 150);
        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // 通过 setEnabled(false) 禁用下拉刷新
        //swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 刷新动画开始后回调到此方法
                        getDatas();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }
        );
        datas = new ArrayList<News>();
        getDatas();
        initData();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_keji.this,NewsInfoActivity.class);
                intent.putExtra("title",datas.get(position).getTitle());
                intent.putExtra("newsUrl", datas.get(position).getUrl());
                //Log.i("str","click");
                startActivity(intent);//启动Activity
            }

        });

    }
    public void initview(){
        listview = (ListView)findViewById(R.id.lv_keji);
    }
    private void initData() {
        //Log.i("str","initdata");
        adapter = new MyAdapter(this, datas);
        listview.setAdapter(adapter);

    }


    public void getDatas() {
        final RequestQueue mQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject object = JSON.parseObject(response);
                        JSONObject result = object.getJSONObject("result");
                        JSONArray data = result.getJSONArray("data");
                        datas.addAll(JSON.parseArray(data.toString(), News.class));
                        Log.e("TAG",data.toString());
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(stringRequest);

    }
}
