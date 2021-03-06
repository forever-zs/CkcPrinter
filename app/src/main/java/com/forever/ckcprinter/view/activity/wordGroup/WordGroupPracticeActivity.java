package com.forever.ckcprinter.view.activity.wordGroup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.configure.GlobalConstant;
import com.forever.ckcprinter.modle.bean.PracticeOption;
import com.forever.ckcprinter.modle.bean.Result;
import com.forever.ckcprinter.modle.bean.WordGroup;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.view.activity.ResultActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WordGroupPracticeActivity extends BaseActivity {
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
    @Bind(R.id.ll_wordGroup)
    LinearLayout llWordGroup;
    @Bind(R.id.et_brushNum)
    EditText etBrushNum;
    private WordGroup currentWordGroup;
    private int groupMode = 0;
    private Handler handler = new Handler(new Callback() {
        public boolean handleMessage(Message paramMessage) {
            return false;
        }
    });
    private boolean isPause = false;
    private long pastTime = 0L;
    private int rightCount = 0;
    private long startTime;
    private List<TextView> textViewList = new ArrayList();
    private boolean isFirstError = true;
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
    private List<WordGroup> wordGroups = new ArrayList();

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_word_group_practice);
        ButterKnife.bind(this);
        initData();
        initView();
        resetData();
    }

    private void clearEditText() {
        this.etBrushNum.setText("");
        this.etBrushNum.setBackgroundResource(R.drawable.rectangle_grey);
    }

    private void initData() {
        PracticeOption localPracticeOption = (PracticeOption) getIntent().getSerializableExtra("option");
        this.groupMode = localPracticeOption.getGroupMode();
        if (localPracticeOption.isWordGroup()) {
            loadWordGroupData(localPracticeOption.getArticleName());
        }
        else{
            loadSimpleChineseCharacterData(localPracticeOption.getArticleName());
        }
    }

    private void loadSimpleChineseCharacterData(String articleName) {
        try {
            InputStream localInputStream = getAssets().open("simpleChineseCharacter/" + articleName + ".xml");
            List<Element> elements = new SAXReader().read(localInputStream).getRootElement().elements("Table");
            for (Element element : elements) {
                String brushNum = element.element("BIANMA").getText();
                String word = element.element("WENZHI").getText();
                WordGroup wordGroup = new WordGroup(brushNum,word);
                wordGroups.add(wordGroup);
            }
            handler.postDelayed(timeRunnable, 1000L);
        } catch (IOException localIOException) {

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void loadWordGroupData(String fileName){

        try {
            InputStream localInputStream = getAssets().open("group/" + fileName + ".xml");
            List<Element> elements = new SAXReader().read(localInputStream).getRootElement().elements("Table");
            for (Element element : elements) {
                String brushNum = element.element("BIANMA").getText();
                String word = element.element("WENZHI").getText();
                WordGroup wordGroup = new WordGroup(brushNum,word);
                wordGroups.add(wordGroup);
            }
            handler.postDelayed(timeRunnable, 1000L);
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void initTime() {
        PracticeOption localPracticeOption = (PracticeOption) getIntent().getSerializableExtra("option");
        if (localPracticeOption.getPracticeMode() == PracticeOption.TIME_MODE) {
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timeRunnable, 1000L);
            this.tvTitle.setText("计时模式");
        }
        else{
            this.totalTime = (60 * localPracticeOption.getPracticeTime());
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timingRunnable, 1000L);
            this.tvTitle.setText("计时模式");
        }

    }

    private void initView() {
        TextView localTextView = new TextView(this);
        LayoutParams localLayoutParams = new LayoutParams(0, -1);
        localLayoutParams.weight = 1.0F;
        localLayoutParams.rightMargin = 2;
        localTextView.setLayoutParams(localLayoutParams);
        localTextView.setGravity(17);
        localTextView.setTextSize(30.0F);
        localTextView.setBackgroundResource(R.drawable.rectangle_grey);
        this.textViewList.add(localTextView);
        this.llWordGroup.addView(localTextView);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                showQuitDialog();
            }
        });
        this.llPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (tvPause.getText().toString().equals("暂停")) {
                    pausePractice();
                    return;
                }
                resumePractice();
            }
        });
        this.llEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                showQuitDialog();
            }
        });
        this.llWordGroup.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View paramView) {
                showBrushNumDialog(currentWordGroup.getBrushNum());
                return false;
            }
        });
        this.llCheckResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                PracticeOption localPracticeOption = (PracticeOption) getIntent().getSerializableExtra("option");
                if (localPracticeOption.isWordGroup()) {
                    Intent localIntent = new Intent(WordGroupPracticeActivity.this, ResultActivity.class);
                    localIntent.putExtra("flag", GlobalConstant.WORD_GROUP_PRACTICE);
                    startActivity(localIntent);
                }
                else{
                    Intent localIntent = new Intent(WordGroupPracticeActivity.this, ResultActivity.class);
                    localIntent.putExtra("flag", GlobalConstant.SIMPLE_CHINESE_PRACTICE);
                    startActivity(localIntent);
                }
            }
        });
        this.etBrushNum.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramEditable) {
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
                if (paramCharSequence.toString().equals("")) {
                    etBrushNum.setBackgroundResource(R.drawable.rectangle_grey);
                    return;
                }
                String input= paramCharSequence.toString();
                if (input.matches("^[0-9]*$")){
                    String brushNum = currentWordGroup.getBrushNum();
                    boolean isRight = true;
                    for(int i = 0;i < input.length();i++){
                        if (input.charAt(i) != brushNum.charAt(i)) {
                            etBrushNum.setBackgroundResource(R.drawable.rectangle_red);
                            isRight = false;
                            break;
                        }
                    }
                    if(input.length() >= brushNum.length()){
                        if(isRight){
                            rightCount++;
                            resetData();
                            isFirstError = true;
                        }
                        else{
                            if(isFirstError){
                                etBrushNum.setText("");
                                isFirstError = false;
                            }
                            else{
                                resetData();
                                isFirstError = true;
                            }
                        }
                        totalCount++;
                    }
                }
                else {
                    String word = currentWordGroup.getWord();
                    boolean isRight = true;
                    for(int i = 0;i < input.length();i++){
                        if (input.charAt(i) != word.charAt(i)) {
                            etBrushNum.setBackgroundResource(R.drawable.rectangle_red);
                            isRight = false;
                            break;
                        }
                    }
                    if(input.length() >= word.length()){
                        if(isRight){
                            rightCount++;
                            resetData();
                            isFirstError = true;
                        }
                        else{
                            if(isFirstError){
                                etBrushNum.setText("");
                                isFirstError = false;
                            }
                            else{
                                resetData();
                                isFirstError = true;
                            }
                        }
                        totalCount++;
                    }
                }
                refreshData();
            }
        });
        this.etBrushNum.setEnabled(true);
        this.etBrushNum.setFocusable(true);
        this.etBrushNum.setFocusableInTouchMode(true);
        this.etBrushNum.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(this.etBrushNum, 0);
        initTime();
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
            this.tvAccuracy.setText(new DecimalFormat("######0.0").format(100.0 * (((float)this.rightCount) / this.totalCount)) + "%");
        }
    }

    private void resetData() {
        clearEditText();
        Random localRandom = new Random();
        this.currentWordGroup =  this.wordGroups.get(localRandom.nextInt(this.wordGroups.size()));
        this.textViewList.get(0).setText(this.currentWordGroup.getWord());
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
                resumePractice();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    private void showTimeOverDialog() {
        new Builder(this).setMessage("练习已经结束").setCancelable(false).setPositiveButton("确定并退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    public void onBackPressed() {
        showQuitDialog();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.totalCount <= 0)
            return;
        PracticeOption localPracticeOption = (PracticeOption) getIntent().getSerializableExtra("option");
        String str = null;
        if (localPracticeOption.isWordGroup()) {
            str = PrefUtils.getString(this, GlobalConstant.WORD_GROUP_PRACTICE, "NULL");
        }
        else{
            str = PrefUtils.getString(this, GlobalConstant.SIMPLE_CHINESE_PRACTICE, "NULL");
        }

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
        if (localPracticeOption.isWordGroup()) {
            PrefUtils.setString(this, GlobalConstant.WORD_GROUP_PRACTICE, gson.toJson(results));
        }
        else{
            str = PrefUtils.getString(this, GlobalConstant.SIMPLE_CHINESE_PRACTICE, "NULL");
        }
    }
}
