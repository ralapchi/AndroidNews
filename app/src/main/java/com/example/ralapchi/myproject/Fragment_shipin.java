package com.example.ralapchi.myproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.utils.Logger;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.ListViewItemPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ralapchi on 2017/5/11.
 */


public class Fragment_shipin extends Fragment {
    RecyclerView rlVideoList;
    List<String> videos=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list_view,container,false);
        rlVideoList=(RecyclerView) view.findViewById(R.id.rv_vieo_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlVideoList.setLayoutManager(layoutManager);
        videos.add("http://119.167.138.9/data10/video09/2017/05/23/4655085-102-0955.mp4");
        videos.add("http://119.167.138.8/data6/video09/2017/05/23/4663768-280-99987624-1923.mp4");
        videos.add("http://119.167.138.10/data5/video09/2017/05/29/4674470-102-1550.mp4");
        videos.add("http://119.167.138.14/data9/video09/2017/05/29/4230746-102-009-160347.mp4");
        videos.add("http://video19.ifeng.com/video09/2017/05/29/4230425-102-009-153748.mp4");
        videos.add("http://119.167.138.14/data2/video09/2017/05/29/4230385-102-009-153348.mp4");

        VideoAdapter adapter=new VideoAdapter(getContext(), videos);
        adapter.notifyDataSetChanged();
        rlVideoList.setAdapter(adapter);


        return view;
    }
}

