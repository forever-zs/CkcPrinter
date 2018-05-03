package com.forever.ckcprinter.view.activity.singleCharacter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.PracticeOption;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCharacterSettingActivity extends BaseActivity {
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
    @Bind(R.id.btn_option)
    Button btnOption;
    @Bind(R.id.btn_start)
    Button btnStart;


    private PracticeOption option = new PracticeOption();

    private void initData() {
        this.option.setPracticeTime(2);
        this.option.setPracticeMode(1);
    }

    private void initView() {
        this.ivBack.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                finish();
            }
        });
        this.btnOption.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(SingleCharacterSettingActivity.this, SingleCharacterOptionActivity.class);
                startActivityForResult(localIntent, 1);
            }
        });
        this.btnStart.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(SingleCharacterSettingActivity.this, SingleCharacterPracticeActivity.class);
                localIntent.putExtra("option", option);
                startActivity(localIntent);
            }
        });
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        PracticeOption localPracticeOption;
        if (paramIntent != null) {
            localPracticeOption = (PracticeOption) paramIntent.getSerializableExtra("option");
            if (localPracticeOption != null) {
                option.setPracticeMode(localPracticeOption.getPracticeMode());
                if (localPracticeOption.getPracticeMode() == PracticeOption.TIMING_MODE) {
                    this.tvPracticeMode.setText("训练模式：定时模式");
                    this.tvPracticeTime.setText("训练时间：" + localPracticeOption.getPracticeTime() + "\t分钟");
                    option.setPracticeTime(localPracticeOption.getPracticeTime());
                } else {
                    this.tvPracticeMode.setText("训练模式：计时模式");
                    this.tvPracticeTime.setText("");
                }

            }
        }
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_single_character_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.singleCharacter.SingleCharacterSettingActivity
 * JD-Core Version:    0.6.0
 */