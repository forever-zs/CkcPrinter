package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.FillTopic;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushFillCollectionActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_cancelCollection)
    ImageView ivCancelCollection;
    @Bind(R.id.iv_B)
    ImageView ivB;
    @Bind(R.id.tv_topicNum)
    TextView tvTopicNum;
    @Bind(R.id.et_brushNum)
    EditText etBrushNum;
    @Bind(R.id.btn_pre)
    Button btnPre;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNum = 0;
    private List<FillTopic> topics;

    private void initData() {
        String str = PrefUtils.getString(this, "fillOption", "null");
        Gson localGson = new Gson();
        if (str.equals("null")) {
            showNoCollectionDialog();
            return;
        }
        Type type = new TypeToken<ArrayList<FillTopic>>() {}.getType();
        this.topics = localGson.fromJson(str, type);
        if (this.topics.size() == 0) {
            showNoCollectionDialog();
            return;
        }
        refreshData((FillTopic) this.topics.get(0));
    }

    private void initView() {
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                showQuitDialog();
            }
        });
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                nextTopic();
            }
        });
        this.btnPre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                preTopic();
            }
        });
        this.ivCancelCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                cancelCollect();
            }
        });
        this.etBrushNum.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable paramEditable) {
            }

            public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
            }

            public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
                if (paramCharSequence.toString().equals(""))
                    return;
                String str = paramCharSequence.toString();
                if (((FillTopic) topics.get(currentTopicNum)).getBrushNumber().equals( str)) {
                    etBrushNum.setTextColor(getResources().getColor(R.color.green));
                    return;
                }
                etBrushNum.setTextColor(getResources().getColor(R.color.bitRed));
            }
        });
    }

    private void nextTopic() {
        if (1 + this.currentTopicNum >= this.topics.size()) {
            ToastUtil.showText("没有下一个了");
            return;
        }
        clearEditText();
        List localList = this.topics;
        int i = 1 + this.currentTopicNum;
        this.currentTopicNum = i;
        refreshData((FillTopic) localList.get(i));
    }

    private void preTopic() {
        clearEditText();
        if (this.currentTopicNum <= 0) {
            ToastUtil.showText("这是第一题，没有上一题了哦");
            return;
        }
        List localList = this.topics;
        int i = -1 + this.currentTopicNum;
        this.currentTopicNum = i;
        refreshData((FillTopic) localList.get(i));
    }

    private void refreshData(FillTopic paramFillTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNum + "");
        Uri localUri = Uri.parse("file:///android_asset/brush/" + paramFillTopic.getImage());
        Glide.with(this).load(localUri).asGif().into(this.ivB);
    }

    private void showCancelCollectionSuccessDialog() {
        new Builder(this).setTitle("温馨提示").setMessage("取消收藏成功").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).create().show();
    }

    private void showNoCollectionDialog() {
        new Builder(this).setTitle("温馨提示").setMessage("您好，还没有收藏题目哦，快去做题吧").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    private void showQuitDialog() {
        new Builder(this).setMessage("确认退出吗？").setCancelable(false).setPositiveButton("确定并退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    public void cancelCollect() {
        topics.remove(currentTopicNum);
        if (this.topics.size() == 0) {
            showNoCollectionDialog();
        } else {
            PrefUtils.setString(this, "fillOption", new Gson().toJson(this.topics));
            if (currentTopicNum == this.topics.size()) {
                preTopic();
            } else {
                refreshData(topics.get(this.currentTopicNum));
            }
            showCancelCollectionSuccessDialog();
        }
    }

    public void clearEditText() {
        this.etBrushNum.setText("");
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_fill_collection);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.writeBrush.WriteBrushFillCollectionActivity
 * JD-Core Version:    0.6.0
 */