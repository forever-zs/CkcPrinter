package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forever.ckc.ckcprinter.R;
import com.forever.ckcprinter.app.BaseActivity;
import com.forever.ckcprinter.modle.bean.FillTopic;
import com.forever.ckcprinter.modle.bean.JudgeTopic;
import com.forever.ckcprinter.modle.bean.SingleTopic;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushJudgeCollectionActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_cancelCollection)
    ImageView ivCancelCollection;
    @Bind(R.id.iv_B)
    ImageView ivB;
    @Bind(R.id.tv_topicNum)
    TextView tvTopicNum;
    @Bind(R.id.tv_brushNum)
    TextView tvBrushNum;
    @Bind(R.id.btn_A)
    Button btnA;
    @Bind(R.id.btn_B)
    Button btnB;
    @Bind(R.id.btn_pre)
    Button btnPre;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNum = 0;
    private List<JudgeTopic> topics;

    private void initData() {
        String str = PrefUtils.getString(this, "judgeOption", "null");
        Gson localGson = new Gson();
        if (str.equals("null")) {
            showNoCollectionDialog();
            return;
        }
        Type type = new TypeToken<ArrayList<JudgeTopic>>() {}.getType();
        this.topics = localGson.fromJson(str, type);
        if (this.topics.size() == 0) {
            showNoCollectionDialog();
            return;
        }
        refreshData((JudgeTopic) this.topics.get(0));
    }

    private void initView() {
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.showQuitDialog();
            }
        });
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.nextTopic();
            }
        });
        this.btnPre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.preTopic();
            }
        });
        this.btnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.clearSelectBtn();
                if (((JudgeTopic) WriteBrushJudgeCollectionActivity.this.topics.get(WriteBrushJudgeCollectionActivity.this.currentTopicNum)).isAnswer()) {
                    WriteBrushJudgeCollectionActivity.this.btnA.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                WriteBrushJudgeCollectionActivity.this.btnA.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.clearSelectBtn();
                if (!((JudgeTopic) WriteBrushJudgeCollectionActivity.this.topics.get(WriteBrushJudgeCollectionActivity.this.currentTopicNum)).isAnswer()) {
                    WriteBrushJudgeCollectionActivity.this.btnB.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                WriteBrushJudgeCollectionActivity.this.btnB.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.ivCancelCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WriteBrushJudgeCollectionActivity.this.cancelCollect();
            }
        });
    }

    private void nextTopic() {
        if (1 + this.currentTopicNum >= this.topics.size()) {
            ToastUtil.showText("没有下一个了");
            return;
        }
        clearSelectBtn();
        List localList = this.topics;
        int i = 1 + this.currentTopicNum;
        this.currentTopicNum = i;
        refreshData((JudgeTopic) localList.get(i));
    }

    private void preTopic() {
        clearSelectBtn();
        if (this.currentTopicNum <= 0) {
            ToastUtil.showText("这是第一题，没有上一题了哦");
            return;
        }
        List localList = this.topics;
        int i = -1 + this.currentTopicNum;
        this.currentTopicNum = i;
        refreshData((JudgeTopic) localList.get(i));
    }

    private void refreshData(JudgeTopic paramJudgeTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNum + "");
        this.tvBrushNum.setText(paramJudgeTopic.getBrushNumber() + "");
        Uri localUri = Uri.parse("file:///android_asset/brush/" + paramJudgeTopic.getImage());
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
                WriteBrushJudgeCollectionActivity.this.finish();
            }
        }).create().show();
    }

    private void showQuitDialog() {
        new Builder(this).setMessage("确认退出吗？").setCancelable(false).setPositiveButton("确定并退出", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                WriteBrushJudgeCollectionActivity.this.finish();
            }
        }).create().show();
    }

    public void cancelCollect() {
        topics.remove(currentTopicNum);
        if (this.topics.size() == 0) {
            showNoCollectionDialog();
        } else {
            PrefUtils.setString(this, "judgeOption", new Gson().toJson(this.topics));
            if (currentTopicNum == this.topics.size()) {
                preTopic();
            } else {
                refreshData(topics.get(this.currentTopicNum));
            }
            showCancelCollectionSuccessDialog();
        }
    }

    public void clearSelectBtn() {
        this.btnA.setBackgroundResource(R.drawable.circle_rectangle_grey);
        this.btnB.setBackgroundResource(R.drawable.circle_rectangle_grey);
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_judge_collection);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}
