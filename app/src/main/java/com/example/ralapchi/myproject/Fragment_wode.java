package com.example.ralapchi.myproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.tencent.connect.UserInfo;


import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;


import static com.tencent.open.utils.AsynLoadImg.getbitmap;

/**
 * Created by ralapchi on 2017/5/11.
 */

public class Fragment_wode extends Fragment {
    private ImageView image_user_logo;
    private TextView text_nickname;
    private TextView text_gender;
    private Button button_login;
    //需要腾讯提供的一个Tencent类

    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）

    private static String APP_ID="1106084677";
    private IUiListener userInfoListener;
    private IUiListener mIUiListener;
    private Tencent mTencent; //qq主操作对象


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wode, container, false);
        image_user_logo = (ImageView) view.findViewById(R.id.user_logo);
        text_nickname = (TextView) view.findViewById(R.id.nickname);
        text_gender = (TextView) view.findViewById(R.id.gender);
        button_login = (Button) view.findViewById(R.id.login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonLogin();
            }
        });
    return view;
    }
    public void buttonLogin(){
        mTencent = Tencent.createInstance(APP_ID, getActivity().getApplicationContext());
        //all表示获取所有权限
        mTencent.login(getActivity(),"all", mIUiListener);
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new IUiListener(){
            @Override
            public void onComplete(Object response) {
                Log.e("TAG", "response:" + response.toString());
                JSONObject obj = (JSONObject) response;
                try {
                    String openID = obj.getString("openid");
                    String accessToken = obj.getString("access_token");
                    String expires = obj.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken,expires);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {
                Toast.makeText(getActivity().getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity().getApplicationContext(), "授权取消", Toast.LENGTH_SHORT).show();
            }

        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {
                if(o == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) o;
                    Log.e(":",jo.toString());
                    int ret = jo.getInt("ret");
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    Toast.makeText(getActivity(), "你好，" + nickName,Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        };
    }



    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG","result");
        if (requestCode == Constants.REQUEST_LOGIN) {
            if (resultCode == -1) {
                Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
                Tencent.handleResultData(data, mIUiListener);
                UserInfo info = new UserInfo(getContext(), mTencent.getQQToken());
                info.getUserInfo(userInfoListener);
            }
        }
    }
}