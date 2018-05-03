package com.forever.ckcprinter.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.view.activity.article.ArticleSettingActivity;
import com.forever.ckcprinter.view.activity.game.IntestingPracticeActivity;
import com.forever.ckcprinter.view.activity.simpleChineseCharacter.SimpleChineseSettingAvtivity;
import com.forever.ckcprinter.view.activity.singleCharacter.SingleCharacterSettingActivity;
import com.forever.ckcprinter.view.activity.wordGroup.WordGroupSettingActivity;
import com.forever.ckcprinter.view.activity.writeBrush.WriteBrushSelectActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @Bind(R.id.ll_writeBrushPractice)
    LinearLayout llWriteBrushPractice;
    @Bind(R.id.ll_singleCharacterPractice)
    LinearLayout llSingleCharacterPractice;
    @Bind(R.id.ll_keypadPractice)
    LinearLayout llKeypadPractice;
    @Bind(R.id.ll_wordGroupPractice)
    LinearLayout llWordGroupPractice;
    @Bind(R.id.ll_articlePractice)
    LinearLayout llArticlePractice;
    @Bind(R.id.ll_interestingPractice)
    LinearLayout llInterestingPractice;

    private void initView() {
        this.llKeypadPractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, SimpleChineseSettingAvtivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
        this.llWriteBrushPractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, WriteBrushSelectActivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
        this.llArticlePractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, ArticleSettingActivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
        this.llSingleCharacterPractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, SingleCharacterSettingActivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
        this.llWordGroupPractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, WordGroupSettingActivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
        this.llInterestingPractice.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, IntestingPracticeActivity.class);
                MainActivity.this.startActivity(localIntent);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.MainActivity
 * JD-Core Version:    0.6.0
 */