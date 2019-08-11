package com.example.applibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.R;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.utils.IntentUtils;

import java.util.Timer;
import java.util.TimerTask;

//一个按钮自定义对话框
public class NoticeDialog extends Dialog {

    public NoticeDialog(Context context) {
        super(context);
    }

    private Context context;
    private String text;    //消息内容
    private int style = 1;    //消息内容
    private TextView textView;  //消息内容
    private ImageView icon;

    public NoticeDialog(Context context, String text) {
        super(context, R.style.dialog);
        this.context = context;
        this.text = text;
        this.style = 1;
    }

    public NoticeDialog(Context context, String text, int style) {
        super(context, R.style.dialog);
        this.context = context;
        this.text = text;
        this.style = style;
    }

    //更新消息内容
    public void setMsg() {
        textView.setText(text);
    }

    public void setStyle() {
        if (style == 1) {
            icon.setImageResource(R.drawable.icon_notice);
        } else {
            icon.setImageResource(R.drawable.icon_right);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog_notice);
        textView = findViewById(R.id.dialog_buttonone_text);
        icon = findViewById(R.id.dialog_notice_icon);
        setMsg();
        setStyle();
        setDismiss();
    }

    private void setDismiss() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                dismiss();
            }
        };
        timer.schedule(task, 2500);
    }

}
