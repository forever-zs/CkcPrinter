package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.forever.ckcprinter.modle.bean.JudgeTopic;
import com.forever.ckcprinter.modle.bean.SingleTopic;
import com.forever.ckcprinter.utils.PrefUtils;
import com.forever.ckcprinter.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WriteBrushJudgeActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_collection)
    ImageView ivCollection;
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
    @Bind(R.id.btn_collect)
    Button btnCollect;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNum = -1;
    private Map<String, List<String>> fileMap = new HashMap();
    private List<JudgeTopic> topics = new ArrayList();

    private JudgeTopic createTopic() {
        clearSelectBtn();
        Random localRandom = new Random();

        boolean answer = localRandom.nextBoolean();
        JudgeTopic localJudgeTopic = null;
        if(answer){
            int i = localRandom.nextInt(10);
            String image = fileMap.get(i+"").get(localRandom.nextInt(fileMap.get(i+"").size()));
            localJudgeTopic = new JudgeTopic(image, i, true);
        }
        else{
            int i = localRandom.nextInt(10);
            String image = fileMap.get(((i+6)%10)+"").get(localRandom.nextInt(fileMap.get(((i+6)%10)+"").size()));
            localJudgeTopic = new JudgeTopic(image, i, false);

        }
        return localJudgeTopic;
    }

    private void initData() {
        for (int i = 0; i < 10; i++)
            this.fileMap.put("" + i, new ArrayList());
        try {
            for (String str : getAssets().list("brush")) {
                String index = str.substring(0, 1);
                this.fileMap.get(index).add(str);
            }
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        nextTopic();
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
                if ((topics.get(currentTopicNum)).isAnswer()) {
                    btnA.setBackgroundResource(R.drawable.circle_rectangle_green);
                }
                else{
                    btnA.setBackgroundResource(R.drawable.circle_rectangle_grey);
                }

            }
        });
        this.btnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if (!(topics.get(currentTopicNum)).isAnswer()) {
                    btnB.setBackgroundResource(R.drawable.circle_rectangle_green);
                }
                else{
                    btnB.setBackgroundResource(R.drawable.circle_rectangle_grey);
                }

            }
        });
        this.btnCollect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                collect();
            }
        });
        this.ivCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String str = PrefUtils.getString(WriteBrushJudgeActivity.this, "judgeOption", "null");
                Gson localGson = new Gson();
                if (str.equals("null")) {
                    showNoCollectionDialog();
                    return;
                }
                Type listType = new TypeToken<ArrayList<JudgeTopic>>(){}.getType();
                ArrayList<JudgeTopic> collectionLIst = localGson.fromJson(str, listType);
                if (collectionLIst.size() == 0) {
                    showNoCollectionDialog();
                    return;
                }
                Intent localIntent = new Intent(WriteBrushJudgeActivity.this, WriteBrushJudgeCollectionActivity.class);
                startActivity(localIntent);
            }
        });
    }

    private void nextTopic() {
        if (this.currentTopicNum >= 19) {
            new Builder(this).setTitle("温馨提示").setMessage("亲，热身结束了").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                }
            }).create().show();
            return;
        } else if(currentTopicNum < (topics.size()-1)){
            this.currentTopicNum = (1 + this.currentTopicNum);
            refreshData(topics.get(currentTopicNum));
        }
        else{
            this.currentTopicNum = (1 + this.currentTopicNum);
            JudgeTopic topic = createTopic();
            topics.add(topic);
            refreshData(topics.get(currentTopicNum));
        }
    }

    private void preTopic() {
        clearSelectBtn();
        if (this.currentTopicNum <= 0) {
            new Builder(this).setTitle("温馨提示").setMessage("您好，这是第一题，没有上一题了。").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                }
            }).create().show();
            return;
        }
        this.currentTopicNum = -1 + this.currentTopicNum;
        refreshData((JudgeTopic) topics.get(currentTopicNum));
    }

    private void refreshData(JudgeTopic paramJudgeTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNum + "");
        this.tvBrushNum.setText(paramJudgeTopic.getBrushNumber() + "");
        Uri localUri = Uri.parse("file:///android_asset/brush/" + paramJudgeTopic.getImage());
        Glide.with(this).load(localUri).asGif().into(this.ivB);
    }

    private void showNoCollectionDialog() {
        new Builder(this).setTitle("温馨提示").setMessage("您好，还没有收藏题目哦，快去做题吧").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).create().show();
    }

    private void showQuitDialog() {
        new Builder(this).setMessage("确认退出吗？").setCancelable(false).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
                finish();
            }
        }).create().show();
    }

    public void clearSelectBtn() {
        this.btnA.setBackgroundResource(R.drawable.circle_rectangle_oragne);
        this.btnB.setBackgroundResource(R.drawable.circle_rectangle_oragne);
    }

    public void collect() {
        String str = PrefUtils.getString(this, "judgeOption", "null");
        Gson gson = new Gson();
        if (str.equals("null")) {
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(this.topics.get(this.currentTopicNum));
            PrefUtils.setString(this, "judgeOption", gson.toJson(localArrayList));
        } else {
            Type type = new TypeToken<ArrayList<JudgeTopic>>() {}.getType();
            List<JudgeTopic> collectionTopics = gson.fromJson(str, type);
            for(JudgeTopic topic:collectionTopics){
                if (topics.get(currentTopicNum).equals((topic))){
                    ToastUtil.showText("该题已收藏，请不要重复收藏");
                    return;
                }
            }
            collectionTopics.add(topics.get(currentTopicNum));
            PrefUtils.setString(this, "judgeOption", gson.toJson(collectionTopics));
            ToastUtil.showText("收藏成功");
        }
    }

    public void onBackPressed() {
        showQuitDialog();
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_judge);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}
