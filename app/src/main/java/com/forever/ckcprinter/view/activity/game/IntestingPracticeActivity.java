package com.forever.ckcprinter.view.activity.game;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.InterestingTopic;
import com.forever.ckcprinter.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import tyrantgit.explosionfield.ExplosionField;

public class IntestingPracticeActivity extends BaseActivity {
    @Bind(R.id.tv_countDown)
    TextView tvCountDown;
    @Bind(R.id.tv_score)
    TextView tvScore;
    @Bind(R.id.iv_image1)
    ImageView ivImage1;
    @Bind(R.id.ll_image1)
    LinearLayout llImage1;
    @Bind(R.id.iv_image2)
    ImageView ivImage2;
    @Bind(R.id.ll_image2)
    LinearLayout llImage2;
    @Bind(R.id.iv_image3)
    ImageView ivImage3;
    @Bind(R.id.ll_image3)
    LinearLayout llImage3;
    @Bind(R.id.iv_image4)
    ImageView ivImage4;
    @Bind(R.id.ll_image4)
    LinearLayout llImage4;
    @Bind(R.id.ll_image)
    LinearLayout llImage;
    @Bind(R.id.et_result)
    EditText etResult;
    private boolean[] currentFlag = new boolean[4];
    private InterestingTopic[] currentResult = new InterestingTopic[4];
    private ExplosionField explosionField;
    private List<String> fileList = new ArrayList();
    private Handler handler = new Handler(new Callback() {
        public boolean handleMessage(Message paramMessage) {
            return false;
        }
    });
    private int score = 0;
    private int timeDown = 40;
    private int level = 0;
    Runnable timeRunnable = new Runnable() {
        public void run() {
            timeDown--;
            tvCountDown.setText("倒计时：" + timeDown + "秒");
            if ((timeDown <= 0) && (!isDestroyed())) {
                showTimeOverDialog();
                return;
            }
            handler.postDelayed(this, 1000L);
        }
    };


    private void incrementScore() {
        this.score = (1 + this.score);
        this.tvScore.setText("得分：" + this.score + "分");
    }

    private void initData() {
        this.explosionField = ExplosionField.attach2Window(this);
        try {
            for (String str : getAssets().list("game")) {
                this.fileList.add(str);
            }
            refreshImage();
            this.handler.postDelayed(this.timeRunnable, 1000L);
        } catch (IOException localIOException) {

        }
    }

    private void initView() {
        this.ivImage1.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[0].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage2.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[1].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage3.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[2].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage4.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[3].getBrushNum() + "");
                return false;
            }
        });
        this.etResult.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramEditable) {
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
                String str = paramCharSequence.toString();
                if ((str.equals(currentResult[0].getBrushNum())) && (currentResult[0].isFlag())) {
                    explosionField.explode(ivImage1);
                    currentResult[0].setFlag(false);
                    etResult.setText("");
                    incrementScore();
                    isInputAll();
                }
                if ((str.equals(currentResult[1].getBrushNum())) && (currentResult[1].isFlag())) {
                    explosionField.explode(ivImage2);
                    currentResult[1].setFlag(false);
                    etResult.setText("");
                    incrementScore();
                    isInputAll();
                    return;
                }
                if ((str.equals(currentResult[2].getBrushNum())) && (currentResult[2].isFlag())) {
                    explosionField.explode(ivImage3);
                    currentResult[2].setFlag(false);
                    etResult.setText("");
                    incrementScore();
                    isInputAll();
                    return;
                }
                if ((str.equals(currentResult[3].getBrushNum())) && (currentResult[3].isFlag())) {
                    explosionField.explode(ivImage4);
                    currentResult[3].setFlag(false);
                    etResult.setText("");
                    incrementScore();
                    isInputAll();
                    return;
                }
            }
        });
    }

    private void isInputAll() {
        for (int i = 0; i < 4; i++)
            if (currentResult[i].isFlag())
                return;
        this.llImage1.removeAllViews();
        this.llImage2.removeAllViews();
        this.llImage3.removeAllViews();
        this.llImage4.removeAllViews();
        this.ivImage1 = new ImageView(this);
        this.ivImage1.setLayoutParams(new LayoutParams(-1, -1));
        this.ivImage2 = new ImageView(this);
        this.ivImage2.setLayoutParams(new LayoutParams(-1, -1));
        this.ivImage3 = new ImageView(this);
        this.ivImage3.setLayoutParams(new LayoutParams(-1, -1));
        this.ivImage4 = new ImageView(this);
        this.ivImage4.setLayoutParams(new LayoutParams(-1, -1));
        this.llImage1.addView(this.ivImage1);
        this.llImage2.addView(this.ivImage2);
        this.llImage3.addView(this.ivImage3);
        this.llImage4.addView(this.ivImage4);
        this.ivImage1.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[0].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage2.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[1].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage3.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[2].getBrushNum() + "");
                return false;
            }
        });
        this.ivImage4.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentResult[3].getBrushNum() + "");
                return false;
            }
        });
        refreshImage();
    }

    private void refreshImage() {
        StringBuilder localStringBuilder = new StringBuilder().append("第");
        int i = 1 + this.level;
        this.level = i;
        ToastUtil.showText(i + "关");
        this.timeDown = (60 - 2 * this.level);
        Random localRandom = new Random();
        for (int j = 0; j < 4;) {
            String str1 = fileList.get(localRandom.nextInt(fileList.size()));
            String str2;
            if (str1.contains("X")){
                str2 = str1.substring(0, str1.lastIndexOf('X'));
            }
            else if (str1.contains("C")) {
                str2 = str1.substring(0, str1.lastIndexOf('C'));
            }
            else{
                str2 = str1.substring(0, str1.lastIndexOf('.'));
            }
            if(containsGame(str2)){
                continue;
            }
            InterestingTopic localInterestingTopic = new InterestingTopic(str1, str2, true);
            this.currentResult[j++] = localInterestingTopic;
        }
        Uri localUri1 = Uri.parse("file:///android_asset/game/" + this.currentResult[0].getFilePath());
        Glide.with(this).load(localUri1).into(this.ivImage1);
        Uri localUri2 = Uri.parse("file:///android_asset/game/" + this.currentResult[1].getFilePath());
        Glide.with(this).load(localUri2).into(this.ivImage2);
        Uri localUri3 = Uri.parse("file:///android_asset/game/" + this.currentResult[2].getFilePath());
        Glide.with(this).load(localUri3).into(this.ivImage3);
        Uri localUri4 = Uri.parse("file:///android_asset/game/" + this.currentResult[3].getFilePath());
        Glide.with(this).load(localUri4).into(this.ivImage4);
    }

    private void showBrushNumDialog(String paramString) {
        new Builder(this).setTitle("温馨提示").setMessage(paramString).setCancelable(false).setPositiveButton("确定", new OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).create().show();
    }

    private void showTimeOverDialog() {
        new Builder(this).setMessage("练习已经结束，最终得分：\t" + this.score).setCancelable(false).setPositiveButton("确定并退出", new OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    private boolean containsGame(String brushNum){
        for(InterestingTopic topic : currentResult){
            if( topic != null && topic.getBrushNum().equals(brushNum)){
                return true;
            }
        }
        return false;
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_intesting_practice);
        ButterKnife.bind(this);
        initData();
        initView();
    }
}
