package com.example.haoss.ui.person.cardConvert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.haoss.helper.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.ui.MainActivity;
import com.example.haoss.ui.person.wallet.WalletActivity;

public class CardConvertResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_card_convert_result);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("兑换成功");
        findViewById(R.id.ui_card_back_index).setOnClickListener(listener);
        findViewById(R.id.ui_card_back_wallet).setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_card_back_index:
                    IntentUtils.startIntentFrist(CardConvertResultActivity.this, MainActivity.class);
                    break;
                case R.id.ui_card_back_wallet:
                    IntentUtils.startIntent(CardConvertResultActivity.this, WalletActivity.class);
                    break;
            }
        }
    };
}
