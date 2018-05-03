package com.forever.ckcprinter.view.activity.writeBrush;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
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

public class WriteBrushSingleSelectionActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_collection)
    ImageView ivCollection;
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
    @Bind(R.id.btn_collect)
    Button btnCollect;
    @Bind(R.id.btn_next)
    Button btnNext;
    private int currentTopicNum = -1;
    private Map<String, List<String>> fileMap = new HashMap();
    private List<SingleTopic> topics = new ArrayList();

    protected void onCreate(Bundle paramBundle) {
        supportRequestWindowFeature(1);
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_write_brush_single_selection);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private boolean contains(int paramInt, int[] paramArrayOfInt) {
        for (int i = 0; i < paramArrayOfInt.length; i++) {
            if (paramInt == paramArrayOfInt[i]) {
                return true;
            }
        }
        return false;
    }

    private SingleTopic createTopic() {
        Random random = new Random();
        ArrayList selectList = new ArrayList();
        int[] arrayOfInt = {-1, -1, -1, -1};
        for (int i = 0; i < 4; ) {
            int brushNum = random.nextInt(10);
            if (contains(brushNum, arrayOfInt)) {
                continue;
            } else {
                arrayOfInt[i] = brushNum;
                i++;
            }
        }
        int answer = random.nextInt(4);
        int rightBrushNum = arrayOfInt[answer];
        for (int i = 0; i < 4; i++) {
            selectList.add(fileMap.get(arrayOfInt[i] + "").get(random.nextInt(fileMap.get(arrayOfInt[i] + "").size())));
        }
        SingleTopic singleTopic = new SingleTopic(answer, selectList, rightBrushNum);
        return singleTopic;
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
                if ((topics.get(currentTopicNum)).getAnswer() == 0) {
                    btnA.setBackgroundResource(R.drawable.circle_rectangle_green);
                } else {
                    btnA.setBackgroundResource(R.drawable.circle_rectangle_grey);
                }
            }
        });
        this.btnB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if ((topics.get(currentTopicNum)).getAnswer() == 1) {
                    btnB.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnB.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if ((topics.get(currentTopicNum)).getAnswer() == 2) {
                    btnC.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnC.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                clearSelectBtn();
                if ((topics.get(currentTopicNum)).getAnswer() == 3) {
                    btnD.setBackgroundResource(R.drawable.circle_rectangle_green);
                    return;
                }
                btnD.setBackgroundResource(R.drawable.circle_rectangle_grey);
            }
        });
        this.btnCollect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                collect();
            }
        });
        this.ivCollection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                String str = PrefUtils.getString(WriteBrushSingleSelectionActivity.this, "singleOption", "null");
                Gson localGson = new Gson();
                if (str.equals("null")) {
                    showNoCollectionDialog();
                    return;
                }
                Type listType = new TypeToken<ArrayList<SingleTopic>>() {
                }.getType();
                ArrayList<SingleTopic> collectionLIst = localGson.fromJson(str, listType);
                if (collectionLIst.size() == 0) {
                    showNoCollectionDialog();
                    return;
                }
                Intent localIntent = new Intent(WriteBrushSingleSelectionActivity.this, WriteBrushSingleSelectionCollectionActivity.class);
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
        } else if (currentTopicNum < (topics.size() - 1)) {
            this.currentTopicNum = (1 + this.currentTopicNum);
            refreshData(topics.get(currentTopicNum));
        } else {
            clearSelectBtn();
            this.currentTopicNum = (1 + this.currentTopicNum);
            SingleTopic topic = createTopic();
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
        refreshData(topics.get(currentTopicNum));
    }

    private void refreshData(SingleTopic paramSingleTopic) {
        this.tvTopicNum.setText(1 + this.currentTopicNum + "");
        this.tvBrushNum.setText(paramSingleTopic.getBrushNumber() + "");
        Uri localUri1 = Uri.parse("file:///android_asset/brush/" + paramSingleTopic.getImages().get(0));
        Uri localUri2 = Uri.parse("file:///android_asset/brush/" + paramSingleTopic.getImages().get(1));
        Uri localUri3 = Uri.parse("file:///android_asset/brush/" + paramSingleTopic.getImages().get(2));
        Uri localUri4 = Uri.parse("file:///android_asset/brush/" + paramSingleTopic.getImages().get(3));
        Glide.with(this).load(localUri1).asGif().into(this.ivA);
        Glide.with(this).load(localUri2).asGif().into(this.ivB);
        Glide.with(this).load(localUri3).asGif().into(this.ivC);
        Glide.with(this).load(localUri4).asGif().into(this.ivD);
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
        this.btnC.setBackgroundResource(R.drawable.circle_rectangle_oragne);
        this.btnD.setBackgroundResource(R.drawable.circle_rectangle_oragne);
    }

    public void collect() {
        String str = PrefUtils.getString(this, "singleOption", "null");
        Gson gson = new Gson();
        if (str.equals("null")) {
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(this.topics.get(this.currentTopicNum));
            PrefUtils.setString(this, "singleOption", gson.toJson(localArrayList));
        } else {
            List<SingleTopic> collectionTopics = gson.fromJson(str, new TypeToken<ArrayList<SingleTopic>>() {
            }.getType());
            for (SingleTopic topic : collectionTopics) {
                if (topics.get(currentTopicNum).equals((topic))) {
                    ToastUtil.showText("该题已收藏，请不要重复收藏");
                }
            }
            ToastUtil.showText("收藏成功");
            collectionTopics.add(topics.get(currentTopicNum));
            PrefUtils.setString(this, "singleOption", gson.toJson(collectionTopics));
        }
    }

    public void onBackPressed() {
        showQuitDialog();
    }

    protected void onResume() {
        super.onResume();
        Log.e("currentTopicNum", this.currentTopicNum + "");
    }
}
