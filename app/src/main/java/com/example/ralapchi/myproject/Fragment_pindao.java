package com.example.ralapchi.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ralapchi on 2017/4/11.
 */

public class Fragment_pindao extends Fragment {
    private ViewPager viewpager;
    private GridView gridview;
    private int previousposition;//记录上一个小圆点的位置
    private LinearLayout linearlayout;
    /**
     * 图片id
     */
    private int[] mImageIds = new int[]{
            R.mipmap.a,
            R.mipmap.b,
            R.mipmap.c,
            R.mipmap.d,
            R.mipmap.e,
 };
    private int[] imageRes = {
            R.drawable.shehui,
            R.drawable.guonei,
            R.drawable.guoji,
            R.drawable.yule,
            R.drawable.tiyu,
            R.drawable.junshi,
            R.drawable.keji,
            R.drawable.caijing,
            R.drawable.shishang,
          };
    private String[] name = {
            "社会",
            "国内",
            "国际",
            "娱乐",
            "体育",
            "军事",
            "科技",
            "财经",
            "时尚",
    };

    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = viewpager.getCurrentItem();//获取viewPager当前页面的位置
            viewpager.setCurrentItem(++currentItem);
            //继续发送延时两秒的消息，类似递归的效果，使广告一直切换
            mHandler.sendEmptyMessageDelayed(0, 2000);
            /**
             * 当用户触摸的时候，自动轮播就应该停止下来
             */
           viewpager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mHandler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            mHandler.sendEmptyMessageDelayed(0, 2000);
                            break;
                        default:
                            break;
                    }
                    /**
                     * 如果这里返回true，viewPager的事件将会被消耗掉，ViewPager将会响应不了
                     * 所以这里要返回false，让viewPager原生的触摸效果正常运行
                     */
                    return false;
                }
            });
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // ---Inflate the layout for this fragment---
        View view = inflater.inflate(R.layout.fragment_pindao, container,false);
        viewpager = (ViewPager)view.findViewById(R.id.vp_hottest);

        linearlayout = (LinearLayout)view.findViewById(R.id.ll_hottest_indicator);
        gridview = (GridView) view.findViewById(R.id.gridview);
        int length = imageRes.length;
        /**
         * 动态添加5个小圆点
         */
        for(int i = 0; i < mImageIds.length; i++)
        {
            ImageView imageView = new ImageView(linearlayout.getContext());
            imageView.setBackgroundResource(i==0?R.drawable.small_dot_selected:R.drawable.small_dot_unselected);
            /**
             * 给小圆点之间设置间距，获取圆点的父布局的布局参数，然后给其设置左边距
             */
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            /**
             * 从第二个圆点开始设置左边距
             */
            if(i != 0) {
                params.leftMargin = 8;
            }
            imageView.setLayoutParams(params);
            linearlayout.addView(imageView);
        }

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 滑动过程回调事件
             * @param position
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            /**
             * 页面选中回调事件
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
               int pos = position % mImageIds.length;
                /**
                 * 更新小圆点
                 * 通过切换背景图，将当前小圆点设置为选中，上一个设置为未选中
                 */
               linearlayout.getChildAt(pos).setBackgroundResource(R.drawable.small_dot_selected);
               linearlayout.getChildAt(previousposition).setBackgroundResource(R.drawable.small_dot_unselected);
               previousposition = pos;
            }

            /**
             * 滑动状态发生改变回调事件
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mHandler.sendEmptyMessageDelayed(0, 2000);

        viewpager.setAdapter(new MyViewPagerAdapter());
        /**
         * 设置ViewPager起始页为第一页，并且可以向左滑动
         */
        viewpager.setCurrentItem(mImageIds.length * 5000);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", imageRes[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getContext(),
                lstImageItem,//数据来源
                R.layout.gridview_item,//item的XML实现
                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},
                //ImageItem的XML文件里面的一个ImageView,一个TextView ID
                new int[]{R.id.img_tiyu, R.id.txt_tiyu});
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //添加消息处理

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
              //  Toast.makeText(getActivity(),name[position],Toast.LENGTH_LONG).show();
                switch(imageRes[position]){
                    case R.drawable.tiyu:
                        intent = new Intent(getActivity(),Activity_tiyu.class);
                        startActivity(intent);
                        break;
                    case R.drawable.shehui:
                        intent = new Intent(getActivity(),Activity_shehui.class);
                        startActivity(intent);
                        break;
                    case R.drawable.caijing:
                        intent = new Intent(getActivity(),Activity_caijing.class);
                        startActivity(intent);
                        break;
                    case R.drawable.yule:
                       intent = new Intent(getActivity(),Activity_yule.class);
                        startActivity(intent);
                        break;
                    case R.drawable.guonei:
                        intent = new Intent(getActivity(),Activity_guonei.class);
                        startActivity(intent);
                        break;
                    case R.drawable.keji:
                         intent = new Intent(getActivity(),Activity_keji.class);
                        startActivity(intent);
                        break;
                    case R.drawable.junshi:
                         intent = new Intent(getActivity(),Activity_junshi.class);
                        startActivity(intent);
                        break;
                    case R.drawable.shishang:
                        intent = new Intent(getActivity(),Activity_shishang.class);
                        startActivity(intent);
                        break;
                    case R.drawable.guoji:
                         intent = new Intent(getActivity(),Activity_guoji.class);
                        startActivity(intent);
                        break;
                    default:break;



                }
            }
        });

        return view;
    }
    class MyViewPagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount()
        {
            /**
             * 返回图片的数量
             * 如果要使viewpager循环，直接返回比mImageIds.length大的值就可以了
             */
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        /**
         * 初始化ViewPager中的View
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % mImageIds.length;
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(mImageIds[pos]);
            container.addView(imageView);
            return imageView;
        }

        /**
         * 移除ViewPager中的View
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}