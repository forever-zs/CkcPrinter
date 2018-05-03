package com.forever.ckcprinter.view.activity.singleCharacter;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.configure.GlobalConstant;
import com.forever.ckcprinter.modle.bean.FillTopic;
import com.forever.ckcprinter.modle.bean.PracticeOption;
import com.forever.ckcprinter.modle.bean.Result;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.view.activity.ResultActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCharacterPracticeActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_pause)
    ImageView ivPause;
    @Bind(R.id.tv_pause)
    TextView tvPause;
    @Bind(R.id.ll_pause)
    LinearLayout llPause;
    @Bind(R.id.ll_checkResult)
    LinearLayout llCheckResult;
    @Bind(R.id.ll_end)
    LinearLayout llEnd;
    @Bind(R.id.tv_rightCount)
    TextView tvRightCount;
    @Bind(R.id.tv_accuracy)
    TextView tvAccuracy;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_speed)
    TextView tvSpeed;
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.et_brushNum)
    EditText etBrushNum;
    private FillTopic currentTopic;
    private List<String> fileList = new LinkedList();
    private Handler handler = new Handler(new Callback() {
        public boolean handleMessage(Message paramMessage) {
            return false;
        }
    });
    private boolean isPause = false;
    private long pastTime = 0L;
    private int rightCount = 0;
    private long startTime;
    Runnable timeRunnable = new Runnable() {
        public void run() {
            if (isPause) {
                handler.postDelayed(this, 1000L);
                return;
            }
            long l1 = (System.currentTimeMillis() - startTime) / 1000;
            long l2 = l1 / 60L / 60L;
            long l3 = l1 / 60L % 60L;
            long l4 = l1 % 60L;
            String str1 = String.valueOf(l2);
            String str2 = String.valueOf(l3);
            String str3 = String.valueOf(l4);
            if (l2 < 10L)
                str1 = "0" + str1;
            if (l3 < 10L)
                str2 = "0" + str2;
            if (l4 < 10L)
                str3 = "0" + str3;
            tvTime.setText(str1 + ":" + str2 + ":" + str3);
            handler.postDelayed(this, 1000L);
        }
    };
    Runnable timingRunnable = new Runnable() {
        public void run() {
            if (isPause) {
                handler.postDelayed(this, 1000L);
                return;
            }
            long l1 = totalTime - (System.currentTimeMillis() - startTime) / 1000;
            long l2 = l1 / 60L / 60L;
            long l3 = l1 / 60L % 60L;
            long l4 = l1 % 60L;
            String str1 = String.valueOf(l2);
            String str2 = String.valueOf(l3);
            String str3 = String.valueOf(l4);
            if (l2 < 10L)
                str1 = "0" + str1;
            if (l3 < 10L)
                str2 = "0" + str2;
            if (l4 < 10L)
                str3 = "0" + str3;
            if (l1 <= 0L) {
                tvTime.setText(str1 + ":" + str2 + ":" + str3);
                showTimeOverDialog();
                return;
            }
            tvTime.setText(str1 + ":" + str2 + ":" + str3);
            handler.postDelayed(this, 1000L);
        }
    };
    private int totalCount = 0;
    private long totalTime;
    private boolean isFirstError = true;

    private void clearEditText() {
        this.etBrushNum.setText("");
        this.etBrushNum.setBackgroundResource(R.drawable.rectangle_grey);
    }

    private void initData() {
        try {
            for (String str : getAssets().list("singleCharacter")){
                this.fileList.add(str);
            }
            initTime();
            resetCurrentTopic();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    private void initTime() {
        PracticeOption localPracticeOption = (PracticeOption) getIntent().getSerializableExtra("option");
        if (localPracticeOption.getPracticeMode() == 1) {
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timeRunnable, 1000L);
            this.tvTitle.setText("计时模式");
        } else {
            this.totalTime = (60 * localPracticeOption.getPracticeTime());
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timingRunnable, 1000L);
            this.tvTitle.setText("定时模式");
        }
    }

    private void initView() {
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                SingleCharacterPracticeActivity.this.showQuitDialog();
            }
        });
        this.llPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (SingleCharacterPracticeActivity.this.tvPause.getText().toString().equals("暂停")) {
                    SingleCharacterPracticeActivity.this.pausePractice();
                    return;
                }
                SingleCharacterPracticeActivity.this.resumePractice();
            }
        });
        this.llEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                SingleCharacterPracticeActivity.this.showQuitDialog();
            }
        });
        this.ivImage.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                SingleCharacterPracticeActivity.this.showBrushNumDialog(SingleCharacterPracticeActivity.this.currentTopic.getBrushNumber() + "");
                return false;
            }
        });
        this.llCheckResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(SingleCharacterPracticeActivity.this, ResultActivity.class);
                localIntent.putExtra("flag", GlobalConstant.SINGLE_CHARACTER_PRACTICE);
                SingleCharacterPracticeActivity.this.startActivity(localIntent);
            }
        });
        this.etBrushNum.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramEditable) {
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
                String input = paramCharSequence.toString();
                if(input.equals("")){
                    etBrushNum.setBackgroundResource(R.drawable.rectangle_grey);
                    return;
                }
                boolean isRight = true;
                for (int i = 0; i < input.length(); i++) {
                    if (input.charAt(i) != currentTopic.getBrushNumber().charAt(i)) {
                        etBrushNum.setBackgroundResource(R.drawable.rectangle_red);
                        isRight =false;
                        break;
                    }
                }
                if(input.length() >= currentTopic.getBrushNumber().length() ){
                    if(isRight){
                        rightCount++;
                        isFirstError = true;
                        resetCurrentTopic();
                    }
                    else{
                        if(isFirstError){
                            etBrushNum.setText("");
                            isFirstError=false;
                        }
                        else{
                            resetCurrentTopic();
                            isFirstError=true;
                        }
                    }
                    refreshData();
                    totalCount++;
                }
            }
        });
        this.etBrushNum.setFocusable(true);
        this.etBrushNum.setFocusableInTouchMode(true);
        this.etBrushNum.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(this.etBrushNum, 0);
    }

    private void pausePractice() {
        this.ivPause.setImageResource(R.drawable.begin);
        this.tvPause.setText("开始");
        this.isPause = true;
        this.etBrushNum.setEnabled(false);
    }

    private void refreshData() {
        this.tvRightCount.setText(this.rightCount + "");
        if (this.totalCount == 0)
            this.tvAccuracy.setText("0%");
        else{
            long usedTime = 1L + (System.currentTimeMillis() - this.startTime) / 1000L / 60L;
            long speed = this.rightCount / usedTime;
            this.tvSpeed.setText(speed + "字/分钟");
            this.tvAccuracy.setText(new DecimalFormat("######0.0").format(100.0D * (((float)this.rightCount) / this.totalCount)) + "%");
        }
    }

    private void refreshData(FillTopic paramFillTopic) {
        Uri localUri = Uri.parse("file:///android_asset/singleCharacter/" + paramFillTopic.getImage());
        Glide.with(this).load(localUri).into(this.ivImage);
    }

    private void resetCurrentTopic() {
        clearEditText();
        Random localRandom = new Random();
        String fileName = fileList.get(localRandom.nextInt(fileList.size()));
        String brushMum;
        if (fileName.contains("X")){
            brushMum = fileName.substring(0, fileName.lastIndexOf('X'));
        }
        else {
            brushMum = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        this.currentTopic = new FillTopic(brushMum, fileName);
        refreshData(this.currentTopic);
    }

    private void resumePractice() {
        this.ivPause.setImageResource(R.drawable.pause);
        this.tvPause.setText("暂停");
        this.isPause = false;
        this.etBrushNum.setEnabled(true);
        this.etBrushNum.setFocusable(true);
        this.etBrushNum.setFocusableInTouchMode(true);
        this.etBrushNum.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(this.etBrushNum, 0);
    }

    private void showBrushNumDialog(String paramString) {
        new Builder(this).setTitle("温馨提示").setMessage(paramString).setCancelable(false).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).create().show();
    }

    private void showQuitDialog() {
        pausePractice();
        new Builder(this).setMessage("确认退出吗？").setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                SingleCharacterPracticeActivity.this.resumePractice();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                SingleCharacterPracticeActivity.this.finish();
            }
        }).create().show();
    }

    private void showTimeOverDialog() {
        new Builder(this).setMessage("练习已经结束").setCancelable(false).setPositiveButton("确定并退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                SingleCharacterPracticeActivity.this.finish();
            }
        }).create().show();
    }

    public void onBackPressed() {
        showQuitDialog();
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_single_character_practice);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.totalCount <= 0)
            return;
        String str = PrefUtils.getString(this, GlobalConstant.SINGLE_CHARACTER_PRACTICE, "NULL");
        Gson gson = new Gson();
        Result localResult = new Result();
        localResult.setAccuracy(new DecimalFormat("######0.0").format(100.0D * (((float)this.rightCount) / this.totalCount)));
        localResult.setCorrectCount(this.rightCount);
        localResult.setPracticeDate(new Date(System.currentTimeMillis()));
        long l = 1L + (System.currentTimeMillis() - this.startTime) / 1000L / 60L;
        localResult.setSpeed((int) (this.rightCount / l));
        ArrayList<Result> results;
        if (str.equals("NULL")){
            results = new ArrayList<>();
        }
        else{
            Type type = new TypeToken<ArrayList<Result>>() {}.getType();
            results = gson.fromJson(str, type);
        }
        results.add(localResult);
        PrefUtils.setString(this, GlobalConstant.SINGLE_CHARACTER_PRACTICE, gson.toJson(results));
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.singleCharacter.SingleCharacterPracticeActivity
 * JD-Core Version:    0.6.0
 */