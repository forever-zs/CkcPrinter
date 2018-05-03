package com.forever.ckcprinter.view.activity.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.PracticeOption;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleSettingActivity extends BaseActivity {
    private static final int REQUEST_CODE = 1;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    @Bind(R.id.tv_practiceTime)
    TextView tvPracticeTime;
    @Bind(R.id.tv_practiceMode)
    TextView tvPracticeMode;
    @Bind(R.id.tv_articleMode)
    TextView tvArticleMode;
    @Bind(R.id.btn_option)
    Button btnOption;
    @Bind(R.id.btn_start)
    Button btnStart;
    private PracticeOption option = new PracticeOption();


    private void initData() {
        option.setPracticeTime(2);
        option.setPracticeMode(PracticeOption.TIME_MODE);
        option.setArticleName("苏州");
    }

    private void initView() {
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                finish();
            }
        });
        this.btnOption.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(ArticleSettingActivity.this, ArticleOptionActivity.class);
                startActivityForResult(localIntent, 1);
            }
        });
        this.btnStart.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(ArticleSettingActivity.this, ArticlePracticeActivity.class);
                localIntent.putExtra("option", option);
                startActivity(localIntent);
            }
        });
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        PracticeOption localPracticeOption;
        if (paramIntent != null) {
            localPracticeOption = (PracticeOption) paramIntent.getSerializableExtra("option");
            if (localPracticeOption != null) {
                option.setPracticeMode(localPracticeOption.getPracticeMode());
                if (localPracticeOption.getPracticeMode() == PracticeOption.TIMING_MODE) {
                    this.tvPracticeMode.setText("训练模式：定时模式");
                    this.tvPracticeTime.setText("训练时间：" + localPracticeOption.getPracticeTime() + "\t分钟");
                } else {
                    this.tvPracticeMode.setText("训练模式：计时模式");
                    this.tvPracticeTime.setText("");
                }

                this.option.setPracticeTime(localPracticeOption.getPracticeTime());
            }
            this.tvArticleMode.setText("文章模式：" + localPracticeOption.getArticleName());
            option.setArticleName(localPracticeOption.getArticleName());
        }
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_article_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}