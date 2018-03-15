package com.example.ralapchi.myproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.news_entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralapchi on 2017/4/13.
 ListView适配器


 */

public class MyAdapter extends BaseAdapter {

    private int TypeOne=0;//注意这个不同布局的类型起始值必须从0开始
    private int TypeTwo=1;
    private int TypeThree=2;

    private List<News> datas = new ArrayList<News>();


    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<News> datas) {
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public News getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Log.i("str","绘制view");

        TypeOneViewHolder typeOneViewHolder = null;
        TypeTwoViewHolder typeTwoViewHolder = null;
        TypeThreeViewHolder typeThreeViewHolder = null;

        int Type=getItemViewType(position);
        if(convertView==null){
            if(Type==TypeOne){
                //Log.i("str","getView");
                convertView=layoutInflater.inflate(R.layout.list_item1,parent,false);
                typeOneViewHolder=new TypeOneViewHolder();
                typeOneViewHolder.tv_title_1= (TextView) convertView.findViewById(R.id.tv_title);
                typeOneViewHolder.tv_date_1= (TextView) convertView.findViewById(R.id.tv_date);
                typeOneViewHolder.tv_author_1= (TextView) convertView.findViewById(R.id.tv_author);
                typeOneViewHolder.img_1_1= (ImageView) convertView.findViewById(R.id.img_1);
                convertView.setTag(typeOneViewHolder);

            }else if(Type==TypeTwo){
                convertView=layoutInflater.inflate(R.layout.list_item2,parent,false);
                typeTwoViewHolder=new TypeTwoViewHolder();
                typeTwoViewHolder.tv_title_2= (TextView) convertView.findViewById(R.id.tv_title);
                typeTwoViewHolder.tv_date_2= (TextView) convertView.findViewById(R.id.tv_date);
                typeTwoViewHolder.tv_author_2= (TextView) convertView.findViewById(R.id.tv_author);
                typeTwoViewHolder.img_1_2= (ImageView) convertView.findViewById(R.id.img_1);
                typeTwoViewHolder.img_2_2= (ImageView) convertView.findViewById(R.id.img_2);

                convertView.setTag(typeTwoViewHolder);
            }else if(Type==TypeThree){
                convertView=layoutInflater.inflate(R.layout.list_item3,parent,false);
                typeThreeViewHolder=new TypeThreeViewHolder();
                typeThreeViewHolder.tv_title_3= (TextView) convertView.findViewById(R.id.tv_title);
                typeThreeViewHolder.tv_date_3= (TextView) convertView.findViewById(R.id.tv_date);
                typeThreeViewHolder.tv_author_3= (TextView) convertView.findViewById(R.id.tv_author);
                typeThreeViewHolder.img_1_3= (ImageView) convertView.findViewById(R.id.img_1);
                typeThreeViewHolder.img_2_3= (ImageView) convertView.findViewById(R.id.img_2);
                typeThreeViewHolder.img_3_3= (ImageView) convertView.findViewById(R.id.img_3);
                convertView.setTag(typeThreeViewHolder);
            }

        }else {

            if(Type==TypeOne){
                typeOneViewHolder= (TypeOneViewHolder) convertView.getTag();
            }else if(Type==TypeTwo){
                typeTwoViewHolder= (TypeTwoViewHolder) convertView.getTag();
            }else if(Type==TypeThree){
                typeThreeViewHolder= (TypeThreeViewHolder) convertView.getTag();
            }

        }

        //设置数据
        if(Type==TypeOne){
            typeOneViewHolder.tv_title_1.setText(datas.get(position).getTitle());
            typeOneViewHolder.tv_date_1.setText(datas.get(position).getDate());
            typeOneViewHolder.tv_author_1.setText(datas.get(position).getAuthor_name());
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeOneViewHolder.img_1_1);

        }else if(Type==TypeTwo){
            typeTwoViewHolder.tv_title_2.setText(datas.get(position).getTitle());
            typeTwoViewHolder.tv_date_2.setText(datas.get(position).getDate());
            typeTwoViewHolder.tv_author_2.setText(datas.get(position).getAuthor_name());
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeTwoViewHolder.img_1_2);
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s02())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeTwoViewHolder.img_2_2);

        }else if(Type==TypeThree){
            typeThreeViewHolder.tv_title_3.setText(datas.get(position).getTitle());
            typeThreeViewHolder.tv_author_3.setText(datas.get(position).getAuthor_name());
            typeThreeViewHolder.tv_date_3.setText(datas.get(position).getDate());
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeThreeViewHolder.img_1_3);
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s02())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeThreeViewHolder.img_2_3);
            Glide
                    .with(context)
                    .load(datas.get(position).getThumbnail_pic_s03())
                    .placeholder(R.mipmap.ic_launcher) // can also be a drawable
                    .crossFade()
                    .into(typeThreeViewHolder.img_3_3);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if((datas.get(position).getThumbnail_pic_s02()==null)&&(datas.get(position).getThumbnail_pic_s03()==null)){
            return TypeOne;
        }
        else if((datas.get(position).getThumbnail_pic_s02()!=null)&&(datas.get(position).getThumbnail_pic_s03()!=null)){
            return TypeThree;
        }
        else
            return TypeTwo;
    }

    @Override
    public int getViewTypeCount() {
//            return super.getViewTypeCount();
        return 3;//共有3种布局
    }


    private class TypeOneViewHolder{
        private TextView tv_title_1;
        private TextView tv_date_1;
        private TextView tv_author_1;
        private ImageView img_1_1;

    }
    private class TypeTwoViewHolder{
        private TextView tv_title_2;
        private TextView tv_date_2;
        private TextView tv_author_2;
        private ImageView img_1_2;
        private ImageView img_2_2;
    }
    private class TypeThreeViewHolder{
        private TextView tv_title_3;
        private TextView tv_date_3;
        private TextView tv_author_3;
        private ImageView img_1_3;
        private ImageView img_2_3;
        private ImageView img_3_3;
    }

}
