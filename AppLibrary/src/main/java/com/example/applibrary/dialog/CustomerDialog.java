package com.example.applibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.applibrary.R;
import com.example.applibrary.dialog.interfac.DialogOnClick;

public class CustomerDialog extends Dialog {

    private Context context;
    private String msg;
    private String confirmText;
    private String cancelText;
    private TextView confirmBtn;
    private TextView cancelBtn;
    private TextView MessageContent;
    private EditText intputText;
    private String intput;
    private String text;    //消息内容
    private DialogOnClick dialogOnClick;    //对话框操作监听
    private boolean msgVisible;

    public CustomerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        setContentView(R.layout.dialog);

        MessageContent = findViewById(R.id.dialog_buttontwo_text);
        intputText = findViewById(R.id.dialog_buttontwo_input);
        TextView sureButton = findViewById(R.id.dialog_buttontwo_sure);
        TextView cancelButton = findViewById(R.id.dialog_buttontwo_cancel);
        setMsg(text);

        sureButton.setOnClickListener(onClickListener);
        cancelButton.setOnClickListener(onClickListener);
    }

    //更新消息内容
    public void setMsg(String text) {
        MessageContent.setText(text);
    }

    public void setDialogOnClick(DialogOnClick dialogOnClick) {
        this.dialogOnClick = dialogOnClick;
    }

    //操作监听
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_buttontwo_sure) {
                dialogOnClick.operate();
            }
            if (v.getId() == R.id.dialog_buttontwo_cancel) {
                dialogOnClick.cancel();
            }
        }
    };


    public String getIntput() {
        return intputText.getText().toString();
    }

    public static class Builder {

        private CustomerDialog mDialog;

        public Builder(Context context) {
            mDialog = new CustomerDialog(context);
        }

        public Builder setContext(Context context) {
            mDialog.context = context;
            return this;
        }

        public Builder setMsg(String msg) {
            mDialog.msg = msg;
            return this;
        }

        public Builder setConfirmText(String confirmText) {
            mDialog.confirmText = confirmText;
            return this;
        }

        public Builder setCancelText(String cancelText) {
            mDialog.cancelText = cancelText;
            return this;
        }

        public Builder setIntput(String intput) {
            mDialog.intput = intput;
            return this;
        }

        public Builder setText(String text) {
            mDialog.text = text;
            return this;
        }

        public Builder setMsgVisible(Boolean msgVisible) {
            mDialog.msgVisible = msgVisible;
            return this;
        }

        public Builder setDialogOnClick(DialogOnClick dialogOnClick) {
            mDialog.setDialogOnClick(dialogOnClick);
            return this;
        }

        /**
         * ͨ 通过Builder类设置完属性后构造对话框的方法
         */
        public CustomerDialog create() {
            // 点击返回按钮，Dialog不消失
            mDialog.setCanceledOnTouchOutside(false);
            return mDialog;
        }
    }

    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(final CustomerDialog mDialog) {
        mDialog.MessageContent.setVisibility(msgVisible ? View.VISIBLE : View.GONE);
    }
}
