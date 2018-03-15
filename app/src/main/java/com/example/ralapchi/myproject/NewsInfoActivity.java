package com.example.ralapchi.myproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activity.BaseActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ralapchi on 2017/4/23.
 */

public class NewsInfoActivity extends SwipeBackActivity {

    private WebView webView;
    private Button button;
    String newsUrl;
    String title;
    private static final String APP_ID = "wx2fecd315fd452b7e";
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        api = WXAPIFactory.createWXAPI(this, APP_ID,true);
        api.registerApp(APP_ID);
        initViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个WXWebPageObject对象
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = newsUrl;

                //创建一个WXMediaMessage对象
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = "资讯快递";
                msg.description = title;

                //设置缩略图
                Bitmap thumbBmp = BitmapFactory.decodeResource(getResources(),R.drawable.wechat);
                msg.thumbData = bmpToByteArray(thumbBmp, true);

                //创建SendMessageToWX.Req对象
                SendMessageToWX.Req req = new  SendMessageToWX.Req();
                req.transaction =buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
                finish();


            }
        });
    }

    public void initViews() {
        webView = (WebView) this.findViewById(R.id.wv_content);
        button = (Button) this.findViewById(R.id.bt_wechat_share);
        Intent intent = this.getIntent();
        newsUrl = intent.getStringExtra("newsUrl");
        title = intent.getStringExtra("title");
        /**
         * 显示新闻信息
         */
        webView.loadUrl(newsUrl);
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}