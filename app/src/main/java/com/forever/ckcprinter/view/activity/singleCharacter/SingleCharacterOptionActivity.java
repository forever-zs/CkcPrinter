package com.forever.ckcprinter.view.activity.singleCharacter;

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
import com.forever.ckcprinter.view.activity.writeBrush.WriteBrushChallengeOptionActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCharacterOptionActivity extends BaseActivity {


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
                SingleCharacterOptionActivity.this.finish();
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
                    SingleCharacterOptionActivity.this.switchTiming.setChecked(false);
                    SingleCharacterOptionActivity.this.llTimeSetting.setVisibility(View.INVISIBLE);
                    return;
                }
                SingleCharacterOptionActivity.this.switchTiming.setChecked(true);
                SingleCharacterOptionActivity.this.llTimeSetting.setVisibility(View.VISIBLE);
            }
        });
        this.switchTiming.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    SingleCharacterOptionActivity.this.switchTime.setChecked(false);
                    SingleCharacterOptionActivity.this.llTimeSetting.setVisibility(View.VISIBLE);
                    return;
                }
                SingleCharacterOptionActivity.this.llTimeSetting.setVisibility(View.INVISIBLE);
                SingleCharacterOptionActivity.this.switchTime.setChecked(true);
            }
        });
        this.btnFinish.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {

                PracticeOption localPracticeOption = new PracticeOption();
                if (switchTime.isChecked()){
                    localPracticeOption.setPracticeMode(PracticeOption.TIME_MODE);
                }
                else{
                    if(etNumberOfTime.getText().toString() == null || etNumberOfTime.getText().toString().length()==0){
                        ToastUtil.showText("请设置练习时间");
                        return;
                    }
                    localPracticeOption.setPracticeMode(PracticeOption.TIMING_MODE);
                    localPracticeOption.setPracticeTime(Integer.parseInt(etNumberOfTime.getText().toString()));
                }
                Intent localIntent = new Intent();
                localIntent.putExtra("option", localPracticeOption);
                setResult(1, localIntent);
                finish();
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_single_character_option);
        ButterKnife.bind(this);
        initView();
    }
}
