package com.example.applibrary.dialog.sharedialog;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.applibrary.R;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.utils.WindowWHUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

//微信分享
public class ShareWeChar extends Dialog {

    IWXAPI iwxapi;  //api
    Context context;
    String title, details, url, image;    //商品名/详情/链接

    public ShareWeChar(Context context) {
        super(context);
    }

    public ShareWeChar(Context context, String title, String image, String details, String url) {
        super(context, R.style.ActionSheetDialogStyle2);
        this.context = context;
        this.title = title;
        this.image = image;
        this.details = details;
        this.url = url;
        iwxapi = WXAPIFactory.createWXAPI(context, ConfigVariate.weChatAppID, false);
        iwxapi.registerApp(ConfigVariate.weChatAppID);
    }

    //刷新
    public void setUpData(String title, String image, String details, String url) {
        this.title = title;
        this.image = image;
        this.details = details;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_sharewechar);
        init();
        WindowWHUtils.setDialogDisplay(context, this, Gravity.BOTTOM, 1, 0.3);
    }

    private void init() {
        findViewById(R.id.dialog_sharewechar_close).setOnClickListener(onClickListener);
        findViewById(R.id.dialog_sharewechar_wx).setOnClickListener(onClickListener);
        findViewById(R.id.dialog_sharewechar_pyq).setOnClickListener(onClickListener);
        findViewById(R.id.dialog_sharewechar_copy).setOnClickListener(onClickListener);
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_sharewechar_wx)
//                shareMiniProgram();
                share(0);
            if (v.getId() == R.id.dialog_sharewechar_pyq)
                share(1);
            if (v.getId() == R.id.dialog_sharewechar_copy) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(url);
                Toast.makeText(getContext(), "链接复制成功，可以发给朋友们了。", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    };


    /**
     * @param flag 0:好友，1：朋友圈
     */
    private void share(int flag) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;//url地址

        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = details;
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.share_launcher);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        boolean fla = iwxapi.sendReq(req);
    }

    private void shareMiniProgram(){
        String appId = "wx3226fb08fc471be9"; // 填应用AppId，APP在开放平台注册的id
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);

        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = "http://www.qq.com"; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_d43f693ca31f";     // 小程序原始id
        miniProgramObj.path = "/pages/activity/goods_combination/index";            //小程序页面路径
        //pages/activity/goods_combination/index
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = "小程序消息Title";                    // 小程序消息title
        msg.description = "小程序消息Desc";               // 小程序消息desc
//        msg.thumbData = getThumb();                      // 小程序消息封面图片，小于128k
        Bitmap thumb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.share_launcher);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前支持会话
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }
}
