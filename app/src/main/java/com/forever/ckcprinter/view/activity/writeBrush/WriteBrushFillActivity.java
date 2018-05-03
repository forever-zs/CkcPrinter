package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.DialogInterface;
import android.content.Intent;
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
import com.forever.ckcprinter.modle.bean.JudgeTopic;
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

public class WriteBrushFillActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_collection)
    ImageView ivCollection;
    @Bind(R.id.iv_B)
    ImageView ivB;
    @Bind(R.id.tv_topicNum)
    TextView tvTopicNum;
    @Bind(R.id.et_brushNum)
    EditText etBrushNum;
    @Bind(R.id.btn_pre)
    Button btnPre;
    @Bind(R.id.btn_collect)
    Button btnCollect;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNum = -1;
    private Map<String, List<String>> fileMap = new HashMap();
    private List<FillTopic> topics = new ArrayList();

    private FillTopic createTopic() {
        clearEditText();
        Random localRandom = new Random();
        int i = localRandom.nextInt(10);
        FillTopic localFillTopic = new FillTopic(i + "",(String) ((List) this.fileMap.get(i + "")).get(localRandom.nextInt(((List) this.fileMap.get(i + "")).size())));
        return localFillTopic;
    }

    private void initData() {
        for (int i = 0; i < 10; i++)
            this.fileMap.put("" + i, new ArrayList());
        try {
            for (String str1 : getAssets().list("brush")) {
                String str2 = str1.substring(0, 1);
                ((List) this.fileMap.get(str2)).add(str1);
            }
            nextTopic();
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
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
        this.btnCollect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                collect();
            }
        });
        this.ivCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String str = PrefUtils.getString(WriteBrushFillActivity.this, "fillOption", "null");
                Gson localGson = new Gson();
                if (str.equals("null")) {
                    showNoCollectionDialog();
                    return;
                }
                Type listType = new TypeToken<ArrayList<FillTopic>>(){}.getType();
                ArrayList<FillTopic> collectionList = localGson.fromJson(str, listType);
                if (collectionList.size() == 0) {
                    showNoCollectionDialog();
                    return;
                }
                Intent localIntent = new Intent(WriteBrushFillActivity.this, WriteBrushFillCollectionActivity.class);
                startActivity(localIntent);
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
                if (( topics.get(currentTopicNum)).getBrushNumber().equals(str) ) {
                    etBrushNum.setTextColor(getResources().getColor(R.color.green));
                }
                else{
                    etBrushNum.setTextColor(getResources().getColor(R.color.bitRed));
                }

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
        } else if(currentTopicNum < (topics.size()-1)){
            this.currentTopicNum = (1 + this.currentTopicNum);
            refreshData(topics.get(currentTopicNum));
        }
        else{
            this.currentTopicNum = (1 + this.currentTopicNum);
            FillTopic topic = createTopic();
            topics.add(topic);
            refreshData(topics.get(currentTopicNum));
        }

    }

    private void preTopic() {
        clearEditText();
        if (this.currentTopicNum <= 0) {
            new Builder(this).setTitle("温馨提示").setMessage("您好，这是第一题，没有上一题了。").setCancelable(false).setPositiveButton("好的", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                }
            }).create().show();
            return;
        }
        this.currentTopicNum = -1 + this.currentTopicNum;
        refreshData((FillTopic) topics.get(currentTopicNum));
    }

    private void refreshData(FillTopic paramFillTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNum + "");
        Uri localUri = Uri.parse("file:///android_asset/brush/" + paramFillTopic.getImage());
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

    public void clearEditText() {
        this.etBrushNum.setText("");
    }

    public void collect() {
        String str = PrefUtils.getString(this, "fillOption", "null");
        Gson gson = new Gson();
        if (str.equals("null")) {
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(this.topics.get(this.currentTopicNum));
            PrefUtils.setString(this, "fillOption", gson.toJson(localArrayList));
        } else {
            Type type = new TypeToken<ArrayList<FillTopic>>() {}.getType();
            List<FillTopic> collectionTopics = gson.fromJson(str, type);
            for(FillTopic topic:collectionTopics){
                if (topics.get(currentTopicNum).equals((topic))){
                    ToastUtil.showText("该题已收藏，请不要重复收藏");
                    return;
                }
            }
            collectionTopics.add(topics.get(currentTopicNum));
            PrefUtils.setString(this, "fillOption", gson.toJson(collectionTopics));
            ToastUtil.showText("收藏成功");
        }
    }

    public void onBackPressed() {
        showQuitDialog();
    }

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_fill);
        ButterKnife.bind(this);
        initView();
        initData();
    }
}

/* Location:           C:\Users\24305\Documents\Tencent Files\2430596243\FileRecv\MobileFile\com.forever.ckcprinter_classes_dex2jar.jar
 * Qualified Name:     com.forever.ckcprinter.view.activity.writeBrush.WriteBrushFillActivity
 * JD-Core Version:    0.6.0
 */