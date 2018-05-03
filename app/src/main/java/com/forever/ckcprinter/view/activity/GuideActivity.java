package com.forever.ckcprinter.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.dataManager.ActivityManager;
import com.forever.ckcprinter.presenter.adapter.UserGuideAdapter;
import com.forever.ckcprinter.utils.UiUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GuideActivity extends BaseActivity {


    @Bind(R.id.vp_userGuide)
    ViewPager vpUserGuide;
    @Bind(R.id.btn_start)
    Button btnStart;
    @Bind(R.id.ll_guideCircle)
    LinearLayout llGuideCircle;
    @Bind(R.id.activity_guide)
    RelativeLayout activityGuide;
    private ArrayList<Bitmap> guideImageList = new ArrayList();
    private int pointIndex = 0;
    private int[] resourse = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3};
    private UserGuideAdapter userGuideAdapter;


    private void initData() {
        for (int i = 0; i < this.resourse.length; i++) {
            Bitmap localBitmap = BitmapFactory.decodeResource(getResources(), this.resourse[i]);
            this.guideImageList.add(localBitmap);
        }
        this.userGuideAdapter = new UserGuideAdapter(this.guideImageList, getBaseContext());
        this.llGuideCircle.getChildAt(this.pointIndex).setEnabled(true);
        this.vpUserGuide.setAdapter(this.userGuideAdapter);
        this.vpUserGuide.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrollStateChanged(int paramInt) {
            }

            public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {
            }

            public void onPageSelected(int paramInt) {
                pointIndex = paramInt;
                llGuideCircle.getChildAt(paramInt).setEnabled(true);
                llGuideCircle.getChildAt(pointIndex).setEnabled(false);
                if (pointIndex == (resourse.length-1)) {
                    btnStart.setVisibility(View.VISIBLE);
                }
                else{
                    btnStart.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    private void initView() {
        this.btnStart.setVisibility(View.INVISIBLE);
        for (int i = 0; i < resourse.length; i++) {
            View localView = new View(getBaseContext());
            LayoutParams localLayoutParams = new LayoutParams(20, 20);
            localLayoutParams.leftMargin = 10;
            localView.setBackgroundResource(resourse[i]);
            localView.setLayoutParams(localLayoutParams);
            localView.setEnabled(false);
            this.llGuideCircle.addView(localView);
        }
        this.btnStart.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(localIntent);
                finish();
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        ActivityManager.addActivity(this);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_guide);
        UiUtil.immerseStatusBar(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.GuideActivity
 * JD-Core Version:    0.6.0
 */