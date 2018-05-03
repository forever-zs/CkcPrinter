package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushSelectActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.ll_single)
    LinearLayout llSingle;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.ll_judge)
    LinearLayout llJudge;
    @Bind(R.id.ll_fill)
    LinearLayout llFill;
    @Bind(R.id.ll_challenge)
    LinearLayout llChallenge;

    private void initView() {
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushSelectActivity.this.finish();
            }
        });
        this.llSingle.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(WriteBrushSelectActivity.this, WriteBrushSingleSelectionActivity.class);
                WriteBrushSelectActivity.this.startActivity(localIntent);
            }
        });
        this.llJudge.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(WriteBrushSelectActivity.this, WriteBrushJudgeActivity.class);
                WriteBrushSelectActivity.this.startActivity(localIntent);
            }
        });
        this.llFill.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(WriteBrushSelectActivity.this, WriteBrushFillActivity.class);
                WriteBrushSelectActivity.this.startActivity(localIntent);
            }
        });
        this.llChallenge.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(WriteBrushSelectActivity.this, WriteBrushChallengeSettingActivity.class);
                WriteBrushSelectActivity.this.startActivity(localIntent);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_select);
        ButterKnife.bind(this);
        initView();
    }
}
