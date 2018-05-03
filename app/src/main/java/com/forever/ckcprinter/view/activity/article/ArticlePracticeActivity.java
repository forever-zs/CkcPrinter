package com.forever.ckcprinter.view.activity.article;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.configure.GlobalConstant;
import com.forever.ckcprinter.modle.bean.PracticeOption;
import com.forever.ckcprinter.modle.bean.Result;
import com.forever.ckcprinter.utils.NoSpaceFilter;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.view.activity.ResultActivity;
import com.forever.ckcprinter.widget.NoCVEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ArticlePracticeActivity extends BaseActivity {
    private final String Tag = "TypeActivity";
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
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.et_text)
    NoCVEditText etText;
    private double blankCount = 0.0D;
    private Set<Integer> blankList;
    private SpannableStringBuilder builder;
    private int cursor = 0;
    private double errorCount = 0.0D;
    private Set<Integer> errorIndexList;

    private Handler handler = new Handler(new Callback() {
        public boolean handleMessage(Message paramMessage) {
            return false;
        }
    });
    private int inputCount = 0;
    private boolean isPause = false;

    private PracticeOption practiceOption;
    private double rightCount = 0.0D;
    private Set<Integer> rightIndexList;
    private long startTime;
    private String textData;
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
    private long totalTime;

    private String ToSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    private void hideErrorText(String paramString) {
        StringBuilder localStringBuilder = new StringBuilder(this.textData);
        Iterator localIterator = this.errorIndexList.iterator();
        while (localIterator.hasNext()) {
            int i = ((Integer) localIterator.next()).intValue();
            localStringBuilder.replace(i, i + 1, paramString.charAt(i) + "");
        }
        this.tvText.setText(localStringBuilder.toString());
    }

    private void initData() {
        this.errorIndexList = new HashSet();
        this.rightIndexList = new HashSet();
        this.blankList = new HashSet();
        loadData();
        this.etText.addTextChangedListener(new TypeTextWatcher());
        NoCVEditText localNoCVEditText = this.etText;
        InputFilter[] arrayOfInputFilter = new InputFilter[1];
        arrayOfInputFilter[0] = new NoSpaceFilter();
        localNoCVEditText.setFilters(arrayOfInputFilter);
    }

    private void initView() {
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                showQuitDialog();
            }
        });
        this.llPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (tvPause.getText().toString().equals("暂停")) {
                    pausePractice();
                }
                else{
                    resumePractice();
                }
            }
        });
        this.llEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                showQuitDialog();
            }
        });
        this.llCheckResult.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(ArticlePracticeActivity.this, ResultActivity.class);
                localIntent.putExtra("flag", GlobalConstant.ARTICLE_PRACTICE);
                startActivity(localIntent);
            }
        });
        this.practiceOption = ((PracticeOption) getIntent().getSerializableExtra("option"));
        if (this.practiceOption.getPracticeMode() == PracticeOption.TIME_MODE) {
            this.tvTitle.setText("计时模式");
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timeRunnable, 1000L);
        } else {
            this.tvTitle.setText("定时模式");
            this.totalTime = (60 * this.practiceOption.getPracticeTime());
            this.startTime = System.currentTimeMillis();
            this.handler.postDelayed(this.timingRunnable, 1000L);
        }
    }

    private void loadData() {
        try {
            InputStream localInputStream = getAssets().open("article/" + this.practiceOption.getArticleName() + ".xml");
            this.textData = new SAXReader().read(localInputStream).getRootElement().getText();
            for (int i = 0; i < 150; i++)
                this.textData += "*";
            this.textData = this.textData.replaceAll(" ", "");
            this.textData = this.textData.replaceAll("　", "");
            this.textData = this.textData.replaceAll("“", "");
            this.textData = this.textData.replaceAll("”", "");
            this.textData = this.textData.replaceAll("，", "");
            this.textData = this.textData.replaceAll("。", "");
            this.textData = this.textData.replaceAll("：", "");
            this.textData = this.textData.replaceAll("、", "");
            this.textData = this.textData.replaceAll("；", "");
            this.textData = this.textData.replaceAll("！", "");
            this.textData = this.textData.replaceAll("？", "");
            this.textData = this.textData.replaceAll("《", "");
            this.textData = this.textData.replaceAll("》", "");
            this.textData = this.textData.replaceAll("──", "");
            this.textData = this.textData.replaceAll("（", "");
            this.textData = this.textData.replaceAll("）", "");
            this.textData = ToSBC(this.textData);
            this.tvText.setText(this.textData);
            return;
        } catch (DocumentException localDocumentException) {
            localDocumentException.printStackTrace();
            return;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
    }

    private void pausePractice() {
        this.ivPause.setImageResource(R.drawable.pause);
        this.tvPause.setText("开始");
        this.isPause = true;
        this.etText.setEnabled(false);
    }

    private void refreshData() {
        this.inputCount = this.etText.getText().length();
        this.rightCount = this.rightIndexList.size();
        this.errorCount = this.errorIndexList.size();
        this.blankCount = this.blankList.size();
        if (this.inputCount != 0) {
            this.tvAccuracy.setText(new DecimalFormat("######0.00").format(100.0D * (this.rightCount / this.inputCount)) + "%");
        }
        this.tvRightCount.setText("" + this.rightCount);
        int i = (int) (60 * this.rightCount / ((System.currentTimeMillis()-startTime)/1000));
        this.tvSpeed.setText(i + "字/分钟");
    }

    private void resumePractice() {
        this.ivPause.setImageResource(R.drawable.begin);
        this.tvPause.setText("暂停");
        this.isPause = false;
        this.etText.setEnabled(true);
        this.etText.setFocusable(true);
        this.etText.setFocusableInTouchMode(true);
        this.etText.requestFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(this.etText, 0);
    }

    private void showErrorColor() {
        this.builder = new SpannableStringBuilder(ToSBC(this.etText.getText().toString()));
        Iterator localIterator1 = this.errorIndexList.iterator();
        while (localIterator1.hasNext()) {
            Integer localInteger2 = (Integer) localIterator1.next();
            this.builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.bitRed)), localInteger2.intValue(), 1 + localInteger2.intValue(), 33);
        }
        Iterator localIterator2 = this.rightIndexList.iterator();
        while (localIterator2.hasNext()) {
            Integer localInteger1 = (Integer) localIterator2.next();
            this.builder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), localInteger1.intValue(), 1 + localInteger1.intValue(), 33);
        }
        this.etText.setText(this.builder);
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

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_article_practice);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.inputCount <= 0)
            return;
        String str = PrefUtils.getString(this, GlobalConstant.ARTICLE_PRACTICE, "NULL");
        Gson gson = new Gson();
        Result localResult = new Result();
        localResult.setAccuracy(new DecimalFormat("######0.0").format(100.0D * (this.rightCount / this.inputCount)));
        localResult.setCorrectCount((int)this.rightCount);
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
        PrefUtils.setString(this, GlobalConstant.ARTICLE_PRACTICE, gson.toJson(results));
    }

    public void validate(String paramString) {
        if (this.textData != null) {
            this.errorIndexList.clear();
            this.rightIndexList.clear();
            this.blankList.clear();
            for (int i = 0; i < paramString.length(); i++) {
                if (paramString.charAt(i) == this.textData.charAt(i)) {
                    this.rightIndexList.add(Integer.valueOf(i));
                } else if (paramString.charAt(i) == ' ') {
                    this.blankList.add(Integer.valueOf(i));
                } else {
                    this.errorIndexList.add(Integer.valueOf(i));
                }
            }
            showErrorColor();
            hideErrorText(paramString);
        }
    }

    class TypeTextWatcher
            implements TextWatcher {
        TypeTextWatcher() {
        }

        public void afterTextChanged(Editable paramEditable) {
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
           cursor = etText.getSelectionStart();
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            Log.e("zs", ToSBC(paramCharSequence.toString()));
            etText.removeTextChangedListener(this);
            validate(ToSBC(paramCharSequence.toString()));
            if (paramInt3 + cursor == etText.getText().length())
                etText.setSelection(etText.getText().length());
            else{
                etText.setSelection(-1 + (paramInt3 + cursor));
            }
            etText.addTextChangedListener(this);
            refreshData();
        }
    }
}
