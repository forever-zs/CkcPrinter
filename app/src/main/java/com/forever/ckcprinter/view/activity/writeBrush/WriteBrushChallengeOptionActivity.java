package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.View.OnClickListener;
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

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushChallengeOptionActivity extends BaseActivity {


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
    @Bind(R.id.btn_finish)
    Button btnFinish;

    private void initView() {
        this.switchTime.setChecked(true);
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushChallengeOptionActivity.this.finish();
            }
        });
        this.btnFinish.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
            }
        });
        this.llTimeSetting.setVisibility(View.INVISIBLE);
        this.switchTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    WriteBrushChallengeOptionActivity.this.switchTiming.setChecked(false);
                    WriteBrushChallengeOptionActivity.this.llTimeSetting.setVisibility(4);
                    return;
                }
                WriteBrushChallengeOptionActivity.this.switchTiming.setChecked(true);
                WriteBrushChallengeOptionActivity.this.llTimeSetting.setVisibility(0);
            }
        });
        this.switchTiming.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    WriteBrushChallengeOptionActivity.this.switchTime.setChecked(false);
                    WriteBrushChallengeOptionActivity.this.llTimeSetting.setVisibility(0);
                    return;
                }
                WriteBrushChallengeOptionActivity.this.llTimeSetting.setVisibility(4);
                WriteBrushChallengeOptionActivity.this.switchTime.setChecked(true);
            }
        });
        this.btnFinish.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                PracticeOption localPracticeOption = new PracticeOption();
                if (WriteBrushChallengeOptionActivity.this.switchTime.isChecked()){
                    localPracticeOption.setPracticeMode(PracticeOption.TIME_MODE);
                }
                else{
                    if(etNumberOfTime.getText().toString() == null || etNumberOfTime.getText().toString().length()==0){
                        ToastUtil.showText("请设置练习时间");
                        return;
                    }
                    localPracticeOption.setPracticeMode(PracticeOption.TIMING_MODE);
                    localPracticeOption.setPracticeTime(Integer.parseInt(WriteBrushChallengeOptionActivity.this.etNumberOfTime.getText().toString()));
                }
                Intent localIntent = new Intent();
                localIntent.putExtra("option", localPracticeOption);
                WriteBrushChallengeOptionActivity.this.setResult(1, localIntent);
                WriteBrushChallengeOptionActivity.this.finish();
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_keypad_option);
        ButterKnife.bind(this);
        initView();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.keypad.WriteBrushChallengeOptionActivity
 * JD-Core Version:    0.6.0
 */