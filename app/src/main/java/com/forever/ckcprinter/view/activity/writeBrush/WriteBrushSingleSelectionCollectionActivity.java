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
import com.forever.ckcprinter.modle.bean.SingleTopic;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushSingleSelectionCollectionActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_cancelCollection)
    ImageView ivCancelCollection;
    @Bind(R.id.tv_topicNum)
    TextView tvTopicNum;
    @Bind(R.id.tv_brushNum)
    TextView tvBrushNum;
    @Bind(R.id.iv_A)
    ImageView ivA;
    @Bind(R.id.btn_A)
    Button btnA;
    @Bind(R.id.iv_B)
    ImageView ivB;
    @Bind(R.id.btn_B)
    Button btnB;
    @Bind(R.id.iv_C)
    ImageView ivC;
    @Bind(R.id.btn_C)
    Button btnC;
    @Bind(R.id.iv_D)
    ImageView ivD;
    @Bind(R.id.btn_D)
    Button btnD;
    @Bind(R.id.btn_pre)
    Button btnPre;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNumCollection = 0;
    private List<SingleTopic> topicsCollection;

    private void initData() {
        String str = PrefUtils.getString(this, "singleOption", "null");
        Gson localGson = new Gson();
        if (str.equals("null")) {
            showNoCollectionDialog();
            return;
        }
        Type listType = new TypeToken<ArrayList<SingleTopic>>(){}.getType();
        topicsCollection = localGson.fromJson(str,listType);
        if (this.topicsCollection.size() == 0) {
            showNoCollectionDialog();
            return;
        }
        refreshData((SingleTopic) this.topicsCollection.get(0));
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
        this.btnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if (((SingleTopic) topicsCollection.get(currentTopicNumCollection)).getAnswer() == 0) {
                    btnA.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnA.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if (((SingleTopic) topicsCollection.get(currentTopicNumCollection)).getAnswer() == 1) {
                    btnB.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnB.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if (((SingleTopic) topicsCollection.get(currentTopicNumCollection)).getAnswer() == 2) {
                    btnC.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnC.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if (((SingleTopic) topicsCollection.get(currentTopicNumCollection)).getAnswer() == 3) {
                    btnD.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnD.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.ivCancelCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                cancelCollect();
            }
        });
    }

    private void nextTopic() {
        if (1 + this.currentTopicNumCollection >= this.topicsCollection.size()) {
            ToastUtil.showText("没有下一个了");
            return;
        }
        clearSelectBtn();
        List localList = this.topicsCollection;
        int i = 1 + this.currentTopicNumCollection;
        this.currentTopicNumCollection = i;
        refreshData((SingleTopic) localList.get(i));
    }

    private void preTopic() {
        clearSelectBtn();
        if (this.currentTopicNumCollection <= 0) {
            ToastUtil.showText("这是第一题，没有上一题了哦");
            return;
        }
        List localList = this.topicsCollection;
        int i = -1 + this.currentTopicNumCollection;
        this.currentTopicNumCollection = i;
        refreshData((SingleTopic) localList.get(i));
    }

    private void refreshData(SingleTopic paramSingleTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNumCollection + "");
        this.tvBrushNum.setText(paramSingleTopic.getBrushNumber() + "");
        Uri localUri1 = Uri.parse("file:///android_asset/brush/" + (String) paramSingleTopic.getImages().get(0));
        Uri localUri2 = Uri.parse("file:///android_asset/brush/" + (String) paramSingleTopic.getImages().get(1));
        Uri localUri3 = Uri.parse("file:///android_asset/brush/" + (String) paramSingleTopic.getImages().get(2));
        Uri localUri4 = Uri.parse("file:///android_asset/brush/" + (String) paramSingleTopic.getImages().get(3));
        Glide.with(this).load(localUri1).asGif().into(this.ivA);
        Glide.with(this).load(localUri2).asGif().into(this.ivB);
        Glide.with(this).load(localUri3).asGif().into(this.ivC);
        Glide.with(this).load(localUri4).asGif().into(this.ivD);
    }

    private void showCancelCollectionSuccessDialog() {
        new Builder(this).setTitle("温馨提示").setMessage("取消收藏成功").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).create().show();
    }

    private void showFirstCollectionDialog() {
        new Builder(this).setTitle("温馨提示").setMessage("您好，这是第一题，没有上一题了哦。").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
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
        topicsCollection.remove(this.currentTopicNumCollection);
        if (this.topicsCollection.size() == 0) {
            showNoCollectionDialog();
        } else {
            ToastUtil.showText("取消收藏成功");
            PrefUtils.setString(this, "singleOption", new Gson().toJson(this.topicsCollection));
            showCancelCollectionSuccessDialog();
            if (this.currentTopicNumCollection == this.topicsCollection.size()) {
                preTopic();
            } else {
                refreshData((SingleTopic) this.topicsCollection.get(this.currentTopicNumCollection));
            }
        }
    }

    public void clearSelectBtn() {
        this.btnA.setBackgroundResource(R.drawable.circle_rectangle_oragne);
        this.btnB.setBackgroundResource(R.drawable.circle_rectangle_oragne);
        this.btnC.setBackgroundResource(R.drawable.circle_rectangle_oragne);
        this.btnD.setBackgroundResource(R.drawable.circle_rectangle_oragne);
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_single_selection_collection);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.writeBrush.WriteBrushSingleSelectionCollectionActivity
 * JD-Core Version:    0.6.0
 */