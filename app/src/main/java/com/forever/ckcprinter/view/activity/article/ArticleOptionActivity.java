package com.forever.ckcprinter.view.activity.article;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.PracticeOption;
import com.forever.ckcprinter.utils.ToastUtil;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticleOptionActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.switch_time)
    SwitchCompat switchTime;
    @Bind(R.id.switch_timing)
    SwitchCompat switchTiming;
    @Bind(R.id.et_numberOfTime)
    EditText etNumberOfTime;
    @Bind(R.id.ll_timeSetting)
    LinearLayout llTimeSetting;
    @Bind(R.id.btn_chooseArticle)
    Button btnChooseArticle;
    @Bind(R.id.btn_finish)
    Button btnFinish;
    private String articleName = "苏州";
    private String[] articles;


    private void initView() {
        this.switchTime.setChecked(true);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                ArticleOptionActivity.this.finish();
            }
        });
        this.llTimeSetting.setVisibility(View.INVISIBLE);
        this.switchTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    switchTiming.setChecked(false);
                    llTimeSetting.setVisibility(View.INVISIBLE);
                }
                else{
                    ArticleOptionActivity.this.switchTiming.setChecked(true);
                    ArticleOptionActivity.this.llTimeSetting.setVisibility(View.VISIBLE);
                }
            }
        });
        this.switchTiming.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    switchTime.setChecked(false);
                    llTimeSetting.setVisibility(View.VISIBLE);
                }
                else{
                    llTimeSetting.setVisibility(View.INVISIBLE);
                    switchTime.setChecked(true);
                }

            }
        });
        this.btnChooseArticle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                try {
                    articles = getAssets().list("article");
                    for (int i = 0; i < articles.length; i++)
                        articles[i] = articles[i].substring(0, articles[i].lastIndexOf(46));
                    showGroupDialog();
                } catch (IOException localIOException) {
                    localIOException.printStackTrace();
                }
            }
        });
        this.btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                PracticeOption localPracticeOption = new PracticeOption();
                localPracticeOption.setArticleName(ArticleOptionActivity.this.articleName);
                if (switchTime.isChecked()){
                    localPracticeOption.setPracticeMode(PracticeOption.TIME_MODE);
                }
                else {
                    localPracticeOption.setPracticeMode(2);
                    try {
                        localPracticeOption.setPracticeTime(Integer.parseInt(etNumberOfTime.getText().toString()));
                    } catch (Exception localException) {
                        ToastUtil.showText("请输入正确的数字");
                    }
                }
                Intent localIntent = new Intent();
                localIntent.putExtra("option", localPracticeOption);
                setResult(1, localIntent);
                finish();
            }
        });
    }

    private void showGroupDialog() {
        new Builder(this).setTitle("文章选择").setItems(this.articles, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                articleName = articles[paramInt];
            }
        }).create().show();
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_article_option);
        ButterKnife.bind(this);
        initView();
    }
}