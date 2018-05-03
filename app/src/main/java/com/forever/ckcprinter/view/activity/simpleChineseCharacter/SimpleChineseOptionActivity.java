package com.forever.ckcprinter.view.activity.simpleChineseCharacter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.modle.bean.PracticeOption;
import com.forever.ckcprinter.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SimpleChineseOptionActivity extends AppCompatActivity {


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
    @Bind(R.id.btn_choose)
    Button btnChoose;
    @Bind(R.id.btn_finish)
    Button btnFinish;
    private String[] group = {"四码汉字", "小于四码汉字", "进阶汉字", "字一键简码", "字二键简码"};
    private int groupMode = -1;

    private void initView() {
        this.switchTime.setChecked(true);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                SimpleChineseOptionActivity.this.finish();
            }
        });
        this.btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
            }
        });
        this.llTimeSetting.setVisibility(View.INVISIBLE);
        this.switchTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    SimpleChineseOptionActivity.this.switchTiming.setChecked(false);
                    SimpleChineseOptionActivity.this.llTimeSetting.setVisibility(View.INVISIBLE);
                    return;
                }
                SimpleChineseOptionActivity.this.switchTiming.setChecked(true);
                SimpleChineseOptionActivity.this.llTimeSetting.setVisibility(View.VISIBLE);
            }
        });
        this.switchTiming.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean) {
                if (paramBoolean) {
                    SimpleChineseOptionActivity.this.switchTime.setChecked(false);
                    SimpleChineseOptionActivity.this.llTimeSetting.setVisibility(View.VISIBLE);
                    return;
                }
                SimpleChineseOptionActivity.this.llTimeSetting.setVisibility(View.INVISIBLE);
                SimpleChineseOptionActivity.this.switchTime.setChecked(true);
            }
        });
        this.btnChoose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                SimpleChineseOptionActivity.this.showGroupDialog();
            }
        });
        this.btnFinish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {

                PracticeOption localPracticeOption = new PracticeOption();
                localPracticeOption.setGroupMode(groupMode);
                if (switchTime.isChecked())
                    localPracticeOption.setPracticeMode(PracticeOption.TIME_MODE);
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

    private void showGroupDialog() {
        new Builder(this).setTitle("汉字选择").setItems(this.group, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                groupMode = paramInt;
            }
        }).create().show();
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_simple_chinese_option);
        ButterKnife.bind(this);
        initView();
    }
}
