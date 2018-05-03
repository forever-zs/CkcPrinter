package com.forever.ckcprinter.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.App;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.configure.GlobalConstant;
import com.forever.ckcprinter.utils.PrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_splash)
    ImageView ivSplash;
    @Bind(R.id.tv_tips)
    TextView tvTips;
    @Bind(R.id.rl_root)
    RelativeLayout rlRoot;
    private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;


    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        startAnim();
    }

    public void startAnim() {
        AnimationSet localAnimationSet = new AnimationSet(false);
        ScaleAnimation localScaleAnimation = new ScaleAnimation(0.95F, 1.0F, 0.95F, 1.0F, 1, 0.5F, 1, 0.5F);
        AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
        localAlphaAnimation.setDuration(2000L);
        localAnimationSet.addAnimation(localScaleAnimation);
        localAnimationSet.addAnimation(localAlphaAnimation);
        localAlphaAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation paramAnimation) {
                if (PrefUtils.getBoolean(App.getGlobalContext(), GlobalConstant.IS_FIRST_ENTRY, true)) {
                    SplashActivity.this.startGuideActivity();
                    PrefUtils.setBoolean(App.getGlobalContext(), GlobalConstant.IS_FIRST_ENTRY, false);
                    return;
                }
                SplashActivity.this.startMainActivity();
            }

            public void onAnimationRepeat(Animation paramAnimation) {
            }

            public void onAnimationStart(Animation paramAnimation) {
            }
        });
        this.ivSplash.startAnimation(localAnimationSet);
    }

    public void startGuideActivity() {
        startActivity(new Intent(this, GuideActivity.class));
        finish();
    }

    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.SplashActivity
 * JD-Core Version:    0.6.0
 */